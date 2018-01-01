package me.wuwenbin.items.sso.dao.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wuwenbin
 * @date 2017/7/19
 */
@Setter
@Getter
public class LoginSumBo {
    /**
     * 截止时间
     */
    private String endTime;
    /**
     * 查询周期时长
     */
    private Integer num;
    /**
     * 部门id
     */
    private Long deptId;

}
