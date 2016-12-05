package xyxc.wx.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


import cn.springmvc.dao.IBaseDao;
import cn.springmvc.service.impl.TopBaseServiceImpl;
import xyxc.wx.dao.IXcWxUsersDao;
import xyxc.wx.model.XcWxUsersModel;
import xyxc.wx.service.IXcWxUsersService;

/**
 * <b>description</b>：微信用户信息表业务实现<br>
 * <b>time</b>：2016-12-05 21:40:52 <br>
 * <b>author</b>：  percy-chen@hotmail.com
 */
@Service("xcWxUsersService")
public class XcWxUsersServiceImpl extends TopBaseServiceImpl implements IXcWxUsersService {

	/**
	 * 插入
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public XcWxUsersModel insert(XcWxUsersModel model) throws Exception {
		return this.xcWxUsersDao.insertModel(model, true);
	}

	/**
	 * 更新
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public int update(XcWxUsersModel model) throws Exception {
		return this.xcWxUsersDao.updateModel(model, true);
	}

	/**
	 * 根据id删除
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int delete(long id) throws Exception {
		Map<Object, Object> paramMap = new HashMap<Object, Object>();
		paramMap.put("id", id);
		return this.xcWxUsersDao.deleteModel(paramMap, true);
	}

	@Resource
	private IXcWxUsersDao xcWxUsersDao;

	@Override
	public IBaseDao getBaseDao() {
		return this.xcWxUsersDao;
	}

}
