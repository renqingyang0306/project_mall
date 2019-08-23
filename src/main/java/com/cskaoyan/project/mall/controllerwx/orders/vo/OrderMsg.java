package com.cskaoyan.project.mall.controllerwx.orders.vo;

/**
 * Created by IntelliJ IDEA
 *
 * @auther XXX
 * @date 2019/8/22
 * @time 15:57
 */
public class OrderMsg {
    /*addressId: 24
    cartId: 0
    couponId: 18
    grouponLinkId: 0
    grouponRulesId: 5
    message: ""*/
    int addressId;

    int cartId;

    int couponId;

    int grouponLinkId;

    int grouponRulesId;

    String message;

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public int getGrouponLinkId() {
        return grouponLinkId;
    }

    public void setGrouponLinkId(int grouponLinkId) {
        this.grouponLinkId = grouponLinkId;
    }

    public int getGrouponRulesId() {
        return grouponRulesId;
    }

    public void setGrouponRulesId(int grouponRulesId) {
        this.grouponRulesId = grouponRulesId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
