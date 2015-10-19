package cn.dota.util;

import java.util.HashMap;
import java.util.Map;

public class BaseModel {
	private Map<String,Object> paramMap=new HashMap<String,Object>();

	public Map<String, Object> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}
	
}
