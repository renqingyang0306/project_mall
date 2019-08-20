package com.cskaoyan.project.mall.controller.mall;

import com.cskaoyan.project.mall.domain.Issue;
import com.cskaoyan.project.mall.service.mall.IssueService;
import com.cskaoyan.project.mall.utils.PageBean;
import com.cskaoyan.project.mall.utils.ResponseUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * @author 申涛涛
 * @date 2019/8/18 18:31
 */
@Controller
public class IssueController {
    @Autowired
    IssueService issueService;

    @RequestMapping("/admin/issue/list")
    @ResponseBody
    public ResponseUtils queryPageIssues(int page,int limit,String question,String sort,String order) {
        List<Issue> issues = issueService.queryPageIssues(page, limit, question, sort, order);

        PageInfo pageInfo = new PageInfo(issues);
        long total = pageInfo.getTotal();
        PageBean pageBean = new PageBean(issues,total);
        ResponseUtils responseUtils = new ResponseUtils();
        if (issues == null) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("服务端错误！");
        } else {
            responseUtils.setErrno(0);
            responseUtils.setErrmsg("成功！");
            responseUtils.setData(pageBean);
        }
        return responseUtils;
    }

    @RequestMapping("/admin/issue/create")
    @ResponseBody
    public ResponseUtils insertIssues(@RequestBody Issue issue) {
        //insert 成功后issue就会变为 insert 后的issue
        Date date = new Date();
        issue.setAddTime(date);
        issue.setUpdateTime(date);
        issue.setDeleted(false);
        int insert = issueService.insertIssue(issue);
        ResponseUtils responseUtils = new ResponseUtils();
        if (insert == 0) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("服务端错误！");
        } else {
            responseUtils.setErrno(0);
            responseUtils.setErrmsg("成功！");
            responseUtils.setData(issue);
        }
        return responseUtils;
    }

    @RequestMapping("/admin/issue/update")
    @ResponseBody
    public ResponseUtils updateIssues(@RequestBody Issue issue) {
        issue.setUpdateTime(new Date());
        int update = issueService.updateIssueById(issue);
        //查询更新后的 issue
        issue = issueService.queryIssue(issue.getId());
        ResponseUtils responseUtils = new ResponseUtils();
        if (update == 0) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("服务端错误！");
        } else {
            responseUtils.setErrno(0);
            responseUtils.setErrmsg("成功！");
            responseUtils.setData(issue);
        }
        return responseUtils;
    }

    @RequestMapping("/admin/issue/delete")
    @ResponseBody
    public ResponseUtils deleteIssues(@RequestBody Issue issue) {
        int deleted = issueService.deleteLogicIssueByDeleted(issue);
        ResponseUtils responseUtils = new ResponseUtils();
        if (deleted == 0) {
            responseUtils.setErrno(401);
            responseUtils.setErrmsg("服务端错误！");
        } else {
            responseUtils.setErrno(0);
            responseUtils.setErrmsg("成功！");
            responseUtils.setData(null);
        }
        return responseUtils;
    }
}
