package me.wuwenbin.items.sso.dao.model.pageVo;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wuwenbin
 * @date 2017/8/19
 */
@Getter
@Setter
@Builder
public class LoginSumVo {

    /**
     * 每日访问人数数组
     */
    private String[] loginSum;
    /**
     * 每日访问人次数组
     */
    private int[] loginMount;
    /**
     * 日期数组
     */
    private String[] loginDate;

}
