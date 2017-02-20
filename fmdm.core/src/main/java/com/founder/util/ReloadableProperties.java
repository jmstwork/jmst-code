package com.founder.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import org.springframework.core.io.Resource;

public class ReloadableProperties extends Properties 
{
	private static final long serialVersionUID = 760576541789141588L;
	private long lastLoaded = 0;
	private int cacheSeconds = -1;
	private Resource resource;
	
	public void setResource(final Resource resource) 
	{
	    this.resource = resource;
	}

	public synchronized void load() throws Exception
	{
		if(resource == null)
			return;
		
		lastLoaded = System.currentTimeMillis();
	}
	
	@Override
	public synchronized void load(Reader reader) throws IOException 
	{
	}

	@Override
	public synchronized void load(InputStream inStream) throws IOException 
	{
	}

	@Override
	public synchronized void loadFromXML(InputStream in) throws IOException,
			InvalidPropertiesFormatException 
	{
	}

	@Override
	public String getProperty(String key) 
	{
		this.refreshProperties();
		return super.getProperty(key);
	}

	@Override
	public String getProperty(String key, String defaultValue) 
	{
		this.refreshProperties();
		return super.getProperty(key, defaultValue);
	}

	public int getCacheSeconds() 
	{
		return cacheSeconds;
	}

	public void setCacheSeconds(int cacheSeconds) 
	{
		this.cacheSeconds = cacheSeconds;
	}
	
	protected void refreshProperties()
	{
		if(this.cacheSeconds <= 0)
			return;
		
		long current = System.currentTimeMillis();
		if(current > this.lastLoaded + this.cacheSeconds * 1000);
		{
			try 
			{
				this.load();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
	}
}
