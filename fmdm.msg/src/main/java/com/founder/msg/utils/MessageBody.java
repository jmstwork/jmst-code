package com.founder.msg.utils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MessageBody
{
  List<DataRow> rows;

  public void addDataRow(String action, Map<Object, Object> data)
  {
    DataRow row = new DataRow();
    row.setAction(action);
    row.putAll(data);
    addDataRow(row);
  }

  public void addDataRow(DataRow row)
  {
    if (this.rows == null)
    {
      this.rows = new ArrayList();
    }
    this.rows.add(row);
  }

  public List<DataRow> getRows()
  {
    return this.rows;
  }

  public void setRows(List<DataRow> rows)
  {
    this.rows = rows;
  }
}