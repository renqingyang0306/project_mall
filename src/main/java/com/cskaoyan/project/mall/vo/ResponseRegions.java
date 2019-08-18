package com.cskaoyan.project.mall.vo;

import com.cskaoyan.project.mall.domain.Region;

import java.util.List;

/**
 * @author 申涛涛
 * @date 2019/8/16 16:06
 */
public class ResponseRegions {
    private Integer id;

    private String name;

    private Byte type;

    private Integer code;

    private List<Children> children;

    public List<Children> getChildren() {
        return children;
    }

    public void setChildren(List<Children> children) {
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
