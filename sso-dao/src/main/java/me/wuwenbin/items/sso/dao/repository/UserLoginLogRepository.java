package me.wuwenbin.items.sso.dao.repository;

import me.wuwenbin.items.sso.dao.entity.UserLoginLog;
import me.wuwenbin.modules.repository.annotation.field.SQL;
import me.wuwenbin.modules.repository.annotation.type.Repository;
import me.wuwenbin.modules.repository.api.open.IPageAndSortRepository;
import me.wuwenbin.modules.repository.constant.Parametric;
import me.wuwenbin.modules.repository.provider.save.annotation.SaveSQL;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * created by Wuwenbin on 2017/12/23 at 10:46
 *
 * @author wuwenbin
 */
@Repository
@Transactional(rollbackFor = Exception.class)
public interface UserLoginLogRepository extends IPageAndSortRepository<UserLoginLog, Long> {

    /**
     * 计算sum
     *
     * @param sumMap
     * @return
     */
    @SQL("SELECT count(0) AS cnt FROM t_oauth_user_login_log toull " +
            "WHERE toull.user_id IN (SELECT tou.id FROM t_oauth_user tou WHERE tou.dept_id IN (:deptIds)) " +
            "AND  date_format(toull.last_login_date,'%Y-%m-%d')=:dateStr")
    Map<String, Object> countSumByDeptIdsAndDate(Map<String, Object> sumMap);

    /**
     * 计算Mount
     *
     * @param mountMap
     * @return
     */
    @SQL("SELECT count(0) FROM t_oauth_user_login_log toull " +
            "WHERE toull.user_id IN (SELECT tou.id FROM t_oauth_user tou WHERE tou.dept_id IN (:deptIds)) " +
            "AND  date_format(toull.last_login_date,'%Y-%m-%d') = :dateStr GROUP BY  toull.user_id")
    int countMountByDeptIdsAndDate(Map<String, Object> mountMap);

    /**
     * 插入基本日志信息，用户id，用户最后登录ip，用户最后登录时间
     *
     * @param userId
     * @param lastLoginIp
     * @return
     */
    @SaveSQL(columns = {"user_id", "last_login_ip,last_login_date"}, type = Parametric.Doubt)
    int insertLogInfo(long userId, String lastLoginIp, LocalDateTime now);
}
