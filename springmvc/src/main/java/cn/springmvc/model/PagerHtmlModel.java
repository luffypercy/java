package cn.springmvc.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: PagerHtmlModel
 * @Description: 分页的html对象
 * @author ready likun_557@126.com
 * @date 2014-8-30 下午11:01:02
 */
public class PagerHtmlModel implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 分页数据
	 */
	private PagerModel pagerModel;
	/**
	 * 分页属性
	 */
	private List<PagerIndexModel> indexModels;
	/**
	 * 参数
	 */
	private Map<String, Object> urlParam;
	/**
	 * 请求的url
	 */
	private String baseUrl;

	public PagerModel getPagerModel() {
		return pagerModel;
	}

	public void setPagerModel(PagerModel pagerModel) {
		this.pagerModel = pagerModel;
	}

	public List<PagerIndexModel> getIndexModels() {
		return indexModels;
	}

	public void setIndexModels(List<PagerIndexModel> indexModels) {
		this.indexModels = indexModels;
	}

	public Map<String, Object> getUrlParam() {
		return urlParam;
	}

	public void setUrlParam(Map<String, Object> urlParam) {
		this.urlParam = urlParam;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

}
