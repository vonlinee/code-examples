<#if classPath??>
package ${classPath};
</#if>

${imports}
import ${Package}.domain.*;
import ${Package}.pojo.${T};
import ${Package}.service.${Service};

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author ${author}
<#if version??>
 * @version ${version}
</#if>
 * @date ${creatortime}
 */
@Api(tags = "${Controller}")
@RestController
@RequestMapping(value = "/${Controller}")
public class ${fileName} {

    @Autowired
    private ${Service} ${Service ? uncap_first};

    @ApiOperation(value = "新增更新")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ApiFinalResult save(HttpServletRequest request, @RequestBody @Valid ${T} param) throws Exception {
        if (param.get<#list newMember as m><#if m.key==true><#if underline==true>${m.humpColumnName ? cap_first}<#else>${m.columnName ? cap_first}</#if></#if></#list>() == null) {
            return ${Service ? uncap_first}.insert(param, request);
        } else {
            return ${Service ? uncap_first}.update(param, request);
        }
    }

    @ApiOperation(value = "删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ApiFinalResult delete(HttpServletRequest request, <#list newMember as m><#if m.key==true>@ApiParam("${m.commentName}") @OneRequestBody @NotNull(message = "${m.commentName}不能为空") ${m.javaType} <#if underline==true>${m.humpColumnName ? uncap_first}<#else>${m.columnName ? uncap_first}</#if></#if></#list>) throws Exception {
        return ${Service ? uncap_first}.delete(<#list newMember as m><#if m.key==true><#if underline==true>${m.humpColumnName ? uncap_first}<#else>${m.columnName ? uncap_first}</#if></#if></#list>, request);
    }

    @ApiOperation(value = "根据主键查询")
    @RequestMapping(value = "/selectByPrimaryKey", method = RequestMethod.GET)
    public ApiFinalResult selectByPrimaryKey(HttpServletRequest request, <#list newMember as m><#if m.key==true>@ApiParam("${m.commentName}") @NotNull(message = "${m.commentName}不能为空") ${m.javaType} <#if underline==true>${m.humpColumnName ? uncap_first}<#else>${m.columnName ? uncap_first}</#if></#if></#list>) throws Exception {
        return ${Service ? uncap_first}.selectByPrimaryKey(<#list newMember as m><#if m.key==true><#if underline==true>${m.humpColumnName ? uncap_first}<#else>${m.columnName ? uncap_first}</#if></#if></#list>, request);
    }

    @ApiOperation(value = "条件查询")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ApiFinalResult getList(HttpServletRequest request, @Valid ${T} param) throws Exception {
        return ${Service ? uncap_first}.getList(param, request);
    }

    @ApiOperation(value = "条件查询(分页)")
    @RequestMapping(value = "/pageList", method = RequestMethod.GET)
    public ApiFinalResult pageList(HttpServletRequest request, @Valid ${T} param,
                                   @ApiParam("当前页码") @RequestParam(value="pageNo",defaultValue = "1") Integer pageNo,
                                   @ApiParam("一页大小") @RequestParam(value="pageSize",defaultValue = "20") Integer pageSize) throws Exception {

        return ${Service ? uncap_first}.pageList(param, pageNo, pageSize, request);
    }
}