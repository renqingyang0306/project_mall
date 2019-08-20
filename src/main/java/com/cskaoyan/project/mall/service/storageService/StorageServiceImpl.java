package com.cskaoyan.project.mall.service.storageService;

import com.cskaoyan.project.mall.domain.Storage;
import com.cskaoyan.project.mall.domain.StorageExample;
import com.cskaoyan.project.mall.mapper.StorageMapper;
import com.cskaoyan.project.mall.utils.UUIDUtils;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

/**
 * @author 任清阳
 * @Email 1277409109@qq.com
 * @date 2019/8/16 20:54
 */
@Service
public class StorageServiceImpl implements StorageService {
    @Autowired
    StorageMapper storageMapper;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return storageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(Storage record) {
        return storageMapper.insertSelective(record);
    }

    @Override
    public Storage insertSelective(MultipartFile file,String newname) {
        String filename = file.getOriginalFilename();
        Storage storage=new Storage();
        storage.setAddTime(new Date());
        storage.setUpdateTime(new Date());
        storage.setSize((int) file.getSize());
        //新名字
        storage.setKey(newname);
        //原始文件名
        storage.setName(file.getOriginalFilename());
        storage.setType(file.getContentType());

        storage.setUrl("https://projectmall.oss-cn-beijing.aliyuncs.com/"+newname);

        //插入数据
        int i = storageMapper.insertSelective(storage);
        if (i==1){
            return storage;
        }
        return null;
    }

    @Override
    public List<Storage> selectByExample(StorageExample example) {
        return storageMapper.selectByExample(example);
    }

    @Override
    public Storage selectByPrimaryKey(Integer id) {
        return storageMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Storage record) {
        return storageMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Storage record) {
        return storageMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<Storage> findAllList(int page, int limit, String username, String key) {
        PageHelper.startPage(page,limit);
        StorageExample storageExample=new StorageExample();

        List<Storage> listAdmin=null;
        //模糊查询
        if (username!=null && !("").equals(username) && key!=null && !("").equals(key)){
            //根据username，和 key查询
            storageExample.createCriteria().andNameLike("%"+username+"%");
            storageExample.createCriteria().andKeyLike("%"+key+"%");
            listAdmin=storageMapper.selectByExample(storageExample);
        }else if(username!=null && !("").equals(username)){
            //根据username查询
            storageExample.createCriteria().andNameLike("%"+username+"%");
            listAdmin=storageMapper.selectByExample(storageExample);
        }else if(key!=null&& !("").equals(key)){
            //根据key查询
            storageExample.createCriteria().andKeyLike("%"+key+"%");
            listAdmin=storageMapper.selectByExample(storageExample);
        }else {
            //正常查询
            listAdmin=storageMapper.selectByExample(storageExample);
        }
        return listAdmin;
    }
}
