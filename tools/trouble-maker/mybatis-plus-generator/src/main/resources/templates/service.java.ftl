package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};

/**
 * <p>
 * ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

 <#if generateServiceMethod??>
  <!-- 默认的单表增删改查sql -->
  <#list serviceMethodList as serviceMethod>
       ${serviceMethod.modifier} ${serviceMethod.returnValue}
  </#list>
 <#else>
 </#if>
}
</#if>
