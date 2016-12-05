package cn.springmvc.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Model implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 字段的字符表示
	 */
	protected Map<String, Object> strMap;

	/**
	 * 数据版本号
	 */
	protected Long version;
	
	/**
	 * 版本号作为条件
	 */
	protected Long where_version;

	public Map<String, Object> getStrMap() {
		if (strMap == null) {
			this.strMap = new HashMap<String, Object>();
		}
		return strMap;
	}

	public void setStrMap(Map<String, Object> strMap) {
		this.strMap = strMap;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Long getWhere_version() {
		return where_version;
	}

	public void setWhere_version(Long where_version) {
		this.where_version = where_version;
	}

}
