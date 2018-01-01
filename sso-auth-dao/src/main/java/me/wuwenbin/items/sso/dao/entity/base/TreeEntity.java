package me.wuwenbin.items.sso.dao.entity.base;

/**
 * created by Wuwenbin on 2017/12/25 at 18:12
 *
 * @author wuwenbin
 */
public abstract class TreeEntity<PK> extends BaseEntity<PK> {

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
        return true;
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
