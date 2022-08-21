package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import com.ly.mp.component.entities.EntityResult;
import com.ly.mp.component.entities.ListResult;
import com.ly.mp.busicen.common.util.BusicenDbBaseUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.seata.spring.annotation.GlobalTransactional;


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

private Logger log = LoggerFactory.getLogger(${entity}Biz.class);
	
	@Autowired
	${table.mapperName} ${table.getEntityPath()}Mapper;
	
	
	/**
	 * 分页查询
	 */
	@Override
	public ListResult<${entity}> ${table.getEntityPath()}QueryFindAll(int pageIndex,int pageSize,${entity} info){
		try {
			return BusicenDbBaseUtils.baseQueryFindAll(pageIndex, pageSize, info, ${table.getEntityPath()}Mapper);
		} catch (Exception e) {
			log.error("dbNodeQueryFindAll", e);
			//抛出RuntimeException，事务才会回滚
			throw new RuntimeException(e.getMessage());
		}
	}
	
	
	/**
	 * 根据主键判断插入或更新
	 * @param info
	 * @return
	 */
	@Override
	@GlobalTransactional(name="${table.getEntityPath()}Save")
	public EntityResult<${entity}> ${table.getEntityPath()}Save(${entity} info){
		
		try {
			return BusicenDbBaseUtils.baseSaveById(info,${table.getEntityPath()}Mapper,null,"",false);
		} catch (Exception e) {
			log.error("${table.getEntityPath()}Save", e);
			throw e;
		} 
	}
	
}
</#if>
