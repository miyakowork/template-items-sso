package me.wuwenbin.items.sso.dao.entity.base;

import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.modules.sql.annotation.MappedSuper;
import me.wuwenbin.modules.sql.annotation.SQLColumn;

import java.time.LocalDateTime;
import java.util.function.Supplier;

import static me.wuwenbin.items.sso.dao.constant.SQLRouters.EDIT_MENU;
import static me.wuwenbin.items.sso.dao.constant.SQLRouters.EDIT_USER;

/**
 * created by Wuwenbin on 2017/12/22 at 9:58
 *
 * @author wuwenbin
 */
@MappedSuper
@Getter
@Setter
public class DataEntity<PK> extends BaseEntity<PK> {

    @SQLColumn(routers = EDIT_USER)
    protected Boolean enabled;
    @SQLColumn(routers = {EDIT_MENU, EDIT_USER})
    protected Integer orderIndex;
    @SQLColumn(routers = EDIT_USER)
    protected String remark;

    @Override
    public void preInsert(Supplier<PK> insertCreateAndUpdateUserId) {
        PK uId = insertCreateAndUpdateUserId.get();
        if (uId != null) {
            super.createUser = uId;
            super.updateUser = uId;
        }
        super.createDate = LocalDateTime.now();
        super.updateDate = LocalDateTime.now();
    }

    @Override
    public void preInsert() {
        super.createDate = LocalDateTime.now();
        super.updateDate = LocalDateTime.now();
    }

    @Override
    public void preUpdate(Supplier<PK> insertUpdateUserId) {
        PK uId = insertUpdateUserId.get();
        if (uId != null) {
            super.updateUser = uId;
        }
        super.updateDate = LocalDateTime.now();
    }

    @Override
    public void preUpdate() {
        super.updateDate = LocalDateTime.now();
    }

    @Override
    public String nodeId() {
        return null;
    }

    @Override
    public String nodePId() {
        return null;
    }

    @Override
    public String nodeName() {
        return null;
    }

    @Override
    public boolean nodeOpen() {
        return false;
    }

    @Override
    public boolean nodeIsParent() {
        return false;
    }

    @Override
    public Object nodeOther() {
        return null;
    }

}
