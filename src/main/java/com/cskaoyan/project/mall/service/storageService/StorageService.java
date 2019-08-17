package com.cskaoyan.project.mall.service.storageService;

import com.cskaoyan.project.mall.domain.Storage;
import com.cskaoyan.project.mall.domain.StorageExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 任清阳
 * @Email 1277409109@qq.com
 * @date 2019/8/16 20:49
 */
public interface StorageService {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Storage record);
    Storage insertSelective( MultipartFile file);

    List<Storage> selectByExample(StorageExample example);

    Storage selectByPrimaryKey(Integer id);


    int updateByPrimaryKeySelective(Storage record);

    int updateByPrimaryKey(Storage record);

    List<Storage> findAllList(int page, int limit, String name, String key);
}
