package com.founder.msg.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class DataRowConverter
  implements Converter
{
  static Logger logger = LoggerFactory.getLogger(DataRowConverter.class.getName());

  private static final DateFormat DATE_FORMAT_MSG = new SimpleDateFormat("yyyyMMddHHmmss");

  private static final DateFormat DATE_FORMAT_DAY = new SimpleDateFormat("yyyy-MM-dd");

  private static final DateFormat DATE_FORMAT_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  private static final Pattern DAY_PATTERN = Pattern.compile("(\\d{4}-\\d{2}-\\d{2}) 00:00:00");

  private Set<String> skipProperties = new HashSet();

  private Set<String> extraProperties = new HashSet();

  public DataRowConverter()
  {
    String[] skipPropertiesArray = { "aheadExamTime", "arriveTime", "detailPrintTime" };

    this.skipProperties.addAll(Arrays.asList(skipPropertiesArray));

    String[] extraPropertiesArray = { "startDateActive", "endDateActive" };
    this.extraProperties.addAll(Arrays.asList(extraPropertiesArray));
  }

  public boolean canConvert(Class type)
  {
    return DataRow.class.equals(type);
  }

  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context)
  {
    DataRow row = (DataRow)source;
    writer.addAttribute("action", row.getAction());
    for (Iterator i$ = row.keySet().iterator(); i$.hasNext(); ) { Object key = i$.next();

      Object v = row.get(key);
      if (v == null)
      {
        v = "";
      }
      else {
        if (v instanceof Class) {
          continue;
        }
      }
      writer.startNode((String)key);
      if (((isDateTypeObjectName((String)key)) && (v instanceof String)) && (StringUtils.isEmpty((String)v)))
      {
        try
        {
          v = MessageUtils.dateConverter.fromString((String)v);
        }
        catch (ConversionException e)
        {
          logger.error("error when try to marshal [" + key + ":" + v + "] to date format");
        }
      }

      if ((v != null) && (Date.class.isAssignableFrom(v.getClass())))
      {
        v = DATE_FORMAT_MSG.format(v);
      }
      if (v instanceof String)
      {
        writer.setValue((String)v);
      }
      else
      {
        context.convertAnother(v);
      }
      writer.endNode();
    }
  }

  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context)
  {
    Class beanClass = (Class)context.get("rowBeanClass");
    Object bean = null;
    DataRow row = null;
    try
    {
      if (beanClass != null)
      {
        bean = beanClass.newInstance();
        row = new DataRow(bean);
      }
    }
    catch (Exception e)
    {
      logger.error("create bean of " + beanClass.toString() + "type error!", e);

      bean = null;
      row = null;
    }
    if (row == null)
    {
      row = new DataRow();
    }
    row.setAction(reader.getAttribute("action"));
    while (reader.hasMoreChildren())
    {
      reader.moveDown();
      String key = reader.getNodeName();
//      Assert.notNull(key);
      Object value = reader.getValue();
      Class type = String.class;
      boolean shouldConvertToDateString = false;
      if (row.getBean() != null)
      {
        type = null;
        type = row.getType(key);
      }
      if (type != null)
      {
        if ((String.class.isAssignableFrom(type)) && (isDateTypeObjectName(key)))
        {
          shouldConvertToDateString = true;
          type = Date.class;
        }
        if ((value instanceof String) && (StringUtils.isEmpty((String)value)))
        {
          if (Date.class.isAssignableFrom(type))
          {
            try
            {
              value = (Date)MessageUtils.dateConverter.fromString((String)value);
            }
            catch (ConversionException e)
            {
              logger.error("error when try to unmarshal [" + key + ":" + value + "] to type [" + type + "]!");

              value = null;
            }

          }
          else
          {
            value = context.convertAnother(value, type);
          }

        }
        else {
          value = null;
        }
        if (shouldConvertToDateString)
        {
          value = convertToDateString(value);
        }
        row.put(key, value);
      }
      reader.moveUp();
    }
    return row;
  }

  private boolean isDateTypeObjectName(String name)
  {
    if ((name.endsWith("Time")) || (name.endsWith("Date")))
    {
      return true;//(this.skipProperties.contains(name));
    }

    return false;//(!(this.extraProperties.contains(name)));
  }

  private Object convertToDateString(Object value)
  {
    if ((value == null) || (!(Date.class.isAssignableFrom(value.getClass()))))
    {
      return value;
    }
    Date date = (Date)value;
    String str = DATE_FORMAT_TIME.format(date);
    Matcher match = DAY_PATTERN.matcher(str);
    if (match.matches())
    {
      str = match.group(1);
    }
    return str;
  }
}