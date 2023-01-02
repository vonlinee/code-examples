package de.saxsys.mvvmfx.core;

import javafx.scene.Parent;

/**
 * <p>
 * A view that is implemented with with pure Java.
 * </p>
 *
 * <p>
 * The implementing class will typically extend from one of JavaFX`s controls or containers etc. For Example:
 * </p>
 * <br>
 *
 * <pre>
 * public class MyCoolView extends VBox implements JavaView{@code <MyCoolViewModel>} {
 *     ...
 *
 *     public MyCoolView(){
 *         getChildren().add(new Label("Hello World"));
 *     }
 * }
 * </pre>
 * <p>
 * It is recommended to implement views with FXML and therefore use {@link FxmlView} instead of this interface. But
 * there may be use-cases where FXML isn't suitable or not supported (i.e. the "Compact-1" profile introduced in Java 8
 * doesn't support FXML) or the developer doesn't like FXML. For this cases this interface provides a way to implement
 * the view without FXML in pure Java.
 * </p>
 */
public interface NodeView<V extends ViewModel, N extends Parent> extends View {

    // 所有控件都继承自Parent类

    @Override
    N getRoot();
}
