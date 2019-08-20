package com.cskaoyan.project.mall.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 任清阳
 * @Email 1277409109@qq.com
 * @date 2019/8/16 15:54
 */
public class StringToList {
    // 将 [abc1, def2] 这种格式的 string 转换成 list
    public static List<String> transfer(String str){
        // 判空
    if(str == null || str.trim().equals("")){
           return null; }

       str = str.substring(1, str.length() - 1);
        String[] arr = str.split(", ");
        List<String> listOutput = new ArrayList<>(Arrays.asList(arr));; // 数组转为 list
        return listOutput;
    }
}
