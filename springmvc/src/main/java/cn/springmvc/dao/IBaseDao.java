package cn.springmvc.dao;

import java.util.List;
import java.util.Map;

import cn.springmvc.model.Model;



/**
 * <b>description</b>：dao顶层接口，里面声明了对一个实体的增删改查，批量修改、添加的方法 <br>
 * <b>time</b>：2014-11-15下午4:15:58 <br>
 * <b>author</b>： ready likun_557@163.com
 */
public interface IBaseDao {

	/**
	 * 数据库类型
	 * 
	 * @return
	 */
	public String getDbtype();

	/**
	 * 主键名称
	 * 
	 * @return
	 */
	public String getPrimaryKeyName();

	/**
	 * 数据库类型
	 * 
	 * @param dbtype
	 */
	public void setDbtype(String dbtype);

	/**
	 * 根据sequence的名称获取sequence的值
	 * 
	 * @param sequenceName
	 * @param write
	 *            是否是操作主库
	 * @return
	 */
	public int getSequenceValueBySequenceName(String sequenceName, boolean write)
			throws Exception;

	/**
	 * 新增model
	 * 
	 * @param <T>
	 * @param model
	 *            需要添加的model,需集成Model类
	 * @param write
	 *            是否是操作主库
	 * @return 新增以后的model
	 * @throws Exception
	 */
	public <T extends Model> T insertModel(T model, boolean write)
			throws Exception;

	/**
	 * 批量插入
	 * 
	 * @param models
	 * @param write
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	public void insertBatch(final List models, final boolean write)
			throws Exception;

	/**
	 * 删除一个对象
	 * 
	 * @param paramMap
	 *            参数列表
	 * @param write
	 *            是否是操作主库
	 * @return 返回影响的行数
	 * @throws Exception
	 */
	public int deleteModel(Object object, boolean write) throws Exception;

	/**
	 * 根据id删除数据
	 * 
	 * @param id
	 * @param write
	 * @return
	 * @throws Exception
	 */
	public int deleteById(Object id, boolean write) throws Exception;

	/**
	 * 修改model
	 * 
	 * @param model
	 * @param write
	 *            是否是操作主库
	 * @return 影响的行数
	 * @throws Exception
	 */
	public int updateModel(Object object, boolean write) throws Exception;

	/**
	 * 批量更新数据
	 * 
	 * @param list
	 * @param write
	 * @throws Exception
	 */
	public int updateBatch(final List<? extends Model> list, boolean write)
			throws Exception;

	/**
	 * 获取符合条件的model的数量
	 * 
	 * @param paramMap
	 * @param write
	 *            是否是操作主库
	 * @return
	 * @throws Exception
	 */
	public long getModelListCount(Map<Object, Object> paramMap, boolean write)
			throws Exception;
	
	/**
	 * 获取model列表 (自定义分页查询，不依赖getModelList)
	 * 
	 * @param <T>
	 * @param paramMap
	 *            查询参数列表
     * @param sqlMapId
	 *            调用数据库查询的sqlMapId
	 * @param write
	 *            是否是操作主库
	 * @return 返回结果列表
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public long getModelListOfCustomCount(Map<Object, Object> paramMap,String sqlMapId, boolean write)
			throws Exception;

	/**
	 * 获取model列表
	 * 
	 * @param <T>
	 * @param paramMap
	 *            查询参数列表
	 * @param write
	 *            是否是操作主库
	 * @return 返回结果列表
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List getModelList(Map<Object, Object> paramMap, boolean write)
			throws Exception;
	
	/**
	 * 获取model列表 (自定义分页查询，不依赖getModelList)
	 * 
	 * @param <T>
	 * @param paramMap
	 *            查询参数列表
     * @param sqlMapId
	 *            调用数据库查询的sqlMapId
	 * @param write
	 *            是否是操作主库
	 * @return 返回结果列表
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List getModelListOfCustom(Map<Object, Object> paramMap,String sqlMapId, boolean write)
			throws Exception;

	/**
	 * 根据对象的id获取对象
	 * 
	 * @param id
	 * @param write
	 *            是否是操作主库
	 * @throws Exception
	 */
	public <T extends Model> T getModelById(Object id, boolean write)
			throws Exception;

	/**
	 * 根据map更新数据
	 * 
	 * @param paramMap
	 * @param write
	 *            是否是操作主库
	 * @return
	 * @throws Exception
	 */
	public int updateByMap(Map<Object, Object> paramMap, boolean write)
			throws Exception;

	/**
	 * 根据idList获取列表
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	public List getModelsByIds(List idList, boolean write) throws Exception;

	/**
	 * 根据idList获取对象列表的map，key为对象的id，value为对象
	 * 
	 * @param idList
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	public Map getModelMapByIds(List idList, boolean write) throws Exception;
	
	/**
	 * 根据对象的id获取对象
	 * 
	 * @param id
	 * @param write
	 *            是否是操作主库
	 * @throws Exception
	 */
	public <T extends Model> T getModel(Map<Object,Object> paramMap, boolean write)
			throws Exception;

	/**
	 * 返回符合查询条件的对象和列表的映射
	 * @param proName 对象属性名称
	 * @param fieldListName 查询条件中字段的名称
	 * @param fieldList 需要查询的条件值的列表
	 * @param write
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public <T> Map<T, List> getModelMapByFieldNameAndFields(
			String fieldName, String fieldListName, List<T> fieldList,
			boolean write) throws Exception;
	
	/**
	* 获取一个model
	* @param paramMap 查询参数
	* @param write 读写条件
	* @return
	* @throws Exception
	 */
	public <T extends Model> T getModelOne(Map paramMap, boolean write) throws Exception;

}
