package ${codeMapModel.serviceImplNameSpace};

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import cn.springmvc.dao.impl.BaseDaoImpl;
import cn.springmvc.service.impl.TopBaseServiceImpl;
import ${codeMapModel.modelNameSpace}.${codeMapModel.modelName}Model;
import ${codeMapModel.serviceNameSpace}.I${codeMapModel.modelName}Service;
import ${codeMapModel.daoNameSpace}.I${codeMapModel.modelName}Dao;

/**
 * <b>description</b>：${codeMapModel.desc}业务实现<br>
 * <b>time</b>：${data} <br>
 * <b>author</b>：  ${codeMapModel.author}
 */
@Service("${codeMapModel.lmodelName}Service")
public class ${codeMapModel.modelName}ServiceImpl extends TopBaseServiceImpl implements I${codeMapModel.modelName}Service {

	/**
	 * 插入
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ${codeMapModel.modelName}Model insert(${codeMapModel.modelName}Model model) throws Exception {
		return this.${codeMapModel.lmodelName}Dao.insertModel(model, true);
	}

	/**
	 * 更新
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public int update(${codeMapModel.modelName}Model model) throws Exception {
		return this.${codeMapModel.lmodelName}Dao.updateModel(model, true);
	}

	/**
	 * 根据id删除
	 * 
	 * @param ${(codeMapModel.idCols[0].columnName)}
	 * @return
	 * @throws Exception
	 */
	public int delete(long ${(codeMapModel.idCols[0].columnName)}) throws Exception {
		Map<Object, Object> paramMap = new HashMap<Object, Object>();
		paramMap.put("${(codeMapModel.idCols[0].columnName)}", ${(codeMapModel.idCols[0].columnName)});
		return this.${codeMapModel.lmodelName}Dao.deleteModel(paramMap, true);
	}

	@Resource
	private I${codeMapModel.modelName}Dao ${codeMapModel.lmodelName}Dao;

	@Override
	public IBaseDao getBaseDao() {
		return this.${codeMapModel.lmodelName}Dao;
	}

}
