package me.wuwenbin.items.sso.dao.repository;

import me.wuwenbin.items.sso.dao.entity.UserLoginLog;
import me.wuwenbin.modules.repository.annotation.field.Routers;
import me.wuwenbin.modules.repository.annotation.field.SQL;
import me.wuwenbin.modules.repository.annotation.type.Repository;
import me.wuwenbin.modules.repository.api.open.IPageAndSortRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * created by Wuwenbin on 2017/12/23 at 10:46
 *
 * @author wuwenbin
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public interface UserLoginLogRepository extends IPageAndSortRepository<UserLoginLog, Long> {

    /**
     * 计算sum
     *
     * @param sumMap
     * @return
     */
    @SQL("SELECT count(0) FROM t_oauth_user_login_log toull " +
            "WHERE toull.user_id IN (SELECT tou.id FROM t_oauth_user tou WHERE tou.dept_id IN (:deptIds)) " +
            "AND  date_format(toull.last_login_date,'%Y-%m-%d')=:dateStr")
    Map<String, Object> countSumByDeptIdsAndDate(Map<String, Object> sumMap);

    /**
     * 计算Mount
     *
     * @param mountMap
     * @return
     */
    @SQL("SELECT count(0) FROM t_oauth_user_login_log toull " +
            "WHERE toull.user_id IN (SELECT tou.id FROM t_oauth_user tou WHERE tou.dept_id IN (:deptIds)) " +
            "AND  date_format(toull.last_login_date,'%Y-%m-%d') = :dateStr GROUP BY  toull.user_id")
    int countMountByDeptIdsAndDate(Map<String, Object> mountMap);

}
