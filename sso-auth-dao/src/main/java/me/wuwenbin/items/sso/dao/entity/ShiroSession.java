package me.wuwenbin.items.sso.dao.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.wuwenbin.items.sso.dao.constant.SQLRouters;
import me.wuwenbin.items.sso.dao.entity.base.DataEntity;
import me.wuwenbin.modules.sql.annotation.SQLColumn;
import me.wuwenbin.modules.sql.annotation.SQLTable;

import java.util.Date;

/**
 * session持久化对象
 *
 * @author wuwenbin
 * @date 2017/7/19
 */
@SQLTable("t_oauth_session")
@Setter
@Getter
@NoArgsConstructor
public class ShiroSession extends DataEntity<Long> {

    @SQLColumn(pk = true)
    private Long id;
    @SQLColumn(routers = SQLRouters.UPDATE_SESSION)
    private String username;
    private String sessionId;
    @SQLColumn(routers = SQLRouters.UPDATE_SESSION)
    private String sessionBase64;
    @SQLColumn(routers = SQLRouters.UPDATE_SESSION)
    private String ip;
    private Long sessionTimeout;
    private String createUrl;
    @SQLColumn(routers = SQLRouters.UPDATE_SESSION)
    private String updateUrl;
    private String userAgent;
    private Date firstVisitDate;
    @SQLColumn(routers = SQLRouters.UPDATE_SESSION)
    private Date lastVisitDate;


}
