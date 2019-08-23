package com.cskaoyan.project.mall.service.mall;

import com.cskaoyan.project.mall.domain.Keyword;
import com.cskaoyan.project.mall.domain.KeywordExample;

import java.util.List;

/**
 * @author 申涛涛
 * @date 2019/8/18 19:40
 */
public interface KeywordService {
    List<Keyword> queryPageKeyword(int page, int limit, String keyword, String url, String sort, String order);
    Keyword queryKeyword(Integer id);

    List<Keyword> queryAllKeyword();
    List<Keyword> selectByExample(KeywordExample example);
    List<Keyword> queryKeywordByIsDefault(Boolean isFefault);
    List<Keyword> queryKeywordByIsHot(Boolean isHot);

    int insertKeyword(Keyword keyword);
    int updateKeyword(Keyword keyword);
    int deleteRealKeywordById(Integer id);
    int deleteLogicKeywordByDeleted(Keyword keyword);

    List<Keyword> searchKeywordByIsHotAndKeyword(Boolean isHot,String keyword);
}
