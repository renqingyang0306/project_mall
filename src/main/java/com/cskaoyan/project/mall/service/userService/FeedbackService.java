package com.cskaoyan.project.mall.service.userService;

import com.cskaoyan.project.mall.domain.Feedback;
import com.cskaoyan.project.mall.domain.FeedbackExample;

import java.util.List;

public interface FeedbackService {
    List<Feedback> selectByExample(FeedbackExample example);

    List<Feedback> findAllFeedback(int page,int limit,Integer id,String username);
}
