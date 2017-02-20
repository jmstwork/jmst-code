package com.founder.core.service.dto;

public class MessageResource
{
    private Long id;

    private String basename;

    private String locale;

    private String key;

    private String value;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getBasename()
    {
        return basename;
    }

    public void setBasename(String basename)
    {
        this.basename = basename;
    }

    public String getLocale()
    {
        return locale;
    }

    public void setLocale(String locale)
    {
        this.locale = locale;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

}
