package com.cskaoyan.project.mall.service.userService;

import com.cskaoyan.project.mall.domain.SearchHistory;
import com.cskaoyan.project.mall.domain.SearchHistoryExample;

import java.util.List;

public interface SearchHistoryService {
    List<SearchHistory> selectByExample(SearchHistoryExample example);

    List<SearchHistory> findAllHistory(int page,int limit,Integer userId,String keyword);

    List<SearchHistory> queryAllSearchHistoryByUserId(Integer userId);
    List<SearchHistory> queryAllSearchHistory();

    SearchHistory querySearchHistory(Integer id);

    int insertSearchHistory(SearchHistory searchHistory);
    int updateSearchHistory(SearchHistory searchHistory);
    int deleteRealSearchHistoryById(Integer id);
    int deleteLogicSearchHistoryByDeleted(SearchHistory searchHistory);

    List<SearchHistory> queryAllSearchHistoryByUserIdAndKeyword(Integer userId, String keyword);
}
