package components.tabpage;

import com.sun.javafx.scene.control.behavior.BehaviorBase;
import com.sun.javafx.scene.control.behavior.KeyBinding;

import java.util.List;

public class TablePaginationBehavior extends BehaviorBase<TablePagination> {
    /**
     * Create a new BehaviorBase for the given control. The Control must not
     * be null.
     * @param control     The control. Must not be null.
     * @param keyBindings The key bindings that should be used with this behavior.
     */
    public TablePaginationBehavior(TablePagination control, List<KeyBinding> keyBindings) {
        super(control, keyBindings);
    }
}
