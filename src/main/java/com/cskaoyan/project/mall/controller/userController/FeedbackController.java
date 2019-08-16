package com.cskaoyan.project.mall.controller.userController;

import com.cskaoyan.project.mall.domain.Feedback;
import com.cskaoyan.project.mall.service.userService.FeedbackService;
import com.cskaoyan.project.mall.utils.PageBean;
import com.cskaoyan.project.mall.utils.ResponseUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("admin/feedback")
public class FeedbackController {
    @Autowired
    FeedbackService feedbackService;
    @RequestMapping("list")
    @ResponseBody
    public ResponseUtils<PageBean> list(int page,int limit,Integer id,String username){
        List<Feedback> allFeedback = feedbackService.findAllFeedback(page, limit, id, username);
        PageInfo<Feedback> pageInfo = new PageInfo<>(allFeedback);
        PageBean<Feedback> pageBean = new PageBean<>(allFeedback, pageInfo.getTotal());
        ResponseUtils<PageBean> responseUtils = new ResponseUtils<>(0, pageBean, "成功");
        return responseUtils;
    }
}
