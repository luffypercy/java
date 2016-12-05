package cn.springmvc.util;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 字符串工具类
 * 
 * @author Ready 2012-10-17
 */
public class StringUtil {

	/**
	 * 将对象转化为字符串，如果对象为null，则返回null
	 * 
	 * @param obj
	 * @return
	 */
	public static String convertObjToString(Object obj) {
		return obj == null ? null : obj.toString();
	}

	/**
	 * 如果字符串为空，则返回空字符""
	 * 
	 * @param str
	 * @return
	 */
	public static String stringNullToEmpty(String str) {
		return str == null ? "" : str;
	}

	/**
	 * 如果字符串为空，则返回null
	 * 
	 * @param str
	 * @return
	 */
	public static String stringEmptyToNull(String str) {
		if (str == null)
			return null;
		return "".equals(str.trim()) ? null : str.trim();
	}

	public static String getPre(int count, String c) {
		StringBuilder result = new StringBuilder("");
		for (int i = 0; i < count; i++) {
			result.append(c);
		}
		return result.toString();
	}

	public static String disponseStr(String str) {
		if (str != null) {
			return str.trim();
		}
		return str;
	}

	/**
	 * 是否为空，如果为null或者""，返回false，不为空返回true
	 * 
	 * @param target
	 * @return
	 */
	public static boolean isNotEmpty(String target) {
		if (target == null || "".equals(target.trim())) {
			return false;
		}
		return true;
	}

	/**
	 * 判断字符串是否为空，如果为空返回true
	 * 
	 * @param target
	 * @return
	 */
	public static boolean isEmpty(String target) {
		return !isNotEmpty(target);
	}

	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		}
		return !isNotEmpty(obj.toString());
	}

	public static void main(String[] args) {
		System.out.println(fillStringByArgs(
				"您已成功投标${price}元，当前账户可用余额${available}元。", FrameUtil.newHashMap(
						"price", 10000.00, "available", 1090030.03)));
		;
	}

	/**
	 * 替换字符串中的变量。变量的标识符为${}。 例如，模板中${name}变量将会被Map列表中键名为name的键值替换，如果Map列表中不存在所需要
	 * 的键名，则会被替换成空。
	 * 
	 * @param template
	 *            模板
	 * @param data
	 *            参数列表
	 * @return
	 * @throws Exception
	 * @author Brian
	 */
	public static String fillStringByArgs(String str, Map<Object, Object> data) {
		if (isEmpty(str) || data == null) {
			return str;
		}
		String regex = "\\$\\{(.+?)\\}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);

		/*
		 * sb用来存储替换过的内容，它会把多次处理过的字符串按源字符串序 存储起来。
		 */
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			String name = matcher.group(1);// 键名
			String value = null == data.get(name) ? "" : data.get(name)
					.toString();// 键值
			if (value == null) {
				value = "";
			} else {
				value = value.replaceAll("\\$", "\\\\\\$");
			}

			matcher.appendReplacement(sb, value);
		}

		matcher.appendTail(sb);
		return sb.toString();
	}

	public static String cutString(String string, int maxLen, String cutReplace) {
		if (isEmpty(string)) {
			return string;
		} else {
			int len = string.length();
			if (len > maxLen) {
				if (isNotEmpty(cutReplace)) {
					return string.substring(0, maxLen - cutReplace.length())
							+ cutReplace;
				} else {
					return string.substring(0, maxLen);
				}
			} else {
				return string;
			}
		}
	}

	/**
	 * 将list通过concatstr链接起来
	 * 
	 * @param list
	 * @param concatStr
	 * @return
	 */
	public static String concat(List list, String concatStr) {
		if (list != null) {
			StringBuilder s = new StringBuilder();
			int i = 0;
			for (Object object : list) {
				s.append(convertObjToString(object)).append(
						(i++ < list.size() - 1) ? concatStr : "");
			}
			return s.toString();
		}
		return null;

	}

	/**
	 * @Title: replaceChar
	 * @Description: 字符串替换,将orgStr中的每个字符都替换成replaceChar
	 * @param @param orgStr
	 * @param @param replaceChar
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String replaceChar(String orgStr, String replaceChar) {
		if (StringUtil.isEmpty(orgStr) || StringUtil.isEmpty(replaceChar)) {
			return orgStr;
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < orgStr.length(); i++) {
			buffer.append(replaceChar);
		}
		return buffer.toString();
	}

	public static String getRandomString(int length) { // length表示生成字符串的长度
		String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}


	/**
	 * 转换银行卡卡号  **** **** **** 1234
	 * @param bankCode
	 * @return
	 */
	public static String convertBankCode(String bankCode) {
		if (StringUtils.isBlank(bankCode)) {
			return "";
		}
		
		int lh = bankCode.length();
		if(lh<=4){
			return bankCode;
		}
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("**** **** **** ");
		buffer.append(bankCode.substring(lh-4,lh));
		return buffer.toString();
	}
	
}
