package de.saxsys.mvvmfx;

import de.saxsys.mvvmfx.internal.viewloader.JavaViewLoader;
import de.saxsys.mvvmfx.internal.viewloader.ResourceBundleManager;

import java.util.*;

/**
 * This class is the builder step to load a java based view. It is accessed
 * from the {@link ViewLoader} with the method
 * {@link ViewLoader#javaView(Class)}.
 * @param <ViewType>      the generic type of the View that should be loaded. This type
 *                        has to implement {@link JavaView}.
 * @param <ViewModelType> the generic type of the ViewModel. This type has to implement
 *                        {@link ViewModel}.
 */
public class JavaViewStep<ViewType extends JavaView<? extends ViewModelType>, ViewModelType extends ViewModel> {

    private final Class<? extends ViewType> viewType;
    private List<ResourceBundle> resourceBundles;

    private ViewModelType viewModel;
    private ViewType codeBehind;
    private Context context;
    private Collection<Scope> providedScopes;

    JavaViewStep(Class<? extends ViewType> viewType) {
        this.viewType = viewType;
    }

    public JavaViewStep<ViewType, ViewModelType> context(Context context) {
        this.context = context;
        return this;
    }

    public JavaViewStep<ViewType, ViewModelType> providedScopes(Scope... providedScopes) {
        this.providedScopes = Arrays.asList(providedScopes);
        return this;
    }

    public JavaViewStep<ViewType, ViewModelType> providedScopes(Collection<Scope> providedScopes) {
        this.providedScopes = providedScopes;
        return this;
    }

    /**
     * Provide a {@link ResourceBundle} that is used while loading this
     * view. Note: It is possible to provide a global application-wide
     * resourceBundle via
     * {@link MvvmFX#setGlobalResourceBundle(ResourceBundle)} method.
     * <p/>
     * If there is a global resourceBundle set it will be merged with the
     * resourceBundle provided by this builder method. The resourceBundle
     * provided by this method will have a higher priority then the global
     * one which means that if there are duplicate keys, the values of the
     * global resourceBundle will be overwritten and the values of this
     * resourceBundle will be used.
     * <p/>
     * It is possible to add multiple resourceBundles by invoking this builder method
     * multiple times. In this case the last provided resourceBundle will have the
     * highest priority when it comes to overwriting values with the same keys.
     * @param resourceBundle the resource bundle that is used while loading the view.
     * @return this instance of the builder step.
     */
    public JavaViewStep<ViewType, ViewModelType> resourceBundle(ResourceBundle resourceBundle) {
        if (resourceBundles == null) {
            resourceBundles = new ArrayList<>();
        }
        resourceBundles.add(resourceBundle);
        return this;
    }

    /**
     * This param is used to define an existing viewModel instance to be
     * used when loading the view.<br>
     * <p>
     * A typical use case is when you like to have two or more views that
     * are sharing the same viewModel.
     * @param viewModel the viewModel instance that is used to load the java view.
     * @return this instance of the builder step.
     */
    public JavaViewStep<ViewType, ViewModelType> viewModel(ViewModelType viewModel) {
        this.viewModel = viewModel;
        return this;
    }

    /**
     * This param is used to define an existing instance of the codeBehind
     * class that is used instead of creating a new one while loading. <br>
     * <p>
     * This can be useful when creating custom controls.
     * @param codeBehind the codeBehind instance that is used to load this java
     *                   view.
     * @return this instance of the builder step.
     */
    public JavaViewStep<ViewType, ViewModelType> codeBehind(ViewType codeBehind) {
        this.codeBehind = codeBehind;
        return this;
    }

    /**
     * The final step of the Fluent API. This method loads the view based on
     * the given params.
     * @return a view tuple containing the loaded view.
     */
    public ViewTuple<ViewType, ViewModelType> load() {
        JavaViewLoader javaViewLoader = new JavaViewLoader();

        final ResourceBundle bundle = ResourceBundleManager.getInstance().mergeListWithGlobal(resourceBundles);

        return javaViewLoader.loadJavaViewTuple(viewType, bundle, viewModel, codeBehind, context, providedScopes);
    }
}
