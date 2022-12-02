package ${Package}.service.impl;

import ${Package}.util.*;
import ${Package}.domain.*;
import ${Package}.pojo.${T};
import ${Package}.mapper.${Mapper};
import ${Package}.service.${Service};
${imports}


/**
 *
 * @author ${author}
<#if version??>
 * @version ${version}
</#if>
 * @date ${creatortime}
 */

@Slf4j
@Service
@Transactional
public class ${fileName} implements ${Service} {

    @Autowired
    private ${Mapper} ${Mapper ? uncap_first};

    @Override
    public ApiFinalResult insert(${T} param, HttpServletRequest request) throws Exception {

        log.info("Request-{} param:{}", request.getServletPath(), JsonUtil.toJson(param));
        int tag = ${Mapper ? uncap_first}.insert(param);
        if(tag <= 0) return ApiFinalResult.show(OperateResult.fail("服务器繁忙,请重试"));

        return ApiFinalResult.show(OperateResult.success("新增成功", param.get<#list newMember as m><#if m.key==true><#if underline==true>${m.humpColumnName ? cap_first}<#else>${m.columnName ? cap_first}</#if></#if></#list>()));

    }

    @Override
    public ApiFinalResult update(${T} param, HttpServletRequest request) throws Exception {

        log.info("Request-{} param:{}", request.getServletPath(), JsonUtil.toJson(param));
        int tag = ${Mapper ? uncap_first}.update(param);
        if(tag <= 0) return ApiFinalResult.show(OperateResult.fail("服务器繁忙,请重试"));

        return ApiFinalResult.show(OperateResult.success("更新成功"));

    }


    @Override
    public ApiFinalResult delete(<#list newMember as m><#if m.key==true>${m.javaType} id</#if></#list>, HttpServletRequest request) throws Exception {
        log.info("Request-{} id:{}", request.getServletPath(), id);
        int tag = ${Mapper ? uncap_first}.delete(id);
        if(tag <= 0) return ApiFinalResult.show(OperateResult.fail("服务器繁忙,请重试"));

        return ApiFinalResult.show(OperateResult.success("删除成功"));
    }

    @Override
    public ApiFinalResult selectByPrimaryKey(<#list newMember as m><#if m.key==true>${m.javaType} id</#if></#list>, HttpServletRequest request) throws Exception {
        log.info("Request-{} id:{}", request.getServletPath(), id);
        return ApiFinalResult.show(${Mapper ? uncap_first}.selectByPrimaryKey(id));
    }

    @Override
    public ApiFinalResult getList(${T} param, HttpServletRequest request) throws Exception {
        log.info("Request-{} param:{}", request.getServletPath(), JsonUtil.toJson(param));
        return ApiFinalResult.show(${Mapper ? uncap_first}.selectByParam(param));
    }

    @Override
    public ApiFinalResult pageList(${T} param, Integer pageNo, Integer pageSize, HttpServletRequest request) throws Exception {

        log.info("Request-{} param:{}", request.getServletPath(), JsonUtil.toJson(param));
        PageHelper.startPage(pageNo, pageSize);
        List<${T}> list = ${Mapper ? uncap_first}.selectByParam(param);

        return ApiFinalResult.show(new PageInfo<>(list));
    }

}