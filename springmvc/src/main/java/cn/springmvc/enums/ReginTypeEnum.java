package cn.springmvc.enums;

/**
 * <b>description</b>：区间类型 <br>
 * <b>time</b>：2014-10-21下午2:58:52 <br>
 * <b>author</b>： ready likun_557@163.com
 */
public enum ReginTypeEnum {
	R1(1, "(最小值,最大值)"), R2(2, "[最小值,最大值)"), R3(3, "(最小值,最大值]"), R4(4,
			"[最小值,最大值]");
	private ReginTypeEnum(Integer value, String description) {
		this.value = value;
		this.description = description;
	}

	/**
	 * 值
	 */
	private Integer value;
	/**
	 * 描述
	 */
	private String description;

	public Integer getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}

}
