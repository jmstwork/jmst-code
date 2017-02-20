package com.founder.xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XmlHelper
{
    private static final Logger logger = LoggerFactory.getLogger(XmlHelper.class);

    public static final String ENCODING_UTF8 = "UTF-8";
    
    /**
     * 加载XML String资源
     * 
     * @param xmlString xml格式的字符串
     * @return Node
     */
    public static Document loadXMLResource(String xmlString, String encoding)
    {
        if (0xFEFF == xmlString.charAt(0))
        {
            xmlString = xmlString.substring(1);
        }
        InputSource source = new InputSource(new BufferedReader(new StringReader(xmlString)));
        
        return xmlSourceToDocument(source, encoding);
    }

    /**
     * 加载XML byte[]资源
     * 
     * @param xmlFile xml文件
     * @return Node
     */
    public static Document loadXMLResource(byte xmlByte[], String encoding)
    {
        String xmlString = "";
        try
        {
            xmlString = new String(xmlByte, encoding);
            return loadXMLResource(xmlString, encoding);
        }
        catch (UnsupportedEncodingException e)
        {
            logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * 加载XML File资源
     * 
     * @param xmlFile xml文件
     * @return Node
     */
    public static Document loadXMLResource(File xmlFile, String encoding)
    {
        InputSource source = null;
        try
        {
            source = new InputSource(new FileInputStream(xmlFile));
        }
        catch (FileNotFoundException e)
        {
            logger.error(e.getMessage());
        }
        
        return xmlSourceToDocument(source, encoding);
    }

    /**
     * 把xml source 转换为Document
     * 
     * @param source
     * @return
     */
    private static Document xmlSourceToDocument(InputSource source, String encoding)
    {
        source.setEncoding(encoding);
        Document document = null;
        try
        {
            document = loadDocument(source);
        }
        catch (SAXParseException spe)
        {
            if (null != spe.getSystemId())
            {
                logger.error("xpath解析错误，出错的行数是：{}，uri：{}", spe.getLineNumber(), spe.getSystemId());
            }
            logger.debug(spe.getMessage());
        }
        catch (SAXException se)
        {
            document = null;
            logger.debug("解析XML错误，请确保存在格式正确的XML文档。");
        }
        catch (IOException ioe)
        {
            document = null;
            logger.debug("不能加载文档，文档不可读取。");
        }
        return document;
    }

    /**
     * 从InputSource加载document
     * 
     * @param source
     * @return Node
     * @throws SAXException
     * @throws IOException
     */
    public static Document loadDocument(InputSource source) throws SAXException, IOException
    {
        Document document = null;
        DocumentBuilder parser = null;
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        domFactory.setNamespaceAware(true);
        domFactory.setValidating(false);
        try
        {
            parser = domFactory.newDocumentBuilder();
        }
        catch (ParserConfigurationException pce)
        {
            logger.error(pce.getMessage());
        }
        parser.reset();
        document = parser.parse(source);

        return document;
    }
}
