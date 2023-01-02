package de.saxsys.mvvmfx.fxml;

/**
 * 基于FXMLLoader的控制器工厂
 */
public interface FxmlControllerFactory extends ControllerFactory {

    /**
     * 当前正在加载的FXML文件ID
     * @param fxmlId FXML ID，一般是FXML文件的相对路径
     */
    void setCurrentLoadingFxmlId(String fxmlId);
}
