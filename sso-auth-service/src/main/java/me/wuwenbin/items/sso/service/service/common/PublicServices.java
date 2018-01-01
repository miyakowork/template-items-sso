package me.wuwenbin.items.sso.service.service.common;

import me.wuwenbin.items.sso.dao.entity.base.BaseEntity;
import me.wuwenbin.items.sso.service.model.Ztree;
import me.wuwenbin.items.sso.service.support.collector.ZtreeCollector;
import me.wuwenbin.items.sso.service.support.util.CacheUtils;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

/**
 * created by Wuwenbin on 2017/12/22 at 14:51
 *
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
    public static <T extends BaseEntity> List<Ztree> model2Ztree(Collection<T> entities) {
        return entities.stream().collect(new ZtreeCollector<>());
    }

    /**
     * 清除缓存
     *
     * @param cacheName
     * @param cacheKeySupplier
     */
    public static void clearCache(String cacheName, Supplier<String> cacheKeySupplier) {
        String cacheKey = cacheKeySupplier.get();
        CacheUtils.remove(cacheName, cacheKey);
    }
}
