package cn.springmvc.dao.impl;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.*;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.ibatis.sqlmap.client.SqlMapExecutor;

import cn.springmvc.dao.IBaseDao;
import cn.springmvc.dao.ISqlMap;
import cn.springmvc.enums.Constant.DbTypes;
import cn.springmvc.enums.Constant.DbTypes.DbTypeEnum;
import cn.springmvc.model.Model;
import cn.springmvc.util.FrameUtil;
import cn.springmvc.util.StringUtil;

/**
 * @ClassName: BaseDaoImpl
 * @Description: dao顶层接口实现类，如果要实现通用的增删改查，只需要继承该类
 * @author ready likun_557@126.com
 * @date 2014-11-3 下午9:16:29
 */
public abstract class BaseDaoImpl implements IBaseDao {

	/**
	 * 写
	 */
	@Resource
	protected ISqlMap writeSqlMap;
	/**
	 * 读
	 */
	@Resource
	protected ISqlMap readSqlMap;
	/**
	 * 数据库类型
	 */
	protected String dbtype = DbTypeEnum.MYSQL.getValue();
	/**
	 * comment 主键名称
	 */
	protected String primaryKeyName;

	/**
	 * sequence的名称
	 */
	protected String sequenceName;

	/**
	 * model对应sqlmap的插入前缀
	 */
	protected String sqlmapInsertPrefix = "insert";

	/**
	 * model对应sqlmap的删除前缀
	 */
	protected String sqlmapDeletePrefix = "delete";

	/**
	 * model对应sqlmap的修改前缀
	 */
	protected String sqlmapUpdatePrefix = "update";

	/**
	 * 根据map更新的sqlmap的前缀
	 */
	protected String sqlmapUpdateBymapPrefix = "updateByMap";

	/**
	 * model对应sqlmap的查询列表数量的前缀
	 */
	protected String sqlmapGetModelListCountPrefix = "getModelListCount";

	/**
	 * model对应sqlmap的查询列表的前缀
	 */
	protected String sqlmapGetModelListPrefix = "getModelList";

	/**
	 * 获取sqlmap的namespace前缀
	 * 
	 * @return
	 */
	protected String getNamesapceToSqlmapPrefix() {
		if (StringUtil.isNotEmpty(this.getSqlmapNamespace())) {
			return this.getSqlmapNamespace() + ".";
		}
		return "";
	}

	/**
	 * 获取statement
	 * 
	 * @param stuff
	 * @return
	 */
	protected String getStatement(String stuff) {
		return getNamesapceToSqlmapPrefix() + stuff;
	}

	/**
	 * 根据sequence的名称获取sequence的值
	 * 
	 * @param sequenceName
	 * @return
	 */
	public int getSequenceValueBySequenceName(String sequenceName,
			boolean write) throws Exception {
		if (StringUtil.isNotEmpty(sequenceName)) {
			return Integer
					.parseInt(this.getSqlMap(write)
							.queryForObject(
									"global.getSequenceValueBySequenceName",
									sequenceName)
							.toString());
		}
		return 0;
	}

	/**
	 * 新增model
	 * 
	 * @param <T>
	 * @param model
	 *            需要添加的model,需集成Model类
	 * @return 新增以后的model
	 * @throws Exception
	 */
	public <T extends Model> T insertModel(T model, boolean write)
			throws Exception {
		// 如果是oracle则主键的值从sequence中获取
		if (StringUtil.isNotEmpty(this.getPrimaryKeyName())) {
			Field field = FrameUtil.getClassField(this.getPrimaryKeyName(),
					model.getClass());
			// Field field = model.getClass().getDeclaredField(
			// this.getPrimaryKeyName());
			if (!field.isAccessible()) {
				field.setAccessible(true);
			}
			if (DbTypes.isOracle(dbtype)) {
				field.set(model,
						getSequenceValueBySequenceName(sequenceName, write));
			}
		}
		this.getSqlMap(write).insert(this.getStatement(sqlmapInsertPrefix),
				model);
		return model;
	}

	/**
	 * 批量插入
	 * 
	 * @param models
	 * @param write
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	public void insertBatch(final List models, final boolean write)
			throws Exception {
		if (models != null && models.size() >= 1) {
			int size = 500;
			int batchSize = 0;
			if (models.size() % size == 0) {
				batchSize = models.size() / size;
			} else {
				batchSize = models.size() / size + 1;
			}
			for (int i = 0; i < batchSize; i++) {
				int startIndex = i * size;
				int endIndex = (i + 1) * size;
				if (endIndex > models.size()) {
					endIndex = models.size();
				}
				this.getSqlMap(true).insert(this.getStatement("insertBatch"),
						models.subList(startIndex, endIndex));
			}
		}
	}

	/**
	 * 删除一个对象
	 * 
	 * @param paramMap
	 *            参数列表
	 * @return 返回影响的行数
	 * @throws Exception
	 */
	public int deleteModel(Object object, boolean write) throws Exception {
		return this.getSqlMap(write)
				.delete(this.getStatement(sqlmapDeletePrefix), object);
	}

	/**
	 * 根据id删除数据
	 * 
	 * @param id
	 * @param write
	 * @return
	 * @throws Exception
	 */
	public int deleteById(Object id, boolean write) throws Exception {
		return this.getSqlMap(write).delete(
				this.getStatement(sqlmapDeletePrefix),
				FrameUtil.newHashMap(this.getPrimaryKeyName(), id));
	}

	/**
	 * 修改model
	 * 
	 * @param model
	 * @return 影响的行数
	 * @throws Exception
	 */
	public int updateModel(Object object, boolean write) throws Exception {
		return this.getSqlMap(write)
				.update(this.getStatement(sqlmapUpdatePrefix), object);
	}

	/**
	 * 根据map更新数据
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public int updateByMap(Map<Object, Object> paramMap, boolean write)
			throws Exception {
		return this.getSqlMap(write)
				.update(this.getStatement(sqlmapUpdateBymapPrefix), paramMap);
	}

	/**
	 * 批量更新数据
	 * 
	 * @param list
	 * @param write
	 * @throws Exception
	 */
	public int updateBatch(final List<? extends Model> list, boolean write)
			throws Exception {
		return (Integer) this.getSqlMap(write)
				.execute(new SqlMapClientCallback() {
					public Object doInSqlMapClient(SqlMapExecutor executor)
							throws SQLException {
						Integer count = 0;
						if (list == null || list.size() <= 0) {
							return null;
						}
						executor.startBatch();
						int batch = 0;
						for (Object listItem : list) {
							count += executor.update(
									getStatement(sqlmapUpdatePrefix), listItem);
							batch++;
							if (batch == 100) {
								executor.executeBatch();
								batch = 0;
							}
						}
						count += executor.executeBatch();
						return count;
					}
				});
	}

	/**
	 * 获取符合条件的model的数量
	 * 
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public long getModelListCount(Map<Object, Object> paramMap, boolean write)
			throws Exception {
		initDbType(paramMap);
		return Long
				.parseLong(this.getSqlMap(write)
						.queryForObject(
								this.getStatement(
										sqlmapGetModelListCountPrefix),
								paramMap)
						.toString());
	}

	private void initDbType(Map<Object, Object> paramMap) {
		if (paramMap != null && paramMap.containsKey("rowstartindex")
				&& !paramMap.containsKey("dbtype")) {
			paramMap.put("dbtype", this.getDbtype());
		}
	}

	/**
	 * 获取model列表
	 * 
	 * @param <T>
	 * @param paramMap
	 *            查询参数列表
	 * @return 返回结果列表
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public List getModelList(Map<Object, Object> paramMap, boolean write)
			throws Exception {
		initDbType(paramMap);
		List list = this.getSqlMap(write).queryForList(
				this.getStatement(sqlmapGetModelListPrefix), paramMap);
		return list;

	}

	/**
	 * 自定义分页查询
	 * 
	 */
	public long getModelListOfCustomCount(Map<Object, Object> paramMap,
			String sqlMapId, boolean write) throws Exception {
		return Long.parseLong(this.getSqlMap(write)
				.queryForObject(this.getStatement(sqlMapId), paramMap)
				.toString());
	}

	public List getModelListOfCustom(Map<Object, Object> paramMap,
			String sqlMapId, boolean write) throws Exception {
		List list = this.getSqlMap(write)
				.queryForList(this.getStatement(sqlMapId), paramMap);
		return list;
	}

	@SuppressWarnings("unchecked")
	public <T extends Model> T getModelById(Object id, boolean write)
			throws Exception {
		Map<Object, Object> paramMap = new HashMap<Object, Object>();
		paramMap.put(this.getPrimaryKeyName(), id);
		List<T> list = this.getModelList(paramMap, write);
		if (list.size() >= 1) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据idList获取列表
	 * 
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getModelsByIds(List idList, boolean write) throws Exception {
		if (idList == null || idList.size() == 0) {
			return FrameUtil.newArrayList();
		}
		return this.getModelList(FrameUtil.newHashMap("idList",
				FrameUtil.removeDuplicateWithOrder(idList)), write);
	}

	/**
	 * 根据idList获取对象列表的map，key为对象的id，value为对象
	 * 
	 * @param idList
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getModelMapByIds(List idList, boolean write) throws Exception {
		Map map = FrameUtil.newLinkedHashMap();
		if (idList != null && idList.size() >= 1) {
			List list = this.getModelsByIds(idList, write);
			if (list != null) {
				for (Object model : list) {
					Field field = model.getClass()
							.getDeclaredField(this.getPrimaryKeyName());
					if (!field.isAccessible()) {
						field.setAccessible(true);
					}
					map.put(field.get(model), model);
				}
			}
		}
		return map;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> Map<T, List> getModelMapByFieldNameAndFields(String fieldName,
			String fieldListName, List<T> fieldList, boolean write)
					throws Exception {
		if (StringUtil.isEmpty(fieldName) || StringUtil.isEmpty(fieldListName)
				|| fieldList == null || fieldList.size() == 0) {
			return FrameUtil.newHashMap();
		}
		Map paramMap = FrameUtil.newHashMap();
		paramMap.put(fieldListName,
				FrameUtil.removeDuplicateWithOrder(fieldList));
		List list = this.getModelList(paramMap, write);
		paramMap.clear();
		for (Object item : list) {
			Object val = FrameUtil.getField(item, fieldName);
			List tempList = (List) paramMap.get(val);
			if (tempList == null) {
				tempList = FrameUtil.newArrayList();
				paramMap.put(val, tempList);
			}
			tempList.add(item);
		}
		return paramMap;
	}

	/**
	 * 根据对象的id获取对象
	 * 
	 * @param id
	 * @param write
	 *            是否是操作主库
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T extends Model> T getModel(Map<Object, Object> paramMap,
			boolean write) throws Exception {
		List list = this.getModelList(paramMap, write);
		if (list != null && list.size() > 0) {
			return ((T) list.get(0));
		}
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T extends Model> T getModelOne(Map paramMap, boolean write)
			throws Exception {
		if (paramMap == null) {
			paramMap = FrameUtil.newHashMap();
		}
		if (!paramMap.containsKey("rowstartindex")) {
			paramMap.put("rowstartindex", 0);
			paramMap.put("rowendindex", 1);
		}
		List<T> list = this.getModelList(paramMap, write);
		return list.size() >= 1 ? list.get(0) : null;
	}

	/**
	 * 数据库类型
	 * 
	 * @return
	 */
	public String getDbtype() {
		return dbtype;
	}

	/**
	 * 数据库类型
	 * 
	 * @param dbtype
	 */
	public void setDbtype(String dbtype) {
		this.dbtype = dbtype;
	}

	/**
	 * mapper的namespace
	 * 
	 * @return
	 */
	public abstract String getSqlmapNamespace();

	/**
	 * sequence的名称
	 * 
	 * @return
	 */
	public String getSequenceName() {
		return sequenceName;
	}

	/**
	 * sequence的名称
	 * 
	 * @param sequenceName
	 */
	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}

	/**
	 * model对应sqlmap的插入前缀
	 * 
	 * @return
	 */
	public String getSqlmapInsertPrefix() {
		return sqlmapInsertPrefix;
	}

	/**
	 * model对应sqlmap的插入前缀
	 * 
	 * @param sqlmapInsertPrefix
	 */
	public void setSqlmapInsertPrefix(String sqlmapInsertPrefix) {
		this.sqlmapInsertPrefix = sqlmapInsertPrefix;
	}

	/**
	 * model对应sqlmap的删除前缀
	 * 
	 * @return
	 */
	public String getSqlmapDeletePrefix() {
		return sqlmapDeletePrefix;
	}

	/**
	 * model对应sqlmap的删除前缀
	 * 
	 * @param sqlmapDeletePrefix
	 */
	public void setSqlmapDeletePrefix(String sqlmapDeletePrefix) {
		this.sqlmapDeletePrefix = sqlmapDeletePrefix;
	}

	/**
	 * model对应sqlmap的修改前缀
	 * 
	 * @return
	 */
	public String getSqlmapUpdatePrefix() {
		return sqlmapUpdatePrefix;
	}

	/**
	 * model对应sqlmap的修改前缀
	 * 
	 * @param sqlmapUpdatePrefix
	 */
	public void setSqlmapUpdatePrefix(String sqlmapUpdatePrefix) {
		this.sqlmapUpdatePrefix = sqlmapUpdatePrefix;
	}

	/**
	 * model对应sqlmap的查询列表数量的前缀
	 * 
	 * @return
	 */
	public String getSqlmapGetModelListCountPrefix() {
		return sqlmapGetModelListCountPrefix;
	}

	/**
	 * model对应sqlmap的查询列表数量的前缀
	 * 
	 * @param sqlmapGetModelListCountPrefix
	 */
	public void setSqlmapGetModelListCountPrefix(
			String sqlmapGetModelListCountPrefix) {
		this.sqlmapGetModelListCountPrefix = sqlmapGetModelListCountPrefix;
	}

	/**
	 * model对应sqlmap的查询列表的前缀
	 * 
	 * @return
	 */
	public String getSqlmapGetModelListPrefix() {
		return sqlmapGetModelListPrefix;
	}

	/**
	 * model对应sqlmap的查询列表的前缀
	 * 
	 * @param sqlmapGetModelListPrefix
	 */
	public void setSqlmapGetModelListPrefix(String sqlmapGetModelListPrefix) {
		this.sqlmapGetModelListPrefix = sqlmapGetModelListPrefix;
	}

	public String getPrimaryKeyName() {
		return primaryKeyName;
	}

	public void setPrimaryKeyName(String primaryKeyName) {
		this.primaryKeyName = primaryKeyName;
	}

	public ISqlMap getWriteSqlMap() {
		return writeSqlMap;
	}

	public void setWriteSqlMap(ISqlMap writeSqlMap) {
		this.writeSqlMap = writeSqlMap;
	}

	public ISqlMap getReadSqlMap() {
		return readSqlMap;
	}

	public void setReadSqlMap(ISqlMap readSqlMap) {
		this.readSqlMap = readSqlMap;
	}

	/**
	 * 获取默认的SqlSessionTemplate
	 * 
	 * @return
	 */
	public SqlMapClientTemplate getSqlMap() {
		return getSqlMap(true);
	}

	public SqlMapClientTemplate getSqlMap(boolean write) {
		if (write || FrameUtil.dbAllOperateToMaster()) {
			return this.getWriteSqlMap().getSqlMapClientTemplate();
		}

		return this.getReadSqlMap().getSqlMapClientTemplate();
	}

}
