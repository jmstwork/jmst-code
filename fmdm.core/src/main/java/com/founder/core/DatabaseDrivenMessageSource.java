package com.founder.core;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractMessageSource;

import com.founder.core.service.MessageResourceService;
import com.founder.core.service.dto.MessageResource;

public class DatabaseDrivenMessageSource extends AbstractMessageSource 
{
	@Autowired
    private MessageResourceService messageResourceService;
	
    private final Map<String, Map<String, String>> properties = new HashMap<String, Map<String, String>>();
	
	public DatabaseDrivenMessageSource()
	{
        reload();
	}
	 
    public DatabaseDrivenMessageSource(MessageResourceService messageResourceService) 
    {
        this.messageResourceService = messageResourceService;
        reload();
    }

	@Override
	protected MessageFormat resolveCode(String code, Locale locale) 
	{
        String msg = getText(code, locale);
        MessageFormat result = createMessageFormat(msg, locale);
        return result;
	}
	 
    @Override
    protected String resolveCodeWithoutArguments(String code, Locale locale) 
    {
        return getText(code, locale);
    }
    
    private String getText(String code, Locale locale) 
    {
        Map<String, String> localized = properties.get(code);
        String textForCurrentLanguage = null;
        if (localized != null) 
        {
            textForCurrentLanguage = localized.get(locale.getLanguage());
            if (textForCurrentLanguage == null) 
            {
                textForCurrentLanguage = localized.get(Locale.FRANCE.getLanguage());
            }
        }
        if (textForCurrentLanguage==null) 
        {
            //Check parent message
            logger.debug("Fallback to properties message");
            try 
            {
                textForCurrentLanguage = getParentMessageSource().getMessage(code, null, locale);
            } 
            catch (Exception e) 
            {
                logger.error("Cannot find message with code: " + code);
            }
        }
        return textForCurrentLanguage != null ? textForCurrentLanguage : code;
    }
 
    public void reload() 
    {
        properties.clear();
        properties.putAll(loadTexts());
    }
 
    protected Map<String, Map<String, String>> loadTexts() 
    {
        Map<String, Map<String, String>> m = new HashMap<String, Map<String, String>>();
        List<MessageResource> texts = messageResourceService.loadAllMessages();
        for (MessageResource text : texts) 
        {
            Map<String, String> v = null;
            String locale = text.getLocale();
            v = properties.get(locale);
            if(v == null)
            {
                v = new HashMap<String, String>();
                properties.put(locale, v);
            }
            String key = text.getKey();
            String value = text.getValue();
            v.put(key, value);
        }
        return m;
    }
}
