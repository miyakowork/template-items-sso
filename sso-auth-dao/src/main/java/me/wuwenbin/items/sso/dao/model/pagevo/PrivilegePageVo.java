package me.wuwenbin.items.sso.dao.model.pagevo;


import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.items.sso.dao.entity.PrivilegePage;
import me.wuwenbin.items.sso.dao.entity.Resource;
import me.wuwenbin.items.sso.dao.entity.ResourceModule;
import me.wuwenbin.modules.repository.annotation.sql.SQLPkRefer;

/**
 *
 * @author wuwenbin
 * @date 2017/7/19
 * 页面资源表VO
 */
@Setter
@Getter
public class PrivilegePageVo extends PrivilegePage {

    @SQLPkRefer(targetClass = ResourceModule.class, targetColumn = "name", joinColumn = "resource_module_id")
    private String resourceModuleName;
    @SQLPkRefer(targetClass = Resource.class, targetColumn = "name", joinColumn = "resource_id")
    private String resourceName;

}
