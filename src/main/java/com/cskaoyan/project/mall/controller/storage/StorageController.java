package com.cskaoyan.project.mall.controller.storage;

import com.cskaoyan.project.mall.domain.Storage;
import com.cskaoyan.project.mall.service.storageService.StorageService;
import com.cskaoyan.project.mall.utils.PageBean;
import com.cskaoyan.project.mall.utils.ResponseUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author 任清阳
 * @Email 1277409109@qq.com
 * @date 2019/8/16 20:47
 */
@RestController
@RequestMapping("admin/storage")
public class StorageController {
    @Autowired
    StorageService storageService;
    @Autowired
    ServletContext context;
    @RequestMapping("create")
    public ResponseUtils<Storage> insert(MultipartFile file){
        //获取本项目的资源路径
        String realPath = context.getRealPath("/static/pic/admin");

        //删除文件路径.
        File receive=new File(realPath,file.getOriginalFilename());
        if (!receive.getParentFile().exists()) {
            receive.getParentFile().mkdirs();
        }
        ResponseUtils<Storage> responseUtils=null;
        //io上传
        try {
            file.transferTo(receive);
            Storage storage = storageService.insertSelective(file);
            responseUtils=new ResponseUtils<>(0,storage,"成功");
        } catch (IOException e) {
            e.printStackTrace();
            responseUtils=new ResponseUtils<>(1,"失败");
        }

        return responseUtils;
    }
    @RequestMapping("list")
    public ResponseUtils<PageBean>  list(int page, int limit, String name,String key){
        List<Storage> allList = storageService.findAllList(page, limit,name,key);
        PageInfo<Storage> pageInfo=new PageInfo<>(allList);
        PageBean<Storage> pageBean=new PageBean<>(allList,pageInfo.getTotal());
        ResponseUtils<PageBean>  responseUtils=new ResponseUtils<>(0,pageBean,"成功");
        return responseUtils;
    }
}
