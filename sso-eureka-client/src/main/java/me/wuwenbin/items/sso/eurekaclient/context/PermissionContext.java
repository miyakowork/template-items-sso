package me.wuwenbin.items.sso.eurekaclient.context;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

/**
 * created by Wuwenbin on 2018/1/4 at 16:40
 */
@Setter
@Getter
@Builder
public class PermissionContext implements Serializable {
    private Set<String> roles;
    private Set<String> permissions;
}
