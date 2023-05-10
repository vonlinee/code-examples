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
package tiwulfx.samples.table.basic;

import com.panemu.tiwulfx.common.TableCriteria;
import com.panemu.tiwulfx.common.TableData;
import com.panemu.tiwulfx.control.LookupFieldController;
import com.panemu.tiwulfx.table.ComboBoxColumn;
import com.panemu.tiwulfx.table.LookupColumn;
import com.panemu.tiwulfx.table.NumberColumn;
import com.panemu.tiwulfx.table.TableControl;
import com.panemu.tiwulfx.table.TableController;
import com.panemu.tiwulfx.table.TextColumn;
import com.panemu.tiwulfx.table.TickColumn;
import com.panemu.tiwulfx.table.TypeAheadColumn;
import java.util.ArrayList;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import tiwulfx.samples.shared.DataGenerator;
import tiwulfx.samples.shared.SharedUtil;
import tiwulfx.samples.shared.dao.DaoBase;
import tiwulfx.samples.shared.misc.EmailValidator;
import tiwulfx.samples.shared.pojo.Insurance;
import tiwulfx.samples.shared.pojo.Person;

/**
 *
 * @author amrullah
 */
public class FrmPerson extends StackPane {

	@FXML private TableControl<Person> tblPerson;
	@FXML
    private ComboBoxColumn<Person, Character> clmGender;

    @FXML
    private TypeAheadColumn<Person, String> clmBirthPlace;

    @FXML
    private NumberColumn<Person, Integer> clmInsuranceId;

    @FXML
    private NumberColumn<Person, Integer> clmVisit;

    @FXML
    private TextColumn<Person> clmName;

    @FXML
    private LookupColumn<Person, Insurance> clmInsurance;

    @FXML
    private NumberColumn<Person, Integer> clmVersion;

    @FXML
    private TextColumn<Person> clmEmail;

    @FXML
    private TickColumn<Person> clmTick;
	 
	 private DaoBase<Insurance> daoInsurance = new DaoBase<>(Insurance.class);

	public FrmPerson() {
		SharedUtil.loadFxml(this, getClass().getResource(getClass().getSimpleName() + ".fxml"));
		init();
	}

	private void init() {
		tblPerson.setController(new CntlPerson());
		tblPerson.setRecordClass(Person.class);
		
		for (String location : DataGenerator.birthPlaces) {
			clmBirthPlace.addItem(location, location);
		}

		clmGender.addItem("Male", 'm');
		clmGender.addItem("Female", 'f');

		clmInsuranceId.setNumberType(Integer.class);
		clmInsurance.setLookupController(insuranceLookupController);
//		clmInsurance.setDisableLookupManualInput(true);
		clmEmail.addValidator(new EmailValidator());
		clmVisit.setNumberType(Integer.class);
		clmVersion.setNumberType(Integer.class);
	}

	public void reload() {
		tblPerson.reloadFirstPage();
	}
	private LookupFieldController<Insurance> insuranceLookupController = new LookupFieldController<Insurance>(Insurance.class) {
		@Override
		public String[] getColumns() {
			return new String[]{
				"id",
				"code",
				"name"
			};
		}

		@Override
		protected String getWindowTitle() {
			return "Find Insurance";
		}

		@Override
		protected void initCallback(VBox container, TableControl<Insurance> table) {
			container.setPrefWidth(500);
			table.getTableView().getColumns().get(2).setPrefWidth(300);
		}

		@Override
		protected TableData loadData(int startIndex, List filteredColumns, List sortedColumns, List sortingTypes, int maxResult) {
			return daoInsurance.fetch(startIndex, filteredColumns, sortedColumns, sortingTypes, maxResult);
		}
	};

	private class CntlPerson extends TableController<Person> {

		private DaoBase<Person> dao = new DaoBase<>(Person.class);

		@Override
		public TableData<Person> loadData(int startIndex, List<TableCriteria> filteredColumns, List<String> sortedColumns, List<TableColumn.SortType> sortingOrders, int maxResult) {
			List<String> lstJoin = new ArrayList<>();
			lstJoin.add("insurance");
			return dao.fetch(startIndex, filteredColumns, sortedColumns, sortingOrders, maxResult, lstJoin);
		}

		@Override
		public List<Person> update(List<Person> records) {
			List<Person> result = dao.update(records);
			result = dao.initRelationship(records, "insurance");
			return result;
		}

		@Override
		public List<Person> insert(List<Person> newRecords) {
			return dao.insert(newRecords);
		}

		@Override
		public void delete(List<Person> records) {
			dao.delete(records);
		}

	}
}
