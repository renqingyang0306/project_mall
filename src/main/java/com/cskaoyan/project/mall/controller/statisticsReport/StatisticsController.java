package com.cskaoyan.project.mall.controller.statisticsReport;

import com.cskaoyan.project.mall.service.statistics.StatisticsService;
import com.cskaoyan.project.mall.utils.ResponseUtils;
import com.cskaoyan.project.mall.utils.statisticsMapBean.TableBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("admin/stat")
public class StatisticsController
{
    @Resource
    StatisticsService statisticsService;

    @RequestMapping("user")
    public ResponseUtils user()
    {
        TableBean<Map> tableBean = new TableBean();
        /*0: "day"
        1: "users"*/
        //添加成员变量columns：就是查询结果map中的key
        List columns = new ArrayList();
        columns.add("day");
        columns.add("users");
        tableBean.setColumns(columns);

        //添加成员变量rows: 其值就是查询结果，就是map中的value
        List<Map> rows = statisticsService.queryUser();
        tableBean.setRows(rows);
        //封装ResponseUtils对象：
        ResponseUtils<TableBean> responseUtils = new ResponseUtils<>(0,tableBean,"成功");
        /*responseUtils.setData(tableBean);
        responseUtils.setErrmsg("成功");
        responseUtils.setErrno(0);*/
        return responseUtils;
    }

    @RequestMapping("order")
    public ResponseUtils order()
    {
        //1. 添加TableBean:
        TableBean<Map> orderTableBean = new TableBean<>();
        //2. 准备columns:
        /*0: "day"
        1: "orders"
        2: "customers"
        3: "amount"
        4: "pcr"*/
        ArrayList<String> columns = new ArrayList<>();
        columns.add("day");
        columns.add("orders");
        columns.add("custormers");
        columns.add("amount");
        columns.add("pcr");
        orderTableBean.setColumns(columns);

        //准备rows:
        List<Map> rows = statisticsService.queryOrder();
        //添加rows
        orderTableBean.setRows(rows);

        //封装ResponseUtils对象：
        ResponseUtils<TableBean> responseUtils = new ResponseUtils<>(0,orderTableBean,"成功");

        return responseUtils;
    }
    @RequestMapping("goods")
    public ResponseUtils goods()
    {
        //1. 添加TableBean:
        TableBean<Map> orderTableBean = new TableBean<>();
        //2. 准备columns:
        /*0: "day"
        1: "orders"
        2: "products"
        3: "amount"*/
        ArrayList<String> columns = new ArrayList<>();
        columns.add("day");
        columns.add("orders");
        columns.add("products");
        columns.add("amount");
        orderTableBean.setColumns(columns);

        //准备rows:
        List<Map> rows = statisticsService.queryGoods();
        //添加rows
        orderTableBean.setRows(rows);

        //封装ResponseUtils对象：
        ResponseUtils<TableBean> responseUtils = new ResponseUtils<>(0,orderTableBean,"成功");

        return responseUtils;
    }



}
