package com.cskaoyan.project.mall.domain;

/**
 * Created by IntelliJ IDEA
 *
 * @auther XXX
 * @date 2019/8/19
 * @time 16:20
 */
public class CommentReply {
    int commentId;
    String content;

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
