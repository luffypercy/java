package cn.springmvc.enums;

/**
 * <b>description</b>：db的操作类型，操作主库还是从库 <br>
 * <b>time</b>：2015-5-7上午10:02:27 <br>
 * <b>author</b>： ready likun_557@163.com
 */
public enum DbWREnums {

	WRITE(true,"主库"),READ(false,"从库");
	private boolean write;
	private String description;
	
	private DbWREnums(boolean write, String description) {
		this.write = write;
		this.description = description;
	}
	public boolean isWrite() {
		return write;
	}
	public String getDescription() {
		return description;
	}
}
