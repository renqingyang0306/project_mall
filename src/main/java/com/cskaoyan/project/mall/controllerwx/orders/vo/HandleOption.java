package com.cskaoyan.project.mall.controllerwx.orders.vo;

/**
 * Created by IntelliJ IDEA
 *
 * @auther XXX
 * @date 2019/8/21
 * @time 19:25
 */
public class HandleOption {
    boolean cancel;
    boolean comment;
    boolean confirm;
    boolean delete;
    boolean pay;
    boolean rebuy;
    boolean refund;


    //
    public static void set101(HandleOption handleOption) {
        handleOption.setCancel(true);
        handleOption.setComment(false);
        handleOption.setConfirm(false);
        handleOption.setDelete(false);
        handleOption.setPay(true);
        handleOption.setRebuy(false);
        handleOption.setRefund(false);
    }
    public static void set102(HandleOption handleOption) {
        handleOption.setCancel(false);
        handleOption.setComment(false);
        handleOption.setConfirm(false);
        handleOption.setDelete(true);
        handleOption.setPay(false);
        handleOption.setRebuy(false);
        handleOption.setRefund(false);
    }
    public static void set103(HandleOption handleOption) {
        handleOption.setCancel(false);
        handleOption.setComment(false);
        handleOption.setConfirm(false);
        handleOption.setDelete(true);
        handleOption.setPay(false);
        handleOption.setRebuy(false);
        handleOption.setRefund(false);
    }
    public static void set201(HandleOption handleOption) {
        handleOption.setCancel(false);
        handleOption.setComment(false);
        handleOption.setConfirm(false);
        handleOption.setDelete(false);
        handleOption.setPay(false);
        handleOption.setRebuy(false);
        handleOption.setRefund(true);
    }
    public static void set202(HandleOption handleOption) {
        handleOption.setCancel(false);
        handleOption.setComment(false);
        handleOption.setConfirm(false);
        handleOption.setDelete(true);
        handleOption.setPay(false);
        handleOption.setRebuy(false);
        handleOption.setRefund(false);
    }
    public static void set301(HandleOption handleOption) {
        handleOption.setCancel(false);
        handleOption.setComment(false);
        handleOption.setConfirm(true);
        handleOption.setDelete(false);
        handleOption.setPay(false);
        handleOption.setRebuy(false);
        handleOption.setRefund(false);
    }
    public static void set401(HandleOption handleOption) {
        handleOption.setCancel(false);
        handleOption.setComment(true);
        handleOption.setConfirm(false);
        handleOption.setDelete(true);
        handleOption.setPay(false);
        handleOption.setRebuy(true);
        handleOption.setRefund(false);
    }
    public static void set402(HandleOption handleOption) {
        handleOption.setCancel(false);
        handleOption.setComment(true);
        handleOption.setConfirm(false);
        handleOption.setDelete(true);
        handleOption.setPay(false);
        handleOption.setRebuy(true);
        handleOption.setRefund(false);
    }

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

    public boolean isComment() {
        return comment;
    }

    public void setComment(boolean comment) {
        this.comment = comment;
    }

    public boolean isConfirm() {
        return confirm;
    }

    public void setConfirm(boolean confirm) {
        this.confirm = confirm;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public boolean isPay() {
        return pay;
    }

    public void setPay(boolean pay) {
        this.pay = pay;
    }

    public boolean isRebuy() {
        return rebuy;
    }

    public void setRebuy(boolean rebuy) {
        this.rebuy = rebuy;
    }

    public boolean isRefund() {
        return refund;
    }

    public void setRefund(boolean refund) {
        this.refund = refund;
    }
}
