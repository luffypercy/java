package cn.springmvc.session;

/**
 * session服务工程
 * 
 * @author ready
 * 
 */
public class SessionServiceFactory {

	/**
	 * session类型
	 * 
	 * @author ready
	 * 
	 */
	public enum SessionTypeEnum {
		WEBSESSION("普通session"), CLUSTERSESSION("集群session");
		private String name;

		private SessionTypeEnum(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	/**
	 * 获取session服务
	 * 
	 * @param sessionType
	 * @return
	 */
	public static ISessionService getSessionService(SessionTypeEnum sessionType) {
			return WebSessionServiceImpl.getInstance();
	}
}
