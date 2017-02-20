package com.founder.xml;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XPathQuery
{
    private static final Logger logger = LoggerFactory.getLogger(XPathQuery.class);

    private MapNamespaceContext namespaceContext;
    private XPath xpath;

    public XPathQuery()
    {
        namespaceContext = new MapNamespaceContext();
    }

    public void init()
    {
        XPathFactory factory = XPathFactory.newInstance();
        xpath = factory.newXPath();
        if(namespaceContext != null)
            xpath.setNamespaceContext(namespaceContext);
    }
    
    public MapNamespaceContext getNamespaceContext()
    {
        return namespaceContext;
    }

    public void setNamespaceContext(MapNamespaceContext namespaceContext)
    {
        this.namespaceContext = namespaceContext;
    }

    public void addPair(String prefix, String uri)
    {
        this.namespaceContext.addPair(prefix, uri);
    }
    
    public Object evaluate(String query, Node xmlDoc, QName returnType)
    {
        if(xmlDoc == null)
            return null;
        
        XPathExpression expr = null;
        try
        {
            expr = xpath.compile(query);
        }
        catch (XPathExpressionException xpee)
        {
            logger.error("xpath表达式错误，请检查命名空间转换或者表达式格式。");
            logger.debug(getFullStackTrace(xpee));
            
            return null;
        }
        
        Object result = null;
        try
        {
            result = expr.evaluate(xmlDoc, returnType);
            if (result == null)
            {
                logger.trace("xpath取值失败：{}", xpath);
            }
        }
        catch (XPathExpressionException e)
        {
            logger.error(getFullStackTrace(e));
        }
        return result;
    }

    private String getFullStackTrace(Throwable throwable)
    {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        throwable.printStackTrace(pw);
        
        return sw.getBuffer().toString();
    }

    public Node getSingleNodeValue(String query, Node xmlDoc)
    {
        return (Node) evaluate(query, xmlDoc, XPathConstants.NODE);
    }

    public NodeList getListValue(String query, Node xmlDoc)
    {
        return (NodeList) evaluate(query, xmlDoc, XPathConstants.NODESET);
    }

    public String getStringValue(String query, Node xmlDoc)
    {
        return (String) evaluate(query, xmlDoc, XPathConstants.STRING);
    }

    public Double getNumberValue(String query, Node xmlDoc)
    {
        return (Double) evaluate(query, xmlDoc, XPathConstants.NUMBER);
    }

    public void addNamespaceMap(Map<String, String> namespaceMap)
    {
        if(namespaceMap == null || namespaceMap.isEmpty())
            return;
        
        String prefix, uri;
        Iterator<String> it = namespaceMap.keySet().iterator();
        while(it.hasNext())
        {
            prefix = it.next();
            uri = namespaceMap.get(prefix);
            this.namespaceContext.addPair(prefix, uri);
        }
    }
}
