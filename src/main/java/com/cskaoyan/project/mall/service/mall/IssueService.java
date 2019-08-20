package com.cskaoyan.project.mall.service.mall;

import com.cskaoyan.project.mall.domain.Issue;
import com.cskaoyan.project.mall.domain.IssueExample;

import java.util.List;

/**
 * @author 申涛涛
 * @date 2019/8/18 18:36
 */
public interface IssueService {
    List<Issue> queryPageIssues(int page,int limit,String question,String sort,String order);
    List<Issue> queryAllIssue();

    List<Issue> selectByExample(IssueExample example);

    Issue queryIssue(Integer id);
    int insertIssue(Issue issue);
    int updateIssueById(Issue issue);
    int deleteRealIssueById(Integer id);
    int deleteLogicIssueByDeleted(Issue issue);
}
