package cn.springmvc.model;

/**
 * @ClassName: ValidateResultModel
 * @Description: 验证结果封装
 * @author ready likun_557@163.com
 * @date 2014-8-16 下午1:25:56
 */
public class ValidateResultModel {

	/**
	 * 验证是否通过
	 */
	private boolean isSuccess;
	/**
	 * 反馈消息
	 */
	private String msg;

	public ValidateResultModel() {
	}

	public ValidateResultModel(boolean isSuccess, String msg) {
		super();
		this.isSuccess = isSuccess;
		this.msg = msg;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
