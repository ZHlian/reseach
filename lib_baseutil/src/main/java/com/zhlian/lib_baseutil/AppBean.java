package com.zhlian.lib_baseutil;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 64553 on 2018-11-13.
 * Power By ChangnAutoMobile RCLink Team
 */


@Entity
public class AppBean {
    public long id;
    @Id
    public String pkgName;
    public String appName;
    public byte[]icon;
    public boolean isInDeleteMode;//是否处于编辑模式，用来修改item视图显示
    public boolean isInSelectMode;//是否在选择列表中，true 在选择列表中
    @Generated(hash = 578008882)
    public AppBean(long id, String pkgName, String appName, byte[] icon,
            boolean isInDeleteMode, boolean isInSelectMode) {
        this.id = id;
        this.pkgName = pkgName;
        this.appName = appName;
        this.icon = icon;
        this.isInDeleteMode = isInDeleteMode;
        this.isInSelectMode = isInSelectMode;
    }
    @Generated(hash = 285800313)
    public AppBean() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getPkgName() {
        return this.pkgName;
    }
    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }
    public String getAppName() {
        return this.appName;
    }
    public void setAppName(String appName) {
        this.appName = appName;
    }
    public byte[] getIcon() {
        return this.icon;
    }
    public void setIcon(byte[] icon) {
        this.icon = icon;
    }
    public boolean getIsInDeleteMode() {
        return this.isInDeleteMode;
    }
    public void setIsInDeleteMode(boolean isInDeleteMode) {
        this.isInDeleteMode = isInDeleteMode;
    }
    public boolean getIsInSelectMode() {
        return this.isInSelectMode;
    }
    public void setIsInSelectMode(boolean isInSelectMode) {
        this.isInSelectMode = isInSelectMode;
    }
}
