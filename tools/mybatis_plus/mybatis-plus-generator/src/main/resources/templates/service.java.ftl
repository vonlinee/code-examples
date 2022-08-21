package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};
import com.ly.mp.component.entities.EntityResult;
import com.ly.mp.component.entities.ListResult;

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
	/**
	 * 分页查询
	 * @param pageInfo
	 * @param info
	 * @return
	 */
	ListResult<${entity}> ${table.getEntityPath()}QueryFindAll(int pageIndex,int pageSize,${entity} info);
	
	/**
	 * 根据主键判断插入或更新
	 * @param info
	 * @return
	 */
	EntityResult<${entity}> ${table.getEntityPath()}Save(${entity} info);
	
}
</#if>
