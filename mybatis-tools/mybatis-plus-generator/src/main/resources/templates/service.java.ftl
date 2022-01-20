package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};
<#if cfg["queryFalg"]=1 >
import com.ly.mp.component.entities.ListResult;
</#if>
<#if cfg["queryFalg"]=1 || cfg["mutationFalg"]=1 >
import java.util.Map;
</#if>
<#if cfg["mutationFalg"]=1 >
import com.ly.mp.component.entities.OptResult;
</#if>
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

	<#if cfg["queryFalg"]=1 >
	/**
	 * ${table.comment!}分页查询
	 * @param pageInfo
	 * @param info
	 * @return
	 */
	ListResult<Map<String, Object>> ${table.getEntityPath()}QueryFindAll(int pageIndex,int pageSize,Map<String,Object> info);
	</#if>
	<#if cfg["mutationFalg"]=1 >
	/**
	 * ${table.comment!}保存
	 * @param info
	 * @return
	 */
	OptResult ${table.getEntityPath()}MutationSave(Map<String,Object> info);
	</#if>
	
}
</#if>
