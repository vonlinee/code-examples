package ${package.GraphqlQuery};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Map;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import ${package.Service}.I${entity}Biz;
<#if cfg["queryFalg"]=1 >
import com.ly.mp.component.entities.ListResult;
import com.ly.mp.busicen.common.context.BusicenInvoker;
import com.ly.mp.busicen.common.util.BusicenUtils;
</#if>

@Component
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${entity}Query implements GraphQLQueryResolver{

	@Autowired
	I${entity}Biz ${table.getEntityPath()}Biz;
	
	public static interface ${entity}Biz extends Map<String,Object>{};
	
	<#if cfg["queryFalg"]=1 >
	/**
	 * ${table.comment!}分页查询
	 * @author ${author}
	 * @since ${date}
	 */
	public ListResult<${entity}Biz> ${table.getEntityPath()}QueryFindAll(int pageIndex,int pageSize,Map<String,Object> dataInfo){
		return BusicenInvoker.doList(() ->  
		BusicenUtils.convert2Dynamic(${entity}Biz.class, ${table.getEntityPath()}Biz.${table.getEntityPath()}QueryFindAll(pageIndex, pageSize, dataInfo)))
				.result();
		
	}
	</#if>
	
	
	
}
</#if>
