
如果报错：javax.servlet的类无法导入
Eclipse：
右键项目 -> Build Path -> Configure Build Path -> Library -> Add Library -> Server Runtime

或者Maven导入 javax.servlet-api 依赖，然后Update Project

控制台提示
一月 12, 2022 10:54:56 上午 org.apache.catalina.startup.ContextConfig getDefaultWebXmlFragment
信息: No global web.xml found

org.apache.catalina.startup.ContextConfig getDefaultWebXmlFragment

```java
/**
 * Scan the web.xml files that apply to the web application and merge them
 * using the rules defined in the spec. For the global web.xml files,
 * where there is duplicate configuration, the most specific level wins. ie
 * an application's web.xml takes precedence over the host level or global
 * web.xml file.
 */
protected void webConfig() {
    /*
     * Anything and everything can override the global and host defaults.
     * This is implemented in two parts
     * - Handle as a web fragment that gets added after everything else so
     *   everything else takes priority
     * - Mark Servlets as overridable so SCI configuration can replace
     *   configuration from the defaults
     */

    /*
     * The rules for annotation scanning are not as clear-cut as one might
     * think. Tomcat implements the following process:
     * - As per SRV.1.6.2, Tomcat will scan for annotations regardless of
     *   which Servlet spec version is declared in web.xml. The EG has
     *   confirmed this is the expected behaviour.
     * - As per http://java.net/jira/browse/SERVLET_SPEC-36, if the main
     *   web.xml is marked as metadata-complete, JARs are still processed
     *   for SCIs.
     * - If metadata-complete=true and an absolute ordering is specified,
     *   JARs excluded from the ordering are also excluded from the SCI
     *   processing.
     * - If an SCI has a @HandlesType annotation then all classes (except
     *   those in JARs excluded from an absolute ordering) need to be
     *   scanned to check if they match.
     */
    WebXmlParser webXmlParser = new WebXmlParser(context.getXmlNamespaceAware(),
            context.getXmlValidation(), context.getXmlBlockExternal());

    Set<WebXml> defaults = new HashSet<>();
    defaults.add(getDefaultWebXmlFragment(webXmlParser));

    WebXml webXml = createWebXml();

    // Parse context level web.xml
    InputSource contextWebXml = getContextWebXmlSource();
    if (!webXmlParser.parseWebXml(contextWebXml, webXml, false)) {
        ok = false;
    }

    ServletContext sContext = context.getServletContext();

    // Ordering is important here

    // Step 1. Identify all the JARs packaged with the application and those
    // provided by the container. If any of the application JARs have a
    // web-fragment.xml it will be parsed at this point. web-fragment.xml
    // files are ignored for container provided JARs.
    Map<String,WebXml> fragments = processJarsForWebFragments(webXml, webXmlParser);

    // Step 2. Order the fragments.
    Set<WebXml> orderedFragments = null;
    orderedFragments =
            WebXml.orderWebFragments(webXml, fragments, sContext);

    // Step 3. Look for ServletContainerInitializer implementations
    if (ok) {
        processServletContainerInitializers();
    }

    if  (!webXml.isMetadataComplete() || typeInitializerMap.size() > 0) {
        // Steps 4 & 5.
        processClasses(webXml, orderedFragments);
    }

    if (!webXml.isMetadataComplete()) {
        // Step 6. Merge web-fragment.xml files into the main web.xml
        // file.
        if (ok) {
            ok = webXml.merge(orderedFragments);
        }

        // Step 7. Apply global defaults
        // Have to merge defaults before JSP conversion since defaults
        // provide JSP servlet definition.
        webXml.merge(defaults);

        // Step 8. Convert explicitly mentioned jsps to servlets
        if (ok) {
            convertJsps(webXml);
        }

        // Step 9. Apply merged web.xml to Context
        if (ok) {
            configureContext(webXml);
        }
    } else {
        webXml.merge(defaults);
        convertJsps(webXml);
        configureContext(webXml);
    }

    if (context.getLogEffectiveWebXml()) {
        log.info("web.xml:\n" + webXml.toXml());
    }

    // Always need to look for static resources
    // Step 10. Look for static resources packaged in JARs
    if (ok) {
        // Spec does not define an order.
        // Use ordered JARs followed by remaining JARs
        Set<WebXml> resourceJars = new LinkedHashSet<>(orderedFragments);
        for (WebXml fragment : fragments.values()) {
            if (!resourceJars.contains(fragment)) {
                resourceJars.add(fragment);
            }
        }
        processResourceJARs(resourceJars);
        // See also StandardContext.resourcesStart() for
        // WEB-INF/classes/META-INF/resources configuration
    }

    // Step 11. Apply the ServletContainerInitializer config to the
    // context
    if (ok) {
        for (Map.Entry<ServletContainerInitializer,
                Set<Class<?>>> entry :
                    initializerClassMap.entrySet()) {
            if (entry.getValue().isEmpty()) {
                context.addServletContainerInitializer(
                        entry.getKey(), null);
            } else {
                context.addServletContainerInitializer(
                        entry.getKey(), entry.getValue());
            }
        }
    }
}
```





```java
private WebXml getDefaultWebXmlFragment(WebXmlParser webXmlParser) {
    // Host should never be null
    Host host = (Host) context.getParent();

    DefaultWebXmlCacheEntry entry = hostWebXmlCache.get(host);

    InputSource globalWebXml = getGlobalWebXmlSource();
    InputSource hostWebXml = getHostWebXmlSource();

    long globalTimeStamp = 0;
    long hostTimeStamp = 0;

    if (globalWebXml != null) {
        URLConnection uc = null;
        try {
            URL url = new URL(globalWebXml.getSystemId());
            uc = url.openConnection();
            globalTimeStamp = uc.getLastModified();
        } catch (IOException e) {
            globalTimeStamp = -1;
        } finally {
            if (uc != null) {
                try {
                    uc.getInputStream().close();
                } catch (IOException e) {
                    ExceptionUtils.handleThrowable(e);
                    globalTimeStamp = -1;
                }
            }
        }
    }

    if (hostWebXml != null) {
        URLConnection uc = null;
        try {
            URL url = new URL(hostWebXml.getSystemId());
            uc = url.openConnection();
            hostTimeStamp = uc.getLastModified();
        } catch (IOException e) {
            hostTimeStamp = -1;
        } finally {
            if (uc != null) {
                try {
                    uc.getInputStream().close();
                } catch (IOException e) {
                    ExceptionUtils.handleThrowable(e);
                    hostTimeStamp = -1;
                }
            }
        }
    }

    if (entry != null && entry.getGlobalTimeStamp() == globalTimeStamp &&
            entry.getHostTimeStamp() == hostTimeStamp) {
        InputSourceUtil.close(globalWebXml);
        InputSourceUtil.close(hostWebXml);
        return entry.getWebXml();
    }

    // Parsing global web.xml is relatively expensive. Use a sync block to
    // make sure it only happens once. Use the pipeline since a lock will
    // already be held on the host by another thread
    synchronized (host.getPipeline()) {
        entry = hostWebXmlCache.get(host);
        if (entry != null && entry.getGlobalTimeStamp() == globalTimeStamp &&
                entry.getHostTimeStamp() == hostTimeStamp) {
            return entry.getWebXml();
        }

        WebXml webXmlDefaultFragment = createWebXml();
        webXmlDefaultFragment.setOverridable(true);
        // Set to distributable else every app will be prevented from being
        // distributable when the default fragment is merged with the main
        // web.xml
        webXmlDefaultFragment.setDistributable(true);
        // When merging, the default welcome files are only used if the app has
        // not defined any welcomes files.
        webXmlDefaultFragment.setAlwaysAddWelcomeFiles(false);

        // Parse global web.xml if present
        if (globalWebXml == null) {
            // This is unusual enough to log
            log.info(sm.getString("contextConfig.defaultMissing"));
        } else {
            if (!webXmlParser.parseWebXml(
                    globalWebXml, webXmlDefaultFragment, false)) {
                ok = false;
            }
        }

        // Parse host level web.xml if present
        // Additive apart from welcome pages
        webXmlDefaultFragment.setReplaceWelcomeFiles(true);

        if (!webXmlParser.parseWebXml(
                hostWebXml, webXmlDefaultFragment, false)) {
            ok = false;
        }

        // Don't update the cache if an error occurs
        if (globalTimeStamp != -1 && hostTimeStamp != -1) {
            entry = new DefaultWebXmlCacheEntry(webXmlDefaultFragment,
                    globalTimeStamp, hostTimeStamp);
            hostWebXmlCache.put(host, entry);
            // Add a Lifecycle listener to the Host that will remove it from
            // the hostWebXmlCache once the Host is destroyed
            host.addLifecycleListener(new HostWebXmlCacheCleaner());
        }
        return webXmlDefaultFragment;
    }
}
```



Debug始终进不去

























