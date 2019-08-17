package com.cskaoyan.project.mall.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(int[].class)
public class String2ArrayHandler implements TypeHandler<int[]> {
    @Override
    public void setParameter(PreparedStatement preparedStatement, int index, int[] ints, JdbcType jdbcType) throws SQLException {
        //int[] ints1 = {1, 2, 3, 4};
        //String value = "[1,2,3,4]";

        ObjectMapper objectMapper = new ObjectMapper();
        String value = null;
        try {
            //将object转换成json字符串
            value = objectMapper.writeValueAsString(ints);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        //将object转换成的值 配置过去
        preparedStatement.setString(index,value);
    }

    @Override
    public int[] getResult(ResultSet resultSet, String s) throws SQLException {
        String content = resultSet.getString(s);
        //[1,2,3,4]
        return transfer(content);
    }

    @Override
    public int[] getResult(ResultSet resultSet, int index) throws SQLException {
        String content = resultSet.getString(index);
        return transfer(content);

    }

    @Override
    public int[] getResult(CallableStatement callableStatement, int i) throws SQLException {
        String content = callableStatement.getString(i);
        return transfer(content);

    }

    private int[] transfer(String content)  {
        ObjectMapper objectMapper = new ObjectMapper();
        int[] ints = new int[0];
        try {
            ints = objectMapper.readValue(content, int[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ints;
    }
}
