package com.founder.msg.utils;

import java.util.Iterator;

import org.apache.commons.beanutils.BeanMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class MsgHeadConverter
  implements Converter
{
  static Logger logger = LoggerFactory.getLogger(MsgHeadConverter.class.getName());

  public boolean canConvert(Class type)
  {
    return MessageHead.class.equals(type);
  }

  public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context)
  {
    MessageHead head = (MessageHead)source;
    BeanMap map = new BeanMap(head);
    for (Iterator i$ = map.keySet().iterator(); i$.hasNext(); ) { Object key = i$.next();

      Object v = map.get(key);
      if (v == null)
      {
        v = "";
      }
      else if (v instanceof Class) {
          continue;
        }

      writer.startNode((String)key);
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
    MessageHead head = new MessageHead();
    BeanMap map = new BeanMap(head);
    while (reader.hasMoreChildren())
    {
      reader.moveDown();
      String key = reader.getNodeName();
//      Assert.notNull(key);
      Object value = reader.getValue();
      if ("msgId".equals(key))
      {
//        DataRowBeanClassSource rowBeanClassSource = DataRowBeanClassSourceHolder.getDataRowBeanClassSource();
//        if (rowBeanClassSource != null)
//        {
//          Class rowBeanClass = rowBeanClassSource.getBeanClass(value);
//          context.put("rowBeanClass", rowBeanClass);
//        }
      }
      if (map.containsKey(key))
      {
        value = context.convertAnother(value, map.getType(key));
        map.put(key, value);
      }
      reader.moveUp();
    }
    return head;
  }
}