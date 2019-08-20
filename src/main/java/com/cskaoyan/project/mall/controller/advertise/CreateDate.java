package com.cskaoyan.project.mall.controller.advertise;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
  public class CreateDate{
    public static Date createDate(){
          Date date = new Date();
          SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
          String format = dateFormat.format(date);
          Date parse = null;
          try {
              parse = dateFormat.parse(format);
          } catch (ParseException e) {
              e.printStackTrace();
          }
          return parse;
      }
  }