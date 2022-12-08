package io.devpl.codegen.fxui.framework;

import javafx.application.Application;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {

    private final Map<String, String> fxmlMappings = new ConcurrentHashMap<>();

    static class ApplicationContextHolder {
        static ApplicationContext context = new ApplicationContext();
    }

    public void addFxmlMappings(Map<String, String> fxmlMappings) {
        this.fxmlMappings.putAll(fxmlMappings);
    }

    public URL getFxmlLocation(String pathname) {
        try {
            String absolutePath = fxmlMappings.get(pathname);
            return new File(absolutePath).toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
