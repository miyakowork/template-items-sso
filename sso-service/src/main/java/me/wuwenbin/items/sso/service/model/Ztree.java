package me.wuwenbin.items.sso.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * created by Wuwenbin on 2017/12/22 at 14:48
 *
 * @author wuwenbin
 */
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Ztree implements Serializable {
    private String id;
    private String pId;
    private String name;
    private String url;
    private String target;
    private String rel;
    private boolean open;
    private boolean isHidden;
    private boolean isParent;
    private String click;
    private boolean checked;
    private boolean halfCheck;
    private String selfId;
    private boolean nocheck;
    private String resourceId;
    private String iconOpen;
    private String iconClose;
    private String icon;
    private boolean noR;
    /**
     * 是否允许拖拽
     */
    private boolean drag;

    /**
     * 是否允许子节点移走
     */
    private boolean childOuter;
    /**
     * 是否允许成为父节点
     */
    private boolean dropInner;
    /**
     * 是否允许成为根节点
     */
    private boolean dropRoot;
    /**
     * 是否允许子节点排序/增加
     */
    private boolean childOrder;

    private Object other;

    public boolean getisParent() {
        return isParent;
    }

    public void setisParent(boolean parent) {
        isParent = parent;
    }

    public boolean getisHidden() {
        return isHidden;
    }

    public void setisHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }
}
