package com.cskaoyan.project.mall.service.goods;

import com.cskaoyan.project.mall.controller.goods.vo.PageVO;
import com.cskaoyan.project.mall.controller.goods.vo.ResponseVO;
import com.cskaoyan.project.mall.domain.Comment;
import com.cskaoyan.project.mall.domain.CommentExample;
import com.cskaoyan.project.mall.domain.Goods;

import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @auther XXX
 * @date 2019/8/16
 * @time 17:39
 */
public interface CommentService {
    ResponseVO<PageVO<Comment>> queryAll(int page, int limit);

    int deleteById(Integer id);

    Comment selectByPrimaryKey(int commentId);

    int updateByPrimaryKey(Comment comment);

    ResponseVO<PageVO<Comment>> fuzzyQuery(int page, int limit, String userId, String valueId);

    List<Comment> selectByExample(CommentExample example);

    List<Comment> query(int page, int size, Byte type, Integer valueId,Integer showType);
    int insertSelective(Comment record);
}
