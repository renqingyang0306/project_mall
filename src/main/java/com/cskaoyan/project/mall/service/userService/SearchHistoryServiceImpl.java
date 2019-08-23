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
        example.createCriteria().andDeletedEqualTo(false);
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

    @Override
    public SearchHistory querySearchHistory(Integer id) {
        SearchHistoryExample searchHistoryExample = new SearchHistoryExample();
        SearchHistoryExample.Criteria criteria = searchHistoryExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andIdEqualTo(id);
        List<SearchHistory> searchHistories = searchHistoryMapper.selectByExample(searchHistoryExample);
        if (searchHistories != null && searchHistories.size() > 0) {
            return searchHistories.get(0);
        }
        return null;
    }

    @Override
    public int insertSearchHistory(SearchHistory searchHistory) {
        return searchHistoryMapper.insert(searchHistory);
    }

    @Override
    public int updateSearchHistory(SearchHistory searchHistory) {
        return searchHistoryMapper.updateByPrimaryKey(searchHistory);
    }

    @Override
    public int deleteRealSearchHistoryById(Integer id) {
        return searchHistoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteLogicSearchHistoryByDeleted(SearchHistory searchHistory) {
        searchHistory.setDeleted(true);
        return searchHistoryMapper.updateByPrimaryKey(searchHistory);
    }

    @Override
    public List<SearchHistory> queryAllSearchHistoryByUserIdAndKeyword(Integer userId, String keyword) {
        SearchHistoryExample searchHistoryExample = new SearchHistoryExample();
        SearchHistoryExample.Criteria criteria = searchHistoryExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andUserIdEqualTo(userId);
        criteria.andKeywordEqualTo(keyword);
        List<SearchHistory> searchHistories = searchHistoryMapper.selectByExample(searchHistoryExample);
        return searchHistories;
    }
}
