package cn.springmvc.service;

import java.util.List;
import java.util.Map;

import cn.springmvc.enums.DbWREnums;
import cn.springmvc.model.Model;
import cn.springmvc.model.PagerModel;

/**
 * <b>description</b>：业务层基类，此接口中只提供通用的查询的方法，不提供增删改的方法，此类中所有方法不要加事务（AOP不会代理基类中的方法
 * ,若要加事务，请在子类中重写父类的方法 <br>
 * <b>time</b>：2014-10-13上午11:30:26 <br>
 * <b>author</b>： ready likun_557@163.com
 */
public interface IBaseService {

	/**
	 * 根据id获取对象
	 * 
	 * @param id
	 * @param dbwr
	 *            数据获取来源
	 * @return
	 * @throws Exception
	 */
	public <T extends Model> T getModelById(Object id, DbWREnums dbwr)
			throws Exception;
	
	/**
	* 获取符合条件的对象列表
	* @param paramMap
	* @param skip 跳过多少条
	* @param size 取多少条
	* @param dbwr
	* @return
	* @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List getModelList(Map paramMap, long skip, long size, DbWREnums dbwr)
			throws Exception;

	/**
	 * 获取符合条件的数据的数量
	 * 
	 * @param paramMap
	 * @param dbwr
	 *            数据获取来源
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Long getModelListCount(Map paramMap, DbWREnums dbwr) throws Exception;

	/**
	 * 获取符合条件的对象的数量
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List getModelList(Map paramMap, DbWREnums dbwr) throws Exception;

	/**
	 * 获取分页数据
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public PagerModel getPageModel(Map map, DbWREnums dbwr) throws Exception;

	/**
	 * 根据map获取一个对象
	 * 
	 * @param paramMap
	 * @param dbwr
	 *            数据获取来源
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public <T extends Model> T getModelOne(Map paramMap, DbWREnums dbwr)
			throws Exception;


	/**
	 * 根据idList获取列表
	 * 
	 * @param ids
	 * @param dbwr
	 *            数据获取来源
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List getModelsByIds(List idList, DbWREnums dbwr) throws Exception;

	/**
	 * 根据idList获取对象列表的map，key为对象的id，value为对象
	 * 
	 * @param idList
	 * @param dbwr
	 *            数据获取来源
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public Map getModelMapByIds(List idList, DbWREnums dbwr) throws Exception;

	
	/**
	 * 获取分页数据(自定义)
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public PagerModel getPageModel(Map map, String sqlMapId,DbWREnums dbwr) throws Exception;
	
}
