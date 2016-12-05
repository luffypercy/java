package cn.springmvc.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 通用参数封装，业务层方法都为该参数
 * 
 * @author Ready 2012-10-17
 */
public class ParametersModel {

	private ParametersModel() {
	}

	private Map<Object, Object> paramMap;

	public Map<Object, Object> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<Object, Object> paramMap) {
		this.paramMap = paramMap;
	}

	/**
	 * 向参数列表中放值
	 * 
	 * @param key
	 * @param value
	 */
	public ParametersModel putValueToParamMap(Object key, Object value) {
		if (this.paramMap == null) {
			this.paramMap = new HashMap<Object, Object>();
		}
		this.paramMap.put(key, value);
		return this;
	}

	/**
	 * 从参数列表中拿值
	 * 
	 * @param key
	 * @return
	 */
	public Object getValueFromParamMap(Object key) {
		if (this.paramMap != null) {
			return this.paramMap.get(key);
		}
		return null;
	}

	public static ParametersModel createParametersModel() {
		return new ParametersModel();
	}

}
