package ${package.GraphqlMutation};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import ${package.Entity}.${entity};
import ${package.Service}.I${entity}Biz;
import com.ly.mp.component.entities.EntityResult;
import com.ly.mp.busicen.common.context.BusicenInvoker;



/**
 * <p>
 * ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Component
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${entity}Mutation implements GraphQLMutationResolver{

	@Autowired
	I${entity}Biz ${table.getEntityPath()}Biz;
	
	/**
	 * 根据主键 插入或更新
	 * @author ${author}
	 * @param info
	 * @return
	 */
	public EntityResult<${entity}> ${table.getEntityPath()}MutationSaveById(${entity} dataInfo) {	
		return BusicenInvoker.doEntity(() ->  
		${table.getEntityPath()}Biz.${table.getEntityPath()}Save(dataInfo))
				.result();
	}
	
}
</#if>
