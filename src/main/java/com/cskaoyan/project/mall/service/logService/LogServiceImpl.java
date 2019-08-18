package com.cskaoyan.project.mall.service.logService;

import com.cskaoyan.project.mall.domain.Log;
import com.cskaoyan.project.mall.domain.LogExample;
import com.cskaoyan.project.mall.mapper.LogMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 任清阳
 * @Email 1277409109@qq.com
 * @date 2019/8/16 22:55
 */
@Service
public class LogServiceImpl implements LogService {
    @Autowired
    LogMapper logMapper;
    @Override
    public int insert(Log record) {
        return logMapper.insert(record);
    }

    @Override
    public int insertSelective(Log record) {
        return logMapper.insertSelective(record);
    }

    @Override
    public List<Log> selectByExample(LogExample example) {
        return logMapper.selectByExample(example);
    }

    @Override
    public Log selectByPrimaryKey(Integer id) {
        return logMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Log record) {
        return logMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Log record) {
        return logMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<Log> findAllList(int page, int limit, String username) {
        PageHelper.startPage(page,limit);
        LogExample logExample=new LogExample();

        List<Log> logs=null;
        //模糊查询
        if (username!=null && !("").equals(username)){
            logExample.createCriteria().andAdminLike("%"+username+"%");
            logs=logMapper.selectByExample(logExample);
        }else {
            //正常查询
            logs=logMapper.selectByExample(logExample);
        }
        return logs;
    }
}
