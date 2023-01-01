package de.saxsys.mvvmfx;

import de.saxsys.mvvmfx.internal.viewloader.View;

/**
 * <p>
 * A view that is implemented with FXML.
 * </p>
 * <p>
 * There has to be an fxml file in the same package with the same name (case-sensitive) as the implementing view class.
 * So for example when your view class is named <code>MyCoolView</code> (filename: MyCoolView.java) then the fxml file
 * has to be named <code>MyCoolView.fxml</code>.
 * </p>
 *
 * <p>
 * The instance of this class is also known as the "code-behind" of the View in terms of the Model-View-ViewModel
 * pattern.
 * </p>
 * @param <ViewModelType> the type of the viewModel.
 * @author manuel.mauky
 */
public interface FxmlView<ViewModelType extends ViewModel> extends View<ViewModelType> {
}
