package ${codeMapModel.daoNameSpace}.impl;

import org.springframework.stereotype.Component;
import com.yjd.comm.base.dao.impl.BaseDaoImpl;
import ${codeMapModel.modelNameSpace}.${codeMapModel.modelName}Model;
import ${codeMapModel.daoNameSpace}.I${codeMapModel.modelName}Dao;

/**
 * <b>description</b>：${codeMapModel.desc}数据访问层 <br>
 * <b>time</b>：${data} <br>
 * <b>author</b>： ${codeMapModel.author}
 */
@Component("${codeMapModel.lmodelName}Dao")
public class ${codeMapModel.modelName}DaoImpl extends BaseDaoImpl implements I${codeMapModel.modelName}Dao {

	private static final String SQLMAPNAMESPACE = ${codeMapModel.modelName}Model.class.getName();
	private static final String PKNAME = "${(codeMapModel.idCols[0].columnName)}";

	@Override
	public String getPrimaryKeyName() {
		return PKNAME;
	}

	@Override
	public String getSqlmapNamespace() {
		return SQLMAPNAMESPACE;
	}
	
}
