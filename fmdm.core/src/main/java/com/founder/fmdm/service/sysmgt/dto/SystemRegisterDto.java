package com.founder.fmdm.service.sysmgt.dto;

public class SystemRegisterDto
{
    private String searchSysID;
    
    private String searchSysName;

    private String searchSupId;
    
    private String searchSupName;

    private String searchCateCode;

    public String getSearchSysName() {
		return searchSysName;
	}
    
	public String getSearchSysID()
    {
        return searchSysID;
    }

    public void setSearchSysID(String searchSysID)
    {
        this.searchSysID = searchSysID;
    }

    public void setSearchSysName(String searchSysName) {
		this.searchSysName = searchSysName;
	}

	public String getSearchSupId()
    {
        return searchSupId;
    }

    public void setSearchSupId(String searchSupId)
    {
        this.searchSupId = searchSupId;
    }
    
    public String getSearchSupName() {
		return searchSupName;
	}

	public void setSearchSupName(String searchSupName) {
		this.searchSupName = searchSupName;
	}

	public String getSearchCateCode()
    {
        return searchCateCode;
    }

    public void setSearchCateCode(String searchCateCode)
    {
        this.searchCateCode = searchCateCode;
    }

    private String newSysId;
    
    private String sysId;

    private String sysName;

    private String cateCode;

    private String cateName;

    private String supId;

    private String supName;

    private String sysUrl;

    private String sysDesc;

    private String deleteFlg;

    private String createBy;

    private String createTime;

    private String updateBy;

    private String updateDt;

    private String extent;

    private String authCopy;

    private String[] userNo;

    private int pageNo = 1;

    public String[] getUserNo()
    {
        return userNo;
    }

    public void setUserNo(String[] userNo)
    {
        this.userNo = userNo;
    }

    public String getExtent()
    {
        return extent;
    }

    public void setExtent(String extent)
    {
        this.extent = extent;
    }

	public String getNewSysId() {
		return newSysId;
	}

	public void setNewSysId(String newSysId) {
		this.newSysId = newSysId;
	}

	public String getSysId() {
		return sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public String getCateCode()
    {
        return cateCode;
    }

    public void setCateCode(String cateCode)
    {
        this.cateCode = cateCode;
    }

    public String getSupId()
    {
        return supId;
    }

    public void setSupId(String supId)
    {
        this.supId = supId;
    }
 
    public String getSysUrl() {
		return sysUrl;
	}

	public void setSysUrl(String sysUrl) {
		this.sysUrl = sysUrl;
	}

	public String getSysDesc() {
		return sysDesc;
	}

	public void setSysDesc(String sysDesc) {
		this.sysDesc = sysDesc;
	}

	public String getDeleteFlg()
    {
        return deleteFlg;
    }

    public void setDeleteFlg(String deleteFlg)
    {
        this.deleteFlg = deleteFlg;
    }

    public String getCreateBy()
    {
        return createBy;
    }

    public void setCreateBy(String createBy)
    {
        this.createBy = createBy;
    }

    public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateBy()
    {
        return updateBy;
    }

    public void setUpdateBy(String updateBy)
    {
        this.updateBy = updateBy;
    }

    public String getUpdateDt()
    {
        return updateDt;
    }

    public void setUpdateDt(String updateDt)
    {
        this.updateDt = updateDt;
    }

    public String getCateName()
    {
        return cateName;
    }

    public void setCateName(String cateName)
    {
        this.cateName = cateName;
    }

    public String getSupName()
    {
        return supName;
    }

    public void setSupName(String supName)
    {
        this.supName = supName;
    }

    public int getPageNo()
    {
        return pageNo;
    }

    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }

    public String getAuthCopy()
    {
        return authCopy;
    }

    public void setAuthCopy(String authCopy)
    {
        this.authCopy = authCopy;
    }

}
