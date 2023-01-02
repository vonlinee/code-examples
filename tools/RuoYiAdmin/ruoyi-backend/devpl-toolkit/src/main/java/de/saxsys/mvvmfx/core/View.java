package de.saxsys.mvvmfx.core;

import javafx.scene.Parent;

/**
 * 内部使用，不直接实现该接口，View实际上还是担任Controller的角色
 * View持有所有UI控件的引用
 * This interface is for internal use only. Don't implement it directly when creating a view.
 * Instead, use {@link FxmlView} for views that are using FXML or {@link NodeView} that are
 * implemented with pure Java.
 * </p>
 */
public interface View {

    /**
     * 获取根节点
     * @return not null
     */
    Parent getRoot();
}
