package com.cskaoyan.project.mall.utils;

/**
 * @author 任清阳
 * @Email 1277409109@qq.com
 * @date 2019/8/16 14:54
 */
public class RoleBean {
    Integer value;
    String label;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public RoleBean(Integer value, String label) {
        this.value = value;
        this.label = label;
    }

    @Override
    public String toString() {
        return "RoleBean{" +
                "value=" + value +
                ", label='" + label + '\'' +
                '}';
    }
}
