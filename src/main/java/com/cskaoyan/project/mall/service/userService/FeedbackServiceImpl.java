package com.cskaoyan.project.mall.service.userService;

import com.cskaoyan.project.mall.domain.Feedback;
import com.cskaoyan.project.mall.domain.FeedbackExample;
import com.cskaoyan.project.mall.mapper.FeedbackMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    FeedbackMapper feedbackMapper;
    @Override
    public List<Feedback> selectByExample(FeedbackExample example) {
        return feedbackMapper.selectByExample(example);
    }

    @Override
    public List<Feedback> findAllFeedback(int page, int limit, Integer id, String username) {
        PageHelper.startPage(page,limit);
        FeedbackExample example = new FeedbackExample();
        List<Feedback> feedbacks = null;
        if(id == null && username == null){
            feedbacks = feedbackMapper.selectByExample(example);
        } else if(id != null && username == null){
            example.createCriteria().andUserIdEqualTo(id);
            feedbacks = feedbackMapper.selectByExample(example);
        } else if(id == null && username != null){
            example.createCriteria().andUsernameLike("%" + username + "%");
            feedbacks = feedbackMapper.selectByExample(example);
        } else {
            example.createCriteria().andUserIdEqualTo(id)
                                    .andUsernameLike("%" + username + "%");
            feedbacks = feedbackMapper.selectByExample(example);
        }
        return feedbacks;
    }
}
