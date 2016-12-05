package cn.springmvc.model;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import cn.springmvc.enums.Constant.ResultEnum;
import cn.springmvc.util.FrameUtil;


/**
 * <b>description</b>：操作结果信息封装，默认操作结果是成功的 <br>
 * <b>time</b>：2014-11-5下午5:26:18 <br>
 * <b>author</b>： ready likun_557@163.com
 */
public class ResultModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 操作结果
	 */
	private ResultEnum result;

	/**
	 * 状态码(当result为ERROR的时候，该值起作用，状态码)
	 */
	private String code;
	/**
	 * 操作反馈信息(操作描述信息)
	 */
	private String message;

	/**
	 * 是否跳转页面
	 */
	private String referer;

	/**
	 * 操作中的其他信息
	 */
	private Map<Object, Object> data;

	public ResultModel(ResultEnum result, String code, String message,
			String referer) {
		super();
		this.result = result;
		this.code = code;
		this.message = message;
		this.referer = referer;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	/**
	 * 获取操作中的其他信息
	 * 
	 * @return
	 */
	public Map<Object, Object> getData() {
		if (this.data == null) {
			this.data = new LinkedHashMap<Object, Object>();
		}
		return data;
	}

	public ResultModel result(ResultEnum result, String code, String msgKey,
			Object... param) {
		this.result = result;
		this.code = code;
		this.setMsgByResource(msgKey, param);
		return this;
	}

	public void setData(Map<Object, Object> data) {
		this.data = data;
	}

	/**
	 * 设置操作描述信息
	 * 
	 * @param msg
	 */
	public void setMsgByResource(String resourceKey, Object... param) {
		this.message = FrameUtil.getResource(resourceKey, param);
	}

	/**
	 * 向extendMap[操作中的其他信息]中存值
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public void putValueToData(Object key, Object value) {
		this.getData().put(key, value);
	}

	/**
	 * 从extendMap[操作中的其他信息]中存值
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T extends Object> T getValueFormData(Object key) {
		return ((T) this.getData().get(key));
	}

	public ResultModel() {
		this.result = ResultEnum.SUCCESS;
		this.message = FrameUtil.getResource("normal.00001");
	}

	public Map<String, Object> toMap() {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("state", this.getResult().toString());
		map.put("code", this.getCode());
		map.put("time", FrameUtil.getTime(null));
		map.put("referer", this.getReferer());
		map.put("message", this.getMessage());
		map.put("data", this.getData());
		return map;
	}

	/**
	 * 创建当前类的一个对象,每次调用该方法将获取一个新的实例
	 * 
	 * @return
	 */
	public static ResultModel createResultModel() {
		return new ResultModel();
	}

	/**
	 * 创建当前类的一个对象,每次调用该方法将获取一个新的实例
	 * 
	 * @return
	 */
	public static ResultModel createResultModel1(ResultEnum result, String code,String referer,
			String resourceKey, Object... param) {
		ResultModel resultModel = new ResultModel();
		resultModel.result(result, code, resourceKey, param);
		resultModel.setReferer(referer);
		return resultModel;
	}
	/**
	 * 创建当前类的一个对象,每次调用该方法将获取一个新的实例
	 * 
	 * @return
	 */
	public static ResultModel createResultModel(ResultEnum result, String code,
			String resourceKey, Object... param) {
		ResultModel resultModel = new ResultModel();
		return resultModel.result(result, code, resourceKey, param);
	}

	/**
	 * 创建当前类的一个对象,每次调用该方法将获取一个新的实例
	 * 
	 * @return
	 */
	public static ResultModel createOkResultModel(String resourceKey,
			Object... param) {
		ResultModel resultModel = new ResultModel();
		return resultModel.result(ResultEnum.SUCCESS, null, resourceKey, param);
	}

	/**
	 * 创建当前类的一个对象,每次调用该方法将获取一个新的实例
	 * 
	 * @return
	 */
	public static ResultModel createErrorResultModel(String resourceKey,
			Object... param) {
		ResultModel resultModel = new ResultModel();
		return resultModel.result(ResultEnum.ERROR, null, resourceKey, param);
	}

	public String toJson() {
		return FrameUtil.json(this.toMap());
	}

	public static void main(String[] args) {
		System.out.println(new ResultModel().toJson());
	}
}
