package me.wuwenbin.items.sso.server.service;

import me.wuwenbin.items.sso.dao.entity.base.BaseEntity;
import me.wuwenbin.items.sso.server.model.Ztree;
import me.wuwenbin.items.sso.server.support.collector.ZtreeCollector;

import java.util.Collection;
import java.util.List;

/**
 * created by Wuwenbin on 2017/12/22 at 14:51
 * @author Wuwenbin
 */
public final class PublicServices {

    /**
     * 实体集合转化为ztree树显示的集合格式
     *
     * @param entities
     * @param <T>
     * @return
     */
    public static <T extends BaseEntity> List<Ztree> entity2Ztree(Collection<T> entities) {
        return entities.stream().collect(new ZtreeCollector<>());
    }

}
