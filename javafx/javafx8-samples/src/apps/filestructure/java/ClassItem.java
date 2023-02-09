package apps.filestructure.java;

import javafx.scene.control.TreeItem;

public class ClassItem extends TreeItem<String> {

    public ClassItem(String name) {
        setValue(name);
    }
}
