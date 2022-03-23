package ${packageName};

<#-- import -->
<#list importList as importItem>
import ${importItem};
</#list>

${typeModifier} ${javaType} ${name} ${extendsFrom?string('extends', '')}
<#if extendsFrom > 0
	<#list extendsList as extendsItem>
		${extendsItem}
	</#list>
</#if>
${toImplements?string('implements', '')}

<#if toImplements > 0
	<#list implementsList as implementsItem>
		${implementsItem}
	</#list>
</#if>

{

	<#-- 静态字段 -->

	<#-- 实例字段 -->

	

}