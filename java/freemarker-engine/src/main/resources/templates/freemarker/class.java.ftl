<#if hasCopyRight == true>${copyright}</#if><#rt>
<#if hasPackage == true>package ${packageName!""};</#if><#rt>

<#if importList?exists>
<#list importList as importItem> 
import ${importItem};<#rt>
</#list>
</#if>

<#-- 静态导入 -->
<#if staticImportList?exists>
<#list staticImportList as staticImportItem> 
import ${staticImportItem};<#rt>
</#list>
</#if>
<#-- 类上的注释 -->
<#if hasJavaDoc == true>${javaDoc}</#if><#rt>
${typeModifier} ${javaType}<#lt> ${typeName} <#if hasSuperClass??>extends ${superClassName}</#if> <#if hasSuperInterface??>implements ${superInterfaces}</#if>{

<#if hasStaticCodeBlock??>${staticCodeBlock}</#if><#t>

<#if hasFields??>
<#list fieldList as field> 
${field.name} <#if field.hasInitialValue??> = ${field.initialValue}</#if>;
</#list>
</#if><#t>

<#if hasMethods??>
<#list methodList as method>
${method.name} <#if method.hasInitialValue??> = ${field.initialValue}</#if>;
</#list>
</#if><#t>
}