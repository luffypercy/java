package cn.springmvc.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import cn.springmvc.enums.Constant.PropertiesFileEnum;
import cn.springmvc.enums.Constant.ResultEnum;
import cn.springmvc.enums.ReginTypeEnum;
import cn.springmvc.exception.BaseException;
import cn.springmvc.model.PagerJson;
import cn.springmvc.model.PagerModel;

/**
 * <b>description</b>：框架工具类 <br>
 * <b>time</b>：2014-6-17下午3:00:38 <br>
 * <b>author</b>： ready likun_557@163.com
 */
public class FrameUtil {

	private static Logger logger = Logger.getLogger(FrameUtil.class);

	public static final String DEFAULTRESOURCEFILENAME = PropertiesFileEnum.DEFAULT.getFilename();

	/**
	 * 拷贝文件
	 * 
	 * @param stream
	 * @param file
	 * @throws IOException
	 */
	public static void copyInputStreamToFile(InputStream stream, File file) throws IOException {
		FileUtils.copyInputStreamToFile(stream, file);
	}

	/**
	 * 读取文件内容，太大的文件不要读
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static byte[] readFile(String filePath) throws Exception {
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		InputStream is = null;
		try {
			is = new FileInputStream(filePath);
			byte[] b = new byte[4048];
			int i = 0;
			while ((i = is.read(b)) != -1) {
				bs.write(b, 0, i);
			}
		} finally {
			if (is != null) {
				is.close();
			}
		}
		return bs.toByteArray();
	}

	/**
	 * 读取一个文件内容
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static List<String> readFile(File file) throws Exception {
		List<String> result = new ArrayList<String>();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			String s = null;
			while ((s = reader.readLine()) != null) {
				result.add(s);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return result;
	}

	/**
	 * 将某个对象以json格式输出到制定的流
	 * 
	 * @param obj
	 * @param stream
	 */
	public static void outJson(Object obj, PrintStream stream) {
		stream.println(JSON.toJSONString(obj, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue));
	}

	/**
	 * 获取志愿文件中key对应的值
	 * 
	 * @param propertiesFileName
	 * @param key
	 * @param param
	 * @return
	 */

	public static String getResourceByFile(String propertiesFileName, String key, Object... param) {
		return getResource(propertiesFileName, key, converArrayToList(param));
	}

	/**
	 * 获取资源文件中key对应的值
	 * 
	 * @param label
	 * @param propertiesFileName
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getResource(String propertiesFileName, String key, List param) {
		String value = "";
		try {
			Object obj = PropertiesCacheUtil.getValue(propertiesFileName, key);
			if (obj != null) {
				value = obj.toString();
				if (param != null) {
					for (int i = 0; i < param.size(); i++) {
						Object val = param.get(i);
						if (val != null) {
							String reg = "\\{" + i + "\\}";
							value = value.replaceAll(reg, val.toString());
						}
					}
				}
			} else {
				value = key;
			}
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
		}
		return value;
	}
	
	/**
	 * 资源文件是否存在
	 * @param propertiesFileName
	 * @return
	 */
	public static boolean propertiesFileIsExists(String propertiesFileName){
		return PropertiesCacheUtil.class.getClassLoader().getResource(
				propertiesFileName) != null;
	}

	/**
	 * @Title: getResource
	 * @Description: 获取resource_default.properties文件的数据
	 * @param @param key
	 * @param @param param
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getResource(String key, Object... param) {
		return getResource(DEFAULTRESOURCEFILENAME, key, converArrayToList(param));
	}

	/**
	 * @Title: getConstResource
	 * @Description: 获取const.properties文件的数据
	 * @param @param key
	 * @param @param param
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getConstResource(String key, Object... param) {
		return getResource(PropertiesFileEnum.CONST.getFilename(), key, converArrayToList(param));
	}

	/**
	 * @Title: getWebResource
	 * @Description: 获取web.properties文件的数据
	 * @param @param key
	 * @param @param param
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getWebResource(String key, Object... param) {
		return getResource(PropertiesFileEnum.WEB.getFilename(), key, converArrayToList(param));
	}

	/**
	 * 将数组转换成list
	 * 
	 * @param objs
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List converArrayToList(Object... objs) {
		List list = null;
		if (objs != null) {
			list = new ArrayList();
			for (Object object : objs) {
				list.add(object);
			}
		}
		return list;
	}

	/**
	 * 跳转到404页面
	 * @throws Exception 
	 */
	public static void toPage404() throws Exception {
		throw new BaseException(ResultEnum.NOTFINDPAGE, "很不幸，你访问的资源不存在");
	}

	/**
	 * 抛出操作异常
	 * 
	 * @param referer
	 *            跳转的页面
	 * @param msgKey
	 *            操作异常信息在properties中对于的key
	 * @param param
	 *            param将替换resource.properties中key对于的value中的占位符的值
	 * @throws Exception 
	 * @throws OperateException
	 */
	public static void throwBaseException(String referer, String msgKey, Object... param) throws Exception {
		BaseException e = new BaseException(ResultEnum.ERROR, getResource(msgKey, param));
		e.setReferer(referer);
		throw e;
	}

	/**
	 * 抛出操作异常
	 * 
	 * @param msgKey
	 *            操作异常信息在properties中对于的key
	 * @param param
	 *            param将替换resource.properties中key对于的value中的占位符的值
	 * @throws Exception 
	 * @throws OperateException
	 */
	public static void throwBaseException1(String msgKey, Object... param) throws Exception {
		throw new BaseException(ResultEnum.ERROR, getResource(msgKey, param));
	}

	/**
	 * @throws Exception 
	 * @Title: throwBaseException2
	 * @Description:抛出操作异常
	 * @param @param msgKey 操作异常信息在properties中对于的key
	 * @param @param code 状态码
	 * @param @param param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public static void throwBaseException2(String msgKey, String code, Object... param) throws Exception {
		throw new BaseException(ResultEnum.ERROR, code, getResource(msgKey, param));
	}

	/**
	 * 抛出异常或者做跳转
	 * 
	 * @param resultEnum
	 * @param code
	 * @param msgKey
	 * @param param
	 * @throws Exception 
	 */
	public static void throwBaseException3(ResultEnum resultEnum, String code, String msgKey, Object... param) throws Exception {
		throw new BaseException(resultEnum, code, getResource(msgKey, param));
	}

	/**
	 * 抛出异常或者做跳转
	 * 
	 * @param resultEnum
	 * @param code
	 * @param msgKey
	 * @param param
	 * @throws Exception 
	 */
	public static void throwBaseException4(ResultEnum resultEnum, String code, String referer, String msgKey, Object... param) throws Exception {
		BaseException baseException = new BaseException(resultEnum, code, getResource(msgKey, param));
		baseException.setReferer(referer);
		throw baseException;
	}

	/**
	 * 将PagerModel对象转换为PagerJson对象
	 * 
	 * @param pagerModel
	 * @return
	 */
	public static PagerJson convertPagerToPagerJson(PagerModel pagerModel) {
		PagerJson pagerJson = new PagerJson();
		if (pagerModel != null) {
			pagerJson.setPage(pagerModel.getCurrentPage());
			pagerJson.setTotal(pagerModel.getCount());
			pagerJson.setRows(pagerModel.getDataList());
		}
		return pagerJson;
	}

	/**
	 * 生成固定长度的字符，不足指定长度，用addPre进行补偿
	 * 
	 * @param target
	 *            原字符
	 * @param length
	 *            长度
	 * @param addPre
	 *            需要补偿的字符
	 * @param isLeft
	 *            指定是否在左边补偿，如果为false表示在右边补偿
	 * @return
	 */
	public static String generateCode(String target, int length, String addPre, boolean isLeft) {
		if (StringUtil.isNotEmpty(target) && target.length() < length) {
			int plus = length - target.length();
			StringBuilder pre = new StringBuilder();
			for (int i = 0; i < plus; i++) {
				pre.append(addPre);
			}
			return isLeft ? (pre.append(target).toString()) : (target + pre.toString());
		}
		return target;
	}

	/**
	 * 超过固定长度显示省略号
	 * 
	 * @param s
	 * @param len
	 * @return
	 */
	public static String ellipsis(String s, int len) {
		if (StringUtil.isNotEmpty(s) && len >= 1 && s.length() > len) {
			return s.substring(0, len) + "...";
		}
		return s;
	}

	/**
	 * 将一个javabean对象转换为map
	 * 
	 * @param keyFieldMap
	 *            map的键和javaben的field映射
	 * @param javabean
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	public static Map<Object, Object> javaBeanToMap(Map<String, String> keyFieldMap, Object javabean) throws Exception {
		if (javabean == null) {
			return null;
		} else {
			Map<Object, Object> map = new LinkedHashMap<Object, Object>();
			Class javabeanClass = javabean.getClass();
			if (keyFieldMap != null) {
				Set<Map.Entry<String, String>> set = keyFieldMap.entrySet();
				for (Map.Entry<String, String> entry : set) {

					Field field = getClassField(entry.getValue(), javabeanClass);
					if (!field.isAccessible()) {
						field.setAccessible(true);
					}
					map.put(entry.getKey(), field.get(javabean));
				}
			} else {
				Field[] fields = getClassAndParentFields(javabeanClass);
				for (Field field : fields) {
					if (!field.isAccessible()) {
						field.setAccessible(true);
					}
					map.put(field.getName(), field.get(javabean));
				}
			}
			return map;
		}
	}
	
	/**
	 * 将一个javabean对象转换为map
	 * 
	 * @param keyFieldMap
	 *            map的键和javaben的field映射
	 * @param javabean
	 * @return
	 * @throws Exception
	 */
	public static Map javaBeanToMap(Object javabean) throws Exception {
		return javaBeanToMap(null, javabean);
	}

	/**
	 * 获取一个class的某个字段，一直追溯到顶级父class对象
	 * 
	 * @param name
	 * @param cs
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Field getClassField(String fieldName, Class cs) {
		for (; cs != Object.class; cs = cs.getSuperclass()) {
			try {
				Field field = cs.getDeclaredField(fieldName);
				if (field != null) {
					return field;
				}
				field = cs.getField(fieldName);
				if (field != null) {
					return field;
				}
			} catch (Exception e) {
			}
		}
		return null;
	}

	/**
	 * 获取一个class对象的所有属性，包括私有以及所有父类的
	 * 
	 * @param cs
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Field[] getClassAndParentFields(Class cs) {
		List<Field> list = new ArrayList<Field>();
		for (; cs != Object.class; cs = cs.getSuperclass()) {
			Field[] fields = cs.getDeclaredFields();
			for (Field field : fields) {
				list.add(field);
			}
		}
		Field[] fs = new Field[list.size()];
		return list.toArray(fs);
	}

	/**
	 * 获取一个类中的某个字段
	 * 
	 * @param cs
	 * @param name
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Field getField(Class cs, String name) {
		for (; cs != Object.class; cs = cs.getSuperclass()) {
			try {
				Field field = cs.getDeclaredField(name);
				if (field != null) {
					return field;
				}
			} catch (Exception e) {
			}
		}
		return null;
	}

	/**
	 * 通过反射设置某个对象属性的值
	 * 
	 * @param target
	 *            目标对象
	 * @param fieldName
	 *            字段
	 * @param fieldValue
	 *            字段值
	 * @throws Exception
	 */
	public static void setField(Object target, String fieldName, Object fieldValue) throws Exception {
		Field field = getField(target.getClass(), fieldName);
		if (field != null) {
			if (!field.isAccessible()) {
				field.setAccessible(true);
			}
			field.set(target, fieldValue);
		}
	}

	/**
	 * 通过反射获取某个对象属性的值
	 * 
	 * @param target
	 *            目标对象
	 * @param fieldName
	 *            字段
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getField(Object target, String fieldName) throws Exception {
		Field field = getField(target.getClass(), fieldName);
		if (field != null) {
			if (!field.isAccessible()) {
				field.setAccessible(true);
			}
			return (T) field.get(target);
		}
		return null;
	}

	/**
	 * 获取一个class对象的某个方法
	 * 
	 * @param cs
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Method getClassAndParentMethod(Class cs, String name, Class... parameterTypes) throws Exception {
		for (; cs != Object.class; cs = cs.getSuperclass()) {
			Method method = cs.getDeclaredMethod(name, parameterTypes);
			if (method != null) {
				return method;
			}
		}
		return null;
	}

	/**
	 * 将字符串数组转换成sql字符串
	 * 
	 * @param strings
	 * @return
	 */
	public static String arrayToSql(String[] strings) {
		if (strings == null) {
			return null;
		}
		List<String> list = new ArrayList<String>();
		for (String string : strings) {
			if (StringUtil.isNotEmpty(string)) {
				list.add(string);
			}
		}
		StringBuilder result = new StringBuilder();
		int tagCodeLen = list.size();
		for (int j = 0; j < tagCodeLen; j++) {
			result.append("'").append(list.get(j)).append((j != tagCodeLen - 1) ? "'," : "'");
		}
		return result.toString();
	}

	/**
	 * 将list通过cat连接
	 * 
	 * @param list
	 * @param cat
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String arrayConcat(List list, String cat) {
		return arrayConcat(list, cat, true);
	}

	/**
	 * 将list通过cat连接
	 * 
	 * @param list
	 * @param cat
	 * @param ismark
	 *            是否在两端加引号
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String arrayConcat(List list, String cat, boolean ismark) {
		if (list == null) {
			return null;
		}
		StringBuilder result = new StringBuilder();
		int len = list.size();
		for (int j = 0; j < len; j++) {
			if (ismark) {
				result.append("'").append(list.get(j)).append("'").append((j != len - 1) ? cat : "");
			} else {
				result.append(list.get(j)).append((j != len - 1) ? cat : "");
			}
		}
		return result.toString();
	}

	/**
	 * 将字符串数组转换成sql字符串
	 * 
	 * @param strings
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String arrayToSql(List list) {
		if (list == null) {
			return null;
		}
		List<Object> tempList = new ArrayList<Object>();
		for (Object object : list) {
			if (object != null) {
				tempList.add(object);
			}
		}
		StringBuilder result = new StringBuilder();
		int tagCodeLen = list.size();
		for (int j = 0; j < tagCodeLen; j++) {
			Object item = list.get(j);
			if (isNaNType(item)) {
				result.append("").append(list.get(j)).append((j != tagCodeLen - 1) ? "," : "");
			} else {
				result.append("'").append(list.get(j)).append((j != tagCodeLen - 1) ? "'," : "'");
			}
		}
		return result.toString();
	}

	/**
	 * 是否是基本数据类型
	 * 
	 * @param object
	 * @return
	 */
	public static boolean isNaNType(Object object) {
		if (object == null) {
			return false;
		}
		if ((object instanceof Byte) || (object instanceof Integer) || (object instanceof Float) || (object instanceof Double) || (object instanceof Long)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断一个字符串是否是一个数字，最大支持18位
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		if (StringUtil.isEmpty(str)) {
			return false;
		}
		Pattern pattern = Pattern.compile("-*\\d{0,18}\\.?\\d*");
		return pattern.matcher(str).matches();
	}

	public static String json(Object object) {
		return json(object, false);
	}

	public static String json(Object object, boolean format) {
		if (format) {
			return JSON.toJSONString(object, SerializerFeature.WriteNonStringKeyAsString, SerializerFeature.WriteNullStringAsEmpty,
					SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue, SerializerFeature.PrettyFormat);
		} else {
			return JSON.toJSONString(object, SerializerFeature.WriteNonStringKeyAsString, SerializerFeature.WriteNullStringAsEmpty,
					SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteMapNullValue);
		}
	}

	/**
	 * 生成随机密码
	 * 
	 * @param digits
	 *            生产验证码的位数
	 * @return
	 */
	public static String getValidateCode(int digits) {
		Integer result = null;
		if (digits <= 0) {
			return null;
		}
		do {
			Random random = new Random();
			result = random.nextInt(1000000);
			if (result.toString().length() == digits) {
				return result.toString();
			}
		} while (true);
	}

	/**
	 * 将字符串转换为Long对象，如果不能转换，则返回空
	 * 
	 * @param s
	 * @return
	 */
	public static Long parseStrToLong(String s) {
		if (StringUtil.isEmpty(s)) {
			return null;
		}
		if (FrameUtil.isNumeric(s)) {
			try {
				return Long.parseLong(s);
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}

	/**
	 * 查看正则表达式是否匹配matchStr
	 * 
	 * @param regEx
	 * @param matchStr
	 * @return
	 */
	public static boolean isMatchReg(String regEx, String matchStr) {
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(matchStr);
		return mat.find();
	}

	/**
	 * 获取正则匹配的数据，然后截取从beginCut，尾部戒掉lastCut个字符
	 * 
	 * @param regEx
	 * @param input
	 * @param beginCut
	 * @param lastCut
	 * @return
	 */
	public static List<String> getRegMathStr(String regEx, String input, int beginCut, int lastCut) {
		List<String> list = getRegMathStr(regEx, input);
		if (list != null) {
			int index = 0;
			for (String s : list) {
				list.set(index++, s.substring(beginCut, s.length() - lastCut));
			}
		}
		return list;
	}

	/**
	 * 将middleRegEx匹配的部分返回
	 * 
	 * @param startRegEx
	 * @param middleRegEx
	 * @param endRegEx
	 * @param input
	 * @return
	 */
	public static List<String> getRegMathStr(String startRegEx, String middleRegEx, String endRegEx, String input) {
		return getRegMathStr(startRegEx + middleRegEx + endRegEx, input, startRegEx.length(), endRegEx.length());
	}

	/**
	 * 获取正则匹配的数据
	 * 
	 * @param regEx
	 * @param input
	 * @return
	 */
	public static List<String> getRegMathStr(String regEx, String input) {
		List<String> list = null;
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(input);
		while (mat.find()) {
			if (list == null) {
				list = new ArrayList<String>();
			}
			list.add(mat.group());
		}
		return list;
	}

	public static Long parseLong(Object object) {
		if (object == null) {
			return null;
		}
		return Long.valueOf(object.toString());
	}

	/**
	 * @Title: getTime
	 * @Description: 获取日期的时间
	 * @param @param date 若date为空，返回当前时间
	 * @param @return 设定文件
	 * @return Long 返回类型
	 * @throws
	 */
	public static Long getTime(Date date) {
		if (date == null) {
			date = Calendar.getInstance().getTime();
		}
		return date.getTime() / 1000L;
	}

	/**
	 * 获取1997年到当前时间的秒
	 * 
	 * @date 2015年11月17日 下午12:53:20
	 * @return Long
	 */
	public static Long getTime() {
		return getTime(Calendar.getInstance().getTime());
	}

	/**
	 * @Title: parseLongToDate
	 * @Description: 将long类型的日期转换成日期
	 * @param @param time
	 * @param @return 设定文件
	 * @return Date 返回类型
	 * @throws
	 */
	public static Date parseLongToDate(Long time) {
		return DateUtil.parseDate(time.toString(), DateUtil.PATTERN_yyyyMMddHHmmssSSS);
	}

	/**
	 * 
	 * @Title: formatTimeToStr
	 * @Description: 将time类型的
	 * @param @param time
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String formatTimeToStr(Long time) {
		return DateUtil.format(parseLongToDate(time), DateUtil.PATTERN_yyyyMMddHHmmssSSS);
	}

	/**
	 * @Title: isRegin
	 * @Description: 判断一个值是否在一个区间之内
	 * @param @param targetValue 目标值
	 * @param @param leftValue 区间左值
	 * @param @param rightValue 区间右值
	 * @param @param reginType 区间类型，1:(),2:[),3:(],4:[]
	 * @param @return 设定文件
	 * @return boolean 返回类型
	 * @throws
	 */
	public static boolean isRegin(Double targetValue, Double leftValue, Double rightValue, int reginType) {
		if (leftValue == null) {
			leftValue = Double.MIN_VALUE;
		}
		if (rightValue == null) {
			rightValue = Double.MAX_VALUE;
		}
		if (reginType == 1) {
			return targetValue > leftValue && targetValue < rightValue;
		} else if (reginType == 2) {
			return targetValue >= leftValue && targetValue < rightValue;
		} else if (reginType == 3) {
			return targetValue > leftValue && targetValue <= rightValue;
		} else if (reginType == 4) {
			return targetValue >= leftValue && targetValue <= rightValue;
		}
		return false;
	}

	/**
	 * @Title: isReginL
	 * @Description: 判断一个值是否在一个区间之内
	 * @param @param targetValue 目标值
	 * @param @param leftValue 区间左值
	 * @param @param rightValue 区间右值
	 * @param @param reginType 区间类型，1:(),2:[),3:(],4:[]
	 * @param @return 设定文件
	 * @return boolean 返回类型
	 * @throws
	 */
	public static boolean isReginL(Long targetValue, Long leftValue, Long rightValue, int reginType) {
		if (leftValue == null) {
			leftValue = Long.MIN_VALUE;
		}
		if (rightValue == null) {
			rightValue = Long.MAX_VALUE;
		}
		if (reginType == 1) {
			return targetValue > leftValue && targetValue < rightValue;
		} else if (reginType == 2) {
			return targetValue >= leftValue && targetValue < rightValue;
		} else if (reginType == 3) {
			return targetValue > leftValue && targetValue <= rightValue;
		} else if (reginType == 4) {
			return targetValue >= leftValue && targetValue <= rightValue;
		}
		return false;
	}

	/**
	 * 四舍五入
	 * 
	 * @param val
	 *            原始数字
	 * @param prec
	 *            保留小数位数
	 * @return
	 */
	public static double round(Double val, int prec) {
		if (val == null) {
			return 0;
		}
		BigDecimal bg = new BigDecimal(val);
		return bg.setScale(prec, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 创建hashMap
	 * 
	 * @param args
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map newHashMap(Object... args) {
		HashMap<Object, Object> paramMap = new HashMap<Object, Object>();
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				paramMap.put(args[i], args[++i]);
			}
		}
		return paramMap;
	}

	/**
	 * 创建LinkedHashMap
	 * 
	 * @param args
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map newLinkedHashMap(Object... args) {
		LinkedHashMap<Object, Object> paramMap = new LinkedHashMap<Object, Object>();
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				paramMap.put(args[i], args[++i]);
			}
		}
		return paramMap;
	}

	public static void main(String[] args) {
		System.out.println(Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
		System.out.println(FrameUtil.getTime(null));
		System.out.println(FrameUtil.json(newHashMap("a", "1", "b", "2", "c", "3")));
		System.out.println(DateUtil.getTimestampToTimestamp(FrameUtil.getTime(null), DateUtil.PATTERN_yyyy_MM_dd));
		System.out.println(generateCode("987654321000", 10, "0", true));
		System.out.println(removeDuplicateWithOrder(FrameUtil.newArrayList(1, 1, 1, 2, 5, 4)));
	}

	/**
	 * 创建arraylist
	 * 
	 * @param args
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List newArrayList(Object... args) {
		ArrayList list = new ArrayList();
		if (args != null) {
			for (int i = 0; i < args.length; i++) {
				list.add(args[i]);
			}
		}
		return list;
	}

	/**
	 * list去重
	 * 
	 * @param list
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List removeDuplicateWithOrder(List list) {
		if (list == null) {
			return list;
		}
		List newList = new ArrayList();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (!newList.contains(element)) {
				newList.add(element);
			}
		}
		return newList;
	}

	/**
	 * 解决get方式传值乱码问题
	 * 
	 * @param str
	 * @return
	 */
	public static String decodeStr(String str) {
		if (StringUtil.isNotEmpty(str)) {
			try {
				return new String(str.getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
		}
		return str;
	}
	
	/**
	 * 转为map 
	* @date 2016年1月25日 上午11:38:42
	* @param list
	* @param field
	* @return
	* @throws Exception
	* Map
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map toMap(Collection list, String field) throws Exception{
		if (list != null) {
			Map result = new HashMap();
			for (Object object : list) {
				Field fd = FrameUtil.getClassField(field, object.getClass());
				fd.setAccessible(true);
				result.put(fd.get(object), object);
			}
			return result;
		}
		return null;
	}
	
	/**
	 * 获取list集合中javabean对于的field的值的集合
	 * 
	 * @param list
	 * @param field
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getBeanFieldValues(Collection list, String field) throws Exception {
		if (list != null) {
			List result = new ArrayList();
			for (Object object : list) {
				Field fd = getClassField(field, object.getClass());
				fd.setAccessible(true);
				result.add(fd.get(object));
			}
			return result;
		}
		return null;
	}

	/**
	 * @Title: getRandomNumber
	 * @Description: 获取数字验证码
	 * @param length
	 * @return String
	 */
	public static String getRandomNumber(int length) {
		if (length <= 0) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append((int) Math.floor(Math.random() * 10));
		}
		return sb.reverse().toString();
	}

	/**
	 * 将数组转换成List
	 * 
	 * @param array
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List arrayToList(Object array) {
		if (array != null && array.getClass().isArray()) {
			List list = new ArrayList();
			int size = Array.getLength(array);
			for (int i = 0; i < size; i++) {
				list.add(Array.get(array, i));
			}
			return list;
		}
		return null;
	}

	/**
	 * 
	 * 格式化yyyymmdd格式日期
	 * 
	 * @param birthday
	 * @return
	 */
	public static String formatBirth(Long birthday) {
		if (birthday == null) {
			return null;
		}
		StringBuilder dateb = new StringBuilder(birthday + "");
		if (dateb.length() < 8) {
			return null;
		}
		dateb.insert(4, "-");
		dateb.insert(7, "-");
		return dateb.toString();
	}

	/**
	 * 判断targetValue是否在leftvalue和rightvalue所在的区间之内
	 * 
	 * @param target
	 * @param start
	 * @param end
	 * @return
	 */
	public static boolean inRegin(long targetValue, Long leftValue, Long rightValue, ReginTypeEnum reginType) {
		if (leftValue == null) {
			leftValue = Long.MIN_VALUE;
		}
		if (rightValue == null) {
			rightValue = Long.MAX_VALUE;
		}
		if (reginType == ReginTypeEnum.R1) {
			return targetValue > leftValue && targetValue < rightValue;
		} else if (reginType == ReginTypeEnum.R2) {
			return targetValue >= leftValue && targetValue < rightValue;
		} else if (reginType == ReginTypeEnum.R3) {
			return targetValue > leftValue && targetValue <= rightValue;
		} else if (reginType == ReginTypeEnum.R4) {
			return targetValue >= leftValue && targetValue <= rightValue;
		}
		return false;
	}

	/**
	 * 数据库是否全部切到主库操作
	 * 
	 * @return
	 */
	public static boolean dbAllOperateToMaster() {
		return "TRUE".equalsIgnoreCase(FrameUtil.getConstResource("db_all_operate_to_master"));
	}
	
	public static Long[] long_array_str_2_long_array(String long_array) {
		Long[] ids = null;
		if (StringUtil.isNotEmpty(long_array) && long_array.length() > 2) {
			long_array = long_array.replaceAll("\\s+","");
			long_array = long_array.substring(1, long_array.length() - 1);
			String[] long_arr = long_array.split(",");
			ids = new Long[long_arr.length];
			for (int i = 0; i < long_arr.length; i++) {
				if(long_arr[i].length()>1){
					ids[i] = Long.parseLong(long_arr[i]);
				}else{
					ids[i] = 0L;
				}
			}
		}
		return ids;
	}
	
}
