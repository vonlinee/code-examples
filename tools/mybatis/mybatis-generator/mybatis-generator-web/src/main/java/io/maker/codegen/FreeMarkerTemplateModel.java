package io.maker.codegen;

import java.util.Map;

import freemarker.ext.beans.BeanModel;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.TemplateCollectionModel;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateHashModelEx;
import freemarker.template.TemplateHashModelEx2;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

public class FreeMarkerTemplateModel implements TemplateHashModel, TemplateHashModelEx, TemplateHashModelEx2 {

	Map<String, Object> dataMap;

	@Override
	public TemplateModel get(String key) throws TemplateModelException {
		Object object = dataMap.get(key);
		BeansWrapper bw = new BeansWrapper(Configuration.VERSION_2_3_31);
		BeanModel beanModel = new BeanModel(object, null);
		return beanModel;
	}

	@Override
	public boolean isEmpty() throws TemplateModelException {
		return false;
	}

	@Override
	public KeyValuePairIterator keyValuePairIterator() throws TemplateModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() throws TemplateModelException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TemplateCollectionModel keys() throws TemplateModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TemplateCollectionModel values() throws TemplateModelException {
		// TODO Auto-generated method stub
		return null;
	}















}
