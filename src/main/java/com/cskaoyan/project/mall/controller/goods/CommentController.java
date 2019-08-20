package com.cskaoyan.project.mall.controller.goods;

import com.cskaoyan.project.mall.controller.goods.vo.CreatVO;
import com.cskaoyan.project.mall.controller.goods.vo.PageVO;
import com.cskaoyan.project.mall.controller.goods.vo.ResponseVO;
import com.cskaoyan.project.mall.domain.Comment;
import com.cskaoyan.project.mall.domain.CommentReply;
import com.cskaoyan.project.mall.service.goods.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
public class CommentController {
    @Autowired
    CommentService commentService;

    @RequestMapping("comment/list")
    @ResponseBody
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
    @ResponseBody
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
    @ResponseBody
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

    /*//http://192.168.2.100:8081/admin/order/reply
    @RequestMapping("order/reply")
    @ResponseBody
    public OperationVO orderReply(@RequestBody CommentReply commentReply) {
        int commentId = commentReply.getCommentId();
        Comment comment = commentService.selectByPrimaryKey(commentId);
        OperationVO operationVO;
        //该评论的内容为空，执行更新评论的操作
        if ("".equals(comment.getContent())) {
            Date now = new Date();
            comment.setContent(commentReply.getContent());
            comment.setUpdateTime(now);
            int update = commentService.updateByPrimaryKey(comment);
            if (update == 1) {
                operationVO = new OperationVO(0, "成功");
            } else {
                operationVO = new OperationVO(1, "商品回复更新失败");
            }
        }
        //该评论的内容不为空返回响应的执行信息
        else {
            operationVO = new OperationVO(622,"订单商品已回复");
        }
        return operationVO;
*/
}
