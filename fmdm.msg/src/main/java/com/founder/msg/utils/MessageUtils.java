package com.founder.msg.utils;

import java.io.InputStream;
import java.io.StringReader;
import java.io.Writer;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.basic.DateConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class MessageUtils {
	static Logger logger = LoggerFactory.getLogger(MessageUtils.class);
	private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
	private static XStream xstream;
	private static Map<String, Validator> validators = new HashMap();

	private static SchemaFactory sf = SchemaFactory
			.newInstance("http://www.w3.org/2001/XMLSchema");

	public static DateConverter dateConverter = new DateConverter(
			"yyyyMMddHHmmss", new String[] { "yyyyMMdd",
					"yyyy-MM-dd HH:mm:ss.S", "yyyy-MM-dd HH:mm:ss",
					"yyyy-MM-dd HH:mm", "yyyy-MM-dd" }, TimeZone.getDefault(),
			false);

	public static void marshal(Message msg, Writer writer) {
		try {
			xstream.toXML(msg, writer);
		} catch (ConversionException e) {
			logger.error("marshal(obj2xml) error", e);
			throw new ConversionException(e);
		}
	}

	public static String marshal(Message msg) {
		try {
			String ret = xstream.toXML(msg);
			ret = ret.replace("&#x1f;"," ").replace("&#x7f;"," ").replace("&#x1c;"," ");
			ret = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + ret;
			return ret;
		} catch (ConversionException e) {
			logger.error("marshal(obj2xml) error", e);
			throw new ConversionException(e);
		}
	}

	public static Message unmarshal(Object source) {
		try {
			if (source == null) {
				return null;
			}
			if (source instanceof String) {
				return ((Message) xstream.fromXML((String) source));
			}
			if (source instanceof InputStream) {
				return ((Message) xstream.fromXML((InputStream) source));
			}

			return null;
		} catch (ConversionException e) {
			logger.error("unmarshal(xml2obj) error", e);
			throw new ConversionException(e);
		}
	}

	public static void validate(Object input, String schema) {
		if ((input == null) || (schema == null)) {
			return;
		}
		try {
			Source source = null;
			if (input instanceof String) {
				source = new StreamSource(new StringReader((String) input));
			} else if (input instanceof Source) {
				source = (Source) input;
			} else {
				return;
			}

			// Validator validator = createValidator(schema);
			// validator.validate(source);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Message buildMessage(MessageHead msgHead, DataRow[] datas) {
		// Assert.notNull(msgHead);
		// Assert.hasLength(msgHead.getMsgId());
		if (msgHead.getCreateTime() == null) {
			msgHead.setCreateTime(Calendar.getInstance().getTime());
		}

		MessageBody body = new MessageBody();
		List list = new ArrayList();
		list.addAll(Arrays.asList(datas));
		body.setRows(list);

		Message msg = new Message();
		msg.setHead(msgHead);
		msg.setBody(body);
		return msg;
	}

	public static Message buildMessage(MessageHead msgHead, Map[] datas) {
		return buildMessage(msgHead, "select", datas);
	}

	public static Message buildMessage(MessageHead msgHead, String action,
			Map[] datas) {
		if (datas == null) {
			datas = new Map[0];
		}

		List list = new ArrayList();
		for (Map data : datas) {
			DataRow dataRow = new DataRow();
			dataRow.putAll(data);
			dataRow.setAction(action);
			list.add(dataRow);
		}

		return buildMessage(msgHead, (DataRow[]) list.toArray(new DataRow[0]));
	}
	
	public static Message buildMessage(MessageHead msgHead, List<String> action,
			Map[] datas,Boolean camelStyle) {
		if (datas == null) {
			datas = new Map[0];
		}
		List list = new ArrayList();
		for (int i=0;i<datas.length;i++) {
			Map data = datas[i];
			DataRow dataRow = new DataRow();
			if(camelStyle){
				for(Object key:data.keySet().toArray()){
					if(data.get(key) != null && Date.class.equals(data.get(key).getClass())){
						dataRow.put(CamelUtils.convertToString((String) key), DateUtils.dateToString((Date)data.get(key),DateUtils.PATTERN_COMPACT_DATETIME));
					}else{
						dataRow.put(CamelUtils.convertToString((String) key), data.get(key));
					}
				}
			}else{
				dataRow.putAll(data);
			}
			dataRow.setAction(action.get(i));
			list.add(dataRow);
		}

		return buildMessage(msgHead, (DataRow[]) list.toArray(new DataRow[0]));
	}

	// private static synchronized Validator createValidator(String schema)
	// throws IOException, SAXException
	// {
	// if (!(validators.containsKey(schema)))
	// {
	// Resource r = new ClassPathResource(schema);
	// StreamSource ss = new StreamSource(r.getInputStream());
	// String path = r.getURI().toString();
	// String systemId = path.substring(0, path.lastIndexOf("/") + 1);
	// ss.setSystemId(systemId);
	// StreamSource[] schemas = { ss };
	// Schema s = sf.newSchema(schemas);
	// validators.put(schema, s.newValidator());
	// }
	// return ((Validator)validators.get(schema));
	// }

	static {
		xstream = new XStream(new DomDriver());
		xstream.addImplicitCollection(MessageBody.class, "rows");
		xstream.alias("msg", Message.class);
		xstream.alias("row", DataRow.class);
		xstream.alias("head", MessageHead.class);

		xstream.registerConverter(new DataRowConverter());
		xstream.registerConverter(new MsgHeadConverter());

		xstream.registerConverter(dateConverter);
	}
}