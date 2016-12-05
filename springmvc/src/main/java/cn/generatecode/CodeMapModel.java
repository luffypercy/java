package cn.generatecode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CodeMapModel {

	private boolean upByVersion = false;
	private Map<String, Map<String, Object>> propertyMap;

	/**
	 * 表名
	 */
	private String tableName;
	/**
	 * model的名称
	 */
	private String modelName;

	private String lmodelName;

	/**
	 * model的包名
	 */
	private String modelNameSpace;
	/**
	 * service接口的包名
	 */
	private String serviceNameSpace;
	/**
	 * service实现类的包名
	 */
	private String serviceImplNameSpace;
	/**
	 * dao接口的包名
	 */
	private String daoNameSpace;
	/**
	 * 主键对应的字段的名称
	 */
	private String uqColumn;

	/**
	 * 注释
	 */
	private String desc;

	/**
	 * 作者
	 */
	private String author;
	List<FieldModel> orgCols;
	List<FieldModel> idCols;

	public CodeMapModel(String tableName, String modelNameSpace, String serviceNameSpace, String serviceImplNameSpace, String daoNameSpace, String uqColumn, String desc,
			boolean upByVersion) {
		this.tableName = tableName;
		this.modelNameSpace = modelNameSpace;
		this.serviceImplNameSpace = serviceImplNameSpace;
		this.serviceNameSpace = serviceNameSpace;
		this.daoNameSpace = daoNameSpace;
		this.uqColumn = uqColumn;
		this.desc = desc;
		this.upByVersion = upByVersion;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getModelNameSpace() {
		return modelNameSpace;
	}

	public void setModelNameSpace(String modelNameSpace) {
		this.modelNameSpace = modelNameSpace;
	}

	public String getServiceNameSpace() {
		return serviceNameSpace;
	}

	public void setServiceNameSpace(String serviceNameSpace) {
		this.serviceNameSpace = serviceNameSpace;
	}

	public String getServiceImplNameSpace() {
		return serviceImplNameSpace;
	}

	public void setServiceImplNameSpace(String serviceImplNameSpace) {
		this.serviceImplNameSpace = serviceImplNameSpace;
	}

	public String getDaoNameSpace() {
		return daoNameSpace;
	}

	public void setDaoNameSpace(String daoNameSpace) {
		this.daoNameSpace = daoNameSpace;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getUqColumn() {
		return uqColumn;
	}

	public void setUqColumn(String uqColumn) {
		this.uqColumn = uqColumn;
	}

	public void setLmodelName(String lmodelName) {
		this.lmodelName = lmodelName;
	}

	public String getLmodelName() {
		this.lmodelName = this.modelName.substring(0, 1).toLowerCase() + this.modelName.substring(1);
		return lmodelName;
	}

	public String getUidKeyFieldName() {
		return GenerateCode.parseCol(this.uqColumn.toUpperCase(), true);
	}

	public String getLidKeyFieldName() {
		return GenerateCode.parseCol(this.uqColumn.toUpperCase(), false);
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public List<FieldModel> getOrgCols() {
		return orgCols;
	}

	public void setOrgCols(List<FieldModel> orgCols) {
		this.orgCols = orgCols;
	}

	public List<FieldModel> getIdCols() {
		return idCols;
	}

	public void setIdCols(List<FieldModel> idCols) {
		this.idCols = idCols;
	}

	public List<FieldModel> getExceptIdCols() {
		List<FieldModel> temp = new ArrayList<FieldModel>();
		if (this.orgCols != null && this.idCols != null) {
			for (FieldModel m1 : this.orgCols) {
				boolean flag = false;
				for (FieldModel m2 : this.idCols) {
					if (m1.getColumnName().equals(m2.getColumnName())) {
						flag = true;
						break;
					}
				}
				if (!flag) {
					temp.add(m1);
				}
			}
		}
		return temp;
	}

	public Map<String, Map<String, Object>> getPropertyMap() {
		return propertyMap;
	}

	public void setPropertyMap(Map<String, Map<String, Object>> propertyMap) {
		this.propertyMap = propertyMap;
	}

	public boolean isUpByVersion() {
		return upByVersion;
	}

	public void setUpByVersion(boolean upByVersion) {
		this.upByVersion = upByVersion;
	}

}
