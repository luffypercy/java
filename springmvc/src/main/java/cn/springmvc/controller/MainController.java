package cn.springmvc.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.springmvc.util.ArithUtil;
import cn.springmvc.util.DateUtil;
import cn.springmvc.util.FrameUtil;
import cn.springmvc.util.ServletUtil;


@Controller
@Scope("prototype")
@RequestMapping("/tt/")
public class MainController extends BaseControl{
	/**
	 * 
	 
	@Resource
	private IXcWxUsersService xcWxUsersService;
	
	@Autowired
	private WeixinProxy weixinProxy ;
	 */
	
	@RequestMapping("index")
	public String index(Double inv, Integer limit, Double apr, String stime,
			Double dis_r, Double L12, Double L6, Double L0, Double pir)
					throws Exception {
		System.out.println(111);
		// List<XcWxUsersModel>
		// users=this.xcWxUsersService.getModelList(FrameUtil.newHashMap(),
		// DbWREnums.WRITE);
		// request.setAttribute("userList", users);
		request.setAttribute("param", ServletUtil.getParameterMap(request));
		if (inv != null
				&& limit != null & apr != null & stime != null & dis_r != null
						& L12 != null & L6 != null & L0 != null & pir != null) {
			request.setAttribute("result",
					work(inv, limit, apr,
							DateUtil.dateStringToTimestamp(stime,
									DateUtil.PATTERN_yyyy_MM_dd),
							dis_r, L12, L6, L0, pir));
		}
		return "index";
	}

	public static Double cal(double P, double A, int m, int scale,
			double s_step, double x, double min, double max) {
		if (x <= 0.0 || s_step <= 0) {
			return 0.0;
		}
		double sc = 1 / (ArithUtil.pow(10.0, scale));
		double y = ArithUtil.div(P, A);
		double x_min = x;
		double temp_min = workY(x_min, m) - y;
		double x_max = x;
		double temp_max = workY(x_max, m) - y;
		// 查找异号区间
		int countT = 0;
		while (temp_min * temp_max > 0) {
			if (x_min > min) {
				x_min -= s_step;
				temp_min = workY(x_min, m) - y;
			} else {
				if (x_max < max) {
					x_max += s_step;
					temp_max = workY(x_max, m) - y;
				}
			}
			countT++;
			if (countT > 1000000000) {
				return null;
			}
		}
		countT = 0;
		while (temp_min * temp_max < 0) {
			double x_temp = (x_min + x_max) / 2;
			double temp = workY(x_temp, m) - y;
			if (temp > 0) {
				x_min = x_temp;
				temp_min = temp;
			} else {
				x_max = x_temp;
				temp_max = temp;
			}
			if (ArithUtil.abs(temp) < sc) {
				x = x_temp;
				break;
			}
			countT++;
			if (countT > 1000000000) {
				return null;
			}
		}
		return x;
	}

	public static Double workY(double x, int m) {
		double result = 0.0;
		double rate = x / 12;
		result = (1 / rate) - (1 / (rate * ArithUtil.pow((1 + rate), m)));
		return result;
	}

	public List<Map<String, Object>> work(double inv, int limit, double apr,
			Long stime, double dis_r, double L12, double L6, double L0,
			double pir) {
		double apr_m = apr / 12;
		double each_month = (inv * apr_m * Math.pow((1 + apr_m), limit))
				/ (Math.pow((1 + apr_m), limit) - 1);
		this.request.setAttribute("each_month", each_month);
		// System.out.println(each_month);
		Calendar endCalendar = Calendar.getInstance(Locale.CHINESE);
		endCalendar.setTimeInMillis(stime * 1000);
		List<Map<String, Object>> resultList = FrameUtil.newArrayList();
		for (int m = 0; m < limit; m++) {
			Calendar thismonth = Calendar.getInstance(Locale.CHINESE);
			thismonth.set(endCalendar.get(Calendar.YEAR),
					endCalendar.get(Calendar.MONTH) + m,
					endCalendar.get(Calendar.DATE));
			Calendar nextmonth = Calendar.getInstance(Locale.CHINESE);
			nextmonth.set(endCalendar.get(Calendar.YEAR),
					endCalendar.get(Calendar.MONTH) + m + 1,
					endCalendar.get(Calendar.DATE));
			Calendar day = Calendar.getInstance(Locale.CHINESE);
			day.set(endCalendar.get(Calendar.YEAR),
					endCalendar.get(Calendar.MONTH) + m,
					endCalendar.get(Calendar.DATE));
			for (int mds = 0; mds < DateUtil.getDaysBetween(thismonth,
					nextmonth); mds++) {
				Map<String, Object> result = FrameUtil.newHashMap();
				day.set(Calendar.DATE, day.get(Calendar.DATE) + 1);
				int lim_now = m + 1;
				result.put("lim_now", lim_now);
				int lim_left = limit - m;
				result.put("lim_left", lim_left);
				int cal_days = mds + 1;
				result.put("cal_days", cal_days);
				// System.out.println(day.getTime()+ " days:"+cal_days+"
				// lim_now:"+lim_now+"lim_left:"+lim_left);
				result.put("transtime",
						DateUtil.timestampToDateString(
								day.getTimeInMillis() / 1000,
								DateUtil.PATTERN_yyyy_MM_dd));
				double base_apr = lim_left >= 12
						? L12
						: lim_left >= 6 ? L6 : L0;
				result.put("base_apr", ArithUtil.round(base_apr, 4));
				double base_apr_m = base_apr / 12;
				double capital = inv * Math.pow((1 + apr_m), lim_now - 1)
						- each_month * (Math.pow((1 + apr_m), lim_now - 1) - 1)
								/ apr_m;
				result.put("capital", ArithUtil.round(capital, 2));
				double fairv = capital
						+ (Math.min(new Double(cal_days), 30.0) / 30.0)
								* capital * apr_m;
				result.put("fairv", ArithUtil.round(fairv, 2));
				double actualp = fairv - capital * dis_r;
				result.put("actualp", ArithUtil.round(actualp, 2));
				double basev = each_month
						* (Math.pow(1 + base_apr_m, lim_left) - 1)
						/ (base_apr_m * Math.pow((1 + base_apr_m), lim_left));
				result.put("basev", ArithUtil.round(basev, 2));
				double profit_arranged = (1 - pir) * (basev - actualp);
				result.put("profit_arranged",
						ArithUtil.round(profit_arranged, 2));
				double price = basev - profit_arranged;
				result.put("price", ArithUtil.round(price, 2));
				// System.out.println("price:"+price+" each_month:"+each_month+"
				// lim_left:"+lim_left);
				double profit_apr = cal(price, each_month, lim_left, 10, 0.1,
						L0, L0, 1000);
				result.put("profit_apr", ArithUtil.round(profit_apr, 6));
				// System.out.println(profit_apr);
				resultList.add(result);
			}
		}
		return resultList;
	}
	
	
	/**
	 * 
	 
	public void test() throws Exception{
		List<User> us=this.weixinProxy.getAllFollowing();
		for(User u:us){
			u=this.weixinProxy.getUser(u.getOpenId());
			XcWxUsersModel xu= this.xcWxUsersService.getModelOne(FrameUtil.newHashMap("openid",u.getOpenId()), DbWREnums.WRITE);
			if(xu==null){
				xu=new XcWxUsersModel();
				xu.copyProperties(u);
				this.xcWxUsersService.insert(xu);
			}else{
				xu.copyProperties(u);
				this.xcWxUsersService.update(xu);
			}
		}
		
	}
	 */
	
}