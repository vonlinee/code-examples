package io.maker.ext.fm;

import java.io.Serializable;

import freemarker.ext.util.WrapperTemplateModel;
import freemarker.template.AdapterTemplateModel;
import freemarker.template.TemplateCollectionModel;
import freemarker.template.TemplateHashModelEx2;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateModelWithAPISupport;

public class MapTemplateModel implements TemplateHashModelEx2, AdapterTemplateModel, WrapperTemplateModel,
		TemplateModelWithAPISupport, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int size() throws TemplateModelException {
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

	@Override
	public TemplateModel get(String key) throws TemplateModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty() throws TemplateModelException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public TemplateModel getAPI() throws TemplateModelException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getWrappedObject() {
		return null;
	}

	@Override
	public Object getAdaptedObject(Class<?> hint) {
		return null;
	}

	@Override
	public KeyValuePairIterator keyValuePairIterator() throws TemplateModelException {
		return null;
	}
}
