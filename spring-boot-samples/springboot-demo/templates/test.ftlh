<#-- 字符和整数 -->
${name}-${age} 
<#-- 布尔值 -->
${sex?string('男', '女')}
<#-- 日期 -->
${birthday?date}
${birthday?time}
${birthday?datetime}
<#-- 遍历list -->
<#list hobbies as hobby>
${hobby}
</#list>
<#-- 遍历Map -->
<#list releationShip?keys as key>
key:${key}-value:${releationShip["${key}"]}
</#list>