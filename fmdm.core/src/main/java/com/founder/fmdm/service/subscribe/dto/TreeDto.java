package com.founder.fmdm.service.subscribe.dto;

import java.util.HashMap;
import java.util.List;

/**
 * 树形菜单节点dto
 * @author dehong
 *
 */
public class TreeDto {
	
	private String id;
	
	private String name;
	
	private String pId; 
	
	private Integer type;
	
	private String url;
	
	private String isParent;
	
	private boolean open;
	
	private List<TreeDto> buttons;          //保存菜单下的按钮集合
	
	private HashMap<String,Object> otherAttr = new HashMap<String,Object>();

	private boolean checked = false;

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIsParent() {
		return isParent;
	}

	public void setIsParent(String isParent) {
		this.isParent = isParent;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public List<TreeDto> getButtons() {
		return buttons;
	}

	public void setButtons(List<TreeDto> buttons) {
		this.buttons = buttons;
	}

	public HashMap<String, Object> getOtherAttr() {
		return otherAttr;
	}

	public void setOtherAttr(HashMap<String, Object> otherAttr) {
		this.otherAttr = otherAttr;
	}


	
	
	
	
}
