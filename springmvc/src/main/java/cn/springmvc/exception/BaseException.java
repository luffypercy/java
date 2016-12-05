package cn.springmvc.exception;

import java.util.HashMap;
import java.util.Map;

import cn.springmvc.enums.Constant.ResultEnum;


/**
 * @ClassName: OperateException
 * @Description: 顶级异常对象
 * @author ready likun_557@126.com
 * @date 2014-11-3 下午9:30:46
 */
public class BaseException extends Exception {

	/**
	 * 状态码
	 */
	private ResultEnum result;

	/**
	 * 状态码
	 */
	private String code;

	/**
	 * 跳转
	 */
	private String referer;

	/**
	 * 扩展参数
	 */
	private Map<Object, Object> extendMap;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BaseException() {
	}

	public BaseException(ResultEnum resultEnum, String message) {
		super(message);
		this.result = resultEnum;
	}

	public BaseException(ResultEnum resultEnum, String code, String message) {
		super(message);
		this.result = resultEnum;
		this.code = code;
	}

	public ResultEnum getResult() {
		return result;
	}

	public void setResult(ResultEnum result) {
		this.result = result;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Map<Object, Object> getExtendMap() {
		return extendMap;
	}

	public void setExtendMap(Map<Object, Object> extendMap) {
		this.extendMap = extendMap;
	}

	public void put(Object key, Object obj) {
		if (this.extendMap == null) {
			this.extendMap = new HashMap<Object, Object>();
		}
		this.extendMap.put(key, obj);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(Object key) {
		if (extendMap == null) {
			return null;
		}
		return (T) this.extendMap.get(key);
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}
}
