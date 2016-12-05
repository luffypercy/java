package ${codeMapModel.modelNameSpace};

import cn.springmvc.model.Model;

/**
 * <b>description</b>：${codeMapModel.desc}模型<br>
 * <b>time</b>：${data} <br>
 * <b>author</b>：  ${codeMapModel.author}
 */
public class ${codeMapModel.modelName}Model extends Model{

	/**
	* @Fields serialVersionUID
	*/
	private static final long serialVersionUID = 1L;
	
	<#list codeMapModel.propertyMap?keys as key>
	 /*
	  *${codeMapModel.propertyMap[key].REMARKS} 
	  */
	private ${codeMapModel.propertyMap[key].columnClassName} ${key};
	</#list>

	<#list codeMapModel.orgCols as col>
	public ${codeMapModel.propertyMap[col.columnName].columnClassName} get${col.ucolumnName}() {
		return ${col.columnName};
	}

	public void set${col.ucolumnName}(${codeMapModel.propertyMap[col.columnName].columnClassName} ${col.columnName}) {
		this.${col.columnName} = ${col.columnName};
	}
	</#list>

}
