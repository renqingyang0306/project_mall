package com.cskaoyan.project.mall.controller.goods;

import com.alibaba.fastjson.JSONObject;
import com.cskaoyan.project.mall.controller.goods.vo.CreatVO;
import com.cskaoyan.project.mall.controller.goods.vo.PageVO;
import com.cskaoyan.project.mall.controller.goods.vo.ResponseVO;
import com.cskaoyan.project.mall.domain.*;
import com.cskaoyan.project.mall.service.goods.*;
import com.cskaoyan.project.mall.service.mall.BrandService;
import com.cskaoyan.project.mall.service.mall.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA
 *
 * @auther XXX
 * @date 2019/8/16
 * @time 15:07
 */
@Controller
//@Api(tags = "商品管理模块的商品列表")
@RequestMapping("admin")
public class GoodsController {

    @Autowired
    GoodsService goodsService;
    @Autowired
    GoodsAttributeService goodsAttributeService;
    @Autowired
    GoodsProductService goodsProductService;
    @Autowired
    GoodsSpecificationService goodsSpecificationService;

    @Autowired
    CartAndBrandService cartAndBrandService;
    /*@Autowired
    CategoryService categoryService;
    @Autowired
    BrandService brandService;*/

    /*
     * description: list
     * version: 1.0
     * date: 2019/8/16 16:00
     * author: du
     * @Param: [page, limit, sort, desc]
     * @return: ResponseVO
     */
    /*goods/list?page=1&limit=20&goodsSn=31&*/
    @RequestMapping("goods/list")
    @ResponseBody
    public ResponseVO list(int page, int limit, String goodsSn,String name,String sort, String desc){
        //没有goodsSn或者name时的查询

        if ((goodsSn == null)&&(name == null)){
            ResponseVO<PageVO<Goods>> responseVO = goodsService.query(page,limit);
            return responseVO;
        }
        else {
            if (goodsSn == null){
                goodsSn = "";
            }else if (name == null){
                name = "";
            }
            ResponseVO<PageVO<Goods>> responseVO = goodsService.fuzzyQuery(page,limit,goodsSn,name);
            return responseVO;
        }

    }
    /*商品列表界面的添加功能*/
    /*
     * description: catAndBrand
     * version: 1.0
     * date: 2019/8/16 19:57
     * author: du
     * @Param: []
     * @return: com.cskaoyan.project.mall.controller.goods.vo.ResponseVO
     */
    /*----------------------查询---------------------*/
    //回显种类和品牌
    @RequestMapping("goods/catAndBrand")
    @ResponseBody
    public ResponseVO catAndBrand(){

        List<Item> brands = cartAndBrandService.queryBrandList();
        List<Categorylist> categories = cartAndBrandService.queryCartList();
        CartAndBrand cartAndBrand = new CartAndBrand(brands,categories);
        ResponseVO<CartAndBrand> responseVO = new ResponseVO<>(cartAndBrand, "成功", 0);
        return responseVO;
    }
    /*---------------------商品上架--------------------*/
    //新增功能
    @RequestMapping("goods/create")
    @ResponseBody
    public CreatVO create(@RequestBody JSONObject jsonObject){
        //得到前端传入的数据
        //jsonObject.put("gallery","[]");
        InsertGoods insertGoods = jsonObject.toJavaObject(InsertGoods.class);
        Goods goods = insertGoods.getGoods();
        List<GoodsAttribute> goodsAttributes = insertGoods.getGoodsAttributes();
        List<GoodsProduct> goodsProducts = insertGoods.getGoodsProducts();
        List<GoodsSpecification> goodsSpecifications = insertGoods.getGoodsSpecifications();
        //插入goods
        //插入更新和add的时间
        Date now = new Date();
        goods.setAddTime(now);
        goods.setUpdateTime(now);
        goods.setIsOnSale(true);
        goods.setIsNew(true);
        goods.setIsHot(false);
        int insert1 = goodsService.insert(goods);
        int goodsId = goods.getId();
        int insert2 = 0;
        int insert3 = 0;
        int insert4 = 0;
        //插入GoodsAttribute
        for (GoodsAttribute goodsAttribute : goodsAttributes) {
            goodsAttribute.setGoodsId(goodsId);
            goodsAttribute.setAddTime(now);
            goodsAttribute.setUpdateTime(now);
            insert2 = goodsAttributeService.insert(goodsAttribute);
        }
        //插入GoodsProduct
        for (GoodsProduct goodsProduct : goodsProducts) {
            goodsProduct.setAddTime(now);
            goodsProduct.setUpdateTime(now);
            goodsProduct.setGoodsId(goodsId);
            insert3 = goodsProductService.insert(goodsProduct);
        }
        //插入GoodsSpecification
        for (GoodsSpecification goodsSpecification : goodsSpecifications) {
            goodsSpecification.setGoodsId(goodsId);
            goodsSpecification.setAddTime(now);
            goodsSpecification.setUpdateTime(now);
            insert4 = goodsSpecificationService.insert(goodsSpecification);
        }
        //如果每个insert都不为0，代表插入成功了
        CreatVO creatVO = new CreatVO();
        if ((insert1 != 0) || (insert2 != 0) || (insert3 != 0) || (insert4 != 0)){
            creatVO.setErrmsg("成功");
            creatVO.setErrno(0);
        }else {
            creatVO.setErrmsg("失败");
            creatVO.setErrno(400);
        }
        return creatVO;
    }
    /*-------------------编辑--------------------*/
    //编辑的回显
    /*admin/goods/detail?id=1006002*/
    @RequestMapping("goods/detail")
    @ResponseBody
    public ResponseVO echoGood(int id){
        Goods goods = goodsService.queryById(id);
        //get对应的种类id
        int categoryId1 = goods.getCategoryId();
        //查出category表的pid
        int categoryId2 = cartAndBrandService.queryPidById(categoryId1);
        //把得到的小分类id和大分类id放到一个数组里
        int[] categoryIds = new int[]{categoryId1,categoryId2};
        //根据id查出对应的goodsAttribute信息
        List<GoodsAttribute> goodsAttributes = goodsAttributeService.queryByGoodsId(id);
        //根据id查出对应的goodsProduct
        List<GoodsProduct> goodsProducts = goodsProductService.queryByGoodsId(id);
        //根据id查出对应的goodsSpecification
        List<GoodsSpecification> goodsSpecifications = goodsSpecificationService.queryByGoodsId(id);
        GoodsDetail goodsDetail = new GoodsDetail(goodsAttributes,categoryIds,goods, goodsProducts, goodsSpecifications);
        ResponseVO<GoodsDetail> responseVO = new ResponseVO<>(goodsDetail, "成功", 0);
        return responseVO;
    }
    /*admin/goods/update*/
    //商品的编辑
    @RequestMapping("goods/update")
    @ResponseBody
    public CreatVO update(@RequestBody JSONObject jsonObject){
        InsertGoods insertGoods = jsonObject.toJavaObject(InsertGoods.class);
        Goods goods = insertGoods.getGoods();
        List<GoodsAttribute> goodsAttributes = insertGoods.getGoodsAttributes();
        List<GoodsProduct> goodsProducts = insertGoods.getGoodsProducts();
        List<GoodsSpecification> goodsSpecifications = insertGoods.getGoodsSpecifications();
        int update1 = goodsService.updateByPrimaryKey(goods);
        int update2 = 0;
        int update3 = 0;
        int update4 = 0;
        CreatVO creatVO = new CreatVO();
        for (GoodsProduct goodsProduct : goodsProducts) {
             update2 = goodsProductService.updateByPrimaryKey(goodsProduct);
        }
        for (GoodsAttribute goodsAttribute : goodsAttributes) {
             update3 = goodsAttributeService.updateByPrimaryKey(goodsAttribute);
        }
        for (GoodsSpecification goodsSpecification : goodsSpecifications) {
             update4 = goodsSpecificationService.updateByPrimaryKey(goodsSpecification);
        }
        if ((update1 != 0)||(update2 != 0)||(update3 != 0)||(update4 != 0)){
            creatVO.setErrno(0);
            creatVO.setErrmsg("成功");
        }else {
            creatVO.setErrno(401);
            creatVO.setErrmsg("失败");
        }

        return creatVO;
    }

    //商品的删除
    /*admin/goods/delete*/
    @RequestMapping("goods/delete")
    @ResponseBody
    public CreatVO delete(@RequestBody Goods goods){
        //得到goods的id，根据id删除
        CreatVO creatVO = new CreatVO();
        int id = goods.getId();
        int i = goodsService.deleteById(id);
        if (i != 0){
            //删除成功
            creatVO.setErrno(0);
            creatVO.setErrmsg("成功");
        }else {
            creatVO.setErrno(401);
            creatVO.setErrmsg("失败");
        }
        return creatVO;
    }

}
