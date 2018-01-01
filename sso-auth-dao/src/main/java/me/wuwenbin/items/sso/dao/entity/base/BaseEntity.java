package me.wuwenbin.items.sso.dao.entity.base;

import lombok.Getter;
import lombok.Setter;
import me.wuwenbin.modules.sql.annotation.MappedSuper;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.function.Supplier;

/**
 * created by Wuwenbin on 2017/12/22 at 9:58
 *
 * @author wuwenbin
 */
@Getter
@Setter
@MappedSuper
public abstract class BaseEntity<PK> implements Serializable {

    LocalDateTime createDate;
    PK createUser;
    LocalDateTime updateDate;
    PK updateUser;

    /**
     * 插入数据库之前干的事情
     */
    public abstract void preInsert();

    /**
     * 插入数据库之前干的事情
     */
    public abstract void preInsert(Supplier<PK> insertCreateAndUpdateUserId);

    /**
     * 更新数据之前干的事情
     */
    public abstract void preUpdate();

    /**
     * 更新数据之前干的事情
     */
    public abstract void preUpdate(Supplier<PK> insertUpdateUserId);

    /**
     * 普通list集合转ztree对象list集合的id中间件
     *
     * @return
     */
    public abstract String nodeId();

    /**
     * 普通list集合转ztree对象list集合的pId中间件
     *
     * @return
     */
    public abstract String nodePId();

    /**
     * 普通list集合转ztree对象list集合的name中间件
     *
     * @return
     */
    public abstract String nodeName();

    /**
     * 普通list集合转ztree对象list集合的open中间件
     *
     * @return
     */
    public abstract boolean nodeOpen();

    /**
     * 普通list集合转ztree对象list集合的isParent中间件
     *
     * @return
     */
    public abstract boolean nodeIsParent();

    /**
     * 普通list集合转ztree对象list集合的other中间件
     *
     * @return
     */
    public abstract Object nodeOther();

}
