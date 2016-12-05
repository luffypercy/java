package cn.springmvc.util;

import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

import cn.springmvc.enums.Constant.PropertiesFileEnum;

/**
 * 资源文件缓存工具类
 * 
 * @author ready
 * 
 */
public class PropertiesCacheUtil {

	static class PropertiesModel {
		private long lastModifyTime;
		private Map<String, Object> prosMap;

		private PropertiesModel(long lastModifyTime, Map<String, Object> prosMap) {
			super();
			this.lastModifyTime = lastModifyTime;
			this.prosMap = prosMap;
		}

		public long getLastModifyTime() {
			return lastModifyTime;
		}

		public void setLastModifyTime(long lastModifyTime) {
			this.lastModifyTime = lastModifyTime;
		}

		public Map<String, Object> getProsMap() {
			return prosMap;
		}

		public void setProsMap(Map<String, Object> prosMap) {
			this.prosMap = prosMap;
		}
	}

	private static Logger logger = Logger.getLogger(PropertiesCacheUtil.class);
	private static Map<String, PropertiesModel> proCachedMap = new HashMap<String, PropertiesModel>();

	/**
	 * 获取某个properties中所有的数据，数据放在map中
	 * 
	 * @param propertiesFile
	 * @return
	 */
	public static Map<String, Object> get(String propertiesFile) {
		File file = getFile(propertiesFile);
		if (proCachedMap.get(propertiesFile) != null
				&& proCachedMap.get(propertiesFile).getLastModifyTime() != file
						.lastModified()) {
			proCachedMap.remove(propertiesFile);
		}
		if (proCachedMap.get(propertiesFile) == null) {
			synchronized (proCachedMap) {
				if (proCachedMap.get(propertiesFile) == null) {
					put(propertiesFile);
				}
			}
		}
		return proCachedMap.get(propertiesFile).getProsMap();
	}

	/**
	 * 获取资源文件指定的文件
	 * 
	 * @param propertiesFile
	 * @return
	 */
	public static File getFile(String propertiesFile) {
		//logger.info(propertiesFile);
		return new File(PropertiesCacheUtil.class.getClassLoader()
				.getResource(propertiesFile).getPath());
	}

	/**
	 * 获取某个资源文件中的key对应的值
	 * 
	 * @param propertiesFile
	 * @param key
	 * @return
	 */
	public static Object getValue(String propertiesFile, String key) {
		return get(propertiesFile).get(key);
	}

	@SuppressWarnings("rawtypes")
	public static Map<String, Object> put(String propertiesFile) {
		try {
			Properties ps = new Properties();
			ps.load(PropertiesCacheUtil.class.getClassLoader()
					.getResourceAsStream(propertiesFile));
			Enumeration enumNames = ps.propertyNames();
			Map<String, Object> map = new HashMap<String, Object>();
			while (enumNames.hasMoreElements()) {
				String name = enumNames.nextElement().toString();
				map.put(name, ps.getProperty(name));
			}
			proCachedMap.put(propertiesFile,
					new PropertiesModel(getFile(propertiesFile).lastModified(),
							map));
			return map;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将propertiesFile文件中的数据转换成json格式
	 * 
	 * @param propertiesFile
	 * @return
	 */
	public static String getJson(String propertiesFile) {
		return JSON.toJSONString(get(propertiesFile));
	}

	public static Map<String, Object> put(String propertiesFile,
			Map<String, Object> map) {
		proCachedMap.put(propertiesFile, new PropertiesModel(0, map));
		return map;
	}

	public static void main(String[] args) {
		System.out.println(getJson(PropertiesFileEnum.CONST.getFilename()));
		System.out.println(PropertiesCacheUtil.getValue(
				PropertiesFileEnum.CONST.getFilename(), ""));
	}
}
