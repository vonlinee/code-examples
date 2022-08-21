<#if table.convert>
#${table.comment!} ${table.name} 
<#else>
# ${entity}
</#if>
type ${entity} {
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
type ListResult${entity} {
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
	rows:[${entity}]
}

#${table.comment!}单个数据
type EntityResult${entity} {
	#响应码
	result: String!
	#响应信息
	msg: String
	#拓展信息
	extInfo: String
	# 为实体类型
	rows:${entity}
}

<#if table.convert>
#${table.comment!} ${table.name}
<#else>
# Input${entity}
</#if>
input Input${entity} {
<#-- ----------  BEGIN 字段循环遍历  ---------->
<#list table.fields as field> 
	<#if field.comment!?length gt 0>
	#${field.comment}
	</#if>
	${field.propertyName}: ${field.propertyType}
</#list>
<#------------  END 字段循环遍历  ---------->
}
