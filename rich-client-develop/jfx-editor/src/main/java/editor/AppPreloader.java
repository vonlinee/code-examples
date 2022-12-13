package editor;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AppPreloader extends Preloader {

    private ProgressBar bar;
    Stage stage;
    final int width = 800;
    final int height = 300;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        bar = new ProgressBar(0);
        BorderPane p = new BorderPane();
        Text text = new Text("JEditor正在疯狂加载中!");
        p.setStyle("-fx-background-color:transparent;");
        // //这里可以看到和Application一样，也有舞台，我们可以定制自己的界面
        ImageView iv = new ImageView();
        iv.setImage(new Image(getClass().getResourceAsStream("LinuxFamily.png")));
        p.setCenter(iv);
        bar = new ProgressBar(0);
        p.setBottom(bar);

        final Scene scene = new Scene(p, width, height);

        p.setCenter(bar);
        p.setStyle("-fx-background-color:transparent;");
        p.setBottom(text);

        text.setFont(new Font(20));
        text.setFill(Color.CRIMSON);

        scene.setFill(null);
        stage.initStyle(StageStyle.TRANSPARENT);
//        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
//        stage.setX(primaryScreenBounds.getWidth() /2- width/2);
//        stage.setY(primaryScreenBounds.getHeight()/2 - height);
        stage.centerOnScreen();
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification scn) {
        if (scn.getType() == StateChangeNotification.Type.BEFORE_START) {
            try {
                Thread.sleep(1024);
            } catch (InterruptedException ex) {
                Logger.getLogger(AppPreloader.class.getName()).log(Level.SEVERE, null, ex);
            }
            stage.hide();
        }
    }

    private Scene createPreloaderScene1() {
        bar = new ProgressBar();
        BorderPane p = new BorderPane();
        p.setCenter(bar);
        Text text = new Text("显示提示信息成功!");
        text.setFont(new Font(12));
        text.setFill(Color.GREEN);
        VBox box = new VBox();
        box.getChildren().add(text);
        box.setStyle("-fx-background:transparent;");
        p.setStyle("-fx-background:transparent;");
        p.setBottom(box);
        final Scene scene = new Scene(p, width, height);
        scene.setFill(null);
        return scene;
    }

    @Override
    public void handleApplicationNotification(PreloaderNotification info) {
//        if (info instanceof ProgressNotification) {
//            //提取应用程序发送过来的进度值
//            double v = ((ProgressNotification) info).getProgress();
//            System.out.println("handleApplicationNotification="+v);
//            bar.setProgress(v);
//        } else if (info instanceof StateChangeNotification) {
//            //隐藏/或者关闭preloader
////            stage.hide();
////            stage.close();
//        }
    }

    @Override
    public void handleProgressNotification(ProgressNotification pn) {
        System.out.println("handleProgressNotification=" + pn.getProgress());
        if (pn.getProgress() != 1.0) {
            bar.setProgress(pn.getProgress() / 2);
        }
        bar.setProgress(pn.getProgress());
    }

    // @Override
    public void handleProgressNotification1(ProgressNotification info) {
        System.out.println("handleProgressNotification=" + info.getProgress());
        if (info.getProgress() != 1.0) {
            bar.setProgress(info.getProgress() / 2);
        }
    }

    /**
     * 重载这个方法可以处理应用通知
     * @param info
     */
    // @Override
    public void handleApplicationNotification1(PreloaderNotification info) {
        if (info instanceof ProgressNotification) {
            // 提取应用程序发送过来的进度值
            double v = ((ProgressNotification) info).getProgress();
            System.out.println("handleApplicationNotification=" + v);
            bar.setProgress(v);
        } else if (info instanceof StateChangeNotification) {
            // 隐藏/或者关闭preloader
            stage.hide();
            stage.close();
        }
    }
}
