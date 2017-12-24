package me.wuwenbin.items.sso.dao.entity;

import lombok.*;
import me.wuwenbin.items.sso.dao.entity.base.DataEntity;
import me.wuwenbin.modules.sql.annotation.SQLColumn;
import me.wuwenbin.modules.sql.annotation.SQLTable;

import java.time.LocalDateTime;

/**
 * Created by wuwenbin on 2017/7/14.
 * 系统模块表
 */
@SQLTable("t_oauth_system_module")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemModule extends DataEntity<Long> {

    @SQLColumn(pk = true)
    private Long id;
    private String name;
    private String indexUrl;
    private String systemCode;


    /**
     * 生成root节点
     *
     * @return root
     */
    public static SystemModule root() {
        SystemModule root = SystemModule.builder().id(0L).name("").build();
        root.setOrderIndex(0);
        root.setCreateDate(LocalDateTime.now());
        root.setRemark("根节点");
        return root;
    }

}
