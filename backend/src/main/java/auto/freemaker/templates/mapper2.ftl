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

    int insert(@Param("param") ${T} param);

    int update(@Param("param") ${T} param);

    int delete(<#list newMember as m><#if m.key==true>${m.javaType} ${m.columnName}</#if></#list>);

    ${T} selectByPrimaryKey(<#list newMember as m><#if m.key==true>${m.javaType} ${m.columnName}</#if></#list>);

    List<${T}> selectByParam(@Param("param") ${T} param);

    int insertBatch(@Param("list") List<${T}> list);
}