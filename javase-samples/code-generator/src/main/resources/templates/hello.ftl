你好啊，${hello}，今天你的精神不错！
if else 语句测试
<#if num gt 18><#-- 不使用 >,大部分时候，freemarker会把 > 解释成标签结束！-->
     及格！
<#else>
    不及格！
</#if>

测试list
<#list list as dizhi >
    <b>${dizhi.country}</b> <br/>
</#list>

测试对象
    <b>${address.country}</b> <br/>
    
自定义指令(macro 对象)
<#macro jason>
    <a>jason</a>
    <b>hello!</b>
</#macro>

<#macro jason1 a b c>
<a>jason${a}</a>
    <b>hello!${b}</b>
    <b>hello!${c}</b>
</#macro>
调用自定义指令
    <@jason />
    <@jason />
    
带参数的指令
    <@jason1 a="jason" b="hello" c="a" /> <#-- 如果少参数,c is not specified. -->