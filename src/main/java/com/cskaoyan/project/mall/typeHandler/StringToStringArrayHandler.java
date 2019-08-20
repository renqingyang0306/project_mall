package com.cskaoyan.project.mall.typeHandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StringToStringArrayHandler extends BaseTypeHandler<String[]> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, String[] strings, JdbcType jdbcType) throws SQLException {
        StringBuffer sb = new StringBuffer("[");
        String subString;
        for (String string : strings) {
            sb.append(string).append(",");
        }
        //传过来的字符数组为空
        if (sb.length() == 1) {
            subString = sb.toString() + "]";
        } else {
            subString = sb.substring(0, sb.length() - 1) + "]";

        }
        preparedStatement.setString(i, subString);
    }

    @Override
    public String[] getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String string = resultSet.getString(s);
        return stringToStringArray(string);    }

    @Override
    public String[] getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String string = resultSet.getString(i);
        return stringToStringArray(string);    }

    @Override
    public String[] getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String string = callableStatement.getString(i);
        return stringToStringArray(string);    }

    public String[] stringToStringArray(String column) {
        //查询到的url为空
        /*if (column.length() == 1) {
            return new String[]{};
        }*/
        String s = column.substring(1, column.length() -1);
        String[] split = s.split(",");
        for (int i = 0; i < split.length; i++) {
            split[i] = split[i].trim();
            if (split[i].startsWith("\"") && split[i].endsWith("\"")) {
                split[i] = split[i].substring(1, split[i].length() -1);
            }
        }
        return split;
    }
}
