package cn.springmvc.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;

/**
 * 日期转换工具类,常用的日期转换方法都有
 * 
 * @author Ready 2012-10-17
 */
public class DateUtil {
	public static final String PATTERN_yyyy_MM_dd = "yyyy-MM-dd";
	public static final String PATTERN_yyyyMM = "yyyyMM";
	public static final String PATTERN_yyyyMMdd = "yyyyMMdd";
	public static final String PATTERN_MMdd = "MM-dd";
	public static final String PATTERN_yyMMdd = "yy/MM/dd";
	public static final String PATTERN_yyyyMMddHHmmss = "yyyyMMddHHmmss";
	public static final String PATTERN_yyyyMMddHHmmssSSS = "yyyyMMddHHmmssSSS";
	public static final String PATTERN_yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
	public static final String PATTERN_yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";
	public static final String PATTERN_HH_mm_ss = "HH:mm:ss";
	
	public static final int ONE_HOUR_SECOND = 3600;
	public static final long DAY_IN_SECOND = 86400;
	public static final long DAY_IN_MILLISECOND = 86400000;

	public static Date addDay(Date date, int day) {
		return org.apache.commons.lang.time.DateUtils.addDays(date, day);
	}

	public String getDurationOfTwoTime(Calendar start, Calendar end) {
		String str = "";
		Long duration = ((end.getTime().getTime() - start.getTime().getTime())) / 1000;

		if (duration > 0) {

		}
		return str;
	}

	public static Date addWeek(Date date, int week) {
		return org.apache.commons.lang.time.DateUtils.addWeeks(date, week);
	}

	public static Date addMonth(Date date, int month) {
		return org.apache.commons.lang.time.DateUtils.addMonths(date, month);
	}

	public static Date getDate(Date date) {
		return parseDate(format(date, "yyyyMMdd"), "yyyyMMdd");
	}

	/**
	 * 获取系统当前日期
	 * 
	 * @return
	 */
	public static Date getCurrentDate() {
		return Calendar.getInstance().getTime();
	}

	public static Date addTime(Date date, int hours, int mins, int seconds) {
		Date d = org.apache.commons.lang.time.DateUtils.addHours(date, hours);
		d = org.apache.commons.lang.time.DateUtils.addMinutes(d, mins);
		return org.apache.commons.lang.time.DateUtils.addSeconds(d, seconds);
	}

	/**
	 * 获得当前开始活参数秒的时间日期
	 * 
	 * @Title: getDateAfter
	 * @Description:
	 * @param
	 * @return Date 返回类型
	 * @throws
	 */
	public static Date getDateAfter(int second) {
		Calendar cal = Calendar.getInstance();
//		System.out.println(DateUtil.format(cal.getTime(), PATTERN_yyyy_MM_dd_HH_mm_ss));
		cal.add(Calendar.SECOND, second);
//		System.out.println(DateUtil.format(cal.getTime(), PATTERN_yyyy_MM_dd_HH_mm_ss));
		return cal.getTime();
	}

	public static Date getPreMonthDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, month - 1);
		return calendar.getTime();
	}

	public static Date getPreYearDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		calendar.set(Calendar.YEAR, year - 1);
		return calendar.getTime();
	}

	public static int get(Date date, int field) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(field);
	}

	/**
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static Date parseDate(String dateString, String pattern) {
		try {
			return org.apache.commons.lang.time.DateUtils.parseDate(dateString,
					new String[] { pattern });
		} catch (ParseException e) {
			return null;
		}

	}

	/**
	 * 
	 * @param date
	 * @param patterns
	 * @return
	 */
	public static Date parseDate(String dateString, String[] patterns) {
		try {
			return org.apache.commons.lang.time.DateUtils.parseDate(dateString,
					patterns);
		} catch (ParseException e) {
			return null;
		}

	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static Timestamp parseDateToTimestamp(String dateString) {
		if (dateString == null || "".equals(dateString))
			return null;
		try {
			Date date = parseDate(dateString, PATTERN_yyyy_MM_dd);
			java.sql.Timestamp dateTime = new java.sql.Timestamp(date.getTime());
			return dateTime;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static String formatTimestamp(Timestamp t, String sFmt) {
		if (t == null)
			return "";
		t.setNanos(0);
		DateFormat ft = new SimpleDateFormat(sFmt);
		String str = "";
		try {
			str = ft.format(t);
		} catch (NullPointerException e) {
		}
		return str;

	}

	/**
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public static Date newDate(int year, int month, int date) {
		return parseDate("" + year + month + date, month >= 10 ? "yyyyMMdd"
				: "yyyyMdd");
	}

	/**
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public static String format(int year, int month, int date, String pattern) {
		return DateFormatUtils.format(newDate(year, month, date), pattern);
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		return format(date, PATTERN_yyyy_MM_dd_HH_mm);
	}

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static String format(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		return DateFormatUtils.format(date, pattern);
	}

	/**
	 * compare the two dates, and return the subtraction between d1 and d2(d1 -
	 * d2) result > 0 when d1 > d2 and result < 0 when d1 < d2
	 * 
	 * @param d1
	 *            Date
	 * @param d2
	 *            Date
	 * @return int
	 */
	public static int compareDateOnDay(Date d1, Date d2) {
		if (d1.getTime() == d2.getTime())
			return 0;
		d1 = org.apache.commons.lang.time.DateUtils.truncate(d1, Calendar.DATE);
		d2 = org.apache.commons.lang.time.DateUtils.truncate(d2, Calendar.DATE);
		long l1 = d1.getTime();
		long l2 = d2.getTime();
		return (int) ((l1 - l2) / DAY_IN_MILLISECOND);
	}

	/**
	 * compare the two dates, and return the subtraction between the dates'
	 * month always return > 0
	 * 
	 * @param d1
	 *            Date
	 * @param d2
	 *            Date
	 * @return int
	 */
	public static int compareDateOnMonth(Date d1, Date d2) {
		if (d1.getTime() == d2.getTime()) {
			return 0;
		}
		int flag = -1;
		// compare the dates, and put the smaller before
		if (d1.getTime() > d2.getTime()) {
			Date temp = d1;
			d1 = d2;
			d2 = temp;
			flag = 1;
		}
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);
		int y1 = c1.get(Calendar.YEAR);
		int y2 = c2.get(Calendar.YEAR);
		int month1 = c1.get(Calendar.MONTH);
		int month2 = c2.get(Calendar.MONTH);
		int months = 0;
		if (y1 == y2) {

			months = month2 - month1;

		} else {

			months = (y2 - y1 - 1) * 12 + (12 - month1) + month2;

		}

		return months * flag;
	}

	/**
	 * judge the year whether is leap year
	 * 
	 * @param year
	 *            int year
	 * @return boolean
	 */
	public static boolean isLeapYear(int year) {
		if ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))) {
			return true;
		}
		return false;

	}

	/**
	 * return the days of the year gevin
	 * 
	 * @param year
	 *            int year
	 * @return int
	 */
	public static int getYearDays(int year) {
		if (isLeapYear(year)) {
			return 366;
		}
		return 365;
	}

	/**
	 * judge whether the two dates are in same day
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameDay(Date date1, Date date2) {
		return org.apache.commons.lang.time.DateUtils.isSameDay(date1, date2);
	}

	public static Date truncate(Date d, int field) {
		return org.apache.commons.lang.time.DateUtils.truncate(d, field);
	}

	public static boolean isLastDayOfMonth(Date date) {
		return isFirstDayOfMonth(addDay(date, 1));
	}

	public static boolean isFirstDayOfMonth(Date date) {
		return get(date, Calendar.DAY_OF_MONTH) == 1;
	}

	// add
	public static Date getLastMonthDay(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}

	public static Date getLastMonthDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}

	public static Date getFirstMonthDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}

	public static Date getFirstMonthDay(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}

	@SuppressWarnings("static-access")
	public static Date getFirstWeekDay(int year, int week) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.WEEK_OF_YEAR, week);
		calendar.set(calendar.DAY_OF_WEEK, 1);
		return addDay(calendar.getTime(), 1);
	}

	@SuppressWarnings("static-access")
	public static Date getFirstWeekDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.DAY_OF_WEEK, 1);
		return addDay(calendar.getTime(), 1);
	}

	@SuppressWarnings("static-access")
	public static Date getLastWeekDay(int year, int week) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.WEEK_OF_YEAR, week);
		calendar.set(calendar.DAY_OF_WEEK, 7);
		return addDay(calendar.getTime(), 1);
	}

	@SuppressWarnings("static-access")
	public static Date getLastWeekDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.DAY_OF_WEEK, 7);
		return addDay(calendar.getTime(), 1);
	}

	public static int getDayOfWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.SUNDAY);
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	public static int getWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.WEEK_OF_YEAR);
	}

	public static int getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH) + 1;
	}

	public static int getYear(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);
	}

	public static int getInterval(Date d1, Date d2) {
		int betweenDays = 0;
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);
		// 保证第二个时间一定大于第一个时间
		if (c1.after(c2)) {
			c1 = c2;
			c2.setTime(d1);
		}
		int betweenYears = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);
		betweenDays = c2.get(Calendar.DAY_OF_YEAR)
				- c1.get(Calendar.DAY_OF_YEAR);
		for (int i = 0; i < betweenYears; i++) {
			c1.set(Calendar.YEAR, (c1.get(Calendar.YEAR) + 1));
			betweenDays += c1.getMaximum(Calendar.DAY_OF_YEAR);
		}

		return betweenDays;
	}

	public static List<Date> getDateList(Date startDate, Date endDate) {
		List<Date> dates = new ArrayList<Date>();

		int betweenDays = DateUtil.getInterval(startDate, endDate);
		for (int i = 0; i <= betweenDays; i++) {
			Date day = DateUtil.addDay(startDate, i);

			if (day.getTime() >= startDate.getTime()
					&& day.getTime() <= endDate.getTime()) {
				dates.add(day);
			}
		}

		return dates;
	}

	public static int getMonthInterval(Date startDate, Date endDate) {
		int betweenMonths = 0;
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTime(startDate);
		c2.setTime(endDate);
		// 保证第二个时间一定大于第一个时间
		if (c1.after(c2)) {
			c1 = c2;
			c2.setTime(startDate);
		}

		int y1 = c1.get(Calendar.YEAR);
		int y2 = c2.get(Calendar.YEAR);

		int m1 = c1.get(Calendar.MONTH);
		int m2 = c2.get(Calendar.MONTH);

		if (y2 > y1) {
			betweenMonths += (y2 - y1) * 12;
		}
		betweenMonths += (m2 - m1);

		return betweenMonths;
	}

	public static int getWeekInterval(Date startDate, Date endDate) {
		int betweenWeeks = 0;
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTime(startDate);
		c2.setTime(endDate);
		// 保证第二个时间一定大于第一个时间
		if (c1.after(c2)) {
			c1 = c2;
			c2.setTime(startDate);
		}

		int y1 = c1.get(Calendar.YEAR);
		int y2 = c2.get(Calendar.YEAR);

		int w1 = c1.get(Calendar.WEEK_OF_YEAR);
		int w2 = c2.get(Calendar.WEEK_OF_YEAR);

		betweenWeeks += (w2 - w1);
		int betweenYears = y2 - y1;
		for (int i = 0; i < betweenYears; i++) {
			c1.set(Calendar.YEAR, (c1.get(Calendar.YEAR) + 1));
			betweenWeeks += c1.getMaximum(Calendar.WEEK_OF_YEAR);
		}

		return betweenWeeks;
	}

	public static int getDaysBetween(java.util.Calendar d1,
			java.util.Calendar d2) {
		if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
			java.util.Calendar swap = d1;
			d1 = d2;
			d2 = swap;
		}
		int days = d2.get(java.util.Calendar.DAY_OF_YEAR)
				- d1.get(java.util.Calendar.DAY_OF_YEAR);
		int y2 = d2.get(java.util.Calendar.YEAR);
		if (d1.get(java.util.Calendar.YEAR) != y2) {
			d1 = (java.util.Calendar) d1.clone();
			do {
				days += d1.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);
				d1.add(java.util.Calendar.YEAR, 1);
			} while (d1.get(java.util.Calendar.YEAR) != y2);
		}
		return days;
	}

	/**
	 * 将时间戳转换为日期,timemillis不能为空和0
	 * 
	 * @param timemillis
	 * @return
	 */
	public static Date timestampToDate(Long timemillis) {
		if (timemillis == null || timemillis == 0) {
			return null;
		}
		return new Date(timemillis * 1000);
	}

	/**
	 * 将时间戳转换为日期,timemillis不能为空
	 * 
	 * @param timemillis
	 * @return
	 */
	public static Date timestampToDate1(Long timemillis) {
		if (timemillis == null) {
			return null;
		}
		return new Date(timemillis * 1000);
	}

	/**
	 * 
	 * 将秒转换为天
	 * 
	 * @param seconds
	 * @return
	 */
	public static int secodsToDay(long seconds) {
		return (int) (seconds / DateUtil.DAY_IN_SECOND);
	}

	public static long getTimestampToTimestamp(long time, String pattern) {
		return FrameUtil.getTime(DateUtil.parseDate(
				DateUtil.format(DateUtil.timestampToDate1(time), pattern),
				pattern));
	}

	/**
	 * 将时间戳转换为制定格式的日期，timemillis不能为null和0
	 * 
	 * @param timemillis
	 * @param pattern
	 * @return
	 */
	public static String timestampToDateString(Long timemillis, String pattern) {
		if (timemillis == null || timemillis == 0
				|| StringUtil.isEmpty(pattern)) {
			return null;
		}
		return DateUtil.format(timestampToDate(timemillis), pattern);
	}

	/**
	 * 将时间戳转换为制定格式的日期，timemillis可以为0
	 * 
	 * @param timemillis
	 * @param pattern
	 * @return
	 */
	public static String timestampToDateString1(Long timemillis, String pattern) {
		if (timemillis == null || StringUtil.isEmpty(pattern)) {
			return null;
		}
		return DateUtil.format(timestampToDate(timemillis), pattern);
	}

	/**
	 * 将时间戳转换为制定格式的日期,timemillis不能为空和0
	 * 
	 * @param timemillis
	 * @param pattern
	 * @return
	 */
	public static String timestampToDateString(Long timemillis) {
		if (timemillis == null || timemillis == 0) {
			return null;
		}
		return DateUtil.format(timestampToDate(timemillis),
				PATTERN_yyyy_MM_dd_HH_mm_ss);
	}

	/**
	 * 将时间戳转换为制定格式的日期,timemillis可以为0
	 * 
	 * @param timemillis
	 * @param pattern
	 * @return
	 */
	public static String timestampToDateString1(Long timemillis) {
		if (timemillis == null) {
			return null;
		}
		return DateUtil.format(timestampToDate(timemillis),
				PATTERN_yyyy_MM_dd_HH_mm_ss);
	}

	/**
	 * 获取日期字符串对应的时间戳
	 * 
	 * @param dateString
	 * @param pattern
	 * @return
	 */
	public static long dateStringToTimestamp(String dateString, String pattern) {
		return DateUtil.parseDate(dateString, pattern).getTime() / 1000;
	}

	public static String switchToWeekDay(int day) {
		switch (day - 1) {
		case 0:
			return "星期日";

		case 1:
			return "星期一";

		case 2:
			return "星期二";

		case 3:
			return "星期三";

		case 4:
			return "星期四";

		case 5:
			return "星期五";

		case 6:
			return "星期六";

		default:
			return "";
		}
	}

	/**
	 * 距离当前时间的分钟数格式化显示
	 * 
	 * @param timemillis
	 * @return String
	 */
	public static String getSeconds(long timemillis) {
		long currentTime = FrameUtil.getTime();
		long bettween = currentTime - timemillis;
		long day = bettween / (24*60*60*1000);
		long hour = (bettween / (60 * 60) - day * 24);
		long min = ((bettween / 60) - day * 24 * 60 - hour * 60);
		long seconds = bettween - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60;
		StringBuffer sb = new StringBuffer();
		if (day > 0){
			sb.append(day + "天");
			return DateUtil.formatTimeToStr(timemillis, DateUtil.PATTERN_HH_mm_ss);
		}else if (hour > 0){
			sb.append(hour + "小时");
			return hour + "小时前";
		}else if(min > 0){
			sb.append(min + "分");
			return min + "分钟前";
		}else {
			sb.append(seconds + "秒 ");
			return seconds + "秒前";
		}
	}
	
	
	public static void main(String[] args) {
	    
	    try {
		System.out.println(getCurrDateFristSecond());
	    } catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}

	/**
	 * 
	 * @param duration
	 *            格式为 hh:MM:ss:FF
	 * @return 返回总帧数 1秒25帧
	 */
	public static int getFrameFromString(String hhmmssStringduration) {
		int frame = 0;
		String[] str = hhmmssStringduration.split(":");
		// hh:MM:ss:FF
		Calendar cal = Calendar.getInstance();
		Long start = cal.getTime().getTime();
		cal.add(Calendar.HOUR, Integer.valueOf(str[0]));
		cal.add(Calendar.MINUTE, Integer.valueOf(str[1]));
		cal.add(Calendar.SECOND, Integer.valueOf(str[2]));
		Long end = cal.getTime().getTime();
		frame = ((end.intValue() - start.intValue()) / 1000) * 25
				+ Integer.valueOf(str[3]).intValue();
		return frame;
	}

	public static String getEndTiemFormDuartion(String playDate,
			String startDate, Long duration) {
		String[] str = startDate.split(":");
		// 格式化
		for (int i = 0; i < str.length; i++) {
			String s = str[i];
			if (s.length() == 1) {
				s = "0" + s;
			}
			str[i] = s;
		}
		String tmpStartDate = str[0] + ":" + str[1] + ":" + str[2];
		@SuppressWarnings("unused")
		String frame = str[3];

		long hour = duration / 90000;
		duration = duration % 90000;

		long min = duration / 1500;
		duration = duration % 1500;

		long sec = duration / 25;
		duration = duration % 25;

		Calendar cal = Calendar.getInstance();
		Date dt = DateUtil.parseDate(playDate + " " + tmpStartDate,
				DateUtil.PATTERN_yyyy_MM_dd_HH_mm_ss);
		cal.setTime(dt);
		cal.add(Calendar.HOUR, Integer.valueOf(String.valueOf(hour)));
		cal.add(Calendar.MINUTE, Integer.valueOf(String.valueOf(min)));
		cal.add(Calendar.SECOND, Integer.valueOf(String.valueOf(sec)));
		String tmp = DateUtil.format(cal.getTime(), DateUtil.PATTERN_HH_mm_ss);
		tmp = tmp + ":" + duration;
		return tmp;
	}

	/**
	 * 返回 1970-01-01 00:00:00
	 * 
	 * @return
	 */
	public static Date getBeginingDate() {
		return parseDate("1970-01-01 00:00:00", PATTERN_yyyy_MM_dd_HH_mm_ss);
	}

	public static String getHHmmssStringFormDuartion(Long duration) {
		long hour = duration / 90000;
		duration = duration % 90000;

		long min = duration / 1500;
		duration = duration % 1500;

		long sec = duration / 25;
		duration = duration % 25;

		String dur = ":0";
		if (duration.intValue() < 10) { // duration 是各位
			dur = dur + duration;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR, Integer.valueOf(String.valueOf(hour)));
		cal.set(Calendar.MINUTE, Integer.valueOf(String.valueOf(min)));
		cal.set(Calendar.SECOND, Integer.valueOf(String.valueOf(sec)));
		String tmp = DateUtil.format(cal.getTime(), DateUtil.PATTERN_HH_mm_ss);
		tmp = tmp + dur;
		return tmp;
	}

	/**
	 * 转换字符类型yyyy-MM-dd HH:mm:ss.ttt 转换为yyyy-MM-dd HH:mm:ss
	 * 
	 * @param target
	 * @return
	 */
	public static String strDateFormat(String target) {
		if (target == null || "".equals(target.trim())) {
			return "";
		} else {
			Calendar c = Calendar.getInstance();

			try {
				c.setTime(new SimpleDateFormat(PATTERN_yyyy_MM_dd_HH_mm_ss)
						.parse(target));

			} catch (ParseException e) {

				e.printStackTrace();
			}

			return format(c.getTime());
		}
	}

	/**
	 * @Title: parseLongToDate
	 * @Description: 将long类型的日期转换成日期
	 * @param @param time
	 * @param @param format
	 * @param @return 设定文件
	 * @return Date 返回类型
	 * @throws
	 */
	public static Date parseLongToDate(Long time, String format) {
		if (StringUtil.isEmpty(format)) {
			format = DateUtil.PATTERN_yyyyMMddHHmmssSSS;
		}
		return DateUtil.parseDate(time.toString(), format);
	}

	/**
	 * 
	 * @Title: formatTimeToStr
	 * @Description: 将time类型的
	 * @param @param time
	 * @param @param format
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String formatTimeToStr(Long time, String format) {
		if (time == null) {
			return null;
		}
		if (StringUtil.isEmpty(format)) {
			format = DateUtil.PATTERN_yyyyMMddHHmmssSSS;
		}
		return DateUtil.format(parseLongToDate(time, format), format);
	}

	/*
	 * 将秒转化X小时X分钟X秒
	 */
	public static String formatTime(long sec) {

		int mi = 60;
		int hh = mi * 60;
		int dd = hh * 24;

		long day = sec / dd;
		long hour = (sec - day * dd) / hh;
		long minute = (sec - day * dd - hour * hh) / mi;
		long second = (sec - day * dd - hour * hh - minute * mi);
		StringBuilder builder = new StringBuilder();
		if (day >= 1) {
			builder.append(day).append("天");
		}
		if (hour >= 1) {
			builder.append(hour).append("小时");
		}
		if (minute >= 1) {
			builder.append(minute).append("分");
		}
		if (second >= 0) {
			builder.append(second).append("秒");
		}
		return builder.toString();
	}

	public static int getBetweenDay(Date d1, Date d2) {
		Date dt1 = DateUtil.parseDate(
				DateUtil.format(d1, DateUtil.PATTERN_yyyyMMdd),
				DateUtil.PATTERN_yyyyMMdd);
		Date dt2 = DateUtil.parseDate(
				DateUtil.format(d2, DateUtil.PATTERN_yyyyMMdd),
				DateUtil.PATTERN_yyyyMMdd);
		return Integer.parseInt(((dt2.getTime() - dt1.getTime())
				/ DateUtil.DAY_IN_MILLISECOND + ""));
	}

	/**
	 * 获取两个timestemp之间的天数
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int getTimeStempBetweenDay(long d1, long d2) {
		return getBetweenDay(DateUtil.timestampToDate1(d1),
				DateUtil.timestampToDate1(d2));
	}

	/**
	 * 获取时间戳的YYYYMMDD的日期时间戳
	 * 
	 * @param timestamp
	 * @return
	 */
	public static Long getTimeDayByTimestamp(Long timestamp) {
		return Long.valueOf(FrameUtil.getTime(DateUtil.parseDate(DateUtil
				.format(DateUtil.timestampToDate(timestamp),
						DateUtil.PATTERN_yyyyMMdd), DateUtil.PATTERN_yyyyMMdd))
				+ "");
	}

	public static Long getNextDateLongtime() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime().getTime() / 1000L;
	}

	public static String getAge(Date birthDay) throws Exception {
		if (birthDay != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(birthDay);
			return (Calendar.getInstance().get(Calendar.YEAR) - c
					.get(Calendar.YEAR)) + "";
		}
		return "";
	}
	

	
	/**
	 * 获取当前日期 23:59:59 时间
	 * @return
	 * @throws Exception
	 */
	public static Long getCurrDateLastSecond()throws Exception{
	    Calendar ca = Calendar.getInstance();    
	    ca.set(Calendar.HOUR_OF_DAY, 23);
	    ca.set(Calendar.MINUTE, 59);
	    ca.set(Calendar.SECOND, 59);
	    ca.set(Calendar.MILLISECOND, 999);
	    return ca.getTime().getTime() / 1000L;
	}
	
	/**
	 * 获取当前日期 00:00:00 时间
	 * @return
	 * @throws Exception
	 */
	public static Long getCurrDateFristSecond()throws Exception{
	    Calendar ca = Calendar.getInstance();    
	    ca.set(Calendar.HOUR_OF_DAY, 0);
	    ca.set(Calendar.MINUTE, 0);
	    ca.set(Calendar.SECOND, 0);
	    ca.set(Calendar.MILLISECOND, 0);
	    return ca.getTime().getTime() / 1000L;
	}
}