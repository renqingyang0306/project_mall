package com.cskaoyan.project.mall.domain;

/**
 * Created by IntelliJ IDEA
 *
 * @auther XXX
 * @date 2019/8/16
 * @time 20:06
 */
public class Item {
    int value;
    String label;

    public Item(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public Item() {
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "Item{" +
                "value=" + value +
                ", label='" + label + '\'' +
                '}';
    }
}
