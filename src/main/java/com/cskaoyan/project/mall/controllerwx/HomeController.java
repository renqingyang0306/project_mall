package com.cskaoyan.project.mall.controllerwx;

import com.cskaoyan.project.mall.domain.*;
import com.cskaoyan.project.mall.mapper.*;
import com.cskaoyan.project.mall.utils.ResponseUtils;
import com.cskaoyan.project.mall.vo.GoodsListBean;
import com.cskaoyan.project.mall.vo.GrouponListBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

//首页显示
@Controller
@RequestMapping("wx")
public class HomeController {
    @Autowired
    AdMapper adMapper;
    @Autowired
    BrandMapper brandMapper;
    @Autowired
    CategoryMapper categoryMapper;
    @Autowired
    GoodsMapper goodsMapper;
    @Autowired
    TopicMapper topicMapper;
    @Autowired
    CouponMapper couponMapper;
    @Autowired
    GrouponRulesMapper grouponRulesMapper;

    @RequestMapping("home/index")
    @ResponseBody
    public ResponseUtils<HashMap> home(){
        //顶部显示的三张滚动图片
        List<Ad> adList = adMapper.selectByExample(new AdExample());
        HashMap<String, List> hashMap = new HashMap<>();
        hashMap.put("banner",adList);

        //品牌制造商直供随机生成4张图
        List<Brand> brandList = brandMapper.selectByExample(new BrandExample());
        Random random = new Random();
        int i = random.nextInt(brandList.size() - 4);
        List<Brand> brandList1 = brandList.subList(i, i+4);
        hashMap.put("brandList",brandList1);

        //顶部滚动图片下面显示的9种分类
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.createCriteria().andPidEqualTo(0);
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);
        hashMap.put("channel",categoryList);

        //优惠卷   数据库coupon
        List<Coupon> couponList = couponMapper.selectByExample(new CouponExample());
        int j = random.nextInt(couponList.size() - 2);
        List<Coupon> couponList1 = couponList.subList(j, j+2);
        hashMap.put("couponList",couponList1);

        //随机选取四种分类显示在页面下方 数据库表category goods
        int g = random.nextInt(categoryList.size() - 4);
        List<Category> categoryList1 = categoryList.subList(g,g+4);
        CategoryExample categoryExample1 = new CategoryExample();
        GoodsExample goodsExample = new GoodsExample();
        //用于收集遍历出来的所有GoodsListBean集合
        List<GoodsListBean> goodsMergeList=new ArrayList<>();
        for (Category category : categoryList1) {
            GoodsListBean goodsListBean = new GoodsListBean();
            //把大标题的Category，id，name,赋值给goodsListBean对应的参数
            goodsListBean.setId(category.getId());
            goodsListBean.setName(category.getName());
            //通过category表的category.getId()，作为pid查到新的二级categoryList
            categoryExample1.createCriteria().andPidEqualTo(category.getId());
            List<Category> categories = categoryMapper.selectByExample(categoryExample1);
            //然后同通过新的categorylist.id遍历来查找goods表中的页面想要的goodslist
            for (Category category1 : categories) {
                goodsExample.createCriteria().andCategoryIdEqualTo(category1.getId());
                List<Goods> goodsList = goodsMapper.selectByExample(goodsExample);
                //查到的goodslist赋值给goodsListBean对应的参数
                goodsListBean.setGoodsList(goodsList);
            }
            goodsMergeList.add(goodsListBean);
        }
        hashMap.put("floorGoodsList",goodsMergeList);


        //团购专区  数据库goods groupon_rules
        //GrouponListBean用来封装团购信息
        //用于收集遍历出来的所有GrouponListBean集合
        List<GrouponListBean> grouponListBeans=new ArrayList<>();
        //获取团购信息表的全部数据
        List<GrouponRules> grouponRules = grouponRulesMapper.selectByExample(new GrouponRulesExample());

        for (GrouponRules grouponRule : grouponRules) {
            GrouponListBean grouponBean = new GrouponListBean();
            //团购人数
            grouponBean.setGroupon_member(grouponRule.getDiscountMember());
            GoodsExample goodsExample1 = new GoodsExample();
            goodsExample1.createCriteria().andIdEqualTo(grouponRule.getGoodsId());
            List<Goods> goods = goodsMapper.selectByExample(goodsExample1);
            Goods good=goods.get(0);
            //set折扣后价格
            grouponBean.setGroupon_price(good.getRetailPrice());
            //set团购商品
            grouponBean.setGoods(good);
            grouponListBeans.add(grouponBean);
        }

        hashMap.put("grouponList",grouponListBeans);

        //人气推荐  数据库goods
        List<Goods> goodsList = goodsMapper.selectByExample(new GoodsExample());
        int a = random.nextInt(goodsList.size() - 6);
        List<Goods> hotGoodsList1 = goodsList.subList(a, a+6);
        hashMap.put("hotGoodsList",hotGoodsList1);

        //周一周四新品首发 数据库goods
        int b = random.nextInt(goodsList.size() - 6);
        List<Goods> newGoodsList1 = goodsList.subList(b, b+6);
        hashMap.put("newGoodsList",newGoodsList1);

        //专题精选，随机选取四个  数据库topic
        List<Topic> topicList = topicMapper.selectByExample(new TopicExample());
        List<Topic> topicList1 = topicList.subList(0, 4);
        hashMap.put("topicList",topicList1);

        ResponseUtils<HashMap> responseUtils = new ResponseUtils<>(0, hashMap, "成功");
        return responseUtils;
    }
}
