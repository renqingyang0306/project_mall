package com.cskaoyan.project.mall.controllerwx;

import com.cskaoyan.project.mall.controller.goods.vo.PageVO;
import com.cskaoyan.project.mall.controller.goods.vo.ResponseVO;
import com.cskaoyan.project.mall.domain.Comment;
import com.cskaoyan.project.mall.domain.CommentExample;
import com.cskaoyan.project.mall.service.goods.CommentService;
import com.cskaoyan.project.mall.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("wx")
public class CommentController {
    @Autowired
    CommentService commentService;

    @RequestMapping("comment/list")
    public ResponseVO list(int page, int limit, String userId, String valueId, String sort, String order){

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

    @RequestMapping("comment/post")
    @ResponseBody
    public ResponseUtils<List> comment(){
        List<Comment> commentList = commentService.selectByExample(new CommentExample());
        ResponseUtils<List> responseUtils = new ResponseUtils<>(0, commentList, "成功");
        return responseUtils;

    }
}
