<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${Package}.mapper.${Mapper}">

    <resultMap id="BaseResultMap" type="${Package}.pojo.${T}">
        <#list newMember as m>
            <#if m.key==true>
        <id column="${m.columnName}" property="<#if underline==true>${m.humpColumnName? uncap_first}<#else>${m.columnName? uncap_first}</#if>"/>
            <#else>
        <result column="${m.columnName}" property="<#if underline==true>${m.humpColumnName? uncap_first}<#else>${m.columnName? uncap_first}</#if>"/>
            </#if>
        </#list>
    </resultMap>

    <sql id="Base_Column_List">
        <#list newMember as m>${m.columnName}<#if m_has_next>,</#if></#list>
    </sql>

    <#if underline==true>
    <sql id="Base_Column_List_Model">
        <#list newMember as m><#if m.columnName?contains("_")>${m.columnName} as ${m.humpColumnName? uncap_first}<#else>${m.columnName}</#if><#if m_has_next>, </#if></#list>
    </sql>
    </#if>

    <!-- 根据主键查询 -->
    <select id="selectByPrimaryKey" resultMap="BaseResultMap" parameterType="java.lang.<#list newMember as m><#if m.key==true>${m.javaType}</#if></#list>">
        select
        <include refid="Base_Column_List"/>
        from ${tableName}
        where <#list newMember as m><#if m.key==true>${m.columnName} = <#noparse>#{</#noparse><#if underline==true>${m.humpColumnName? uncap_first}<#else>${m.columnName? uncap_first}</#if><#noparse>}</#noparse></#if></#list>
    </select>

    <!-- 根据对象条件查询 -->
    <select id="selectByParam" parameterType="${Package}.pojo.${T}" resultType="${Package}.pojo.${T}">
        select
        <#if underline==true><include refid="Base_Column_List_Model"/><#else><include refid="Base_Column_List"/></#if>
        from ${tableName}
        <where>
            <#list newMember as m>
                <if test="param.<#if underline==true>${m.humpColumnName? uncap_first}<#else>${m.columnName? uncap_first}</#if> != null">
                    and ${m.columnName} = <#noparse>#{param.</#noparse><#if underline==true>${m.humpColumnName? uncap_first}<#else>${m.columnName? uncap_first}</#if><#noparse>}</#noparse>
                </if>
            </#list>
        </where>
    </select>

    <!-- 根据主键删除 -->
    <delete id="delete" parameterType="java.lang.<#list newMember as m><#if m.key==true>${m.javaType}</#if></#list>">
        delete from ${tableName}
        where <#list newMember as m><#if m.key==true>${m.columnName} = <#noparse>#{</#noparse><#if underline==true>${m.humpColumnName? uncap_first}<#else>${m.columnName? uncap_first}</#if><#noparse>}</#noparse></#if></#list>
    </delete>

    <!-- 插入数据 -->
    <insert id="insert" parameterType="${Package}.pojo.${T}" useGeneratedKeys="true" keyProperty="<#list newMember as m><#if m.key==true><#if underline==true>${m.humpColumnName? uncap_first}<#else>${m.columnName? uncap_first}</#if></#if></#list>">
        insert into ${tableName}
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <#list newMember as m>
                <if test="param.<#if underline==true>${m.humpColumnName? uncap_first}<#else>${m.columnName? uncap_first}</#if> != null">
                    ${m.columnName? uncap_first},
                </if>
            </#list>
        </trim>
        <trim prefix="values (" suffix=")" suffixOverrides=",">
            <#list newMember as m>
                <if test="param.<#if underline==true>${m.humpColumnName? uncap_first}<#else>${m.columnName? uncap_first}</#if> != null">
                    <#noparse>#{param.</#noparse>${m.humpColumnName? uncap_first}<#noparse>}</#noparse>,
                </if>
            </#list>
        </trim>
    </insert>

    <!-- 根据主键修改 -->
    <update id="update" parameterType="${Package}.pojo.${T}">
        update ${tableName}
        <set>
            <#list newMember as m>
                <#if m.key==false>
                    <if test="param.<#if underline==true>${m.humpColumnName? uncap_first}<#else>${m.columnName? uncap_first}</#if> != null">
                        ${m.columnName} = <#noparse>#{param.</#noparse><#if underline==true>${m.humpColumnName? uncap_first}<#else>${m.columnName? uncap_first}</#if><#noparse>}</#noparse>,
                    </if>
                </#if>
            </#list>
        </set>
        where <#list newMember as m><#if m.key==true>${m.columnName} = <#noparse>#{param.</#noparse><#if underline==true>${m.humpColumnName? uncap_first}<#else>${m.columnName? uncap_first}</#if><#noparse>}</#noparse></#if></#list>
    </update>


    <!-- 批量插入数据 -->
    <insert id="insertBatch">
        insert into ${tableName}
        <trim prefix="(" suffix=")" suffixOverrides=",">
            <#list newMember as m><#if m.key==false><#if m.columnName!="create_time"><#if m.columnName!="update_time">${m.columnName? uncap_first},</#if></#if></#if></#list>
        </trim>
        values
        <foreach collection="list" item="item" index="index" separator=",">
            <trim prefix="(" suffix=")" suffixOverrides=",">
                <#list newMember as m><#if m.key==false><#if m.columnName!="create_time"><#if m.columnName!="update_time"><#noparse>#{item.</#noparse>${m.humpColumnName? uncap_first}<#noparse>}</#noparse>,</#if></#if></#if></#list>
            </trim>
        </foreach>
    </insert>

</mapper>