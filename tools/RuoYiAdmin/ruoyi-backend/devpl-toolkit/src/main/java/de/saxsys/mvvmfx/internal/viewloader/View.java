package de.saxsys.mvvmfx.internal.viewloader;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.JavaView;
import javafx.scene.Parent;

/**
 * <p>
 * This Interface is used as base interface for specific view types for mvvmFX. The generic type defines the View Model
 * that is used.
 * </p>
 *
 * <p>
 * This interface is for internal use only. Don't implement it directly when creating a view. Instead use
 * {@link FxmlView} for views that are using FXML or {@link JavaView} that are implemented with pure Java.
 * </p>
 * @param <T> type
 * @author alexander.casall, manuel.mauky
 */
public interface View<T> {

}
