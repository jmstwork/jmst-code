package com.founder.web.log.dto;

import java.util.Date;

import com.founder.web.dto.PagingDto;

public class LogManagerlDto  extends PagingDto
{

    private String logId;

    private String operorId;

    private String operDt;

    private Date operDt1;

    private Date operDt2;

    private String operModu;

    private String resrCode;

    private String resrName;

    private String optCode;

    private String optName;

    private String operObj;

    private String operCont;

    private Number deleteFlg;

    private String createBy;

    private Date createDt;

    private String updateBy;

    private Date updateDt;

    private String userName;

    //private int pageNo = 1;
    
    private int logInit;
    
    public String getLogId()
    {
        return logId;
    }

    public void setLogId(String logId)
    {
        this.logId = logId;
    }

    public String getOperorId()
    {
        return operorId;
    }

    public void setOperorId(String operorId)
    {
        this.operorId = operorId;
    }

    public String getOperDt()
    {
        return operDt;
    }

    public void setOperDt(String operDt)
    {
        this.operDt = operDt;
    }

    public Date getOperDt1()
    {
        return operDt1;
    }

    public void setOperDt1(Date operDt1)
    {
        this.operDt1 = operDt1;
    }

    public Date getOperDt2()
    {
        return operDt2;
    }

    public void setOperDt2(Date operDt2)
    {
        this.operDt2 = operDt2;
    }

    public String getOperModu()
    {
        return operModu;
    }

    public void setOperModu(String operModu)
    {
        this.operModu = operModu;
    }

    public String getOperObj()
    {
        return operObj;
    }

    public void setOperObj(String operObj)
    {
        this.operObj = operObj;
    }

    public String getOperCont()
    {
        return operCont;
    }

    public void setOperCont(String operCont)
    {
        this.operCont = operCont;
    }

    public Number getDeleteFlg()
    {
        return deleteFlg;
    }

    public void setDeleteFlg(Number deleteFlg)
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

    public Date getCreateDt()
    {
        return createDt;
    }

    public void setCreateDt(Date createDt)
    {
        this.createDt = createDt;
    }

    public String getUpdateBy()
    {
        return updateBy;
    }

    public void setUpdateBy(String updateBy)
    {
        this.updateBy = updateBy;
    }

    public Date getUpdateDt()
    {
        return updateDt;
    }

    public void setUpdateDt(Date updateDt)
    {
        this.updateDt = updateDt;
    }

    /*public int getPageNo()
    {
        return pageNo;
    }

    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }*/

    public String getResrName()
    {
        return resrName;
    }

    public void setResrName(String resrName)
    {
        this.resrName = resrName;
    }

    public String getOptName()
    {
        return optName;
    }

    public void setOptName(String optName)
    {
        this.optName = optName;
    }

    public String getResrCode()
    {
        return resrCode;
    }

    public void setResrCode(String resrCode)
    {
        this.resrCode = resrCode;
    }

    public String getOptCode()
    {
        return optCode;
    }

    public void setOptCode(String optCode)
    {
        this.optCode = optCode;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

	public int getLogInit() {
		return logInit;
	}

	public void setLogInit(int logInit) {
		this.logInit = logInit;
	}


}
