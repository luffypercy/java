package cn.generatecode;
import java.io.File;


public class CodeUtil {

	/**
	 * 代码存放目录
	 */
	public static String CODE_DIR = new File("generate_code_folder").getAbsolutePath();
	/**
	 * jdbc url
	 */
//	public static String JDBC_URL = "jdbc:mysql://192.168.10.14:3306/www_yijiedai_com_test4?characterEncoding=UTF-8";
	/**
	 * jdbc url
	 * read.jdbc.url=jdbc:mysql://192.168.11.13:3306/www_yijiedai_com?characterEncoding=UTF-8
read.jdbc.driverClassName=com.mysql.jdbc.Driver
read.jdbc.username=www_yijiedai_com
read.jdbc.password=yijiedai123
	 * 
	 * 
	 */
	public static String JDBC_URL = "jdbc:mysql://192.168.11.13:3306/www_yijiedai_com?characterEncoding=UTF-8";
	/**
	 * jdbc 驱动
	 */
	public static String JDBC_DRIVERCLASSNAME = "com.mysql.jdbc.Driver";
	/**
	 * 数据库用户名
	 */
	public static String JDBC_USERNAME = "www_yijiedai_com";
	/**
	 * 数据库密码
	 */
	public static String JDBC_PASSWORD = "yijiedai123";

	public static void main(String[] args) throws Exception {
		
		// 以下参数的名称各位都小写
		// 表名
		String tableName = "yjd_debt_record";
		// 描述
		String desc = "逾期债权购买记录";
		// model所在的包名
		String modelNameSpace = "com.yjd.comm.debt.model";
		// service接口所在的包名
		String serviceNameSpace = "com.yjd.comm.debt.service";
		// service实现类所在的包名
		String serviceImplNameSpace = "com.yjd.pub.debt.service.impl";
		// dao接口所在的包名
		String daoNameSpace = "com.yjd.pub.debt.dao";
		// 作者
		String author = "ready percy-chen@hotmail.com";
		CodeMapModel codeMapModel = new CodeMapModel(tableName, modelNameSpace,
				serviceNameSpace, serviceImplNameSpace, daoNameSpace, null,
				desc,true);
		codeMapModel.setAuthor(author);
		GenerateCode.createAll(codeMapModel);
		System.out.println("生成完毕!");
	}
}
