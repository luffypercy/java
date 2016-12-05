package ${codeMapModel.serviceNameSpace};

import cn.springmvc.service.IBaseService;
import ${codeMapModel.modelNameSpace}.${codeMapModel.modelName}Model;

/**
 * <b>description</b>：${codeMapModel.desc}业务接口<br>
 * <b>time</b>：${data} <br>
 * <b>author</b>：  ${codeMapModel.author}
 */
public interface I${codeMapModel.modelName}Service extends IBaseService {
	/**
	 * 插入
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public ${codeMapModel.modelName}Model insert(${codeMapModel.modelName}Model model) throws Exception;

	/**
	 * 更新,大于等于1表示成功，其他失败
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public int update(${codeMapModel.modelName}Model model) throws Exception;

	/**
	 * 根据id删除,大于等于1表示成功，其他失败
	 * 
	 * @param ${codeMapModel.idCols[0].columnName}
	 * @return
	 * @throws Exception
	 */
	public int delete(long ${codeMapModel.idCols[0].columnName}) throws Exception;

}
