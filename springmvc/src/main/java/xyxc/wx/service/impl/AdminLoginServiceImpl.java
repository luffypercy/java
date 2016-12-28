package xyxc.wx.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.springmvc.dao.IBaseDao;
import cn.springmvc.dto.OperParamDto;
import cn.springmvc.util.DateUtil;
import cn.springmvc.util.FrameUtil;
import xyxc.wx.dao.IAdminLoginDao;
import xyxc.wx.model.AdminLoginModel;
import xyxc.wx.service.IAdminLoginService;


/**
 * <b>description</b>：登陆记录业务实现<br>
 * <b>time</b>：2014-11-07 09:33:55 <br>
 * <b>author</b>： ready likun_557@163.com
 */
@Service("adminLoginService")
public class AdminLoginServiceImpl extends BaseServiceImpl implements
		IAdminLoginService {

	/**
	 * 插入
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public AdminLoginModel insert(AdminLoginModel model) throws Exception {
		return this.adminLoginDao.insertModel(model, true);
	}

	/**
	 * 更新
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public int update(AdminLoginModel model) throws Exception {
		return this.adminLoginDao.updateModel(model, true);
	}

	/**
	 * 根据id删除
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int delete(Long id) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		return this.adminLoginDao.deleteModel(paramMap, true);
	}

	/**
	 * 删除一个月之前的数据
	 * 
	 * @param operParamDto
	 * 
	 * @return
	 * @throws Exception
	 */
	public int deleteAmonthData(OperParamDto operParamDto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("beforeLastlogin", FrameUtil.getTime(DateUtil.addMonth(
				Calendar.getInstance().getTime(), -1)));
		this.adminLog(operParamDto, "删除登陆日志", FrameUtil.getTime(null));
		return this.adminLoginDao.deleteModel(paramMap, true);
	}

	@Resource
	private IAdminLoginDao adminLoginDao;

	@Override
	public IBaseDao getBaseDao() {
		return this.adminLoginDao;
	}

}
