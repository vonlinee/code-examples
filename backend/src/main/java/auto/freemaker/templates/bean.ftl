package ${Package}.pojo;

${imports}

/**
 *
 * @author ${author}
<#if version??>
 * @version ${version}
</#if>
 * @date ${creatortime}
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ${fileName} implements Serializable {
<#list newMember as m>

    @ApiParam("${m.commentName}")
    <#if underline==true>
    private ${m.javaType} ${m.humpColumnName ? uncap_first};
    <#else>
    private ${m.javaType} ${m.columnName ? uncap_first};
    </#if>
</#list>
}