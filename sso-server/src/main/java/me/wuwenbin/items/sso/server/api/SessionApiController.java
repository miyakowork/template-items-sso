package me.wuwenbin.items.sso.server.api;

import me.wuwenbin.items.sso.dao.entity.ShiroSession;
import me.wuwenbin.items.sso.dao.model.queryBo.SessionBo;
import me.wuwenbin.items.sso.server.web.BaseController;
import me.wuwenbin.items.sso.service.constant.ShiroConsts;
import me.wuwenbin.items.sso.service.service.ShiroSessionService;
import me.wuwenbin.items.sso.service.service.session.MySQLSessionDao;
import me.wuwenbin.modules.jpa.support.Page;
import me.wuwenbin.modules.pagination.model.bootstrap.BootstrapTable;
import me.wuwenbin.modules.scanner.annotation.ResourceScan;
import me.wuwenbin.modules.utils.http.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Wuwenbin
 * @date 2017/7/20
 */
@RestController
@RequestMapping("oauth2/session/api")
public class SessionApiController extends BaseController {

    @Autowired
    private ShiroSessionService shiroSessionService;
    @Autowired
    private MySQLSessionDao sessionDao;

    @RequestMapping("list")
    @RequiresPermissions("base:session:list")
    @ResourceScan("获取会话列表页面的数据")
    public BootstrapTable<ShiroSession> page(Page<ShiroSession> page, SessionBo sessionBO) {
        page = shiroSessionService.findPage(page, sessionBO);
        return bootstrapTable(page);
    }

    /**
     * 强制踢出用户
     *
     * @param sessionId session id
     * @return 处理信息
     */
    @RequestMapping("forcelogout")
    @RequiresPermissions("base:session:forcelogout")
    @ResourceScan("强制踢出用户")
    public R forcelogout(String sessionId) {
        String[] sessionIds = sessionId.split(",");
        try {
            for (String sId : sessionIds) {
                Session session = sessionDao.readSession(sId);
                if (session != null) {
                    session.setAttribute(ShiroConsts.SESSION_FORCE_LOGOUT_KEY, Boolean.TRUE);
                    sessionDao.delete(session);
                }
            }
            return R.ok("强制退出成功！");
        } catch (Exception e) {
            return R.error("强制退出发生异常，异常信息：" + e.getMessage());
        }
    }
}
