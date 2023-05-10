/*
 * Copyright (c) 2015, Panemu ( http://www.panemu.com/ )
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package tiwulfx.samples.detachabletab;

import com.panemu.tiwulfx.common.TiwulFXUtil;
import com.panemu.tiwulfx.control.DetachableTab;
import com.panemu.tiwulfx.control.DetachableTabPane;
import com.panemu.tiwulfx.control.DetachableTabPaneFactory;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;

/**
 *
 * @author amrullah
 */
public class FrmDetachableTab extends VBox {

	 @FXML
    private DetachableTabPane tpnScope1;

    @FXML
    private DetachableTabPane tpnScope2;

    @FXML
    private DetachableTabPane tpnNoScope;
	
	public FrmDetachableTab() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(getClass().getSimpleName() + ".fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		fxmlLoader.setResources(TiwulFXUtil.getLiteralBundle());
		try {
			fxmlLoader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		init();
	}

	private void init() {
		
		tpnScope1.getTabs().add(new DetachableTab("1 Scope1", createTabContent()));
		tpnScope1.getTabs().add(new DetachableTab("2 Scope1", createTabContent()));
		
		tpnScope1.setSceneFactory((param) -> {
			FrmScope1 frm = new FrmScope1();
			SplitPane sp = new SplitPane(param);
			VBox.setVgrow(sp, Priority.ALWAYS);
			frm.getChildren().add(sp);
			Scene scene1 = new Scene(frm);
			return scene1;
		});
		
		tpnScope1.setStageOwnerFactory(new Callback<Stage, Window>() {

			@Override
			public Window call(Stage param) {
				param.setTitle("Custom Stage & Scene");
				return getScene().getWindow();//return parent for the detached stage
			}
		});
		
		tpnScope2.getTabs().add(new DetachableTab("1 Scope2", createTabContent()));
		tpnScope2.getTabs().add(new DetachableTab("2 Scope2", createTabContent()));
		tpnScope2.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
		tpnNoScope.getTabs().add(new DetachableTab("1 No Scope", createTabContent()));
		tpnNoScope.getTabs().add(new DetachableTab("2 No Scope", createTabContent()));
		tpnNoScope.getTabs().add(new Tab("traditional tab"));
		tpnNoScope.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
		tpnNoScope.setDetachableTabPaneFactory(new DetachableTabPaneFactory(){
			@Override
			protected void init(DetachableTabPane newTabPane) {
				newTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
			}
		
		});
	}
	
	private TableView createTabContent() {
		TableView tbl = new TableView();
		tbl.getColumns().addAll(new TableColumn<Object, Object>("Example"), new TableColumn<Object, Object>("Test"));
		return tbl;
	}
}
