package com.cskaoyan.project.mall.service.userService;

import com.cskaoyan.project.mall.domain.SearchHistory;
import com.cskaoyan.project.mall.domain.SearchHistoryExample;
import com.cskaoyan.project.mall.mapper.SearchHistoryMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SearchHistoryServiceImpl implements SearchHistoryService {
    @Autowired
    SearchHistoryMapper searchHistoryMapper;
    @Override
    public List<SearchHistory> selectByExample(SearchHistoryExample example) {
        return searchHistoryMapper.selectByExample(example);
    }

    @Override
    public List<SearchHistory> findAllHistory(int page, int limit, Integer userId, String keyword) {
        PageHelper.startPage(page,limit);
        SearchHistoryExample example = new SearchHistoryExample();
        List<SearchHistory> searchHistories = null;
        if(userId == null && keyword == null){
            searchHistories = searchHistoryMapper.selectByExample(example);
        } else if(userId != null && keyword == null){
            example.createCriteria().andUserIdEqualTo(userId);
            searchHistories = searchHistoryMapper.selectByExample(example);
        } else if(userId == null && keyword != null){
            example.createCriteria().andKeywordLike("%" + keyword + "%");
            searchHistories = searchHistoryMapper.selectByExample(example);
        } else {
            example.createCriteria().andKeywordLike("%" + keyword + "%")
                                    .andUserIdEqualTo(userId);
            searchHistories = searchHistoryMapper.selectByExample(example);
        }
        return searchHistories;
    }

    @Override
    public List<SearchHistory> queryAllSearchHistoryByUserId(Integer userId) {
        SearchHistoryExample searchHistoryExample = new SearchHistoryExample();
        SearchHistoryExample.Criteria criteria = searchHistoryExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andUserIdEqualTo(userId);
        List<SearchHistory> searchHistories = searchHistoryMapper.selectByExample(searchHistoryExample);
        return searchHistories;
    }

    @Override
    public List<SearchHistory> queryAllSearchHistory() {
        SearchHistoryExample searchHistoryExample = new SearchHistoryExample();
        SearchHistoryExample.Criteria criteria = searchHistoryExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        List<SearchHistory> searchHistories = searchHistoryMapper.selectByExample(searchHistoryExample);
        return searchHistories;
    }
}
