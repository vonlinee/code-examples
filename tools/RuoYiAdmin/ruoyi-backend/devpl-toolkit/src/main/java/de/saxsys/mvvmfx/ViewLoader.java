package de.saxsys.mvvmfx;

/**
 * <p>
 * The typical usage will look like this:
 * <br>
 * <pre>
 * public class MyCoolViewModel implements ViewModel {...}
 * public class MyCoolView implements FxmlView{@code <MyCoolViewModel>} {...}
 *
 *
 * public class MyApp extends Application {
 *
 *    {@literal @}Override
 *     public void start(Stage stage) {
 * 		ViewTuple{@code <MyCoolView, MyCoolViewModel>} viewTuple
 * 				= FluentViewLoader.fxmlView(MyCoolView.class).load();
 *
 * 		Parent root = viewTuple.getView();
 * 		stage.setScene(new Scene(root));
 * 		stage.show();
 *     }
 * }
 *
 * </pre>
 * <p>
 * <p>
 * This class is implemented as a Step-Builder. You can choose between
 * {@link FxmlView} and {@link JavaView} with the first method call. After that
 * you will only get builder-methods that are suitable for the view type you
 * have chosen.
 * @author manuel.mauky
 */
public abstract class ViewLoader {

    /**
     * This method is the entry point of the Fluent API to load a java based
     * view.
     * @param viewType        the type of the view that should be loaded.
     * @param <ViewType>      the type of the View that should be loaded. This type has to
     *                        implement {@link JavaView}.
     * @param <ViewModelType> the type of the ViewModel. This type has to implement
     *                        {@link ViewModel}.
     * @return a builder step that can be further configured and then load the
     * actual view.
     */
    public static <ViewType extends JavaView<? extends ViewModelType>, ViewModelType extends ViewModel> JavaViewStep<ViewType, ViewModelType> javaView(Class<? extends ViewType> viewType) {
        return new JavaViewStep<>(viewType);
    }

    /**
     * This method is the entry point of the Fluent API to load a fxml based
     * View.
     * @param viewType        the type of the view that should be loaded.
     * @param <ViewType>      the generic type of the View that should be loaded. This type
     *                        has to implement {@link FxmlView}.
     * @param <ViewModelType> the generic type of the ViewModel. This type has to implement
     *                        {@link ViewModel}.
     * @return a builder step that can be further configured and then load the
     * actual view.
     */
    public static <ViewType extends FxmlView<? extends ViewModelType>, ViewModelType extends ViewModel>
    FxmlViewStep<ViewType, ViewModelType> fxmlView(Class<? extends ViewType> viewType) {
        return new FxmlViewStep<>(viewType);
    }
}
