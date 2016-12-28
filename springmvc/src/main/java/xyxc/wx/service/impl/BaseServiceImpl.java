package xyxc.wx.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import cn.springmvc.dto.OperParamDto;
import cn.springmvc.service.impl.TopBaseServiceImpl;
import cn.springmvc.util.AdminLogsUtil;
import xyxc.wx.model.AdminLogsModel;
import xyxc.wx.service.IAdminLogsService;

/**
 * <b>description</b>：业务层基类，此类中所有方法不要加事务（AOP不会代理基类中的方法,若要加事务，请在子类中重写父类的方法） <br>
 * <b>time</b>：2014-10-13上午11:30:38 <br>
 * <b>author</b>： ready likun_557@163.com
 */
public abstract class BaseServiceImpl extends TopBaseServiceImpl {

	/**
	 * logger记录系统日志
	 */
	protected Logger logger = Logger.getLogger(getClass());

	@Resource
	protected IAdminLogsService adminLogsService;




	/**
	 * 记录管理员操作记录
	 * 
	 * @param operParamDto
	 * @param msg
	 *            操作信息
	 * @param time
	 *            操作时间
	 * @throws Exception
	 */
	public void adminLog(OperParamDto operParamDto, String msg, Long time)
			throws Exception {
		if (operParamDto != null) {
			this.adminLogsService.insert(AdminLogsUtil.build(operParamDto, msg,
					time));
		}
	}

	/**
	 * 记录管理员操作记录
	 * 
	 * @param adminLogsModel
	 * @throws Exception
	 */
	public void adminLog(AdminLogsModel adminLogsModel) throws Exception {
		this.adminLogsService.insert(adminLogsModel);
	}

}
