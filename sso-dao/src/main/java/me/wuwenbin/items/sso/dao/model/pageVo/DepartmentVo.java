package me.wuwenbin.items.sso.dao.model.pageVo;


import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.items.sso.dao.entity.Department;
import me.wuwenbin.items.sso.dao.entity.User;
import me.wuwenbin.modules.repository.annotation.sql.SQLPkRefer;

/**
 * 部门管理页面的对象VO
 * Created by wuwenbin on 2017/7/16.
 */
@Setter
@Getter
public class DepartmentVo extends Department {

    @SQLPkRefer(targetClass = Department.class, targetColumn = "name", joinColumn = "parent_id")
    private String parentName;
    @SQLPkRefer(targetClass = User.class, targetColumn = "username", joinColumn = "create_user")
    private String createName;
    @SQLPkRefer(targetClass = User.class, targetColumn = "username", joinColumn = "update_user")
    private String updateName;

}
