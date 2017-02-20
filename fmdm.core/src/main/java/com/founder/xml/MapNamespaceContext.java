package com.founder.xml;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;

public class MapNamespaceContext implements NamespaceContext
{
    private Map<String, String> pairs;

    public MapNamespaceContext()
    {
        pairs = new HashMap<String, String>();
        pairs.put(XMLConstants.XML_NS_PREFIX, XMLConstants.XML_NS_URI);
    }

    @Override
    public String getNamespaceURI(String prefix)
    {
        return pairs.get(prefix);
    }

    @Override
    public String getPrefix(String namespaceURI)
    {
        throw new UnsupportedOperationException("getPrefix");
    }

    public void addPair(String prefix, String uri)
    {
        this.pairs.put(prefix, uri);
    }

    public void removePrefix(String prefix)
    {
        this.pairs.remove(prefix);
    }
    
    @SuppressWarnings("rawtypes")
    @Override
    public Iterator getPrefixes(String namespaceURI)
    {
        throw new UnsupportedOperationException("getPrefixs");
    }

}
