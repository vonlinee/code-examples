package ${package.GraphqlMutation};

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import ${package.Service}.I${entity}Biz;
<#if cfg["mutationFalg"]=1 >
import com.ly.mp.busicen.common.context.BusicenInvoker;
import com.ly.mp.component.entities.OptResult;
import java.util.Map;
</#if>

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
	<#if cfg["mutationFalg"]=1 >
	/**
	 * ${table.comment!}保存
	 * @author ${author}
	 * @since ${date}
	 * @param info
	 * @return
	 */
	public OptResult ${table.getEntityPath()}MutationSave(Map<String,Object> dataInfo) {	
		return BusicenInvoker.doOpt(() ->  
		${table.getEntityPath()}Biz.${table.getEntityPath()}MutationSave(dataInfo))
				.result();
	}
	</#if>
	
	
	
}
</#if>
