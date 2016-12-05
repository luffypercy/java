package cn.springmvc.service;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author wanganping
 * @date 2015-5-27
 * @version V1.0
 */
public interface ICustomPager{

	public long getPageCount(Map<Object, Object> paramMap, String sqlMapId) throws Exception;

	@SuppressWarnings("rawtypes")
	public List getPageList(Map<Object, Object> paramMap, String sqlMapId) throws Exception;
}
