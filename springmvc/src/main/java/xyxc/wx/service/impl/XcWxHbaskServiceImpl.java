package xyxc.wx.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.springmvc.dao.IBaseDao;
import cn.springmvc.service.impl.TopBaseServiceImpl;
import xyxc.wx.dao.IXcWxHbaskDao;
import xyxc.wx.model.XcWxHbaskModel;
import xyxc.wx.service.IXcWxHbaskService;

/**
 * <b>description</b>：微信用户信息表业务实现<br>
 * <b>time</b>：2016-12-05 22:17:13 <br>
 * <b>author</b>：  percy-chen@hotmail.com
 */
@Service("xcWxHbaskService")
public class XcWxHbaskServiceImpl extends TopBaseServiceImpl implements IXcWxHbaskService {

	/**
	 * 插入
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public XcWxHbaskModel insert(XcWxHbaskModel model) throws Exception {
		return this.xcWxHbaskDao.insertModel(model, true);
	}

	/**
	 * 更新
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public int update(XcWxHbaskModel model) throws Exception {
		return this.xcWxHbaskDao.updateModel(model, true);
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
		return this.xcWxHbaskDao.deleteModel(paramMap, true);
	}

	@Resource
	private IXcWxHbaskDao xcWxHbaskDao;

	@Override
	public IBaseDao getBaseDao() {
		return this.xcWxHbaskDao;
	}

}
