package io.devpl.toolkit.fxui.app;

import io.devpl.fxtras.JavaFXApplication;
import io.devpl.fxtras.mvc.ViewLoader;
import io.devpl.toolkit.fxui.controller.MainFrameController;
import io.devpl.toolkit.fxui.dao.ConnectionConfigurationDao;
import io.devpl.toolkit.fxui.model.ConnectionRegistry;
import io.devpl.toolkit.fxui.model.props.ConnectionInfo;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.mybatis.generator.logging.Log;
import org.mybatis.generator.logging.LogFactory;

import java.util.List;

/**
 * 这是本软件的主入口,要运行本软件请直接运行本类就可以了,不用传入任何参数
 * 本软件要求jkd版本大于1.8.0.40
 */
public class MainUI extends JavaFXApplication {

    private static final String MAIN_WINDOW_TITLE = "代码生成器";

    private final Log log = LogFactory.getLog(MainUI.class);

    @Override
    protected void onInit() throws Exception {
        ConnectionConfigurationDao dao = new ConnectionConfigurationDao();
        List<ConnectionInfo> connConfigList = dao.selectList();
        for (ConnectionInfo item : connConfigList) {
            ConnectionRegistry.registerConnection(item);
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = ViewLoader.load(MainFrameController.class).getRoot();
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle(MAIN_WINDOW_TITLE);
        primaryStage.getIcons().add(new Image("static/icons/mybatis-logo.png"));
        primaryStage.show();
    }
}
