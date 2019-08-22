package com.cskaoyan.project.mall.service.mall.impl;

import com.cskaoyan.project.mall.domain.Keyword;
import com.cskaoyan.project.mall.domain.KeywordExample;
import com.cskaoyan.project.mall.mapper.KeywordMapper;
import com.cskaoyan.project.mall.service.mall.KeywordService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 申涛涛
 * @date 2019/8/18 19:40
 */
@Service
public class KeywordServiceImpl implements KeywordService {
    @Autowired
    KeywordMapper keywordMapper;

    @Override
    public List<Keyword> queryPageKeyword(int page, int limit, String keyword, String url, String sort, String order) {
        PageHelper.startPage(page,limit);
        KeywordExample keywordExample = new KeywordExample();
        keywordExample.setOrderByClause(sort + " " + order);
        KeywordExample.Criteria criteria = keywordExample.createCriteria();
        //只查询没有被逻辑删除的数据
        criteria.andDeletedEqualTo(false);
        if (keyword != null) {
            //添加模糊查询条件
            keyword = "%" + keyword + "%";
            criteria.andKeywordLike(keyword);
        }
        if (url != null) {
            //添加模糊查询条件
            url = "%" + url + "%";
            criteria.andUrlLike(url);
        }
        List<Keyword> keywords = keywordMapper.selectByExample(keywordExample);
        return keywords;
    }

    @Override
    public Keyword queryKeyword(Integer id) {
        Keyword keyword = keywordMapper.selectByPrimaryKey(id);
        return keyword;
    }

    @Override
    public List<Keyword> queryAllKeyword() {
        KeywordExample keywordExample = new KeywordExample();
        KeywordExample.Criteria criteria = keywordExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        List<Keyword> keywords = keywordMapper.selectByExample(keywordExample);
        return keywords;
    }

    @Override
    public List<Keyword> selectByExample(KeywordExample example) {
        return keywordMapper.selectByExample(example);
    }

    @Override
    public List<Keyword> queryKeywordByIsDefault(Boolean isFefault) {
        KeywordExample keywordExample = new KeywordExample();
        KeywordExample.Criteria criteria = keywordExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andIsDefaultEqualTo(isFefault);
        //按照修改时间降序排序
        keywordExample.setOrderByClause("update_time desc");
        List<Keyword> keywords = keywordMapper.selectByExample(keywordExample);
        return keywords;
    }

    @Override
    public List<Keyword> queryKeywordByIsHot(Boolean isHot) {
        KeywordExample keywordExample = new KeywordExample();
        KeywordExample.Criteria criteria = keywordExample.createCriteria();
        criteria.andDeletedEqualTo(false);
        criteria.andIsHotEqualTo(isHot);
        //按照修改时间降序排序
        keywordExample.setOrderByClause("update_time desc");
        List<Keyword> keywords = keywordMapper.selectByExample(keywordExample);
        return keywords;
    }

    @Override
    public int insertKeyword(Keyword keyword) {
        int insert = keywordMapper.insert(keyword);
        return insert;
    }

    @Override
    public int updateKeyword(Keyword keyword) {
        int update = keywordMapper.updateByPrimaryKey(keyword);
        return update;
    }

    @Override
    public int deleteRealKeywordById(Integer id) {
        int delete = keywordMapper.deleteByPrimaryKey(id);
        return delete;
    }

    @Override
    public int deleteLogicKeywordByDeleted(Keyword keyword) {
        keyword.setDeleted(true);
        int update = keywordMapper.updateByPrimaryKey(keyword);
        return update;
    }
}
