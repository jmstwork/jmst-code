package com.founder.fmdm.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.xml.sax.InputSource;

/**
 * 读取配置文件<BR>
 * 
 * @version 1.0
 */
public final class ReadResource {

	/** LOGGER */
	static Logger logger = Logger.getLogger(ReadResource.class);

	/** 字符集 */
	private static final String XML_ENCODING = "utf-8";

	/** */
	private static ClassLoader defaultClassLoader = null;

	/**
	 * 构造函数
	 */
	private ReadResource() {
	}

	/**
	 * 获取classloader
	 * 
	 * @return defaultClassLoader
	 */
	public static ClassLoader getDefaultClassLoader() {
		return defaultClassLoader;
	}

	/**
	 * 设置defaultClassLoader
	 * 
	 * @param classLoader
	 */
	public static void setDefaultClassLoader(ClassLoader classLoader) {
		defaultClassLoader = classLoader;
	}

	/**
	 * 读取跟目录下的文件
	 * 
	 * @param resource
	 * @return InputStream
	 */
	public static InputStream getResourceAsStream(String resource) {
		return getResourceAsStream(getClassLoader(), resource);
	}

	/**
	 * 获取InputStream
	 * 
	 * @param loader
	 * @param resource
	 * @return InputStream
	 */
	public static InputStream getResourceAsStream(ClassLoader loader,
			String resource) {
		InputStream in = null;
		if (loader != null) {
			in = loader.getResourceAsStream(resource);
		}
		if (in == null) {
			in = ClassLoader.getSystemResourceAsStream(resource);
		}
		if (in == null) {
			throw new RuntimeException("Could not find resource " + resource);
		} else {
			return in;
		}
	}

	/**
	 * 获取 Properties
	 * 
	 * @param resource
	 * @return
	 */
	public static Properties getResourceAsProperties(String resource) {
		Properties props;
		InputStream in;
		try {
			props = new Properties();
			in = getResourceAsStream(resource);
			props.load(in);
		} catch (IOException ioe) {
			System.out.println("读取配置文件错误……");
			throw new RuntimeException(ioe);
		}
		try {
			in.close();
		} catch (IOException ignore) {
			throw new RuntimeException(ignore);
		}
		return props;
	}

	/**
	 * 读取文件转换成String类型字符串
	 * 
	 * @param resource
	 * @return
	 */
	public static String getResourceAsString(String resource) {
		InputStream inputStream;
		StringBuffer sbXml = new StringBuffer("");
		BufferedReader target = null;
		try {
			inputStream = getResourceAsStream(resource);
			InputStreamReader input = new InputStreamReader(inputStream,
					"UTF-8");
			target = new BufferedReader(input);
			String targetLine = (String) target.readLine();
			while (targetLine != null) {
				sbXml.append(targetLine);
				targetLine = (String) target.readLine();
			}
		} catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
		try {
			target.close();
		} catch (IOException ignore) {
			throw new RuntimeException(ignore);
		}
		return sbXml.toString();
	}

	/**
	 * 获取ClassLoader
	 * 
	 * @return
	 */
	private static ClassLoader getClassLoader() {
		if (defaultClassLoader != null) {
			return defaultClassLoader;
		} else {
			return Thread.currentThread().getContextClassLoader();
		}
	}

	/**
	 * 根据传入的xml字符串返回docoument对象
	 * 
	 * @param xmlContent
	 * @return
	 * @throws DocumentException
	 */
	public static Document parseXmlString(String xmlContent) {
		Document document = null;
		StringReader read = new StringReader(xmlContent);
		// 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
		InputSource source = new InputSource(read);
		SAXReader saxReader = new SAXReader();
		try {
			document = saxReader.read(source);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return document;
	}

	/**
	 * 根据传入的xml字符串格式化输出xml
	 * 
	 * @param xmlContent
	 * @return
	 */
	public static String format(String xmlContent) {
		try {
			SAXReader reader = new SAXReader();
			// System.out.println(reader);
			// 注释：创建一个串的字符输入流
			StringReader in = new StringReader(xmlContent);
			Document doc;
			doc = reader.read(in);

			// 注释：创建输出格式
			OutputFormat formater = OutputFormat.createPrettyPrint();
			// formater=OutputFormat.createCompactFormat();
			// 注释：设置xml的输出编码
			formater.setEncoding(XML_ENCODING);
			// 注释：创建输出(目标)
			StringWriter out = new StringWriter();
			// 注释：创建输出流
			XMLWriter writer = new XMLWriter(out, formater);
			// 注释：输出格式化的串到目标中，执行后。格式化后的串保存在out中。
			writer.write(doc);

			writer.close();
			// 注释：返回我们格式化后的结果
			return out.toString();
		} catch (Exception e) {
			logger.error("xml format error!", e);
		}
		return xmlContent;
	}

	/**
	 * 根据传入的dom对象格式化输出xml
	 * 
	 * @param doc
	 * @return
	 */
	public static String format(Document doc) {
		try {
			// 注释：创建输出格式
			OutputFormat formater = OutputFormat.createPrettyPrint();
			// formater=OutputFormat.createCompactFormat();
			// 注释：设置xml的输出编码
			formater.setEncoding(XML_ENCODING);
			// 注释：创建输出(目标)
			StringWriter out = new StringWriter();
			// 注释：创建输出流
			XMLWriter writer = new XMLWriter(out, formater);
			// 注释：输出格式化的串到目标中，执行后。格式化后的串保存在out中。
			writer.write(doc);
			writer.close();
			// 注释：返回我们格式化后的结果
			return out.toString();
		} catch (Exception e) {
			logger.error("xml format error!", e);
		}
		return doc.asXML();
	}
}
