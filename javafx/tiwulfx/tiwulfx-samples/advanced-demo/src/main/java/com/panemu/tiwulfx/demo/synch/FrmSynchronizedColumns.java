/*
 * Copyright (c) 2014, Panemu
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
package com.panemu.tiwulfx.demo.synch;

import com.panemu.tiwulfx.common.TableCriteria;
import com.panemu.tiwulfx.common.TableData;
import com.panemu.tiwulfx.common.TiwulFXUtil;
import com.panemu.tiwulfx.table.CellEditorListener;
import com.panemu.tiwulfx.table.TableControl;
import com.panemu.tiwulfx.table.TableController;
import com.panemu.tiwulfx.table.TypeAheadColumn;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.VBox;

/**
 *
 * @author Heru Tri Julianto <heru.trijulianto@panemu.com>
 */
public class FrmSynchronizedColumns extends VBox {

	@FXML
	private TypeAheadColumn<SynchColumnPojo, String> clmContinent;
	@FXML
	private TypeAheadColumn<SynchColumnPojo, String> clmCountry;
	@FXML
	private TypeAheadColumn<SynchColumnPojo, String> clmProvince;
	@FXML
	private TableControl<SynchColumnPojo> tblSynchColumn;
	private List<SynchColumnPojo> lstColumns = new ArrayList<>();

	public FrmSynchronizedColumns() {
		FXMLLoader fxmlLoader = new FXMLLoader(FrmSynchronizedColumns.class.getResource("FrmSynchronizedColumns.fxml"));
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
		String continent = "Asia";
		String country = "Indonesia";
		String province = "Yogyakarta";
		
		String continent2 = "Europe";
		String country2 = "Italy";
		String province2 = "Milan";
		
		Map<String, List<String>> continentCountry = new LinkedHashMap<>();
		Map<String, List<String>> countryProvince = new LinkedHashMap<>();
		
		continentCountry.put("Asia", Arrays.asList("Indonesia", "China", "Philipine"));
		continentCountry.put("Europe", Arrays.asList("Italy", "Spain", "Netherlands"));
		continentCountry.put("America", Arrays.asList("Canada", "Cuba"));
		
		countryProvince.put("Indonesia", Arrays.asList("Yogyakarta", "Bandung", "Jakarta"));
		countryProvince.put("China", Arrays.asList("Shanghai", "Fujian", "Shandong"));
		countryProvince.put("Philipine", Arrays.asList("Albay", "Bataan", "Capiz"));
		countryProvince.put("Italy", Arrays.asList("Milan", "Livorno", "Parma"));
		countryProvince.put("Spain", Arrays.asList("Madrid", "Almeria", "Barcelona"));
		countryProvince.put("Netherlands", Arrays.asList("Limburg", "Drenthe", "Friesland"));
		countryProvince.put("Canada", Arrays.asList("Quebec", "Ontario", "Manitoba"));
		countryProvince.put("Cuba", Arrays.asList("Granma", "Las Tunas", "Matanzas"));
		
		for(String cont : continentCountry.keySet()) {
			clmContinent.addItem(cont.toUpperCase(), cont);
		}
		
		clmContinent.addCellEditorListener(new CellEditorListener<SynchColumnPojo, String>() {

			@Override
			public void valueChanged(int rowIndex, String propertyName, SynchColumnPojo record, String value) {
				if (value != null && !value.equals(record.getContinent())) {
					clmCountry.clearItems();
					
					for (String country : continentCountry.get(value)) {
						clmCountry.addItem(country, country);
					}

					record.setContinent(value);
					record.setCountry(null);
					record.setProvince(null);
					tblSynchColumn.markAsChanged(record);
					tblSynchColumn.refresh(record);
					
				}

			}
		});
		
		clmCountry.addCellEditorListener(new CellEditorListener<SynchColumnPojo, String>() {

			@Override
			public void valueChanged(int rowIndex, String propertyName, SynchColumnPojo record, String value) {
				
				if (value != null && !value.equals(record.getCountry())) {
					clmProvince.clearItems();
					for (String prov : countryProvince.get(value)) {
						clmProvince.addItem(prov, prov);
					}
					
					record.setCountry(value);
					record.setProvince(null);
					tblSynchColumn.markAsChanged(record);
					tblSynchColumn.refresh(record);
				}
				
			}
		});
		
		tblSynchColumn.selectedItemProperty().addListener(new ChangeListener<SynchColumnPojo>() {

			@Override
			public void changed(ObservableValue<? extends SynchColumnPojo> observable, SynchColumnPojo oldValue, SynchColumnPojo newValue) {
				if (newValue != null) {
					clmCountry.clearItems();
					List<String> lstCountry = continentCountry.get(newValue.getContinent());
					if (lstCountry != null) {
						for (String country : lstCountry) {
							clmCountry.addItem(country, country);
						}
					}
					clmProvince.clearItems();
					List<String> lstProvince = countryProvince.get(newValue.getCountry());
					if(lstProvince != null) {
						for (String province : lstProvince) {
							clmProvince.addItem(province, province);
						}
					}
				}
			}
		});

		lstColumns.add(new SynchColumnPojo(continent, country, province));
		lstColumns.add(new SynchColumnPojo(continent2, country2, province2));
		tblSynchColumn.setRecordClass(SynchColumnPojo.class);
		tblSynchColumn.setController(controller);
	}
	
	public void reload() {
		tblSynchColumn.reloadFirstPage();
	}
	
	private TableController<SynchColumnPojo> controller = new TableController<SynchColumnPojo>() {
		@Override
		public TableData<SynchColumnPojo> loadData(int startIndex, List<TableCriteria> filteredColumns, List<String> sortedColumns, List<TableColumn.SortType> sortingOrders, int maxResult) {
			return new TableData<>(lstColumns, false, lstColumns.size());
		}

		@Override
		public List<SynchColumnPojo> update(List<SynchColumnPojo> records) {
			return records;
		}

		@Override
		public List<SynchColumnPojo> insert(List<SynchColumnPojo> newRecords) {
			lstColumns.addAll(newRecords);
			return newRecords;
		}
	};

	public static class SynchColumnPojo {
		private String continent;
		private String country;
		private String province;

		public SynchColumnPojo() {
		}
		
		
		public SynchColumnPojo(String continent, String country, String province) {
			this.continent = continent;
			this.country = country;
			this.province = province;
		}
		
		public String getContinent() {
			return continent;
		}

		public void setContinent(String continent) {
			this.continent = continent;
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public String getProvince() {
			return province;
		}

		public void setProvince(String province) {
			this.province = province;
		}

	}
}
