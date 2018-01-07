package me.wuwenbin.items.sso.dao.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 前台下拉搜索的对象
 *
 * @author wuwenbin
 * @date 2017/7/23
 */
@Setter
@Getter
@Builder
@ToString
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
