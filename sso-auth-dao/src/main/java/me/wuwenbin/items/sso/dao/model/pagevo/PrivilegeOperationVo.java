package me.wuwenbin.items.sso.dao.model.pagevo;


import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.items.sso.dao.entity.OperationPrivilegeType;
import me.wuwenbin.items.sso.dao.entity.PrivilegeOperation;
import me.wuwenbin.items.sso.dao.entity.PrivilegePage;
import me.wuwenbin.items.sso.dao.entity.Resource;
import me.wuwenbin.modules.repository.annotation.sql.SQLPkRefer;

/**
 * created by wuwenbin on 2017/8/22 at 15:52
 *
 * @author wuwenbin
 */
@Getter
@Setter
public class PrivilegeOperationVo extends PrivilegeOperation {

    @SQLPkRefer(targetClass = PrivilegePage.class, targetColumn = "name", joinColumn = "page_privilege_id")
    private String privilegePageName;
    @SQLPkRefer(targetClass = OperationPrivilegeType.class, targetColumn = "name", joinColumn = "operation_type_id")
    private String operationTypeName;
    @SQLPkRefer(targetClass = Resource.class, targetTableAlias = "tares1", targetColumn = "name", joinColumn = "resource_id")
    private String resourceName;
    @SQLPkRefer(targetClass = Resource.class, targetTableAlias = "tares2", targetColumn = "url", joinColumn = "resource_id")
    private String url;


}
