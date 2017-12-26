package me.wuwenbin.items.sso.dao.entity;


import lombok.*;
import me.wuwenbin.items.sso.dao.entity.base.DataEntity;
import me.wuwenbin.items.sso.dao.repository.RoleRepository;
import me.wuwenbin.modules.repository.api.repository.RepositoryFactory;
import me.wuwenbin.modules.sql.annotation.SQLColumn;
import me.wuwenbin.modules.sql.annotation.SQLTable;

import java.time.LocalDateTime;

/**
 * 角色表
 *
 * @author wuwenbin
 * @date 2017/7/12
 */
@SQLTable("t_oauth_role")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role extends DataEntity<Long> {

    @SQLColumn(pk = true)
    private Long id;
    private String name;
    private String cnName;
    private Long parentId;
    private String systemCode;

    /**
     * 生成root节点
     *
     * @return root
     */
    public static Role root() {
        Role r = Role.builder().id(0L).cnName("上帝角色").parentId(-1L).build();
        r.setOrderIndex(-1);
        r.setCreateDate(LocalDateTime.now());
        r.setRemark("根节点");
        return r;
    }

    @Override
    public String nodeId() {
        return getId().toString();
    }

    @Override
    public String nodePId() {
        return getParentId().toString();
    }

    @Override
    public String nodeName() {
        return getCnName();
    }

    @Override
    public boolean nodeIsParent() {
        return RepositoryFactory.get(RoleRepository.class).countByParentIdAndEnabled(getParentId(), true) != 0;
    }

}
