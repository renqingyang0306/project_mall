package com.cskaoyan.project.mall.utils;

import com.cskaoyan.project.mall.service.NumberTotalService;
import com.cskaoyan.project.mall.service.NumberTotalServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author 任清阳
 * @Email 1277409109@qq.com
 * @date 2019/8/16 9:50
 */
public class NumberTotal {


    private long goodsTotal;

    private long orderTotal;

    private long productTotal;

    private long userTotal;

    public NumberTotal(long goodsTotal, long orderTotal, long productTotal, long userTotal) {
        this.goodsTotal = goodsTotal;
        this.orderTotal = orderTotal;
        this.productTotal = productTotal;
        this.userTotal = userTotal;
    }

    public NumberTotal() {
    }

   /* public  NumberTotal getNumberTotal(){
        System.out.println( numberTotalService.goodsTotal());
        NumberTotal total=new NumberTotal(
                numberTotalService.goodsTotal(),numberTotalService.orderTotal(),numberTotalService.productTotal(),numberTotalService.userTotal());
        System.out.println(total);
        return  total;
    }
*/
    public long getGoodsTotal() {
        return goodsTotal;
    }

    public void setGoodsTotal(long goodsTotal) {
        this.goodsTotal = goodsTotal;
    }

    public long getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(long orderTotal) {
        this.orderTotal = orderTotal;
    }

    public long getProductTotal() {
        return productTotal;
    }

    public void setProductTotal(long productTotal) {
        this.productTotal = productTotal;
    }

    public long getUserTotal() {
        return userTotal;
    }

    public void setUserTotal(long userTotal) {
        this.userTotal = userTotal;
    }

    @Override
    public String toString() {
        return "NumberTotal{" +
                ", goodsTotal=" + goodsTotal +
                ", orderTotal=" + orderTotal +
                ", productTotal=" + productTotal +
                ", userTotal=" + userTotal +
                '}';
    }
}
