package org.example;

import com.panemu.tiwulfx.control.sidemenu.SideMenu;
import com.panemu.tiwulfx.control.sidemenu.SideMenuCategory;
import com.panemu.tiwulfx.control.sidemenu.SideMenuItem;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SideMenuApplication extends Application {

    @Override
    public void start(Stage primaryStage) {

        TreeView<String> treeView = new TreeView<>();


        SideMenu sideMenu = new SideMenu();

        SideMenuCategory smCat = new SideMenuCategory("image-menu-cat1", "Editing");
        SideMenuItem smItem1 = new SideMenuItem("image-menu-item1", "Edit In Row", "showFrmPersonTable");
        SideMenuItem smItem6 = new SideMenuItem("image-menu-item1", "Edit In Row(Odd Editable)", "showFrmOddEditablePersonTable");
        SideMenuItem smItem2 = new SideMenuItem("image-menu-item2", "Edit In Form", "showFrmPersonTable2");
        SideMenuItem smItem3 = new SideMenuItem("image-menu-item3", "Master Detail", "showFrmMasterDetail");
        SideMenuItem smItem4 = new SideMenuItem("image-menu-item4", "Tab Pane", "showFrmDetachableTabPane");
        SideMenuItem smItem5 = new SideMenuItem("image-menu-item2", "History", "showFrmLog");
        SideMenuItem smItemDate = new SideMenuItem("image-menu-item2", "Date Table", "showFrmDateColumn");
        SideMenuItem smItemText = new SideMenuItem("image-menu-item1", "Text Table", "showFrmTextColumn");
        SideMenuItem smItemSynch = new SideMenuItem("image-menu-item1", "Synchronized columns", "showFrmSychronizedColumns");
        smCat.addMainMenuItem(smItem1, smItem6, smItem2, smItemText, smItemDate, smItemSynch, smItem5);
        sideMenu.addMenuItems(smCat, smItem3, smItem4);


        BorderPane borderPane = new BorderPane();

        borderPane.setLeft(sideMenu);

        Scene scene = new Scene(borderPane);

        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
