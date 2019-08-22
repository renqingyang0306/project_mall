package com.cskaoyan.project.mall.controllerwx.storage;

import com.cskaoyan.project.mall.config.MyOssClient;
import com.cskaoyan.project.mall.domain.Storage;
import com.cskaoyan.project.mall.service.storageService.StorageService;
import com.cskaoyan.project.mall.utils.PageBean;
import com.cskaoyan.project.mall.utils.ResponseUtils;
import com.cskaoyan.project.mall.utils.UUIDUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.List;

/**
 * @author 任清阳
 * @Email 1277409109@qq.com
 * @date 2019/8/16 20:47
 */
@RestController
@RequestMapping("wx/storage")
public class WxStorageController {
    @Autowired
    StorageService storageService;
    @Autowired
    MyOssClient myOssClient;
    @PostMapping("/upload")
    public ResponseUtils<Storage> insert(MultipartFile file){
        //获取本项目的资源路径
        //String realPath = ClassUtils.getDefaultClassLoader().getResource("").getPath()+"static/pic/admin/";
        //拼接后缀
        String uuid=UUIDUtils.getId();
        ResponseUtils<Storage> responseUtils=null;
        //io上传
        try {
            myOssClient.ossFileUpload(file,uuid);
            Storage storage = storageService.insertSelective(file,uuid);
            responseUtils=new ResponseUtils<>(0,storage,"成功");
        } catch (IOException e) {
            e.printStackTrace();
            responseUtils=new ResponseUtils<>(1,"失败");
        }
        return responseUtils;
    }

}
