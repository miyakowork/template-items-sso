package me.wuwenbin.items.sso.dao.entity;

import lombok.*;
import me.wuwenbin.items.sso.dao.entity.base.DataEntity;
import me.wuwenbin.modules.sql.annotation.SQLColumn;
import me.wuwenbin.modules.sql.annotation.SQLTable;

import java.time.LocalDateTime;


/**
 *
 * @author Wuwenbin
 * @date 2017/7/12/012
 * 部门表
 */
@SQLTable("t_oauth_department")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Department extends DataEntity<Long> {

    @SQLColumn(pk = true)
    private Long id;
    private String name;
    private Long parentId;

    /**
     * 生成root节点
     *
     * @return root
     */
    public static Department root() {
        Department d = Department.builder().id(0L).name("部门根节点").parentId(-1L).build();
        d.setOrderIndex(0);
        d.setCreateDate(LocalDateTime.now());
        d.setRemark("根节点部门，无任何意义，作为树形部门的象征节点");
        return d;
    }

}
