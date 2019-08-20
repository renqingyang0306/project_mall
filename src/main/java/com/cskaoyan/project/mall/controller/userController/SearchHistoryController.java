package com.cskaoyan.project.mall.controller.userController;

import com.cskaoyan.project.mall.domain.SearchHistory;
import com.cskaoyan.project.mall.service.userService.SearchHistoryService;
import com.cskaoyan.project.mall.utils.PageBean;
import com.cskaoyan.project.mall.utils.ResponseUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("admin/history")
public class SearchHistoryController {
    @Autowired
    SearchHistoryService searchHistoryService;
    @RequestMapping("list")
    @ResponseBody
    public ResponseUtils<PageBean> list(int page,int limit,Integer userId,String keyword){
        List<SearchHistory> allHistory = searchHistoryService.findAllHistory(page, limit, userId, keyword);
        PageInfo<SearchHistory> pageInfo = new PageInfo<>(allHistory);
        PageBean<SearchHistory> pageBean = new PageBean<>(allHistory, pageInfo.getTotal());
        ResponseUtils<PageBean> responseUtils = new ResponseUtils<>(0, pageBean, "成功");
        return responseUtils;
    }
}
