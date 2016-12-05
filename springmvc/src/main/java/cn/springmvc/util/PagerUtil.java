package cn.springmvc.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.BeanUtils;

import cn.springmvc.enums.Constant.DbTypes;
import cn.springmvc.model.JsonPagerModel;
import cn.springmvc.model.PagerHtmlModel;
import cn.springmvc.model.PagerIndexModel;
import cn.springmvc.model.PagerModel;
import cn.springmvc.service.ICustomPager;
import cn.springmvc.service.IPager;

/**
 * 分页辅助类
 * 
 * @author likun 2013-7-24 下午5:07:12
 */
public class PagerUtil {

	public static final int pageSize = 20;

	public static void main(String[] args) {
		for (int i = 1; i <= 20; i++) {
			for (int j = 1; j <= 21; j++) {
				System.out.println(i + ":" + j + ":"
						+ FrameUtil.json(getPageIndex(i, j)));
			}

		}
	}

	/**
	 * 获取分页参数
	 * 
	 * @param page
	 *            当前页
	 * @param rows
	 *            行数
	 * @return
	 */
	public static Map<Object, Object> getPageMap(int page, int rows) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("page", page);
		map.put("rows", rows);
		return map;
	}

	/**
	 * 获取分页页码集合
	 * 
	 * @param pageCount
	 * @param currentPage
	 * @return
	 */
	public static List<String> getPageIndex(long pageCount, long currentPage) {
		StringBuilder builder = new StringBuilder("");
		int pcsize = (int) pageCount;
		int cp = (int) currentPage;
		// 总共显示数量
		int pagersize = 11;
		// 两端数量
		int betweensize = 2;
		// 中间数量
		int middlesize = 5;
		if (pcsize <= pagersize) {
			for (int i = 1; i <= pcsize; i++) {
				builder.append(i + ",");
			}
		} else {
			int betmaxsize = betweensize + middlesize;
			int endMaxSize = pcsize - betweensize - middlesize + 1;
			if (cp <= betmaxsize) {
				for (int i = 1; i <= betmaxsize + 1; i++) {
					builder.append(i + ",");
				}
				builder.append("...,");
				for (int i = pcsize - 1; i <= pcsize; i++) {
					builder.append(i + ",");
				}
			} else if (cp >= endMaxSize) {
				for (int i = 1; i <= betweensize; i++) {
					builder.append(i + ",");
				}
				builder.append("...,");
				for (int i = pcsize - betmaxsize; i <= pcsize; i++) {
					builder.append(i + ",");
				}
			} else {
				for (int i = 1; i <= betweensize; i++) {
					builder.append(i + ",");
				}
				builder.append("...,");
				for (int i = cp - betweensize; i <= cp + betweensize; i++) {
					builder.append(i + ",");
				}
				builder.append("...,");
				for (int i = pcsize - 1; i <= pcsize; i++) {
					builder.append(i + ",");
				}
			}
		}
		String[] temp = builder.toString().split(",");
		List<String> list = new ArrayList<String>();
		for (String string : temp) {
			if (string != null && !"".equals(string)) {
				list.add(string);
			}
		}
		return list;
	}

	/**
	 * 分页
	 * 
	 * @param pager
	 * @param paramMap
	 * @param dbtype
	 * @return
	 * @throws Exception
	 */
	public static PagerModel getPagerModel(IPager pager,
			Map<Object, Object> paramMap, String dbtype) throws Exception {
		PagerModel pagerModel = new PagerModel();
		if (paramMap == null) {
			paramMap = new HashMap<Object, Object>();
		}
		Integer currentPage = paramMap.get("page") == null ? null : Integer
				.parseInt(paramMap.get("page").toString());
		Integer pageSize = paramMap.get("rows") == null ? null : Integer
				.parseInt(paramMap.get("rows").toString());
		if (currentPage == null) {
			currentPage = 1;
		}
		// 此处每页如果大于100，将其置为20，主要是防止恶意查询大量数据让系统down掉
		if (pageSize == null || pageSize > 100) {
			pageSize = PagerUtil.pageSize;
		}

		pagerModel.setPageSize(pageSize);
		pagerModel.setCurrentPage(currentPage);
		pagerModel.setCount(pager.getPageCount(paramMap));
		paramMap.put("dbtype", dbtype);

		if (DbTypes.isMysql(dbtype)) {
			if (pagerModel.getStartIndex() <= 0) {
				paramMap.put("rowstartindex", 0);
				paramMap.put("rowendindex", 0);
			} else {
				paramMap.put("rowstartindex", pagerModel.getStartIndex() - 1);
				paramMap.put("rowendindex", pagerModel.getPageSize());
			}
		} else {
			paramMap.put("rowstartindex", pagerModel.getStartIndex());
			paramMap.put("rowendindex", pagerModel.getEndIndex());
		}
		if (pagerModel.getCount() >= 1) {
			pagerModel.setDataList(pager.getPageList(paramMap));
		} else {
			pagerModel.setDataList(FrameUtil.newArrayList());
		}
		return pagerModel;
	}
	
	
	/**
	 * 分页(自定义)
	 * 
	 * @param customPager
	 * @param paramMap
	 * @param dbtype
	 * @return
	 * @throws Exception
	 */
	public static PagerModel getPagerModelOfCustom(ICustomPager customPager,
			Map<Object, Object> paramMap,String sqlMapId, String dbtype) throws Exception {
		PagerModel pagerModel = new PagerModel();
		if (paramMap == null) {
			paramMap = new HashMap<Object, Object>();
		}
		Integer currentPage = paramMap.get("page") == null ? null : Integer
				.parseInt(paramMap.get("page").toString());
		Integer pageSize = paramMap.get("rows") == null ? null : Integer
				.parseInt(paramMap.get("rows").toString());
		if (currentPage == null) {
			currentPage = 1;
		}
		// 此处每页如果大于100，将其置为20，主要是防止恶意查询大量数据让系统down掉
		if (pageSize == null || pageSize > 100) {
			pageSize = PagerUtil.pageSize;
		}

		pagerModel.setPageSize(pageSize);
		pagerModel.setCurrentPage(currentPage);
		pagerModel.setCount(customPager.getPageCount(paramMap,sqlMapId));
		paramMap.put("dbtype", dbtype);

		if (DbTypes.isMysql(dbtype)) {
			if (pagerModel.getStartIndex() <= 0) {
				paramMap.put("rowstartindex", 0);
				paramMap.put("rowendindex", 0);
			} else {
				paramMap.put("rowstartindex", pagerModel.getStartIndex() - 1);
				paramMap.put("rowendindex", pagerModel.getPageSize());
			}
		} else {
			paramMap.put("rowstartindex", pagerModel.getStartIndex());
			paramMap.put("rowendindex", pagerModel.getEndIndex());
		}
		if (pagerModel.getCount() >= 1) {
			pagerModel.setDataList(customPager.getPageList(paramMap,sqlMapId));
		} else {
			pagerModel.setDataList(FrameUtil.newArrayList());
		}
		return pagerModel;
	}
	

	/**
	 * 获取一个pageModel
	 * 
	 * @param page
	 * @param rows
	 * @param count
	 * @return
	 */
	public static PagerModel getPagerModel(int page, int rows, int count) {
		PagerModel pagerModel = new PagerModel();
		pagerModel.setPageSize(rows);
		pagerModel.setCurrentPage(page);
		pagerModel.setCount(count);
		return pagerModel;
	}

	/**
	 * 处理map参数
	 * 
	 * @param paramMap
	 * @param dbtype
	 */
	public static void disposeParamMap(Map<Object, Object> paramMap,
			String dbtype) {
		if (paramMap != null) {
			paramMap.put("dbtype", dbtype);
			Integer currentPage = paramMap.get("page") == null ? null : Integer
					.parseInt(paramMap.get("page").toString());
			Integer pageSize = paramMap.get("rows") == null ? null : Integer
					.parseInt(paramMap.get("rows").toString());
			if (currentPage != null && pageSize != null) {
				paramMap.put("dbtype", dbtype);

				if (DbTypes.isMysql(dbtype)) {
					paramMap.put("rowstartindex", (currentPage - 1) * pageSize);
					paramMap.put("rowendindex", pageSize);
				} else {
					paramMap.put("rowstartindex", (currentPage - 1) * pageSize);
					paramMap.put("rowendindex", currentPage * pageSize);
				}
			}
		}
	}

	/**
	 * @Title: getPagerHtmlModel
	 * @Description: 获取分页数据
	 * @param @param pagerModel 分页数据
	 * @param @param urlParam 分页参数列表
	 * @param @param baseUrl 请求的基本路径
	 * @param @return 设定文件
	 * @return PagerHtmlModel 返回类型
	 * @throws
	 */
	public static PagerHtmlModel getPagerHtmlModel(PagerModel pagerModel,
			Map<String, Object> urlParam, String baseUrl) {
		PagerHtmlModel htmlModel = new PagerHtmlModel();
		htmlModel.setPagerModel(pagerModel);
		htmlModel.setUrlParam(urlParam);
		htmlModel.setBaseUrl(baseUrl);
		List<PagerIndexModel> indexModels = new ArrayList<PagerIndexModel>();
		List<String> list = getPageIndex(pagerModel.getPageCount(),
				pagerModel.getCurrentPage());
		if (urlParam == null) {
			urlParam = new LinkedHashMap<String, Object>();
		}
		for (String page : list) {
			if (FrameUtil.isNumeric(page)) {
				urlParam.put("page", page);
			}
			StringBuilder builder = new StringBuilder();
			// int i = 0;
			for (Entry<String, Object> entry : urlParam.entrySet()) {
				// 对order、sort、rows不输出到页面
				if (!entry.getKey().trim().toLowerCase().equals("order")
						&& !entry.getKey().trim().toLowerCase().equals("sort")
						&& !entry.getKey().trim().toLowerCase().equals("rows")) {
					builder.append(entry.getKey()).append("=")
							.append(entry.getValue()).append("&");
					// .append(i++ < urlParam.size() - 1 ? "&" : "");
				}

			}

			String temp = new String(builder);
			if (temp.endsWith("&")) {
				temp = builder.substring(0, builder.length() - 1);
			}
			indexModels.add(new PagerIndexModel(FrameUtil.isNumeric(page),
					page, baseUrl + "?" + temp));
		}
		htmlModel.setIndexModels(indexModels);
		return htmlModel;
	}

	public static JsonPagerModel convert(PagerModel pagerModel) {
		if (pagerModel == null) {
			return null;
		}
		JsonPagerModel jp = new JsonPagerModel();
		BeanUtils.copyProperties(pagerModel, jp);
		return jp;
	}
}
