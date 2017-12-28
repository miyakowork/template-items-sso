package me.wuwenbin.items.sso.service.support.collector;

import me.wuwenbin.items.sso.service.model.Ztree;

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
 * @since jdk1.8
 */
public class PagePrivilegeTreeCollector implements Collector<Map<String, Object>, List<Ztree>, List<Ztree>> {

    @Override
    public Supplier<List<Ztree>> supplier() {
        return ArrayList::new;
    }

    @Override
    public BiConsumer<List<Ztree>, Map<String, Object>> accumulator() {
        return (list, m) -> {
            Ztree pageTree = new Ztree();
            pageTree.setId(m.get("id").toString());
            pageTree.setName(m.get("name").toString());
            pageTree.setOther(m.get("url").toString());
            pageTree.setOpen(true);
            pageTree.setpId(m.get("resource_module_id").toString());
            pageTree.setResourceId(m.get("resource_id").toString());
            pageTree.setisParent(false);
            list.add(pageTree);
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
        return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH));
    }
}
