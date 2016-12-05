<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE sqlMap PUBLIC "-//ibatis.apache.org//DTD SQL Map 2.0//EN" "http://ibatis.apache.org/dtd/sql-map-2.dtd" >
<!-- ${codeMapModel.desc} -->
<sqlMap namespace="${codeMapModel.modelNameSpace}.${codeMapModel.modelName}Model">
	
	<!-- 新增 -->
	<insert id="insert" parameterClass="${codeMapModel.modelNameSpace}.${codeMapModel.modelName}Model">
		insert into `${codeMapModel.tableName}` (
		<dynamic prepend=" ">  
			<#list codeMapModel.orgCols as col>
			<isNotEmpty prepend=","   property="${col.columnName}">
			<![CDATA[ `${col.columnName}` ]]>
			</isNotEmpty>
			</#list>
		</dynamic>
		)
		values(
	   <dynamic prepend=" ">  
			<#list codeMapModel.orgCols as col>
			<isNotEmpty prepend=","  property="${col.columnName}">
			<![CDATA[ #${col.columnName}# ]]>
			</isNotEmpty>
			</#list>
		</dynamic>
		)
		<selectKey resultClass="java.lang.Long" keyProperty="${codeMapModel.idCols[0].columnName}" type="post"> 
		<![CDATA[select last_insert_id() as id ]]>
		</selectKey>
	</insert>
	
	<!-- 批量插入 -->
	<insert id="insertBatch" parameterClass="java.util.List">
		insert into `${codeMapModel.tableName}` (<#list codeMapModel.orgCols as col>`${col.columnName}`<#if (codeMapModel.orgCols?size>col_index+1)>,</#if></#list>)
		values
		<iterate conjunction=",">
		(<#list codeMapModel.orgCols as col>#item[].${col.columnName}#<#if (codeMapModel.orgCols?size>col_index+1)>,</#if></#list>)
		</iterate>
	</insert>

	<!-- 修改 -->
	<update id="update" parameterClass="${codeMapModel.modelNameSpace}.${codeMapModel.modelName}Model">
		update `${codeMapModel.tableName}` 
		<dynamic prepend="SET ">  
			<#list codeMapModel.exceptIdCols as col>
			<isNotEmpty prepend=","   property="${col.columnName}">
			<![CDATA[ `${col.columnName}` = #${col.columnName}# ]]>
			</isNotEmpty>
			</#list>
		 </dynamic>  
		<dynamic prepend="where">
			<#list codeMapModel.idCols as col>
			<isNotEmpty prepend="AND" property="${col.columnName}">
			<![CDATA[ `${col.columnName}` = #${col.columnName}# ]]>
			</isNotEmpty>
			</#list>
			<#if codeMapModel.upByVersion>
			<isNotEmpty prepend="AND" property="where_version">
			<![CDATA[ `version` = #where_version# ]]>
			</isNotEmpty>
			</#if>
		</dynamic>
	</update>
	
	<!-- 修改 -->
	<update id="updateByMap" parameterClass="map">
		update `${codeMapModel.tableName}` 
		<dynamic prepend="SET ">  
			<#list codeMapModel.exceptIdCols as col>
			<isNotEmpty prepend=","   property="${col.columnName}">
			<![CDATA[ `${col.columnName}` = #${col.columnName}# ]]>
			</isNotEmpty>
			</#list>
		 </dynamic>  
		<dynamic prepend="where">
			<#list codeMapModel.idCols as col>
			<isNotEmpty prepend="AND" property="${col.columnName}">
			<![CDATA[ `${col.columnName}` = #${col.columnName}# ]]>
			</isNotEmpty>
			</#list>
		</dynamic>
	</update>
	
	<!-- 删除 -->
	<delete id="delete" parameterClass="map">
		<![CDATA[
			delete from `${codeMapModel.tableName}`
		]]>
		<dynamic prepend="where">
			<#list codeMapModel.idCols as col>
			<isNotEmpty prepend="AND" property="${col.columnName}">
			<![CDATA[ `${col.columnName}` = #${col.columnName}# ]]>
			</isNotEmpty>
			</#list>
		</dynamic>
	</delete>

	<sql id="findSql">
		<dynamic prepend="where">
			<#list codeMapModel.orgCols as col>
			<isNotEmpty prepend="AND" property="${col.columnName}">
			<![CDATA[ a.`${col.columnName}` = #${col.columnName}# ]]>
			</isNotEmpty>
			</#list>
		</dynamic>
	</sql>
	
	<select id="getModelListCount" parameterClass="map"
		resultClass="int">
		<![CDATA[
			select count(*)
			  from `${codeMapModel.tableName}` a
		]]>
		<include refid="findSql" />
	</select>

	<select id="getModelList" parameterClass="map"
		resultClass="${codeMapModel.modelNameSpace}.${codeMapModel.modelName}Model">
		<include refid="global.pageStart" />
		<![CDATA[
			select 
			<#list codeMapModel.orgCols as col>
			   a.`${col.columnName}`<#if (codeMapModel.orgCols?size>col_index+1)>,</#if>
			</#list>
			FROM `${codeMapModel.tableName}`  a
		]]>
		<include refid="findSql" />
		<include refid="global.globalSortsql" />
		<include refid="global.pageEnd" />
	</select>

</sqlMap>

