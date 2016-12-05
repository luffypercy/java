package cn.springmvc.enums;

import java.util.ArrayList;
import java.util.List;

import cn.springmvc.util.StringUtil;



/**
 * 常量集合
 * 
 * @author ready
 * 
 */
public class Constant {
	/**
	 * 数据库类型
	 * 
	 */
	public static class DbTypes {

		/**
		 * 目前支持的数据库类型
		 * 
		 */
		public enum DbTypeEnum {
			ORACLE("oracle"), SQLSERVER("sqlserver"), MYSQL("mysql");
			private String value;

			DbTypeEnum(String value) {
				this.value = value;
			}

			public String getValue() {
				return value;
			}

			public void setValue(String value) {
				this.value = value;
			}
		}

		// 支持的数据库类型列表,目前只支持oracle，sqlserver，mysql
		public static List<String> dbTypeList = new ArrayList<String>();
		static {
			dbTypeList.add(DbTypeEnum.ORACLE.getValue());
			dbTypeList.add(DbTypeEnum.SQLSERVER.getValue());
			dbTypeList.add(DbTypeEnum.MYSQL.getValue());
		}

		/**
		 * 数据库是否是oracle
		 * 
		 * @param dbType
		 * @return
		 */
		public static boolean isOracle(String dbType) {
            return StringUtil.isNotEmpty(dbType)
                    && DbTypeEnum.ORACLE.getValue().equals(dbType);
        }

		/**
		 * 数据库是否是sqlserver
		 * 
		 * @param dbType
		 * @return
		 */
		public static boolean isSqlserver(String dbType) {
            return StringUtil.isNotEmpty(dbType)
                    && DbTypeEnum.SQLSERVER.getValue().equals(dbType);
        }

		/**
		 * 数据库是否是mysql
		 * 
		 * @param dbType
		 * @return
		 */
		public static boolean isMysql(String dbType) {
            return StringUtil.isNotEmpty(dbType)
                    && DbTypeEnum.MYSQL.getValue().equals(dbType);
        }
	}

	/**
	 * 操作结果主要的几个类型(1000：成功，1001：失败，1002：系统异常，1003：没有登录， 1004：无权访问，404:没有找到该页面)
	 * 
	 * @author Ready 2012-10-17
	 */
	public enum ResultEnum {
		// 成功,失败,没有登录,无权限,其他
		ERROR("0", "ERROR"), SUCCESS("1", "SUCCESS"), SYSEXCEPTION("1002",
				"SYSEXCEPTION"), NOLOGIN("1003", "NOLOGIN"), NOPERMISSION(
				"1004", "NOPERMISSION"), NOTFINDPAGE("404", "没有找到该页面");
		private String result;

		private String desc;

		ResultEnum(String result, String desc) {
			this.result = result;
			this.desc = desc;
		}

		public String getResult() {
			return result;
		}

		public void setResult(String result) {
			this.result = result;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		@Override
		public String toString() {
			return this.result;
		}
	}

	/**
	 * 内容类型
	 * 
	 */
	public enum ContentTypeEnum {
		TEXT_HTML("text/html; charset=UTF-8"), TEXT_PLAIN(
				"text/plain; charset=UTF-8"), TEXT_JSON(
				"text/json; charset=UTF-8");
		String value;

		ContentTypeEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	/**
	 * servle中的范围枚举
	 * 
	 * @author Ready 2012-10-17
	 */
	public enum HttpScopeEnum {

		REQUEST("request"), SESSION("session"), APPLICATION("application");
		private String scope;

		HttpScopeEnum(String scope) {
			this.scope = scope;
		}

		public String getScope() {
			return scope;
		}

		public void setScope(String scope) {
			this.scope = scope;
		}
	}

	/**
	 * 系统一些资源文件名称
	 * 
	 * @author ready
	 * 
	 */
	public enum PropertiesFileEnum {

		WEB("web.properties"), CONST("const.properties"), RPM_SERVICE("rpm_service.properties"), DEFAULT(
				"default.properties");

		private String filename;

		public String getFilename() {
			return filename;
		}

		public void setFilename(String filename) {
			this.filename = filename;
		}

		PropertiesFileEnum(String filename) {
			this.filename = filename;
		}
	}

	/**
	 * 文件系统类型
	 * 
	 * @author ready
	 * 
	 */
	public enum FsTypeEnum {
		FTP, WEB
    }

	/**
	 * 文件服务类型
	 * 
	 * @author ready
	 * 
	 */
	public enum FsServiceTypeEnum {
		DEFAULT
    }

	/**
	 * 短信模板编码
	 * 
	 * @author ready
	 * @date 2014-8-7
	 */
	public enum SmsTemplateCodeEnum {
		SMS_TEMPLATE_DEMO("建周短信测试"), SMS_CN_DEMO("中国短信网短信测试"), SMS_TEMPLATE_USER_REGISTOR(
				"用户注册短信"), SMS_TEMPLATE_MOBILE_BIND("手机绑定"), SMS_TEMPLATE_MOBILE_UNBIND(
				"手机解绑"), SMS_TEMPLATE_MOBILE_ADDPAYPWD("设置交易密码"), SMS_TEMPLATE_MOBILE_MODIFYPAYPWD(
				"修改交易密码"), SMS_TEMPLATE_REPAY_NOTICE_TO_INVESTOR("投资人收到还款的短信"), SMS_PASSWORD_REGAIN(
				"密码找回"), SMS_TEMPLATE_INVEST_BORROW("成功投标通知"), SMS_TEMPLATE_AUDIT4_SUCCESS_BORROWER(
				"满标审核通过给借款人发送短信"), SMS_TEMPLATE_AUDIT4_SUCCESS_INVESTOR(
				"满标审核通过给投资人发送短信"), SMS_TEMPLATE_REPAY_SUCCESS_TO_BORROW(
				"借款人每笔还款成功以后给借款人发送的短信"), SMS_TEMPLATE_REPAY_SUCCESS_TO_ADMIN(
				"借款人每笔还款成功以后给领导发送的短信"), SMS_TEMPLATE_REPAY_SUCCESS_TO_INVESTOR_1(
				"借款人每笔还款成功以后给投资人发送的短信(非最后一期)"), SMS_TEMPLATE_REPAY_SUCCESS_TO_INVESTOR_2(
				"借款人每笔还款成功以后给投资人发送的短信(最后一期)"), SMS_TEMPLATE_CREDITOR_TRANSFER_SUCCESS_TO_TRANSFER(
				"债权转让成功以后给转让人发送的短信"), SMS_TEMPLATE_CREDITOR_TRANSFER_SUCCESS_TO_BUYER(
				"债权转让成功以后给购买人发送的短信"), SMS_RECHARGE_TEMPLATE("充值短信"), SMS_TEMPLATE_ADVANCE_SUCCESS_TO_ADMIN(
				"垫付成功以后给领导发送的短信"), SMS_TEMPLATE_URG_EREPAY_NOTICE_TO_ADMIN_0(
				"敦促借款人还款提醒（未逾期情况）"), SMS_TEMPLATE_URG_EREPAY_NOTICE_TO_ADMIN_1(
				"敦促借款人还款提醒（逾期情况）"), SMS_SALARY_TEMPLATE("代发工资"), SMS_SALARY_ACTIVITE_PWD(
				"工资卡激活发送用户登录密码"), SMS_CASH_CHECK_TEMPLATE("提现审核通知"), SMS_CASH_FINISH_TEMPLATE(
				"提现完成通知"), SMS_CASH_REFUSE_TEMPLATE("提现拒绝通知"), SMS_SALARY_ACTIVITE(
				"工资卡激活发送用户通知"), SMS_SALARY_PROFITCOUNT_JOB("工资卡利息统计通知"), SMS_SALARY_PROFITSEND_JOB(
				"工资卡利息发送通知"), WAL_PROFITCOUNT_JOB("小钱包利息统计通知"), GOLD_PROFITCOUNT_JOB("小金库利息统计通知"),
				SMS_TEMPLATE_VOICE("语音短信"),SMS_TEMPLATE_VOICE_NOW("语音短信-立即发送"),
				SMS_TEMPLATE_GENERAL("通用短信"),SMS_TEMPLATE_GENERAL_NOW("通用短信-立即发送"),
				ACTIVITY8888DAY4("8888活动第四天提醒"), ACTIVITY8888DAY8("8888活动第八天提醒"), BORROW_REPAYMENT_NOTICE("提前还款通知"), WAL_OFFLINE_ANNONCE(
				"小钱包下线通知"), WAL_OFFLINE_EXP_ANNONCE("小钱包下线，体验金发放通知");
		SmsTemplateCodeEnum(String description) {
			this.description = description;
		}

		private String description;

		public String getDescription() {
			return description;
		}
	}

	/**
	 * 短信通道编码
	 * 
	 * @author ready
	 * @date 2014-8-7
	 */
	public enum SmsChannelCodeEnum {
		SMS_CHANNEL_JIANZHOU("SMS_CHANNEL_JIANZHOU","建周短信"), SMS_CHANNEL_CN("SMS_CHANNEL_CN","中国短信"), SMS_CHANNEL_WODONGKEJI(
				"SMS_CHANNEL_WODONGKEJI","沃动科技短信"),SMS_CHANNEL_CHUANLAN_YUYIN("SMS_CHANNEL_CHUANLAN_YUYIN","创蓝语音短信"),
				SMS_CHANNEL_MONTNETS("SMS_CHANNEL_MONTNETS","梦网短信");
		SmsChannelCodeEnum(String code, String description) {
			this.description = description;
			this.code=code;
		}

		private String code;
		private String description;

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}

	}

	/**
	 * job的log状态
	 * 
	 * @author ready
	 * @date 2014-8-8
	 */
	public enum JobLogStatusEnum {
		SUCCESS(1), FAIL(0);
		private Integer status;

		JobLogStatusEnum(Integer status) {
			this.status = status;
		}

		public Integer getStatus() {
			return status;
		}
	}

	/**
	 * @ClassName: SysParamKeyEnum
	 * @Description: 系统参数key
	 * @author ready likun_557@163.com
	 * @date 2014-8-15 下午4:06:37
	 */
	public enum SysParamKeyEnum {
		PROJECT_RATE("PROJECT_RATE", "借款项目利率"),
		PROJECT_SERVICES_FEE("PROJECT_SERVICES_FEE", "借款服务费费率"), 
		PROJECT_MANAGE_FEE("PROJECT_MANAGE_FEE", "账户管理费费率"), 
		INVEST_EACH_AMOUNT("INVEST_EACH_AMOUNT", "标的每份金额"), 
		OVERDUE_FINE_PRICE_FEE("OVERDUE_FINE_PRICE_FEE", "逾期罚款利率费率"), 
		OVERDUE_FINE_MANAGE_FEE("OVERDUE_FINE_MANAGE_FEE", "逾期罚款管理费费率"), 
		OVERDUE_IS_ADVANCE("OVERDUE_IS_ADVANCE", "逾期是否垫付"), 
		CREDITOR_TRANSFER_BEFORE_DAY("CREDITOR_TRANSFER_BEFORE_DAY", "债权生效多少天之后可以转让"), 
		CREDITOR_TRANSFER_FEE("CREDITOR_TRANSFER_FEE", "债权转让费率"),
		SMS_SEND_TEST("SMS_SEND_TEST", "是否启用短信发送测试功能"), 
		SMS_SEND_TEST_MOBILE_LIST("SMS_SEND_TEST_MOBILE_LIST", "测试手机号码列表(之间用分号隔开)"), 
		ISPROMOTION("ISPROMOTION", "是否开启邀请奖励"), 
		ISEXTENSION("ISEXTENSION","是否开启了推广奖励"), 
		FINANCIAL_PRICE_RETURN("FINANCIAL_PRICE_RETURN","理财金投资返现基数金额"), 
		FINANCIAL_PRICE_RETURN_EVERY("FINANCIAL_PRICE_RETURN_EVERY", "理财金每次返现金额"), 
		ADMIN_MOBILE_LIST("ADMIN_MOBILE_LIST", "领导手机列表"), 
		MAIL_CONFIG("MAIL_CONFIG","邮件配置"), 
		CREDITOR_TRANSFER_TIMELIMIT("CREDITOR_TRANSFER_TIMELIMIT", "债权转让有效天数(从发起转让到自动取消转让的时间【天】)"), 
		RECHARGE_IP("RECHARGE_IP", "后台充值ip允许列表"), 
		REGISTER_REGEX("REGISTER_REGEX","注册时验证手机号不能做用户名的正则"), 
		AWARD_AMOUNT("AWARD_AMOUNT", "奖励35.00"), 
		SIGNUP_SWITCH("SIGNUP_SWITCH", "签到活动开关(1开启，0关闭)"), 
		SIGNUP_OVERDUE("SIGNUP_OVERDUE", "签到过期时间"), 
		SIGNUP_QUOTIENT("SIGNUP_QUOTIENT","签到一次多少钱（1元乘以该系数）"), 
		MARKET_SIGNATURE("MARKET_SIGNATURE","发送营销短信的签名"), 
		ALLOW_ACCESS_IP_LIST("ALLOW_ACCESS_IP_LIST","功能对外开放的所有ip列表"), 
		PRE_REPAY_FINE_RATE("PRE_REPAY_FINE_RATE","提前还款违约金费率"), 
		AUTHEN_ADDRESS_CHANGE("AUTHEN_ADDRESS_CHANGE","实名认证环境切换"), 
		ACT_2015001_REWARD_TIME1("ACT_2015001_REWARD_TIME1","庆祝医界贷A轮融资活动累计统计投资金额时间段(开始时间和结束时间之间用分号隔开)"), 
		ACT_2015001_REWARD_TIME("ACT_2015001_REWARD_TIME","庆祝医界贷A轮融资活动累计统计投资金额时间段(开始时间和结束时间之间用分号隔开)"), 
		CASHLIMIT("CASHLIMIT", "提现申请限制金额"), 
		CASHAPPLYLIMIT("CASHAPPLYLIMIT","提现申请次数限制"), 
		MSG_CODE_VALID_TIME("MSG_CODE_VALID_TIME","短信验证码有效期"), 
		CASHOUT_FEE("CASHOUT_FEE", "提现申请手续费率"), 
		MOBILE_BANK_LIST("MOBILE_BANK_LIST", "手机支付银行"), 
		PUSH_APP_KEY("PUSH_APP_KEY","推送appkey"),
		PUSH_APP_SECRET("PUSH_APP_SECRET", "推送API主密码"), 
		PUSH_RATE("PUSH_RATE", "推送发送频率(次/分钟)"), 
		ACTIVITY1116_END_TIME("ACTIVITY1116_END_TIME", "1116活动结束日期"), 
		ACTIVITY999_END_TIME("ACTIVITY999_END_TIME", "999活动结束日期"), 
		ACT_2015002_REWARD_TIME("ACT_2015002_REWARD_TIME", "999活动时间段(开始时间和结束时间之间用分号隔开)"), 
		ACTIVITY2015_END_TIME("ACTIVITY2015_END_TIME", "2015新年红包活动结束日期"), 
		AUTO_ADVANCE_ADMIN_MOBILE_LIST("AUTO_ADVANCE_ADMIN_MOBILE_LIST", "自动垫付需要通知的领导手机列表"), 
		ACTIVITY20150318_END_TIME("ACTIVITY20150318_END_TIME", "2015妇女节活动结束日期"), 
		CASH_APPLY_FEE("CASH_APPLY_FEE", "提现应收手续费，建行，工行，农行，招行，兴业这五家是每50万收取2元收费"), 
		CASH_APPLY_FEE2("CASH_APPLY_FEE2", "提现应收手续费，除建行，工行，农行，招行，兴业这五家，每49990收取2元手续费"), 
		CASH_BANK_IDS("CASH_BANK_IDS", "中国建设银行,招商银行,中国工商银行,中国农业银行,兴业银行 ID"), 
		EXTENSION_USER_LIST("EXTENSION_USER_LIST", "58红包推广用户列表"), 
		EXTENSION_AWARD_AMOUNT("EXTENSION_AWARD_AMOUNT", "58红包推广的奖励金额"), 
		RECHARGE_BAOFU_POUNDAGE("RECHARGE_BAOFU_POUNDAGE", "宝付充值手续费费率"), 
		RECHARGE_GOPAY_POUNDAGE("RECHARGE_GOPAY_POUNDAGE", "国付宝充值手续费费率"), 
		RECHARGE_EBANK_POUNDAGE("RECHARGE_EBANK_POUNDAGE", "网银在线充值手续费费率"), 
		RECHARGE_DEDUCTION_POUNDAGE("RECHARGE_DEDUCTION_POUNDAGE", "充值减免手续费比率"), 
		DAILY_PROFIT_RATE("DAILY_PROFIT_RATE", "余额生息年利率"), 
		LCK_START_SERI("LCK_START_SERI", "理财卡号起始序列"), 
		AWARD35_DATE("AWARD35_DATE","微信注册35红包禁用日期区间"), 
		CASH_SPE("CASH_SPE", "借款人提现初审分配给徐婷"),
		REGISTER_GENERALIZE_ON("REGISTER_GENERALIZE_ON", "注册推广页面开关,1:开 2:关"),
		WAL_PROFIT_RATE("WAL_PROFIT_RATE", "小钱包余额生息年利率"),
		WAL_MAX_TRANS_AMT("WAL_MAX_TRANS_AMT", "小钱包里面最大可转入金额"),
		WAL_PROFIT_RATE_ADDON("WAL_PROFIT_RATE_ADDON", "小钱包余额生息年利率(附加利率)"),
		WAL_CASHOUT_MAX_TIMES_BY_DAY("WAL_CASHOUT_MAX_TIMES_BY_DAY","小钱包每日转出次数上限"),
		WAL_TEMP_ACCOUNT("WAL_TEMP_ACCOUNT","小钱包的第三方账户"),
		GOLD_TEMP_ACCOUNT("GOLD_TEMP_ACCOUNT", "医信宝中间账户"),
		WAL_ACTIVITY_1010("WAL_ACTIVITY_1010","小钱包10月10日活动参数"),
		QRCODE_DISPATCH_DOMAIN("QRCODE_DISPATCH_DOMAIN","人人推广二维码域名"),
		SPREAD_MIN_RATE("SPREAD_MIN_RATE","人人推广最小推广费率"),
		SPREAD_MAX_RATE("SPREAD_MAX_RATE","人人推广最大推广费率"),
		PC_APP_RECH_CONF("PC_APP_RECH_CONF", "网站及App充值参数设置"),
		GOLD_PROFIT_RATE("GOLD_PROFIT_RATE","医信宝余额生息年利率"),
		GOLD_MIN_TRANS_AMT("GOLD_MIN_TRANS_AMT","医信宝最少交易金额"),
		GOLD_TRANS_OUT_POUNDAGE("GOLD_TRANS_OUT_POUNDAGE", "转出手续费减免次数"),
		ANNUAL_MEETING_ACTIVITY("ANNUAL_MEETING_ACTIVITY","2016年会活动奖励截止时间"),
		LTK_BANK("LTK_BANK","灵通卡合作银行"),
		LTK_BANK_INFO("LTK_BANK_INFO","灵通卡开户行"),
		FENG_KONG_ROLE("FENG_KONG_ROLE", "风控角色ID"),ARREARS_FORM_ROLE("ARREARS_FORM_ROLE", "可查看所有角色ID，逗号隔开")
		,AUTH_CHANNEL("AUTH_CHANNEL", "第三方实名认证渠道：2.鹏远征信 4.合一道（宝付）"),YJD_APP_USER("YJD_APP_USER","医界贷客户端配置"),
		YJD_ONE_LINE_ACTIVITY("YJD_ONE_LINE_ACTIVITY","医界贷在线活动配置"),YJD_REWARD_RETURN_RATE("YJD_REWARD_RETURN_RATE","投资返现红包比率默认0.01")
		,EASY_BORROW_MANAGE_FEE("EASY_BORROW_MANAGE_FEE","易借卡借款费率"),CREDITOR_LIMIT_ZRZ("CREDITOR_LIMIT_ZRZ","理财大厅中显示转让债权的数量"),
		CREDITOR_TRANSFER_APPLY_COUNT_ON_DAY("CREDITOR_TRANSFER_APPLY_COUNT_ON_DAY","每天允许发起债权转让的次数"),
		ACTIVITY_2016_5_18("ACTIVITY_2016_5_18", "518活动的截止时间和url"),
		BORROW_AUDIT1_ROLE("BORROW_AUDIT1_ROLE","借款初审可分配角色权限"),
		CREDITOR_TRANSFER_NOT_LIMIT_USERID_LIST("CREDITOR_TRANSFER_NOT_LIMIT_USERID_LIST","债权转让不受限制的用户id列表，中间用逗号隔开"),
		ACT_FOR_ON("ACT_FOR_ON","当前进行中的活动"),
		CREDITOR_BASE_APR_1_5("CREDITOR_BASE_APR_1_5","1-5月基准年化率"),
		CREDITOR_BASE_APR_6_11("CREDITOR_BASE_APR_6_11","6-11月基准年化率"),
		CREDITOR_BASE_APR_12("CREDITOR_BASE_APR_12","12月及以上基准年化率"),
		CREDITOR_SHARE_RATE("CREDITOR_SHARE_RATE","债权转让收益分配比例"),
		CREDITOR_SPLIT("CREDITOR_SPLIT","转让人是否可拆分 1可拆分 0不可拆分"),
		TRANSFER_PAGE_MAX("TRANSFER_PAGE_MAX","债权显示的最大页数"),
		CREDITOR_RECOVERY_START("CREDITOR_RECOVERY_START", "债权转让待回收金额最小起步值"),
		CREDITOR_DISC_APR_MAX("CREDITOR_DISC_APR_MAX","折让率上限"),
		CREDITOR_DISC_APR_MIN("CREDITOR_DISC_APR_MIN","折让率下限"),
		BORROW_PROTOCOL_NEW("BORROW_PROTOCOL_NEW", "新的借款协议生效时间"),
		SHARE_AWARD_EXPIRY("SHARE_AWARD_EXPIRY", "分享的返现红包的有效期(单位:分钟)"),
		BORROW_REPAYMENT_RATE("BORROW_REPAYMENT_RATE","提前还款补偿金额系数"),
		AUTH_BANK_LIST("AUTH_BANK_LIST","认证支持的银行列表"),
		ACT_NEW_ANNOUNCEMENT("ACT_NEW_ANNOUNCEMENT","当前进行中的活动公告ID"),
		CREDITOR_DISC_APR_DEFAULT("CREDITOR_DISC_APR_DEFAULT","折让率默认值"),
		ACT_AOYUN_CONFIG("ACT_AOYUN_CONFIG", "奥运活动配置"),
		RETURN_PRICE_RATE("RETURN_PRICE_RATE","返现红包比例"),
		ACCT_BANK_CODE("ACCT_BANK_CODE","银存账户编码"),
		ACCT_TRANSFER_MONEY("ACCT_TRANSFER_MONEY","公司自有资金－调拨资金"),	
		FINANCE_RETURN_PRICE_RATE("FINANCE_RETURN_PRICE_RATE","理财返现红包比例"),
		ACT_AOYUN_LUCKY_PACKAGE("ACT_AOYUN_LUCKY_PACKAGE","奥运理财礼包"),
		AUTO_INVEST_OPEN_TIME("AUTO_INVEST_OPEN_TIME","自动投标开启时间(分钟)"),
		AUTO_INVEST_MAX_PERCENT("AUTO_INVEST_MAX_PERCENT","自动投标最大占比(%)"),
		AUTO_INVEST_MAX_PRICE("AUTO_INVEST_MAX_PRICE","单次投资上限(元)"),
		AUTO_INVEST_ON_OFF("AUTO_INVEST_ON_OFF","自动投标开关"),
		AUTO_INVEST_WHITE_LIST("AUTO_INVEST_WHITE_LIST","自动投标白名单"),
		MARKET_SMS_CHANNEL("MARKET_SMS_CHANNEL","营销短信发送渠道"),
		QUESTION_END_TIME("QUESTION_END_TIME","调查问卷戒指时间"),
		FENG_KONG_ADMIN_ROLE("FENG_KONG_ADMIN_ROLE", "风控主管角色ID"),
		ACCESS_FLAG("ACCESS_FLAG","分控审核评语开关"),
		LCK_CASH_KEY("LCK_CASH_KEY","理财卡体现手续费银行关键词"),
		LCK_CASH_PRICE1("LCK_CASH_PRICE1","招商银行、中国工商银行、中国建设银行、中国农业银行、兴业银行  提现手续费金额"),
		LCK_CASH_PRICE2("LCK_CASH_PRICE2","以上5家银行，其它银行提现手续费金额"),
		CREDITOR_TRANSFER_PORTION("CREDITOR_TRANSFER_PORTION","是否按份数购买债权(1可以，0不可以)"),
		DEBET_USER_ID("DEBET_USER_ID","逾期还款用户id");
		private String value;
		private String description;

		SysParamKeyEnum(String value, String description) {
			this.value = value;
			this.description = description;
		}

		public String getDescription() {
			return description;
		}

		public String getValue() {
			return value;
		}
	}

	/**
	 * @ClassName: BorrowTypeEnum
	 * @Description: 借款项目类型
	 * @author ready likun_557@163.com
	 * @date 2014-8-15 上午9:20:57
	 */
	public enum BorrowTypeEnum {
		TYPE1(1, "精英圆梦贷"), TYPE2(2, "发展助业贷"), TYPE3(3, "尊贵白金贷"), TYPE4(4, "易借贷");
		private Integer type;
		private String name;

		BorrowTypeEnum(Integer type, String name) {
			this.type = type;
			this.name = name;
		}

		public Integer getType() {
			return type;
		}

		public String getName() {
			return name;
		}

	}

	/**
	 * <b>description</b>：借款审批状态 <br>
	 * <b>time</b>：2014-8-22 下午7:14:54 <br>
	 * <b>author</b>： ready likun_557@163.com
	 */
	public enum BorrowApprovalStatusEnum {
		tongguo(1, "通过"), huitui(2, "回退"), jujue(3, "拒绝");
		private Integer status;
		private String description;

		BorrowApprovalStatusEnum(Integer status, String description) {
			this.status = status;
			this.description = description;
		}

		public Integer getStatus() {
			return status;
		}

		public void setStatus(Integer status) {
			this.status = status;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
	}

	/**
	 * <b>description</b>：满标待审状态 <br>
	 * <b>time</b>：2014-8-22 下午7:14:54 <br>
	 * <b>author</b>： ready likun_557@163.com
	 */
	public enum FullBidApprovalStatusEnum {
		tongguo(1, "放标"), jujue(3, "流标");
		private Integer status;
		private String description;

		FullBidApprovalStatusEnum(Integer status, String description) {
			this.status = status;
			this.description = description;
		}

		public Integer getStatus() {
			return status;
		}

		public void setStatus(Integer status) {
			this.status = status;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
	}

	/**
	 * 用户联系人类型
	 * 
	 * @ClassName: UserContactDirectTypeEnum
	 * @Description: TODO
	 * @author ready likun_557@163.com
	 * @date 2014-8-20 下午9:07:31
	 */
	public enum UserContactDirectTypeEnum {
		kinship(1, "亲属"), other(2, "其他联系人");
		private Integer directType;
		private String description;

		UserContactDirectTypeEnum(Integer directType, String description) {
			this.directType = directType;
			this.description = description;
		}

		public Integer getDirectType() {
			return directType;
		}

		public String getDescription() {
			return description;
		}
	}

	/**
	 * @ClassName: IdentityVerifyCertTypeEnum
	 * @Description: 实名认证类型
	 * @author ready likun_557@163.com
	 * @date 2014-8-21 上午10:41:44
	 */
	public enum IdentityVerifyCertTypeEnum {
		idcard(1, "身份证");
		private Integer certType;
		private String description;

		IdentityVerifyCertTypeEnum(Integer certType, String description) {
			this.certType = certType;
			this.description = description;
		}

		public Integer getCertType() {
			return certType;
		}

		public String getDescription() {
			return description;
		}
	}

	/**
	 * <b>description</b>：投标是否有效 <br>
	 * <b>time</b>：2014-8-22 下午7:14:54 <br>
	 * <b>author</b>： ready likun_557@163.com
	 */
	public enum InvestEnableEnum {
		YES(1, "有效"), NO(2, "无效");
		private Integer enable;
		private String description;

		InvestEnableEnum(Integer enable, String description) {
			this.enable = enable;
			this.description = description;
		}

		public Integer getEnable() {
			return enable;
		}

		public String getDescription() {
			return description;
		}
	}

	/**
	 * <b>description</b>：费用计算方式 <br>
	 * <b>time</b>：2014-8-22 下午7:14:54 <br>
	 * <b>author</b>： ready likun_557@163.com
	 */
	public enum CalcModeEnum {
		SAVE_CAPITAL_INTEREST(1, "等额本息"), ON_MONTH_INTEREST(2, "每月付息，到期还本");
		private Integer mode;
		private String description;

		CalcModeEnum(Integer mode, String description) {
			this.mode = mode;
			this.description = description;
		}

		public Integer getMode() {
			return this.mode;
		}

		public String getDescription() {
			return description;
		}

		public static CalcModeEnum getCalcModeEnum(Integer mode) {
			if (mode == null) {
				return null;
			} else {
				CalcModeEnum[] ems = CalcModeEnum.values();
				for (CalcModeEnum calcModeEnum : ems) {
					if (calcModeEnum.getMode().equals(mode)) {
						return calcModeEnum;
					}
				}
				return null;
			}
		}
	}

	/**
	 * <b>description</b>：是否有逾期记录 <br>
	 * <b>time</b>：2014-8-22 下午7:14:54 <br>
	 * <b>author</b>： ready likun_557@163.com
	 */
	public enum HasOverdueEnum {
		NO(0, "否"), YES(1, "是");
		private Integer value;
		private String description;

		HasOverdueEnum(Integer value, String description) {
			this.value = value;
			this.description = description;
		}

		public Integer getValue() {
			return value;
		}

		public String getDescription() {
			return description;
		}
	}

	/**
	 * <b>description</b>：投资中的资金来源 <br>
	 * <b>time</b>：2014-8-22 下午7:14:54 <br>
	 * <b>author</b>： ready likun_557@163.com
	 */
	public enum CapitalSourceTypeEnum {
		balance(1, "可用余额账户"), voucher(2, "充值券"), reward(3, "奖金金额");
		CapitalSourceTypeEnum(Integer value, String description) {
			this.value = value;
			this.description = description;
		}

		// 值
		private Integer value;
		// 描述
		private String description;

		public Integer getValue() {
			return value;
		}

		public String getDescription() {
			return description;
		}
	}

	public static String PAYORDER_RESULT_CODE = "resultCode";
	public static String PAYORDER_RESULT_MSG = "resultMsg";
}
