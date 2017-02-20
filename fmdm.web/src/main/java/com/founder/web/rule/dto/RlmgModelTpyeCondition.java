package com.founder.web.rule.dto;

import com.founder.web.dto.PagingDto;

public class RlmgModelTpyeCondition extends PagingDto
{
    private String modelId;
    
    private String modelName; 
    
    private String modelEnName; 
    
    private String fieldId;
    
    private String fieldName;
    
    private String fieldEnName;
    
    private String fieldType;
    
	private String operFlg;
	
	private String selectId;
	
	private String opt;
	
	

    public String getOpt() {
		return opt;
	}

	public void setOpt(String opt) {
		this.opt = opt;
	}

	public String getModelId()
    {
        return modelId;
    }

    public void setModelId(String modelId)
    {
        this.modelId = modelId;
    }

    public String getModelName()
    {
        return modelName;
    }

    public void setModelName(String modelName)
    {
        this.modelName = modelName;
    }

    public String getModelEnName()
    {
        return modelEnName;
    }

    public void setModelEnName(String modelEnName)
    {
        this.modelEnName = modelEnName;
    }

    public String getFieldId()
    {
        return fieldId;
    }

    public void setFieldId(String fieldId)
    {
        this.fieldId = fieldId;
    }

    public String getFieldName()
    {
        return fieldName;
    }

    public void setFieldName(String fieldName)
    {
        this.fieldName = fieldName;
    }

    public String getFieldEnName()
    {
        return fieldEnName;
    }

    public void setFieldEnName(String fieldEnName)
    {
        this.fieldEnName = fieldEnName;
    }

    public String getFieldType()
    {
        return fieldType;
    }

    public void setFieldType(String fieldType)
    {
        this.fieldType = fieldType;
    }

    public String getOperFlg()
    {
        return operFlg;
    }

    public void setOperFlg(String operFlg)
    {
        this.operFlg = operFlg;
    }

    public String getSelectId()
    {
        return selectId;
    }

    public void setSelectId(String selectId)
    {
        this.selectId = selectId;
    }
	
    
}
