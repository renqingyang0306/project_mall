package com.cskaoyan.project.mall.controllerwx.person;

import com.cskaoyan.project.mall.controller.goods.vo.ResponseVO;
import com.cskaoyan.project.mall.controllerwx.orders.vo.FootprintVO;
import com.cskaoyan.project.mall.domain.User;
import com.cskaoyan.project.mall.service.userService.FootprintService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA
 *
 * @auther XXX
 * @date 2019/8/21
 * @time 22:06
 */
@Controller
@RequestMapping("wx")
public class PersonControllerWx {

    @Autowired
    FootprintService footprintService;
    /*wx/collect/list?type=0&page=1&size=10*/
    /*
     * description: 展示个人的收藏
     * version: 1.0
     * date: 2019/8/21 22:09
     * author: du
     * @Param: [type, page, size]
     * @return: com.cskaoyan.project.mall.controller.goods.vo.ResponseVO
     */
    //我的收藏功能
    /*@RequestMapping("collect/list")
    @ResponseBody
    public ResponseVO showCollect(int type,int page,int size){




    }*/
    //获得访问足迹
    public ResponseVO getFootprintList(int page, int size){
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        Integer uid = user.getId();
        ResponseVO<FootprintVO> responseVO = footprintService.findFootprintByUid(page, size, uid);
        return responseVO;
    }


    /*@Autowired
    FootprintService footprintService;
    //http://192.168.2.100:8081/wx/footprint/list?page=1&size=10
    //足迹列表
    @RequestMapping("footprint/list")
    public ResponseVO getFootprintList(int page, int size, HttpServletRequest request) {
        //获得请求头
        String tokenKey = request.getHeader("X-Litemall-Token");
        Integer userId = UserTokenManager.getUserId(tokenKey);
        ResponseVO<FootprintVO> responseVO = footprintService.findFootprintByUid(page, size, userId);
        return responseVO;

    }*/
}
