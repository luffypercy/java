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
import xyxc.wx.dao.IAdminLogsDao;
import xyxc.wx.model.AdminLogsModel;
import xyxc.wx.service.IAdminLogsService;

/**
 * <b>description</b>：系统日志业务实现<br>
 * <b>time</b>：2014-11-05 14:06:16 <br>
 * <b>author</b>： ready likun_557@163.com
 */
@Service("adminLogsService")
public class AdminLogsServiceImpl extends BaseServiceImpl implements
		IAdminLogsService {

	/**
	 * 插入
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public AdminLogsModel insert(AdminLogsModel model) throws Exception {
		return this.adminLogsDao.insertModel(model, true);
	}

	/**
	 * 更新
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public int update(AdminLogsModel model) throws Exception {
		return this.adminLogsDao.updateModel(model, true);
	}

	/**
	 * 根据id删除
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int delete(Long id) throws Exception {
		Map<Object, Object> paramMap = new HashMap<Object, Object>();
		paramMap.put("id", id);
		return this.adminLogsDao.deleteModel(paramMap, true);
	}

	/**
	 * 删除一个月之前的数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public int deleteAmonthData(OperParamDto operParamDto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("beforetime", FrameUtil.getTime(DateUtil.addMonth(Calendar
				.getInstance().getTime(), -1)));
		this.adminLog(operParamDto, "删除操作日志", FrameUtil.getTime(null));
		return this.adminLogsDao.deleteModel(paramMap, true);
	}
	

	@Resource
	private IAdminLogsDao adminLogsDao;

	@Override
	public IBaseDao getBaseDao() {
		return this.adminLogsDao;
	}

}
