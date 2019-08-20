package com.cskaoyan.project.mall.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.*;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes({String[].class})
public class StringToStringArrayHandler implements TypeHandler<String[]> {

    @Override
    public void setParameter(PreparedStatement preparedStatement, int index, String[] strings, JdbcType jdbcType) throws SQLException {
        ObjectMapper objectMapper = new ObjectMapper();
        String value = null;
        try {
            //将object转换成json字符串
            value = objectMapper.writeValueAsString(strings);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        //将object转换成的值 配置过去
        preparedStatement.setString(index,value);
    }

    @Override
    public String[] getResult(ResultSet resultSet, String s) throws SQLException {
        String content = resultSet.getString(s);
        return transfer(content);
    }



    @Override
    public String[] getResult(ResultSet resultSet, int i) throws SQLException {
        String content = resultSet.getString(i);
        return transfer(content);
    }

    @Override
    public String[] getResult(CallableStatement callableStatement, int i) throws SQLException {
        String content = callableStatement.getString(i);
        return transfer(content);
    }

    private String[] transfer(String content) {
        ObjectMapper objectMapper = new ObjectMapper();
        String[] strings = new String[]{};
        try {
            strings = objectMapper.readValue(content, String[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return strings;
    }
}
