package ${Package}.mapper;

import ${Package}.pojo.${T};
import java.util.List;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ${author}
 <#if version??>
 * @version ${version}
 </#if>
 * @date ${creatortime}
 */
@Mapper
@Repository
public interface ${fileName} {

    @Insert({"<script>",
    "insert into ${tableName} (",
    <#list newMember as m>
        "<if test='param.<#if underline==true>${m.humpColumnName? uncap_first}<#else>${m.columnName? uncap_first}</#if> != null'>",
        "${m.columnName? uncap_first}<#if m_has_next>,</#if>",
        "</if>",
        </#list>
    ") values(",
     <#list newMember as m>
        "<if test='param.<#if underline==true>${m.humpColumnName? uncap_first}<#else>${m.columnName? uncap_first}</#if> != null'>",
        "<#noparse>#{</#noparse>param.${m.humpColumnName? uncap_first}<#noparse>}</#noparse><#if m_has_next>,</#if>",
        "</if>",
     </#list>
    ")",
    "</script>"})
    @SelectKey(statement = "SELECT last_insert_id()", keyProperty = "<#list newMember as m><#if m.key==true>${m.columnName}", before = false, resultType = ${m.javaType}.class</#if></#list>)
    int insert(@Param("param") ${T} param);

    @Update({"<script>",
    "update ${tableName}",
    "<set>",
    <#list newMember as m><#if m.key==false>
        "<when test='param.<#if underline==true>${m.humpColumnName? uncap_first}<#else>${m.columnName? uncap_first}</#if> != null'>",
            "${m.columnName} = <#noparse>#{</#noparse>param.<#if underline==true>${m.humpColumnName? uncap_first}<#else>${m.columnName? uncap_first}</#if><#noparse>}</#noparse>,",
        "</when>",
    </#if></#list>
    "</set>",
    "<where>",
        "<#list newMember as m><#if m.key==true>and ${m.columnName} = <#noparse>#{</#noparse>param.<#if underline==true>${m.humpColumnName? uncap_first}<#else>${m.columnName? uncap_first}</#if><#noparse>}</#noparse></#if></#list>",
    "</where>",
    "</script>"})
    int update(@Param("param") ${T} param);

    @Delete("delete from ${tableName} where <#list newMember as m><#if m.key==true>${m.columnName} = <#noparse>#{</#noparse><#if underline==true>${m.humpColumnName? uncap_first}<#else>${m.columnName? uncap_first}</#if><#noparse>}</#noparse></#if></#list>")
    int delete(<#list newMember as m><#if m.key==true>${m.javaType} ${m.columnName}</#if></#list>);

    @Select("select * from ${tableName} where <#list newMember as m><#if m.key==true>${m.columnName} = <#noparse>#{</#noparse><#if underline==true>${m.humpColumnName? uncap_first}<#else>${m.columnName? uncap_first}</#if><#noparse>}</#noparse></#if></#list>")
    ${T} selectByPrimaryKey(<#list newMember as m><#if m.key==true>${m.javaType} ${m.columnName}</#if></#list>);

    @Select({"<script>",
    "select * from ${tableName}",
    "<where>",
        <#list newMember as m>
        "<when test='param.<#if underline==true>${m.humpColumnName? uncap_first}<#else>${m.columnName? uncap_first}</#if> != null'>",
            "and ${m.columnName} = <#noparse>#{</#noparse>param.<#if underline==true>${m.humpColumnName? uncap_first}<#else>${m.columnName? uncap_first}</#if><#noparse>}</#noparse>",
        "</when>",
        </#list>
    "</where>",
    "</script>"})
    List<${T}> selectByParam(@Param("param") ${T} param);


    @Insert({"<script>",
    "insert into ${tableName} (",
        <#list newMember as m><#if m.key==false><#if m.columnName!="create_time"><#if m.columnName!="update_time">
        "${m.columnName? uncap_first}<#if m_has_next>,</#if>",
        </#if></#if></#if></#list>
        ") values",
        " <foreach collection = 'list' item='param' separator=','>",
        "(",
        <#list newMember as m><#if m.key==false><#if m.columnName!="create_time"><#if m.columnName!="update_time">
        "<#noparse>#{</#noparse>param.${m.humpColumnName? uncap_first}<#noparse>}</#noparse><#if m_has_next>,</#if>",
        </#if></#if></#if></#list>
        ")",
        "</foreach>",
        "</script>"})
    int insertBatch(@Param("list") List<${T}> list);
}