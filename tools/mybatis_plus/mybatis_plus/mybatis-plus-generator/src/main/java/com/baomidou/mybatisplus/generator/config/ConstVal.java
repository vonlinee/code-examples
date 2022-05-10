/*
 * Copyright (c) 2011-2020, hubin (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.baomidou.mybatisplus.generator.config;

import java.nio.charset.Charset;

import com.baomidou.mybatisplus.core.toolkit.StringPool;

/**
 * <p>
 * 定义常量
 * </p>
 *
 * @author YangHu, tangguo, hubin
 * @since 2016-08-31
 */
public interface ConstVal {

    String MODULE_NAME = "ModuleName";

    String ENTITY = "Entity";
    String GRAPHQL = "Graphql";
    String GRAPHQL1 = "Graphql1";
    String GRAPHQL_QUERY = "GraphqlQuery";
    String GRAPHQL_MUTATION = "GraphqlMutation";
    String SERVICE = "Service";
    String SERVICE_IMPL = "ServiceImpl";
    String MODEL = "Model";
    String MAPPER = "Mapper";
    String XML = "Xml";
    String CONTROLLER = "Controller";
    String EXCEL_QUERY = "ExcelQuery";
    String EXCEL_MUTATION = "ExcelMutation";

    String ENTITY_PATH = "entity_path";
    String GRAPHQL_PATH = "graphql_path";
    String GRAPHQL1_PATH = "graphql1_path";
    String GRAPHQL_Query_PATH = "graphql_query_path";
    String GRAPHQL_Mutation_PATH = "graphql_mutation_path";
    String SERVICE_PATH = "service_path";
    String SERVICE_IMPL_PATH = "service_impl_path";
    String Model_PATH = "model_path";
    String MAPPER_PATH = "mapper_path";
    String XML_PATH = "xml_path";
    String CONTROLLER_PATH = "controller_path";
    String EXCEL_QUERY_PATH = "excel_query_path";
    String EXCEL_MUTATION_PATH = "excel_mutation_path";

    String JAVA_TMPDIR = "java.io.tmpdir";
    String UTF8 = Charset.forName("UTF-8").name();
    String UNDERLINE = "_";

    String JAVA_SUFFIX = StringPool.DOT_JAVA;
    String KT_SUFFIX = ".kt";
    String XML_SUFFIX = ".xml";
    String GRAPHQL_SUFFIX = ".graphqls";
    String GRAPHQL1_SUFFIX = ".graphqls";
    String EXCELL_SUFFIX = ".xls";

    String TEMPLATE_ENTITY_JAVA = "/templates/entity.java";
    String TEMPLATE_GRAPHQL = "/templates/entity.graphqls";
    String TEMPLATE_GRAPHQL1 = "/templates/entity.graphqls1";
    String TEMPLATE_GRAPHQL_Query = "/templates/query.java";
    String TEMPLATE_GRAPHQL_Mutation = "/templates/mutation.java";
    String TEMPLATE_ENTITY_KT = "/templates/entity.kt";
    String TEMPLATE_MAPPER = "/templates/mapper.java";
    String TEMPLATE_XML = "/templates/mapper.xml";
    String TEMPLATE_SERVICE = "/templates/service.java";
    String TEMPLATE_SERVICE_IMPL = "/templates/serviceImpl.java";
    String TEMPLATE_MODEL = "/templates/model.java";
    String TEMPLATE_CONTROLLER = "/templates/controller.java";
    
    String TEMPLATE_EXCEL_QUERY = "/templates/query.xls";
    String TEMPLATE_EXCEL_Mutation = "/templates/mutation.xls";

    String VM_LOAD_PATH_KEY = "file.resource.loader.class";
    String VM_LOAD_PATH_VALUE = "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader";

    String SUPER_MAPPER_CLASS = "com.baomidou.mybatisplus.core.mapper.BaseMapper";
    String SUPER_SERVICE_CLASS = "com.baomidou.mybatisplus.extension.service.IService";
    String SUPER_SERVICE_IMPL_CLASS = "com.baomidou.mybatisplus.extension.service.impl.ServiceImpl";
    String SUPER_MODEL_CLASS="com.coxautodev.graphql.tools.GraphQLResolver";

}
