package com.cskaoyan.project.mall.vo;

import java.util.List;

public class PermVo {
    private String id;
    private String label;
    private String api;
    private  String pid;
    private List<PermVo> children;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getApi() {
        return api;
    }

    public List<PermVo> getChildren() {
        return children;
    }

    public void setChildren(List<PermVo> children) {
        this.children = children;
    }

}
