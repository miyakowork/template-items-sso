package me.wuwenbin.items.sso.server.support.collector;

import me.wuwenbin.items.sso.dao.entity.base.BaseEntity;
import me.wuwenbin.items.sso.server.model.Ztree;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;


/**
 * 集合转化为ztree的list集合的收集器
 * created by Wuwenbin on 2017/12/22 at 14:59
 *
 * @author Wuwenbin
 * @param <T> 实体类类型
 * @since jdk1.8
 */
public class ZtreeCollector<T extends BaseEntity> implements Collector<T, List<Ztree>, List<Ztree>> {

    @Override
    public Supplier<List<Ztree>> supplier() {
        return ArrayList::new;
    }

    @Override
    public BiConsumer<List<Ztree>, T> accumulator() {
        return (list, t) -> {
            Ztree treeBO = new Ztree();
            treeBO.setId(t.nodeId());
            treeBO.setName(t.nodeName());
            treeBO.setPId(t.nodePId());
            treeBO.setisParent(t.nodeIsParent());
            treeBO.setOpen(t.nodeOpen());
            treeBO.setOther(t.nodeOther());
            list.add(treeBO);
        };
    }

    @Override
    public BinaryOperator<List<Ztree>> combiner() {
        return (listA, listB) -> {
            listA.addAll(listB);
            return listA;
        };
    }

    @Override
    public Function<List<Ztree>, List<Ztree>> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.IDENTITY_FINISH));
    }
}
