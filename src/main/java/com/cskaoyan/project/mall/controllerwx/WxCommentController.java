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
public class WxCommentController {
    @Autowired
    CommentService commentService;

    @RequestMapping("comment/list")
    public ResponseVO list(int page, int size, String type, String valueId, String showType){

        if ((type == null)&&(valueId ==null)&&(showType == null)){
            ResponseVO<PageVO<Comment>> responseVO = commentService.queryAll(page,size);
            return responseVO;
        }else {
            if (type == null){
                type = "";
            }else if (valueId == null){
                valueId = "";
            }else if (showType == null){
                showType = "";
            }
            ResponseVO<PageVO<Comment>> responseVO = commentService.fuzzyQueryAll(page,size,type,valueId,showType);
            return responseVO;
        }
    }

    @RequestMapping("comment/post")
    @ResponseBody
    public ResponseUtils<List> comment(){
        List<Comment> commentList = commentService.selectByExample(new CommentExample());
        List<Comment> commentList1 = commentList.subList(0, 4);
        ResponseUtils<List> responseUtils = new ResponseUtils<>(0, commentList1, "成功");
        return responseUtils;

    }
}
