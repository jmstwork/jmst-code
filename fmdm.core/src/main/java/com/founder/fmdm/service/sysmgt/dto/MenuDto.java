package com.founder.fmdm.service.sysmgt.dto;

import java.util.ArrayList;
import java.util.List;

public class MenuDto
{

    String id;

    String name;

    Integer level;
    
    String pid;
    
    String url;
    
    List<MenuDto> child = new ArrayList<MenuDto>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<MenuDto> getChild() {
		return child;
	}

	public void setChild(List<MenuDto> child) {
		this.child = child;
	}
    
    
}
