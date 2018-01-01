package me.wuwenbin.items.sso.service.service.impl;

import me.wuwenbin.items.sso.dao.entity.Department;
import me.wuwenbin.items.sso.dao.model.LoginSumBo;
import me.wuwenbin.items.sso.dao.model.pagevo.LoginSumVo;
import me.wuwenbin.items.sso.dao.repository.DepartmentRepository;
import me.wuwenbin.items.sso.dao.repository.UserLoginLogRepository;
import me.wuwenbin.items.sso.service.service.LoginSumService;
import me.wuwenbin.modules.utils.util.Maps;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * created by Wuwenbin on 2017/12/22 at 21:44
 *
 * @author Wuwenbin
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class LoginSumServiceImpl implements LoginSumService {

    @Resource
    private DepartmentRepository departmentRepository;
    @Resource
    private UserLoginLogRepository userLoginLogRepository;


    @Override
    public LoginSumVo getData(LoginSumBo loginSumBo) {
        LocalDate endDate = LocalDate.parse(loginSumBo.getEndTime());
        List<String> date = Stream.iterate(0, item -> item + 1).limit(loginSumBo.getNum()).collect(timeSpanCollector(endDate));
        Collections.reverse(date);
        int num = loginSumBo.getNum();
        String[] sums = new String[num];
        String[] dates = new String[num];
        int[] mounts = new int[num];
        List<Long> deptIds = deptIdFormatter(loginSumBo.getDeptId());
        IntStream.rangeClosed(0, num - 1).boxed().forEach(i -> {
            Map<String, Object> paramMap = Maps.hashMap("deptIds", deptIds, "dateStr", date.get(i));
            sums[i] = userLoginLogRepository.countSumByDeptIdsAndDate(paramMap).get("cnt").toString();
            mounts[i] = userLoginLogRepository.countMountByDeptIdsAndDate(paramMap);
            dates[i] = date.get(i);
        });
        return LoginSumVo.builder().loginSum(sums).loginMount(mounts).loginDate(dates).build();
    }

    /**
     * timeSpan的收集器
     *
     * @param endDate
     * @return
     */
    private static Collector<Integer, List<String>, List<String>> timeSpanCollector(LocalDate endDate) {
        return new Collector<Integer, List<String>, List<String>>() {
            @Override
            public Supplier<List<String>> supplier() {
                return ArrayList::new;
            }

            @Override
            public BiConsumer<List<String>, Integer> accumulator() {
                return (list, i) -> {
                    LocalDate date = endDate.minusDays(i);
                    list.add(date.toString());
                };
            }

            @Override
            public BinaryOperator<List<String>> combiner() {
                return (listA, listB) -> {
                    listA.addAll(listB);
                    return listA;
                };
            }

            @Override
            public Function<List<String>, List<String>> finisher() {
                return Function.identity();
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));
            }
        };
    }

    /**
     * 根据departmentId查询下所所有子节点，包括子节点的子节点
     *
     * @param list
     * @param pId
     * @return
     */
    private List<Department> findDeptIdsByPIds(List<Department> list, Long pId) {
        List<Department> departments = departmentRepository.findByParentId(pId);
        list.addAll(departments);
        departments.forEach(d -> findDeptIdsByPIds(list, d.getId()));
        return list;
    }

    /**
     * 结合getDeptId方法将departmentId改为适用于数据库的IN的格式:1,2,3
     *
     * @param id
     * @return
     */
    private List<Long> deptIdFormatter(Long id) {
        List<Long> deptIds = Stream.of(id).collect(Collectors.toList());
        List<Department> list = new ArrayList<>();
        findDeptIdsByPIds(list, id).forEach(d -> deptIds.add(d.getId()));
        return deptIds;
    }


}
