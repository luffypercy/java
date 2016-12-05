package cn.springmvc.service;

import java.util.List;
import java.util.Map;

/**
 * 分页数量获取接口
 * 
 * @author likun 2013-7-24 下午4:57:56
 */
public interface IPager{

	public long getPageCount(Map<Object, Object> paramMap) throws Exception;

	@SuppressWarnings("rawtypes")
	public List getPageList(Map<Object, Object> paramMap) throws Exception;
}
