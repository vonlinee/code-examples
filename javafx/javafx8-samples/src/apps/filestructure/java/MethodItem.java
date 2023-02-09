package apps.filestructure.java;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.HBox;
import org.girod.javafx.svgimage.SVGImage;
import org.girod.javafx.svgimage.SVGLoader;
import utils.ResourceLoader;

public class MethodItem extends TreeItem<String> {

    private String name;

    private final ObjectProperty<JavaVisibility> visibility = new SimpleObjectProperty<>(JavaVisibility.PUBLIC);

    public MethodItem(String name) {
        this.name = name;
        visibility.addListener(new ChangeListener<JavaVisibility>() {
            @Override
            public void changed(ObservableValue<? extends JavaVisibility> observable, JavaVisibility oldValue, JavaVisibility newValue) {
                changeVisibilityIcon(newValue);
            }
        });
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void attach(TreeItem<String> parent) {
        parent.getChildren().add(this);
        SVGImage svgImage = SVGLoader.load(ResourceLoader.load(getClass(), "icons/method.svg"));
        HBox hBox = new HBox(svgImage);
        hBox.setSpacing(2);
        hBox.setAlignment(Pos.CENTER);
    }

    private void changeVisibilityIcon(JavaVisibility visibility) {
        switch (visibility) {
            case PUBLIC:
                setGraphic(null);
                break;
            case PRIVATE:
                break;
            case PROTECTED:
                break;
            case PACKAGE_VISIABLE:
                break;
            default:
                break;
        }
    }

    public JavaVisibility getVisibility() {
        return visibility.get();
    }

    public ObjectProperty<JavaVisibility> visibilityProperty() {
        return visibility;
    }

    public void setVisibility(JavaVisibility visibility) {
        this.visibility.set(visibility);
    }
}
