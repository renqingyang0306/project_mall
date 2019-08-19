package com.cskaoyan.project.mall.utils.statisticsMapBean;

import java.util.List;
import java.util.Map;

public class TableBean<T>
{
    List columns;
    List<T> rows;

    public TableBean()
    {
    }

    public TableBean(List columns, List<T> rows)
    {
        this.columns = columns;
        this.rows = rows;
    }

    public List getColumns()
    {
        return columns;
    }

    public void setColumns(List columns)
    {
        this.columns = columns;
    }

    public List<T> getRows()
    {
        return rows;
    }

    public void setRows(List<T> rows)
    {
        this.rows = rows;
    }
}
