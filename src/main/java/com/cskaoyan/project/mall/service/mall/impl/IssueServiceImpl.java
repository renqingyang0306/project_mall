package com.cskaoyan.project.mall.service.mall.impl;

import com.cskaoyan.project.mall.domain.Issue;
import com.cskaoyan.project.mall.domain.IssueExample;
import com.cskaoyan.project.mall.mapper.IssueMapper;
import com.cskaoyan.project.mall.service.mall.IssueService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 申涛涛
 * @date 2019/8/18 18:38
 */
@Service
public class IssueServiceImpl implements IssueService {
    @Autowired
    IssueMapper issueMapper;

    @Override
    public List<Issue> queryPageIssues(int page, int limit, String question, String sort, String order) {
        PageHelper.startPage(page,limit);
        IssueExample issueExample = new IssueExample();
        IssueExample.Criteria criteria = issueExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        issueExample.setOrderByClause(sort + " " + order);
        if (question != null) {
            question = "%" + question + "%";
            criteria.andQuestionLike(question);
        }
        List<Issue> issues = issueMapper.selectByExample(issueExample);
        return issues;
    }

    @Override
    public Issue queryIssue(Integer id) {
        Issue issue = issueMapper.selectByPrimaryKey(id);
        return issue;
    }

    @Override
    public int insertIssue(Issue issue) {
        int insert = issueMapper.insert(issue);
        return insert;
    }

    @Override
    public int updateIssueById(Issue issue) {
        int update = issueMapper.updateByPrimaryKey(issue);
        return update;
    }

    @Override
    public int deleteRealIssueById(Integer id) {
        int delete = issueMapper.deleteByPrimaryKey(id);
        return delete;
    }

    @Override
    public int deleteLogicIssueByDeleted(Issue issue) {
        issue.setDeleted(true);
        int delete = issueMapper.updateByPrimaryKeySelective(issue);
        return delete;
    }
}
