package cn.catherine.token.bean;


import cn.catherine.token.constant.Constants;

import java.io.Serializable;

/**
 * @author catherine.brainwilliam
 * @since 2018/8/16
 *
 * 用於顯示首頁「Setting」頁面所需要展示的所有類別數據類
 */
public class SettingsBean implements Serializable {

    private String type;
    private Constants.SettingType tag;

    public SettingsBean(String type, Constants.SettingType tag) {
        super();
        this.tag = tag;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Constants.SettingType getTag() {
        return tag;
    }

    public void setTag(Constants.SettingType tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return "SettingsBean{" +
                "type='" + type + '\'' +
                ", tag=" + tag +
                '}';
    }
}
