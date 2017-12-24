package me.wuwenbin.items.sso.dao.model.pageVo;


import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.items.sso.dao.entity.Department;
import me.wuwenbin.items.sso.dao.entity.Role;
import me.wuwenbin.items.sso.dao.entity.User;
import me.wuwenbin.modules.repository.annotation.sql.SQLPkRefer;

/**
 * Created by wuwenbin on 2017/7/19/019.
 * 设置通过连接查询查找到部门名字和角色名字
 */
@Setter
@Getter
public class UserVo extends User {

    @SQLPkRefer(targetClass = Department.class, targetColumn = "name", joinColumn = "dept_id")
    private String departmentName;
    @SQLPkRefer(targetClass = Role.class, targetColumn = "name", joinColumn = "default_role_id")
    private String roleName;

}
