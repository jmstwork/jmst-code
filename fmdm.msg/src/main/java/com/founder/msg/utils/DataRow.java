package com.founder.msg.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.apache.commons.beanutils.BeanMap;

public class DataRow implements Map<Object, Object> {
	private static final long serialVersionUID = -4074157556635645821L;
	public static final String ACTION_INSERT = "insert";
	public static final String ACTION_DELETE = "delete";
	public static final String ACTION_UPDATE = "update";
	public static final String ACTION_SELECT = "select";
	private String action;
	private Map map;

	public DataRow(Object bean) {
		this.map = new BeanMap(bean);
	}

	public DataRow() {
		this.map = new HashMap();
	}

	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Set entrySet() {
		return this.map.entrySet();
	}

	public void clear() {
		this.map.clear();
	}

	public boolean containsKey(Object key) {
		return this.map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return this.map.containsValue(value);
	}

	public Object get(Object key) {
		return this.map.get(key);
	}

	public boolean isEmpty() {
		return this.map.isEmpty();
	}

	public Set<Object> keySet() {
		return this.map.keySet();
	}

	public Object put(Object key, Object value) {
		return this.map.put(key, value);
	}

	public void putAll(Map<? extends Object, ? extends Object> m) {
		this.map.putAll(m);
	}

	public Object remove(Object value) {
		return this.map.remove(value);
	}

	public int size() {
		return this.map.size();
	}

	public Collection<Object> values() {
		return this.map.values();
	}

	public Object getBean() {
		if (this.map instanceof BeanMap) {
			return ((BeanMap) this.map).getBean();
		}
		return null;
	}

	public Class getType(String name) {
		if (this.map instanceof BeanMap) {
			return ((BeanMap) this.map).getType(name);
		}
		return null;
	}
}