<#if cfg["queryFalg"]=1 >
extend type Query{
	#[open][中心名][菜单名][功能名-查询][1.00]
	${table.getEntityPath()}QueryFindAll(pageIndex:Int,pageSize:Int,dataInfo:Input${entity}Ext):ListResult${entity}Ext
}
</#if>
<#if cfg["mutationFalg"]=1 >
extend type Mutation{
	#[open][中心名][菜单名][功能名-保存][1.00]
	${table.getEntityPath()}MutationSave(dataInfo:Input${entity}Ext):OptResult
}
</#if>


type ${entity}Ext {
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field> 
	<#if field.comment!?length gt 0>
	#${field.comment}
	</#if>
	${field.propertyName}: ${field.propertyType}
</#list>
<#------------  END 字段循环遍历  ---------->
}

#${table.comment!}分页数据
type ListResult${entity}Ext {
	#响应码
	result: String!
	#响应信息
	msg: String	
	#分页数据-当前页
	pageindex:Int
	#分页数据-页数
	pages:Int
	#分页数据-条数
	records:Int
	# 为实体类型
	rows:[${entity}Ext]
}

#${table.comment!}单个数据
type EntityResult${entity}Ext {
	#响应码
	result: String!
	#响应信息
	msg: String
	#拓展信息
	extInfo: String
	# 为实体类型
	rows:${entity}Ext
}

<#if table.convert>
#${table.comment!} ${table.name}
<#else>
# Input${entity}Ext
</#if>
input Input${entity}Ext {
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field> 
	<#if field.comment!?length gt 0>
	#${field.comment}
	</#if>
	${field.propertyName}: ${field.propertyType}
</#list>
<#------------  END 字段循环遍历  ---------->
}