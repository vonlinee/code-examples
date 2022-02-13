package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
<#if cfg["queryFalg"]=1 >
import com.ly.mp.component.entities.ListResult;
</#if>
import com.ly.mp.busicen.rule.flow.IFireFlowFocus;
<#if cfg["mutationFalg"]=1 >
import com.ly.mp.busicen.common.util.OptResultBuilder;
import com.ly.mp.component.entities.OptResult;
import io.seata.spring.annotation.GlobalTransactional;
</#if>
<#if cfg["queryFalg"]=1 || cfg["mutationFalg"]=1>
import java.util.Map;
</#if>

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



/**
 * <p>
 * ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {
	
	@Autowired
	IFireFlowFocus fireFlowFocus;
	
	<#if cfg["queryFalg"]=1 >
	/**
	 * ${table.comment!}分页查询
	 * @param pageInfo
	 * @param info
	 * @return
	 */
	@Override
	public ListResult<Map<String, Object>> ${table.getEntityPath()}QueryFindAll(int pageIndex,int pageSize,Map<String,Object> info){
		try {
			return fireFlowFocus.add("info",info)
					.add("pageIndex",pageIndex)
					.add("pageSize", pageSize)
					.flow("").fireExcpt().exitResult();
		} catch (Exception e) {
			log.error("${table.getEntityPath()}QueryFindAll", e);
			throw e;
		}
	}
	</#if>
	<#if cfg["mutationFalg"]=1 >
	/**
	 * ${table.comment!}保存
	 * @param info
	 * @return
	 */
	@Override
	@GlobalTransactional(name="${table.serviceImplName}_${table.getEntityPath()}MutationSave")
	public OptResult ${table.getEntityPath()}MutationSave(Map<String,Object> info){
		
		try {
			fireFlowFocus.flow("").add("info", info).fireExcpt();
		} catch (Exception e) {
			log.error("${table.getEntityPath()}MutationSave", e);
			throw e;
		} 
		return OptResultBuilder.createOk().build();
	}
	</#if>
}
</#if>
