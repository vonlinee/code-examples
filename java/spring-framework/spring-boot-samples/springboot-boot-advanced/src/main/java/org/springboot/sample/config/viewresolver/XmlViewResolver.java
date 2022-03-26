package org.springboot.sample.config.viewresolver;

import java.util.Locale;

import org.springframework.oxm.Marshaller;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.xml.MarshallingView;

public class XmlViewResolver implements ViewResolver {

    private MarshallingView view;

    public XmlViewResolver() {
        // Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // marshaller.setClassesToBeBound(Pizza.class);
        Marshaller marshaller = new XStreamMarshaller();
        view = new MarshallingView();
        view.setMarshaller(marshaller);
    }

    public View resolveViewName(String viewName, Locale locale) throws Exception {
        return view;
    }
}