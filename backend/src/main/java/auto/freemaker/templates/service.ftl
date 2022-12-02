package ${Package}.service;


${imports}
import ${Package}.domain.*;
import ${Package}.pojo.${T};
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author ${author}
<#if version??>
 * @version ${version}
</#if>
 * @date ${creatortime}
 */
public interface ${fileName} {

    ApiFinalResult insert(${T} param, HttpServletRequest request) throws Exception;

    ApiFinalResult update(${T} param, HttpServletRequest request) throws Exception;

    ApiFinalResult delete(<#list newMember as m><#if m.key==true>${m.javaType} ${m.columnName}</#if></#list>, HttpServletRequest request) throws Exception;

    ApiFinalResult selectByPrimaryKey(<#list newMember as m><#if m.key==true>${m.javaType} ${m.columnName}</#if></#list>, HttpServletRequest request) throws Exception;

    ApiFinalResult getList(${T} param, HttpServletRequest request) throws Exception;

    ApiFinalResult pageList(${T} param, Integer pageNo, Integer pageSize, HttpServletRequest request) throws Exception;
}