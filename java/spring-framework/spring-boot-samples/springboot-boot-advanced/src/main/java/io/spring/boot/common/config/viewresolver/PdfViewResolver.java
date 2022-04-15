package io.spring.boot.common.config.viewresolver;

import java.util.Locale;

import io.spring.boot.common.config.viewresolver.view.PdfView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

public class PdfViewResolver implements ViewResolver {

    private PdfView view;

    public PdfViewResolver() {
        super();
        view = new PdfView();
    }

    public View resolveViewName(String viewName, Locale locale) throws Exception {
        return view;
    }


}