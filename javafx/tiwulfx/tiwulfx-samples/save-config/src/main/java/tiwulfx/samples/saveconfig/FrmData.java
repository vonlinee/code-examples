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
package tiwulfx.samples.saveconfig;

import com.panemu.tiwulfx.common.TableCriteria;
import com.panemu.tiwulfx.common.TableData;
import com.panemu.tiwulfx.common.TiwulFXUtil;
import com.panemu.tiwulfx.dialog.MessageDialogBuilder;
import com.panemu.tiwulfx.table.TableControl;
import com.panemu.tiwulfx.table.TableBehaviourBase;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

/**
 *
 * @author amrullah
 */
public class FrmData extends StackPane {

	@FXML
	private TableControl<RecordPojo> tblData;
	@FXML private TextField txtConfigFile;
	@FXML private TextField txtConfigItem;
	@FXML private Button btnSave;
	@FXML private Button btnRead;
	private static final String DEVCONFIG = "dev.config";
	
	public FrmData() {
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
		tblData.setConfigurationID("FrmData.tblData");
		tblData.setController(new CntlRecord());
		tblData.setRecordClass(RecordPojo.class);
		
		String home = System.getProperty("user.home");
		String confPath = home + File.separator + TiwulFXUtil.getApplicationId() + File.separator +"conf.properties";
		txtConfigFile.setText(confPath);
		
		btnSave.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					if (txtConfigItem.getText().isEmpty()) {
						MessageDialogBuilder.error().message("Please enter value for " + DEVCONFIG).show(getScene().getWindow());
						return;
					}
					TiwulFXUtil.writeProperties(DEVCONFIG, txtConfigItem.getText());
				} catch (Exception ex) {
					Logger.getLogger(FrmData.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
			
		});
		
		btnRead.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				String value = TiwulFXUtil.readProperty(DEVCONFIG);
				MessageDialogBuilder.info().message("The value of " + DEVCONFIG + ": " + value).show(getScene().getWindow());
			}
		});
	}

	public void reload() {
		tblData.reloadFirstPage();
	}

	private class CntlRecord extends TableBehaviourBase<RecordPojo> {

		@Override
		public TableData<RecordPojo> loadData(int startIndex, List<TableCriteria<?>> filteredColumns, List<String> sortedColumns, List<TableColumn.SortType> sortingOrders, int maxResult) {
			RecordPojo person = new RecordPojo();
			person.setId(1);
			person.setName("Allie Mckenzie");
			person.setEmail("allie@panemu.com");
			List<RecordPojo> lstPerson = new ArrayList<>();
			lstPerson.add(person);
			person = new RecordPojo();
			person.setId(2);
			person.setName("Agus Suwono");
			person.setEmail("suwono@panemu.com");
			lstPerson.add(person);
			return new TableData<>(lstPerson, false, lstPerson.size());
		}

	}

}
