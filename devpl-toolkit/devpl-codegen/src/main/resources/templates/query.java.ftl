package ${package.GraphqlQuery};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import ${package.Entity}.${entity};
import ${package.Service}.I${entity}Biz;
import com.ly.mp.component.entities.ListResult;
import com.ly.mp.busicen.common.context.BusicenInvoker;
import com.ly.mp.busicen.common.util.BusicenUtils;


@Component
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${entity}Query implements GraphQLQueryResolver{

	@Autowired
	I${entity}Biz ${table.getEntityPath()}Biz;
	
	/** 分页查询
	 * @author ${author}
	 */
	public ListResult<${entity}> ${table.getEntityPath()}QueryFindAll(int pageIndex,int pageSize,${entity} dataInfo){
		return BusicenInvoker.doList(() ->  
		BusicenUtils.convert2Dynamic(${entity}.class, ${table.getEntityPath()}Biz.${table.getEntityPath()}QueryFindAll(pageIndex, pageSize, dataInfo)))
				.result();
		
	}
	

	
}
</#if>
