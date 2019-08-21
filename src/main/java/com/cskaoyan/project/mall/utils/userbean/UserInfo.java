package com.cskaoyan.project.mall.utils.userbean;

/**
 * @author 任清阳
 * @Email 1277409109@qq.com
 * @date 2019/8/21 11:59
 */
//用户页面封装的user对象
public class UserInfo {
    private String nickName;
    private String avatarUrl;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
