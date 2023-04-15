package io.devpl.codegen.api;

import java.util.List;
import java.util.Properties;

/**
 * This interface defines methods that will be called at different times during
 * the code generation process. These methods can be used to extend or modify
 * the generated code. Clients may implement this interface in its entirety, or
 * extend the PluginAdapter (highly recommended).
 *
 * <p>Plugins have a lifecycle. In general, the lifecycle is this:
 *
 * <ol>
 * <li>The setXXX methods are called one time</li>
 * <li>The validate method is called one time</li>
 * <li>The initialized method is called for each introspected table</li>
 * <li>The clientXXX methods are called for each introspected table</li>
 * <li>The providerXXX methods are called for each introspected table</li>
 * <li>The modelXXX methods are called for each introspected table</li>
 * <li>The sqlMapXXX methods are called for each introspected table</li>
 * <li>The contextGenerateAdditionalJavaFiles(IntrospectedTable) method is
 * called for each introspected table</li>
 * <li>The contextGenerateAdditionalXmlFiles(IntrospectedTable) method is called
 * for each introspected table</li>
 * <li>The contextGenerateAdditionalJavaFiles() method is called one time</li>
 * <li>The contextGenerateAdditionalXmlFiles() method is called one time</li>
 * </ol>
 *
 * <p>Plugins are related to contexts - so each context will have its own set of
 * plugins. If the same plugin is specified in multiple contexts, then each
 * context will hold a unique instance of the plugin.
 *
 * <p>Plugins are called, and initialized, in the same order they are specified in
 * the configuration.
 *
 * <p>The clientXXX, modelXXX, and sqlMapXXX methods are called by the code
 * generators. If you replace the default code generators with other
 * implementations, these methods may not be called.
 * @see PluginAdapter
 */
public interface Plugin {

    /**
     * Set the context under which this plugin is running.
     * @param context the new context
     */
    void setContext(Context context);

    /**
     * Set properties from the plugin configuration.
     * @param properties the new properties
     */
    void setProperties(Properties properties);

    /**
     * This method is called just before the getGeneratedXXXFiles methods are called on the introspected table. Plugins
     * can implement this method to override any of the default attributes, or change the results of database
     * introspection, before any code generation activities occur. Attributes are listed as static Strings with the
     * prefix ATTR_ in IntrospectedTable.
     *
     * <p>A good example of overriding an attribute would be the case where a user wanted to change the name of one
     * of the generated classes, change the target package, or change the name of the generated SQL map file.
     *
     * <p><b>Warning:</b> Anything that is listed as an attribute should not be changed by one of the other plugin
     * methods. For example, if you want to change the name of a generated example class, you should not simply change
     * the Type in the <code>modelExampleClassGenerated()</code> method. If you do, the change will not be reflected
     * in other generated artifacts.
     * @param introspectedTable the introspected table
     */
    default void initialized(IntrospectedTable introspectedTable) {
    }

    /**
     * This method is called after all the setXXX methods are called, but before
     * any other method is called. This allows the plugin to determine whether
     * it can run or not. For example, if the plugin requires certain properties
     * to be set, and the properties are not set, then the plugin is invalid and
     * will not run.
     * @param warnings add strings to this list to specify warnings. For example, if
     *                 the plugin is invalid, you should specify why. Warnings are
     *                 reported to users after the completion of the run.
     * @return true if the plugin is in a valid state. Invalid plugins will not
     * be called
     */
    boolean validate(List<String> warnings);
}
