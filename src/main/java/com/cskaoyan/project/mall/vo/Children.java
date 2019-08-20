package com.cskaoyan.project.mall.vo;

import java.util.List;
import java.util.Map;

/**
 * @author 申涛涛
 * @date 2019/8/16 16:09
 */
public class Children {
    private Integer id;

    private String name;

    private Byte type;

    private Integer code;

    private List children;

    public Children(Integer id, String name, Byte type, Integer code) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.code = code;
    }

    public Children() {
    }

    public Children(Integer id, String name, Byte type, Integer code, List children) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.code = code;
        this.children = children;
    }

    public List getChildren() {
        return children;
    }

    public void setChildren(List children) {
        this.children = children;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
