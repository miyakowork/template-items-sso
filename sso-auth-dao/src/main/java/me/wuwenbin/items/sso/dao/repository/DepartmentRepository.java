package me.wuwenbin.items.sso.dao.repository;

import me.wuwenbin.items.sso.dao.entity.Department;
import me.wuwenbin.modules.repository.annotation.type.Repository;
import me.wuwenbin.modules.repository.api.open.IPageAndSortRepository;
import me.wuwenbin.modules.repository.provider.update.annotation.Modify;
import me.wuwenbin.modules.sql.constant.Router;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * created by Wuwenbin on 2017/12/22 at 14:40
 *
 * @author wuwenbin
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public interface DepartmentRepository extends IPageAndSortRepository<Department, Long> {

    /**
     * 根据可用状态查找部门集合
     *
     * @param isEnabled
     * @return
     */
    List<Department> findByEnabled(boolean isEnabled);

    /**
     * 统计父节点为id的记录数
     *
     * @param id
     * @return
     */
    int countByParentId(long id);

    /**
     * 查找父节点为id的部门集合
     *
     * @param id
     * @return
     */
    List<Department> findByParentId(long id);

    /**
     * 更新Department
     *
     * @return
     */
    @Modify(Router.DEFAULT)
    int updateById(Department department);

}
