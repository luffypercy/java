package cn.springmvc.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import cn.springmvc.dao.IBaseDao;
import cn.springmvc.enums.DbWREnums;
import cn.springmvc.model.Model;
import cn.springmvc.model.PagerModel;
import cn.springmvc.service.IBaseService;
import cn.springmvc.service.ICustomPager;
import cn.springmvc.util.FrameUtil;
import cn.springmvc.util.PagerUtil;

/**
 * <b>description</b>：业务层基类，此类中所有方法不要加事务（AOP不会代理基类中的方法,若要加事务，请在子类中重写父类的方法） <br>
 * <b>time</b>：2014-10-13上午11:30:38 <br>
 * <b>author</b>： ready likun_557@163.com
 */
public abstract class TopBaseServiceImpl implements IBaseService {

	/**
	 * logger记录系统日志
	 */
	protected Logger logger = Logger.getLogger(getClass());

	public abstract IBaseDao getBaseDao();

	/**
	 * 根据id获取对象
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Model> T getModelById(Object id, DbWREnums dbwr) throws Exception {
		if (id != null) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put(this.getBaseDao().getPrimaryKeyName(), id);
			List<T> list = this.getBaseDao().getModelList(map, dbwr.isWrite());
			if (list.size() == 0) {
				return null;
			}
			return list.get(0);
		}
		return null;
	}

	/**
	 * 获取符合条件的对象数量
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Long getModelListCount(Map paramMap, DbWREnums dbwr) throws Exception {
		return this.getBaseDao().getModelListCount(paramMap, dbwr.isWrite());
	}

	/**
	 * 获取符合条件的对象列表
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getModelList(Map paramMap, DbWREnums dbwr) throws Exception {
		return this.getBaseDao().getModelList(paramMap, dbwr.isWrite());
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List getModelList(Map paramMap, long skip, long size, DbWREnums dbwr)
			throws Exception {
		if (paramMap == null) {
			paramMap = FrameUtil.newHashMap();
		}
		paramMap.put("dbtype", this.getBaseDao().getDbtype());
		paramMap.put("rowstartindex", skip);
		paramMap.put("rowendindex", size);
		return this.getBaseDao().getModelList(paramMap, dbwr.isWrite());
	}

	/**
	 * 获取分页数据
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	@Override
	public PagerModel getPageModel(Map paramMap, DbWREnums dbwr) throws Exception {
		return this.getPageModel(paramMap, this.getBaseDao(), dbwr);
	}

	@SuppressWarnings({ "rawtypes"})
	public PagerModel getPageModel(Map paramMap, final IBaseDao baseDao, final DbWREnums dbwr) throws Exception {
		return this.getPageModel(paramMap, "getModelList", baseDao, dbwr);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public <T extends Model> T getModelOne(Map paramMap, DbWREnums dbwr) throws Exception {
		Object obj = this.getBaseDao().getModelOne(paramMap, dbwr.isWrite());
		return (T) obj;
	}

	/**
	 * 根据idList获取列表
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List getModelsByIds(List idList, DbWREnums dbwr) throws Exception {
		return this.getBaseDao().getModelsByIds(idList, dbwr.isWrite());
	}

	/**
	 * 根据idList获取对象列表的map，key为对象的id，value为对象
	 * 
	 * @param idList
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Map getModelMapByIds(List idList, DbWREnums dbwr) throws Exception {
		return this.getBaseDao().getModelMapByIds(idList, dbwr.isWrite());
	}



	@SuppressWarnings("rawtypes")
	@Override
	public PagerModel getPageModel(Map map, String sqlMapId, DbWREnums dbwr) throws Exception {
		return this.getPageModel(map, sqlMapId, this.getBaseDao(), dbwr);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public PagerModel getPageModel(Map paramMap, String sqlMapId, final IBaseDao baseDao, final DbWREnums dbwr)
			throws Exception {
		if (StringUtils.isBlank(sqlMapId)) {
			FrameUtil.throwBaseException1("请问你想干嘛?");
		}

		if (paramMap != null) {
			Object obj = paramMap.get("order");
			if (obj != null && paramMap.get("sort") != null) {
				FrameUtil.throwBaseException1("请问你想干嘛?");
			}
			if (obj != null) {
				String order = obj.toString().trim().toUpperCase();
				if (!order.startsWith("ORDER")) {
					FrameUtil.throwBaseException1("请问你想干嘛?");
				}
			}
		}
		return PagerUtil.getPagerModelOfCustom(new ICustomPager() {

			@Override
			public List getPageList(Map<Object, Object> paramMap, String sqlMapId) throws Exception {
				return baseDao.getModelListOfCustom(paramMap, sqlMapId, dbwr.isWrite());
			}

			@Override
			public long getPageCount(Map<Object, Object> paramMap, String sqlMapId) throws Exception {
				return baseDao.getModelListOfCustomCount(paramMap, sqlMapId+"Count", dbwr.isWrite());
			}
		}, paramMap, sqlMapId, baseDao.getDbtype());
	}

}
