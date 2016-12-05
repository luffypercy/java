package cn.generatecode;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;

public class GenerateCode
{

	/**
	 * 换行符
	 */
	static String lineSplit = "\n";
	/**
	 * 字段分隔符
	 */
	static String filedSplit = "_";

	static String tableNameStartFilter = "yjd_";
	
	static Map<String,Map<String,Object>> propertyMap = new LinkedHashMap<String,Map<String,Object>>();

	public static void createAll(CodeMapModel codeMapModel) throws Exception
	{

		String tn = codeMapModel.getTableName();
		if (tableNameStartFilter != null && !tableNameStartFilter.trim().equals("")
				&& codeMapModel.getTableName().toLowerCase().startsWith(tableNameStartFilter)) {
			tn = tn.substring(tableNameStartFilter.length());
		}
		codeMapModel.setModelName(parseCol(tn, true));
		String parentPath = CodeUtil.CODE_DIR + "/" + codeMapModel.getModelName();
		File file = new File(parentPath);
		if (!file.exists()) {
			file.mkdirs();
		}
		
		getColumnsInfo(codeMapModel.getTableName());
		
		writeFile(generateSql2(codeMapModel), parentPath + "/sqlmap-" + tn.toLowerCase() + ".xml");
		writeFile(getModel(codeMapModel), parentPath + "/" + codeMapModel.getModelName()+"Model.java");
		writeFile(getIService(codeMapModel), parentPath + "/I" + codeMapModel.getModelName()+ "Service.java");
		writeFile(getServiceImpl(codeMapModel), parentPath + "/" + codeMapModel.getModelName()+ "ServiceImpl.java");
		writeFile(getIDao(codeMapModel), parentPath + "/I" + codeMapModel.getModelName()+ "Dao.java");
		writeFile(getDaoImpl(codeMapModel), parentPath + "/" + codeMapModel.getModelName()+ "DaoImpl.java");
		writeFile(getSpring(codeMapModel), parentPath + "/spring.xml");
	}

	public static void writeFile(String str, String filePath) throws Exception
	{
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
				new File(filePath)), "UTF-8"));
		bw.write(str);
		bw.close();
		System.out.println("生成文件:" + filePath);
	}

	public static String generateSql(String tableName, String modelName) throws IOException
	{
		List<String> orgCols = new ArrayList<String>();
		List<String> idCols = new ArrayList<String>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("select * from " + tableName);
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int colsCount = rsmd.getColumnCount();
			for (int i = 1; i <= colsCount; i++) {
				orgCols.add(rsmd.getColumnName(i));
				if (i == 1) {
					idCols.add(rsmd.getColumnName(i));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// 生成语句，然后输出到os
		StringBuilder builder = new StringBuilder();
		builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		builder.append(lineSplit);
		builder.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
		builder.append(lineSplit);
		builder.append("<mapper namespace=\"").append(modelName).append("\">");
		builder.append(lineSplit);
		builder.append(lineSplit);
		builder.append(getModelMap(modelName, orgCols));
		builder.append(getInsert(modelName, tableName, orgCols));
		builder.append(getDelete(tableName, idCols));
		builder.append(getUpdate2(modelName, tableName, orgCols, idCols));
		builder.append(getFindsql(orgCols));
		builder.append(getModelListCount(tableName));
		builder.append(getModelList(tableName, orgCols));
		builder.append("</mapper>");
		return builder.toString();
	}

	public static void getColumnsInfo(String tableName) throws IOException,TemplateException{
		Connection conn = null;
		PreparedStatement ps = null;
		 ResultSet rs = null;
		try {
			conn = getConnection();
			 try {
		            DatabaseMetaData dbmd=conn.getMetaData();
		                     rs = dbmd.getColumns(null, "%", tableName, "%");
		                    while(rs.next()){
		                    	Map<String,Object> columnDataMap = new HashMap<String,Object>();
		                    	columnDataMap.put("REMARKS",rs.getString("REMARKS"));
		                    	columnDataMap.put("DATA_TYPE",rs.getString("DATA_TYPE"));
		                    	propertyMap.put(rs.getString("COLUMN_NAME"), columnDataMap);
		                    }
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String generateSql2(CodeMapModel codeMapModel) throws IOException,
			TemplateException{
		List<FieldModel> orgCols = new ArrayList<FieldModel>();
		List<FieldModel> idCols = new ArrayList<FieldModel>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			ps = conn.prepareStatement("select * from " + codeMapModel.getTableName());
			rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int colsCount = rsmd.getColumnCount();
			for (int i = 1; i <= colsCount; i++) {
				//获得指定列的列名 
				String columnName = rsmd.getColumnName(i); 
				//对应数据类型的类 
				String columnClassName=rsmd.getColumnClassName(i); 
				if(propertyMap.containsKey(columnName)){
					propertyMap.get(columnName).put("columnClassName", columnClassName);
				}
				orgCols.add(new FieldModel(rsmd.getColumnName(i).toLowerCase(),
							      parseCol(rsmd.getColumnName(i).toLowerCase())));
				if (i == 1) {
					idCols.add(new FieldModel(rsmd.getColumnName(i).toLowerCase(), parseCol(rsmd.getColumnName(i).toLowerCase())));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		String ftlname = "sqlmap.ftl";
		codeMapModel.setOrgCols(orgCols);
		codeMapModel.setIdCols(idCols);
		codeMapModel.setPropertyMap(propertyMap);
		return getCode(codeMapModel, ftlname);
	}

	private static Connection getConnection() throws ClassNotFoundException, SQLException
	{
		Connection conn;
		Class.forName(CodeUtil.JDBC_DRIVERCLASSNAME);
		conn = DriverManager.getConnection(CodeUtil.JDBC_URL, CodeUtil.JDBC_USERNAME,
				CodeUtil.JDBC_PASSWORD);
		return conn;
	}

	/**
	 * 
	 * @Title: getModelMap
	 * @Description: 生成mapper
	 * @param @param modelName
	 * @param @param orgCols
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getModelMap(String modelName, List<String> orgCols)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("<!-- 字段映射对象 -->");
		builder.append(lineSplit);
		builder.append("<resultMap type=\"" + modelName + "\" id=\"modelMap\">");
		builder.append(lineSplit);
		for (String string : orgCols) {
			builder.append("<result column=\"" + string + "\" property=\"" + parseCol(string)
					+ "\" />");
			builder.append(lineSplit);
		}
		builder.append("</resultMap>");
		builder.append(lineSplit);
		builder.append(lineSplit);
		return builder.toString();
	}

	/**
	 * @Title: getInsert
	 * @Description: 生成insert语句
	 * @param @param modelName
	 * @param @param orgCols
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getInsert(String modelName, String tableName, List<String> orgCols)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("<!-- 新增 -->");
		builder.append(lineSplit);
		builder.append("<insert id=\"insert\" parameterType=\"").append(modelName).append("\">");
		builder.append(lineSplit);
		builder.append("	<![CDATA[");
		builder.append(lineSplit);
		builder.append("	INSERT INTO " + tableName.toUpperCase() + "(");
		for (int i = 0; i < orgCols.size(); i++) {
			builder.append(orgCols.get(i).toUpperCase()).append(
					(i != orgCols.size() - 1) ? "," : "");
		}
		builder.append(") VALUES (");
		for (int i = 0; i < orgCols.size(); i++) {
			builder.append("#{").append(parseCol(orgCols.get(i)))
					.append((i != orgCols.size() - 1) ? "}," : "}");
		}
		builder.append(")");
		builder.append(lineSplit);
		builder.append("	]]>");
		builder.append(lineSplit);
		builder.append("</insert>");
		builder.append(lineSplit);
		builder.append(lineSplit);
		return builder.toString();
	}

	/**
	 * 
	 * @Title: getDelete
	 * @Description: 生存delete语句
	 * @param @param tableName
	 * @param @param idCols
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getDelete(String tableName, List<String> idCols)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("<!-- 删除 -->");
		builder.append(lineSplit);
		builder.append("<delete id=\"delete\" parameterType=\"java.util.Map\">");
		builder.append(lineSplit);
		builder.append("	<![CDATA[");
		builder.append(lineSplit);
		builder.append("	DELETE FROM ").append(tableName.toUpperCase());
		builder.append(lineSplit);
		builder.append("	]]>");
		if (idCols != null) {
			builder.append(lineSplit);
			builder.append("<where>");
			builder.append(lineSplit);
			for (String string : idCols) {
				builder.append("<if test=\"" + string.toUpperCase() + "!=null\">");
				builder.append(lineSplit);
				builder.append("				<![CDATA[AND " + string.toUpperCase() + " = #{")
						.append(string.toUpperCase()).append("}]]>");
				builder.append(lineSplit);
				builder.append("</if>");
				builder.append(lineSplit);
				builder.append("<if test=\"" + parseCol(string) + "!=null\">");
				builder.append(lineSplit);
				builder.append("				<![CDATA[AND " + string.toUpperCase() + " = #{")
						.append(parseCol(string)).append("}]]>");
				builder.append(lineSplit);
				builder.append("</if>");
			}
			builder.append(lineSplit);
			builder.append("</where>");
			builder.append(lineSplit);
		}

		builder.append("</delete>");
		builder.append(lineSplit);
		builder.append(lineSplit);
		return builder.toString();
	}

	/**
	 * @Title: getUpdate
	 * @Description: TODO
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getUpdate(String modelName, String tableName, List<String> orgCols,
			List<String> idCols)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("<!-- 修改 -->");
		builder.append(lineSplit);
		builder.append("<update id=\"update\" parameterType=\"" + modelName + "\">");
		builder.append(lineSplit);
		builder.append("	<![CDATA[");
		builder.append(lineSplit);
		builder.append("	UPDATE ").append(tableName.toUpperCase()).append(" SET ");
		for (int i = 0; i < orgCols.size(); i++) {
			if (idCols.contains(orgCols.get(i))) {
				continue;
			}
			builder.append(orgCols.get(i).toUpperCase()).append(" = ").append("#{")
					.append(parseCol(orgCols.get(i))).append("}")
					.append((i != orgCols.size() - 1) ? "," : "");
		}
		builder.append(lineSplit);
		builder.append("	]]>");
		builder.append(lineSplit);
		if (idCols != null) {
			builder.append("<where>");
			builder.append(lineSplit);
			for (String string : idCols) {
				builder.append("<if test=\"" + parseCol(string.toLowerCase()) + "!=null\">");
				builder.append(lineSplit);
				builder.append("				<![CDATA[AND " + string.toUpperCase() + " = #{")
						.append(parseCol(string)).append("}]]>");
				builder.append(lineSplit);
				builder.append("</if>");
			}
			builder.append(lineSplit);
			builder.append("</where>");
			builder.append(lineSplit);
		}
		builder.append("</update>");
		builder.append(lineSplit);
		builder.append(lineSplit);
		return builder.toString();
	}

	/**
	 * @Title: getUpdate
	 * @Description: TODO
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getUpdate2(String modelName, String tableName, List<String> orgCols,
			List<String> idCols)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("<!-- 修改 -->");
		builder.append(lineSplit);
		builder.append("<update id=\"update\" parameterType=\"" + modelName + "\">");
		builder.append(lineSplit);
		builder.append("	<![CDATA[");
		builder.append(lineSplit);
		builder.append("	UPDATE ").append(tableName.toUpperCase());
		builder.append(lineSplit);
		builder.append("	]]>");
		builder.append(lineSplit);
		builder.append(" <set> ");
		builder.append(lineSplit);
		for (int i = 0; i < orgCols.size(); i++) {
			if (idCols.contains(orgCols.get(i))) {
				continue;
			}
			builder.append("	<if test=\"" + parseCol(orgCols.get(i)) + "!=null\">");
			builder.append(lineSplit);
			builder.append("		" + orgCols.get(i).toUpperCase() + " = #{" + parseCol(orgCols.get(i))
					+ "},");
			builder.append(lineSplit);
			builder.append("	</if>");
			builder.append(lineSplit);
		}
		builder.append("</set> ");
		builder.append(lineSplit);
		if (idCols != null) {
			builder.append("<where>");
			builder.append(lineSplit);
			for (String string : idCols) {
				builder.append("<if test=\"" + parseCol(string.toLowerCase()) + "!=null\">");
				builder.append(lineSplit);
				builder.append("				<![CDATA[AND " + string.toUpperCase() + " = #{")
						.append(parseCol(string)).append("}]]>");
				builder.append(lineSplit);
				builder.append("</if>");
			}
			builder.append(lineSplit);
			builder.append("</where>");
			builder.append(lineSplit);
		}
		builder.append("</update>");
		builder.append(lineSplit);
		builder.append(lineSplit);
		return builder.toString();
	}

	/**
	 * @Title: getFindsql
	 * @Description: 获取通用查询的语句
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getFindsql(List<String> orgCols)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("<!-- 通用查询条件 -->");
		builder.append(lineSplit);
		builder.append("<sql id=\"findSql\">");
		builder.append(lineSplit);
		builder.append("<where>");
		builder.append(lineSplit);
		for (String string : orgCols) {
			builder.append("<if test=\"" + parseCol(string) + "!=null\">");
			builder.append(lineSplit);
			builder.append("				<![CDATA[AND " + string.toUpperCase() + " = #{")
					.append(parseCol(string)).append("}]]>");
			builder.append(lineSplit);
			builder.append("</if>");
			builder.append(lineSplit);
			builder.append("<if test=\"" + string.toUpperCase() + "!=null\">");
			builder.append(lineSplit);
			builder.append("				<![CDATA[AND " + string.toUpperCase() + " = #{")
					.append(string.toUpperCase()).append("}]]>");
			builder.append(lineSplit);
			builder.append("</if>");
			builder.append(lineSplit);
		}
		builder.append("</where>");
		builder.append(lineSplit);
		builder.append("</sql>");
		builder.append(lineSplit);
		builder.append(lineSplit);
		return builder.toString();
	}

	/**
	 * @Title: getModelListCount
	 * @Description: 生成获取数量列表的sql
	 * @param @param tableName
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getModelListCount(String tableName)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("<!-- 获取列表数量 -->");
		builder.append(lineSplit);
		builder.append("<select id=\"getModelListCount\" parameterType=\"java.util.Map\" resultType=\"long\">");
		builder.append(lineSplit);
		builder.append("		<![CDATA[");
		builder.append(lineSplit);
		builder.append("		SELECT COUNT(1) FROM ").append(tableName.toUpperCase()).append(" A");
		builder.append(lineSplit);
		builder.append("		]]>");
		builder.append(lineSplit);
		builder.append("<include refid=\"findSql\" />");
		builder.append(lineSplit);
		builder.append("</select>");
		builder.append(lineSplit);
		builder.append(lineSplit);
		return builder.toString();
	}

	/**
	 * @Title: getModelList
	 * @Description: 生成获取列表的sql
	 * @param @param tableName 表名
	 * @param @param orgCols 原列名列表
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String getModelList(String tableName, List<String> orgCols)
	{
		StringBuilder builder = new StringBuilder();
		builder.append("<!-- 获取列表 -->");
		builder.append(lineSplit);
		builder.append("<select id=\"getModelList\" parameterType=\"java.util.Map\" resultMap=\"modelMap\">");
		builder.append(lineSplit);
		builder.append("<include refid=\"global.pageStart\" />");
		builder.append(lineSplit);
		builder.append("		<![CDATA[");
		builder.append(lineSplit);
		builder.append("		SELECT ");
		for (int i = 0; i < orgCols.size(); i++) {
			builder.append(orgCols.get(i).toUpperCase()).append(
					(i != orgCols.size() - 1) ? ", " : "");
		}
		builder.append(" FROM ").append(tableName.toUpperCase());
		builder.append(" A");
		builder.append(lineSplit);
		builder.append("		]]>");
		builder.append(lineSplit);
		builder.append("<include refid=\"findSql\" />");
		builder.append(lineSplit);
		builder.append("<include refid=\"global.globalSortsql\" />");
		builder.append(lineSplit);
		builder.append("<include refid=\"global.pageEnd\" />");
		builder.append(lineSplit);
		builder.append("</select>");
		builder.append(lineSplit);
		builder.append(lineSplit);
		return builder.toString();
	}

	public static String getModel(CodeMapModel codeMapModel) throws TemplateException, IOException
	{
		String ftlname = "model.ftl";
		return getCode(codeMapModel, ftlname);
	}

	public static String getIService(CodeMapModel codeMapModel) throws TemplateException,
			IOException
	{
		String ftlname = "IService.ftl";
		return getCode(codeMapModel, ftlname);
	}

	public static String getServiceImpl(CodeMapModel codeMapModel) throws TemplateException,
			IOException
	{
		String ftlname = "ServiceImpl.ftl";
		return getCode(codeMapModel, ftlname);
	}

	public static String getIDao(CodeMapModel codeMapModel) throws TemplateException, IOException
	{
		String ftlname = "IDao.ftl";
		return getCode(codeMapModel, ftlname);
	}

	public static String getDaoImpl(CodeMapModel codeMapModel) throws TemplateException,
			IOException
	{
		String ftlname = "DaoImpl.ftl";
		return getCode(codeMapModel, ftlname);
	}

	public static String getSpring(CodeMapModel codeMapModel) throws TemplateException, IOException
	{
		String ftlname = "spring-remote.ftl";
		return getCode(codeMapModel, ftlname);
	}

	private static String getCode(CodeMapModel codeMapModel, String ftlname)
			throws TemplateException, IOException
	{
		Configuration cfg = new Configuration();
		cfg.setDefaultEncoding("UTF-8");
		// 设置模板文件所在的目录
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		cfg.setClassForTemplateLoading(GenerateCode.class, "ftl/");
		Map<Object, Object> rootMap = new HashMap<Object, Object>();
		rootMap.put("codeMapModel", codeMapModel);
		rootMap.put("data", sdf.format(Calendar.getInstance().getTime()));
		ByteArrayOutputStream bs = new ByteArrayOutputStream();

		cfg.getTemplate(ftlname).process(rootMap, new OutputStreamWriter(bs));
		return bs.toString();
	}

	/**
	 * @Title: parseCol
	 * @Description: 将列名转换成javabean的字段名称
	 * @param @param col
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String parseCol(String col, boolean firstIsUpper)
	{
		String[] strs = col.toLowerCase().split(filedSplit);
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < strs.length; i++) {
			String s = strs[i];
			str.append((i == 0 ? s : (s.substring(0, 1).toUpperCase() + s.substring(1))));
		}
		return firstIsUpper ? str.substring(0, 1).toUpperCase() + str.substring(1) : str.toString();
	}

	/**
	 * @Title: parseCol
	 * @Description: 将列名转换成javabean的字段名称
	 * @param @param col
	 * @param @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	public static String parseCol(String col)
	{
		return parseCol(col, false);
	}
}
