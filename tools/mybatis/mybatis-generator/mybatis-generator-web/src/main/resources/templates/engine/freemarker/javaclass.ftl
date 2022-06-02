<#assign name="Tom">

${javaVisiablity!' '}${javaType} ${typeName} {

<#if (fieldList.size > 0)>
	<#list fieldList.list as field>
		${field.name!'AAA'}    ${field.price!'BBB'}  ${name}
	</#list>
</#if>

}
