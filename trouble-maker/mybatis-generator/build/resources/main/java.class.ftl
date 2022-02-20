package ${packageName};

<#--导入列表-->
<#list importList as importItem>
import <#if importItem.isStaticImport>static</#if> ${importItem.fullName};
</#list>
/* *
 *
 * @author ${author}
 * @since ${createdDate}
 * * /
<#list antotationList as annotation>
annotation
</#list>
${typeModifier} ${type} ${typeName}<#if hasGenericType>typeVars</#if>
    <#if extends>extends ${superClass}</#if>
    <#if implements>implements</#if>
<#list superInterfaces as superInterface>
    ${superInterface.name} <#if superInterface.hasGenericType>${superInterface.typeVars}</#if>
</#list>
{
    <#list declaredFields as field>
        ${field.decoratedFlags} ${field.name} <#if field.valueAssigned>= ${field.value}</#if>
    </#list>

}