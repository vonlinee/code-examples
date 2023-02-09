package samples.shape.svg;

import com.jfoenix.svg.SVGGlyph;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import org.girod.javafx.svgimage.SVGImage;
import org.girod.javafx.svgimage.SVGLoader;
import utils.FXColor;
import utils.ResourceLoader;

import java.net.URL;

public class TestSvg extends Application {
    protected double initialWidth = 500.0;
    protected double initialHeight = 500.0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createRoot(), initialWidth, initialHeight);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * 创建根节点
     *
     * @return
     * @throws Exception
     */
    public Parent createRoot() throws Exception {
        VBox root = new VBox();


        SVGPath svgPath = new SVGPath();
        svgPath.setContent("");
        // svgPath.setFill(Color.TRANSPARENT);
        Button btn = new Button("Button");

        SVGGlyph glyph = new SVGGlyph("M0 0h5.5v5.5h-5.5z M6.5 0h5.5v5.5h-5.5z M0 6.5h5.5v5.5h-5.5z M6.5 6.5h5.5v5.5h-5.5z");

        SVGPath path1 = new SVGPath();
        path1.setContent("M0 0h5.5v5.5h-5.5z");
        path1.setFill(FXColor.of("#59A869"));

        SVGPath path2 = new SVGPath();
        path2.setContent("M6.5 0h5.5v5.5h-5.5z");
        path2.setFill(FXColor.of("#EDA200"));

        SVGPath path3 = new SVGPath();
        path3.setContent("M0 6.5h5.5v5.5h-5.5z");
        path3.setFill(FXColor.of("#389FD6"));

        SVGPath path4 = new SVGPath();
        path4.setContent("M6.5 6.5h5.5v5.5h-5.5z");
        path4.setFill(FXColor.of("#DB5860"));

        VBox svg = new VBox(path1, path2, path3, path4);
        svg.setPrefSize(12.0, 12.0);


        SVGPath classIcon = new SVGPath();
        classIcon.setContent("M5,1.10001812 L5,2.12601749 C3.27477279,2.57006028 2,4.13616057 2,6 C2,8.209139 3.790861,10 6,10 C8.209139,10 10,8.209139 10,6 C10,4.51943529 9.19560274,3.22674762 8,2.53512878 L8,1.41604369 C9.76590478,2.18760031 11,3.94968096 11,6 C11,8.76142375 8.76142375,11 6,11 C3.23857625,11 1,8.76142375 1,6 C1,3.58104209 2.71775968,1.56328845 5,1.10001812 Z");
        classIcon.setFill(FXColor.of("#6E6E6E"));

        SVGPath path6 = new SVGPath();
        path6.setContent("M4 6a2 2 0 1 0 4 0a2 2 0 1 0 -4 0z");
        path6.setFill(FXColor.of("#59A869"));

        SVGPath path7 = new SVGPath();
        path7.setContent("M7 1L11 1 7 5z");
        path7.setFill(FXColor.of("#6E6E6E"));

        SVGPath path8 = new SVGPath();
        path8.setContent("M5,1.10001812 L5,2.12601749 C3.27477279,2.57006028 2,4.13616057 2,6 C2,8.209139 3.790861,10 6,10 C8.209139,10 10,8.209139 10,6 C10,4.51943529 9.19560274,3.22674762 8,2.53512878 L8,1.41604369 C9.76590478,2.18760031 11,3.94968096 11,6 C11,8.76142375 8.76142375,11 6,11 C3.23857625,11 1,8.76142375 1,6 C1,3.58104209 2.71775968,1.56328845 5,1.10001812 Z M4 6a2 2 0 1 0 4 0a2 2 0 1 0 -4 0z M7 1L11 1 7 5z");
        path8.setFill(FXColor.of("#6E6E6E"));

        AnchorPane icon = new AnchorPane();
        icon.getChildren().add(classIcon);
        icon.getChildren().add(path6);
        icon.getChildren().add(path7);

        btn.setGraphic(path8);

        root.getChildren().add(btn);


        URL svgFile = ResourceLoader.load(this.getClass(), "javaDocFolder.svg");

        SVGImage img = SVGLoader.load(svgFile);
        root.getChildren().add(img);


        return root;
    }
}
