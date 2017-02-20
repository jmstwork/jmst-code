package com.founder.fmdm.service.term.dto;

import org.seasar.doma.Column;
import org.seasar.doma.Entity;

@Entity
public class DictItemDto {
	private String field_id;
    
	private String field_name;
	
	private String field_desc;
    
	private String field_type;
    
	private Integer length; 
	
	private String field_is_edit;
	
	private String is_show;
	
	private String is_primary;
	
	private String is_must;
	
	private String table_name;
	
	private String dict_id;
	
	private String default_value;

	private String is_default;
	
	private String dict_name;
	
	private String is_edit;
	
	private String service_id;
	
	private String is_filter;
	
	/**
	 * 精度
	 */
    private String precision;
	
	
    
	public String getPrecision() {
		return precision;
	}

	public void setPrecision(String precision) {
		this.precision = precision;
	}

	public String getService_id() {
		return service_id;
	}

	public void setService_id(String service_id) {
		this.service_id = service_id;
	}

	public String getField_id() {
		return field_id;
	}

	public void setField_id(String field_id) {
		this.field_id = field_id;
	}

	public String getField_name() {
		return field_name;
	}

	public void setField_name(String field_name) {
		this.field_name = field_name;
	}

	public String getField_desc() {
		return field_desc;
	}

	public void setField_desc(String field_desc) {
		this.field_desc = field_desc;
	}

	public String getField_type() {
		return field_type;
	}

	public void setField_type(String field_type) {
		this.field_type = field_type;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public String getIs_show() {
		return is_show;
	}

	public void setIs_show(String is_show) {
		this.is_show = is_show;
	}

	public String getIs_primary() {
		return is_primary;
	}

	public void setIs_primary(String is_primary) {
		this.is_primary = is_primary;
	}



	public String getIs_must() {
		return is_must;
	}

	public void setIs_must(String is_must) {
		this.is_must = is_must;
	}

	public String getTable_name() {
		return table_name;
	}

	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}

	public String getDict_id() {
		return dict_id;
	}

	public void setDict_id(String dict_id) {
		this.dict_id = dict_id;
	}

	public String getDefault_value() {
		return default_value;
	}

	public void setDefault_value(String default_value) {
		this.default_value = default_value;
	}

	public String getIs_default() {
		return is_default;
	}

	public void setIs_default(String is_default) {
		this.is_default = is_default;
	}

	public String getDict_name() {
		return dict_name;
	}

	public void setDict_name(String dict_name) {
		this.dict_name = dict_name;
	}

	public String getIs_edit() {
		return is_edit;
	}

	public void setIs_edit(String is_edit) {
		this.is_edit = is_edit;
	}

	public String getIs_filter() {
		return is_filter;
	}

	public void setIs_filter(String is_filter) {
		this.is_filter = is_filter;
	}

	public String getField_is_edit() {
		return field_is_edit;
	}

	public void setField_is_edit(String field_is_edit) {
		this.field_is_edit = field_is_edit;
	}
	
}
