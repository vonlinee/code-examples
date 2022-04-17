package io.spring.boot.common.web.viewresolver;

import java.util.Locale;

import io.spring.boot.common.web.viewresolver.view.XlsView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

public class XlsViewResolver implements ViewResolver{
	
	private XlsView view;
	
	public XlsViewResolver() {
		super();
		this.view = new XlsView();
	}

	public View resolveViewName(String viewName, Locale locale) throws Exception {
		return view;
	}


}

 


 
