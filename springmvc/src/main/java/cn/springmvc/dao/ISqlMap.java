package cn.springmvc.dao;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

/**
 * @ClassName: ISqlMap
 * @Description: sqlmapclient接口
 * @author ready likun_557@126.com
 * @date 2014-11-3 下午9:10:47
 */
public interface ISqlMap {

	public SqlMapClientTemplate getSqlMapClientTemplate();
}
