package com.cskaoyan.project.mall.controller.goods;

import com.cskaoyan.project.mall.controller.goods.util.CommentReply;
import com.cskaoyan.project.mall.controller.goods.vo.CreatVO;
import com.cskaoyan.project.mall.controller.goods.vo.PageVO;
import com.cskaoyan.project.mall.controller.goods.vo.ResponseVO;
import com.cskaoyan.project.mall.domain.Comment;
import com.cskaoyan.project.mall.service.goods.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by IntelliJ IDEA
 *
 * @auther XXX
 * @date 2019/8/16
 * @time 17:34
 */
@Controller
@RequestMapping("admin")
@RestController
public class CommentController {
    @Autowired
    CommentService commentService;

    @RequestMapping("comment/list")
    public ResponseVO list(int page,int limit,String userId, String valueId, String sort, String order){

        if ((userId == null)&&(valueId ==null)){
            ResponseVO<PageVO<Comment>> responseVO = commentService.queryAll(page,limit);
            return responseVO;
        }else {
            if (userId == null){
                userId = "";
            }else if (valueId == null){
                valueId = "";
            }
            ResponseVO<PageVO<Comment>> responseVO = commentService.fuzzyQuery(page,limit,userId,valueId);
            return responseVO;
        }
    }
    /*评论的删除*/
    @RequestMapping("comment/delete")
    public CreatVO delete(@RequestBody Comment comment){
        CreatVO creatVO = new CreatVO();
        //根据评论的id删除评论
        Integer id = comment.getId();
        int i = commentService.deleteById(id);
        if (i != 0){
            creatVO.setErrno(0);
            creatVO.setErrmsg("成功");
        }else {
            creatVO.setErrno(401);
            creatVO.setErrmsg("失败");
        }
        return creatVO;
    }
    /*评论的回复*/
    @RequestMapping("order/reply")
    public CreatVO orderReply(@RequestBody CommentReply commentReply){
        //找到要回复的评论的id
        int commentId = commentReply.getCommentId();
        //根据id找到评论
        Comment comment = commentService.selectByPrimaryKey(commentId);
        //如果该评论的内容为空，执行更新评论的操作
        Date now = new Date();
        CreatVO creatVO = new CreatVO();
        comment.setUpdateTime(now);
        comment.setContent(commentReply.getContent());
        int i = commentService.updateByPrimaryKey(comment);
        if (i != 0){
            creatVO.setErrno(0);
            creatVO.setErrmsg("成功");
        }else {
            creatVO.setErrno(1);
            creatVO.setErrmsg("商品回复更新失败");
        }

        return creatVO;
    }

}
