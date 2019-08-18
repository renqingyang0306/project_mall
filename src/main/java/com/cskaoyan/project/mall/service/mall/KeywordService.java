package com.cskaoyan.project.mall.service.mall;

import com.cskaoyan.project.mall.domain.Keyword;

import java.util.List;

/**
 * @author 申涛涛
 * @date 2019/8/18 19:40
 */
public interface KeywordService {
    List<Keyword> queryPageKeyword(int page, int limit, String keyword, String url, String sort, String order);
    Keyword queryKeyword(Integer id);

    int insertKeyword(Keyword keyword);
    int updateKeyword(Keyword keyword);
    int deleteRealKeywordById(Integer id);
    int deleteLogicKeywordByDeleted(Keyword keyword);
}
