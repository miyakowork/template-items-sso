package me.wuwenbin.items.sso.dao.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 前台下拉搜索的对象
 * Created by wuwenbin on 2017/7/23.
 */
@Setter
@Getter
@Builder
public class SelectBO {

    private Object value;
    private Object text;

    public void setText(Object text) {
        this.text = text;
    }

    public static SelectBO create(String value, String text) {
        return SelectBO.builder().value(value).text(text).build();
    }

}
