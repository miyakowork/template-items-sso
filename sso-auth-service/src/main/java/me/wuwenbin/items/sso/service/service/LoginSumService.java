package me.wuwenbin.items.sso.service.service;

import me.wuwenbin.items.sso.dao.model.LoginSumBo;
import me.wuwenbin.items.sso.dao.model.pagevo.LoginSumVo;

/**
 * created by Wuwenbin on 2017/12/22 at 20:26
 */
public interface LoginSumService {
    /**
     * 根据查询条件获取LoginSumVO数据
     *
     * @param loginSumBo
     * @return
     */
    LoginSumVo getData(LoginSumBo loginSumBo);

}
