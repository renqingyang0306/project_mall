package com.cskaoyan.project.mall.utils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author 任清阳
 * @Email 1277409109@qq.com
 * @date 2019/8/19 23:05
 */
public class PermissionUtils {
    public static Set<String> transfer(Set<String> stringSet) {
        Set<String> hashSet=new HashSet<>();
        for(String temp:stringSet){
            if (!temp.equals("*")){
                String b = temp.replace(":", "/");
                String result="POST /"+b;
                hashSet.add(result);
            }else {
                hashSet.add(temp);
            }
        }
    return hashSet;
    }

}
