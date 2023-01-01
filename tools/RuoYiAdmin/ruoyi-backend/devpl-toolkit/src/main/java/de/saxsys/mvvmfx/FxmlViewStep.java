package de.saxsys.mvvmfx;

import de.saxsys.mvvmfx.internal.viewloader.FxmlViewLoader;
import de.saxsys.mvvmfx.internal.viewloader.ResourceBundleManager;
import javafx.util.BuilderFactory;

import java.util.*;

/**
     * This class is the builder step to load a fxml based view. It is accessed
     * from the {@link ViewLoader} with the method
     * {@link ViewLoader#fxmlView(Class)}.
     * @param <ViewType>      the generic type of the View that should be loaded. This type
     *                        has to implement {@link FxmlView}.
     * @param <ViewModelType> the generic type of the ViewModel. This type has to implement
     *                        {@link ViewModel}.
     */
    public  class FxmlViewStep<ViewType extends FxmlView<? extends ViewModelType>, ViewModelType extends ViewModel> {

        private final Class<? extends ViewType> viewType;
        private List<ResourceBundle> resourceBundles;
        private Object root;
        private ViewType codeBehind;
        private ViewModelType viewModel;
        private Context context;
        private Collection<Scope> providedScopes;

        private List<BuilderFactory> builderFactories;

        FxmlViewStep(Class<? extends ViewType> viewType) {
            this.viewType = viewType;
        }

        public FxmlViewStep<ViewType, ViewModelType> context(Context context) {
            this.context = context;
            return this;
        }

        public FxmlViewStep<ViewType, ViewModelType> providedScopes(Scope... providedScopes) {

            // TODO: add scopes instead of reinitialization
            this.providedScopes = Arrays.asList(providedScopes);
            return this;
        }

        public FxmlViewStep<ViewType, ViewModelType> providedScopes(Collection<Scope> providedScopes) {
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
        public FxmlViewStep<ViewType, ViewModelType> resourceBundle(ResourceBundle resourceBundle) {
            if (resourceBundles == null) {
                resourceBundles = new ArrayList<>();
            }
            resourceBundles.add(resourceBundle);
            return this;
        }

        /**
         * This param is used to define a JavaFX node that is used as the root
         * element when loading the fxml file. <br>
         * <p>
         * This can be useful when creating custom controls with the fx:root
         * element.
         * @param root the root element that is used to load the fxml file.
         * @return this instance of the builder step.
         */
        public FxmlViewStep<ViewType, ViewModelType> root(Object root) {
            this.root = root;
            return this;
        }

        /**
         * This param is used to define an existing instance of the codeBehind
         * class that is used instead of creating a new one while loading. <br>
         * <p>
         * This can be useful when creating custom controls with the fx:root
         * element.
         * @param codeBehind the codeBehind instance that is used to load the fxml
         *                   file.
         * @return this instance of the builder step.
         */
        public FxmlViewStep<ViewType, ViewModelType> codeBehind(ViewType codeBehind) {
            this.codeBehind = codeBehind;
            return this;
        }

        /**
         * This param is used to define an existing viewModel instance to be
         * used when loading the view.<br>
         * <p>
         * A typical use case is when you like to have two or more views that
         * are sharing the same viewModel.
         * @param viewModel the viewModel instance that is used to load the fxml file.
         * @return this instance of the builder step.
         */
        public FxmlViewStep<ViewType, ViewModelType> viewModel(ViewModelType viewModel) {
            this.viewModel = viewModel;
            return this;
        }

        /**
         * This param is used to add a {@link BuilderFactory} that is used when loading the view.<br/>.
         * MvvmFX supports multiple builder factories. There are two ways of defining builder factories:
         * <ol>
         *     <li>a local builder factory by using this method.
         *     In this case the builder factory is only used for this loading procedure.</li>
         *     <li>a global builder factory by using {@link MvvmFX#addGlobalBuilderFactory(BuilderFactory)}.
         *     This defines a global builder factory that is used for all loading procedures.</li>
         * </ol>
         * <br/>
         * For most use cases it's better to define a global builder factory.
         * Only if you like to limit the usage of the
         * builder factory to only this loading procedure then use this fluent API method instead.
         * @param builderFactory a builder factory that is used only for this loading procedure.
         * @return this instance of the builder step.
         */
        public FxmlViewStep<ViewType, ViewModelType> builderFactory(BuilderFactory builderFactory) {
            if (this.builderFactories == null) {
                this.builderFactories = new ArrayList<>();
            }

            this.builderFactories.add(builderFactory);

            return this;
        }

        /**
         * The final step of the Fluent API. This method loads the view based on
         * the given params.
         * @return a view tuple containing the loaded view.
         */
        public ViewTuple<ViewType, ViewModelType> load() {
            FxmlViewLoader fxmlViewLoader = new FxmlViewLoader();
            final ResourceBundle bundle = ResourceBundleManager.getInstance().mergeListWithGlobal(resourceBundles);
            return fxmlViewLoader.loadFxmlViewTuple(viewType, bundle, codeBehind, root, viewModel, context, providedScopes, builderFactories);
        }
    }
