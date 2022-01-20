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

/**
 * <p>
 * 模板路径配置项
 * </p>
 *
 * @author tzg hubin
 * @since 2017-06-17
 */

public class TemplateConfig {

    private String entity = ConstVal.TEMPLATE_ENTITY_JAVA;

    private String entityKt = ConstVal.TEMPLATE_ENTITY_KT;

    private String service = ConstVal.TEMPLATE_SERVICE;

    private String serviceImpl = ConstVal.TEMPLATE_SERVICE_IMPL;
    
    private String modelImpl = ConstVal.TEMPLATE_MODEL;

    private String mapper = ConstVal.TEMPLATE_MAPPER;

    private String xml = ConstVal.TEMPLATE_XML;
    
    private String graphql = ConstVal.TEMPLATE_GRAPHQL;
    
    private String graphql1 = ConstVal.TEMPLATE_GRAPHQL1;
    
    private String graphqlQuery = ConstVal.TEMPLATE_GRAPHQL_Query;
    
    private String graphqlMutation = ConstVal.TEMPLATE_GRAPHQL_Mutation;

    private String controller = ConstVal.TEMPLATE_CONTROLLER;
    
    private String excelQuery = ConstVal.TEMPLATE_EXCEL_QUERY;
    
    private String excelMutation = ConstVal.TEMPLATE_EXCEL_Mutation;
    
    

    public String getModelImpl() {
		return modelImpl;
	}

	public TemplateConfig setModelImpl(String modelImpl) {
		this.modelImpl = modelImpl;
		return this;
	}

	public String getGraphql() {
		return graphql;
	}

	public TemplateConfig setGraphql(String graphql) {
		this.graphql = graphql;
		return this;
	}
	
	public String getGraphql1() {
		return graphql1;
	}

	public TemplateConfig setGraphql1(String graphql1) {
		this.graphql1 = graphql1;
		return this;
	}
	
	public String getGraphqlQuery() {
		return graphqlQuery;
	}

	public TemplateConfig setGraphqlQuery(String graphqlQuery) {
		this.graphqlQuery = graphqlQuery;
		return this;
	}
	
	public String getGraphqlMutation() {
		return graphqlMutation;
	}

	public TemplateConfig setGraphqlMutation(String graphqlMutation) {
		this.graphqlMutation = graphqlMutation;
		return this;
	}
	
	

	public String getEntity(boolean kotlin) {
        return kotlin ? entityKt : entity;
    }

    public TemplateConfig setEntityKt(String entityKt) {
        this.entityKt = entityKt;
        return this;
    }

    public TemplateConfig setEntity(String entity) {
        this.entity = entity;
        return this;
    }

    public String getService() {
        return service;
    }

    public TemplateConfig setService(String service) {
        this.service = service;
        return this;
    }

    public String getServiceImpl() {
        return serviceImpl;
    }

    public TemplateConfig setServiceImpl(String serviceImpl) {
        this.serviceImpl = serviceImpl;
        return this;
    }

    public String getMapper() {
        return mapper;
    }

    public TemplateConfig setMapper(String mapper) {
        this.mapper = mapper;
        return this;
    }

    public String getXml() {
        return xml;
    }

    public TemplateConfig setXml(String xml) {
        this.xml = xml;
        return this;
    }

    public String getController() {
        return controller;
    }

    public TemplateConfig setController(String controller) {
        this.controller = controller;
        return this;
    }
    
    public String getExcelQuery() {
		return excelQuery;
	}

	public TemplateConfig setExcelQuery(String excelQuery) {
		this.excelQuery = excelQuery;
		return this;
	}
	
	public String getExcelMutation() {
		return excelMutation;
	}

	public TemplateConfig setExcelMutation(String excelMutation) {
		this.excelMutation = excelMutation;
		return this;
	}

}
