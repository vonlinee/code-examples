package io.spring.boot.common.web.viewresolver;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.xml.MarshallingView;

import java.util.Locale;

public class XmlViewResolver implements ViewResolver {

    private MarshallingView view;

    public XmlViewResolver() {
        // Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // marshaller.setClassesToBeBound(Pizza.class);
        // Marshaller marshaller = new XStreamMarshaller();
        // view = new MarshallingView();
        // view.setMarshaller(marshaller);
    }

    public View resolveViewName(String viewName, Locale locale) throws Exception {
        return view;
    }
}