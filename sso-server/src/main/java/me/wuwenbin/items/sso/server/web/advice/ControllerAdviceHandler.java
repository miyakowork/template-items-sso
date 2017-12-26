package me.wuwenbin.items.sso.server.web.advice;

import me.wuwenbin.items.sso.server.web.BaseController;
import me.wuwenbin.items.sso.server.web.editor.DateEditor;
import me.wuwenbin.items.sso.server.web.editor.DoubleEditor;
import me.wuwenbin.items.sso.server.web.editor.IntegerEditor;
import me.wuwenbin.items.sso.server.web.editor.LongEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.Date;

/**
 * web->Controller数据绑定操作
 *
 * @author wuwenbin
 * @date 2017/5/20
 */
@ControllerAdvice
public class ControllerAdviceHandler extends BaseController {

    @InitBinder
    protected void initBinderByCustom(ServletRequestDataBinder binder) {
        binder.registerCustomEditor(int.class, new IntegerEditor());
        binder.registerCustomEditor(long.class, new LongEditor());
        binder.registerCustomEditor(double.class, new DoubleEditor());
        binder.registerCustomEditor(Date.class, new DateEditor());
    }


}
