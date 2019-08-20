package com.cskaoyan.project.mall.service.logService;

import com.cskaoyan.project.mall.domain.Log;
import com.cskaoyan.project.mall.domain.LogExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 任清阳
 * @Email 1277409109@qq.com
 * @date 2019/8/16 22:50
 */
public interface LogService {

    int insert(Log record);

    int insertSelective(Log record);

    List<Log> selectByExample(LogExample example);

    Log selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Log record);

    int updateByPrimaryKey(Log record);

    List<Log> findAllList(int page, int limit, String name);
}
