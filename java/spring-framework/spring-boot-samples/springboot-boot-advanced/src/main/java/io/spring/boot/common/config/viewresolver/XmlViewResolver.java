package io.spring.boot.common.config.viewresolver;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.xml.MarshallingView;

/**
 * SpringMVC解析XML视图
 * https://blog.csdn.net/Good_omen/article/details/121142624
 */
public class XmlViewResolver implements ViewResolver {

    private MarshallingView view;

    public XmlViewResolver() {
        // Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // marshaller.setClassesToBeBound(Pizza.class);
//        Marshaller marshaller = new XStreamMarshaller();
//        view = new MarshallingView();
//        view.setMarshaller(marshaller);
    }

    public View resolveViewName(String viewName, Locale locale) throws Exception {
        return view;
    }
}