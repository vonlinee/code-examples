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
package com.panemu.tiwulfx.demo.text;

import com.panemu.tiwulfx.common.TableCriteria;
import com.panemu.tiwulfx.common.TableData;
import com.panemu.tiwulfx.common.TiwulFXUtil;
import com.panemu.tiwulfx.table.TableControl;
import com.panemu.tiwulfx.table.TableControlBehaviour;
import com.panemu.tiwulfx.table.TextColumn;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author panemu
 */
public class FrmTextColumn extends VBox {

    @FXML
    private TextColumn clmMaxLength;
    @FXML
    private TextColumn clmCapitalize;
    @FXML
    private TextColumn clmMaxCapitalize;
    @FXML
    private TableControl<TextColumnPojo> tblTextColumn;
    private List<TextColumnPojo> lstColumns = new ArrayList<>();

    public FrmTextColumn() {
        FXMLLoader fxmlLoader = new FXMLLoader(FrmTextColumn.class.getResource("FrmTextColumn.fxml"));
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
        String max = "check";
        String cap = "CAPITALIZE";
        String maxCap = "MXCAP";

        clmMaxLength.setMaxLength(5);
        clmCapitalize.setCapitalize(true);
        clmMaxCapitalize.setMaxLength(5);
        clmMaxCapitalize.setCapitalize(true);
        lstColumns.add(new TextColumnPojo(max, cap, maxCap));
//		lstColumns.add(new TextColumnPojo(new String(), new String(), new String()));
        tblTextColumn.setRecordClass(TextColumnPojo.class);
        tblTextColumn.setBehaviour(controller);

        tblTextColumn.setFooterVisibility(false);
    }

    private TableControlBehaviour<TextColumnPojo> controller = new TableControlBehaviour<TextColumnPojo>() {
        @Override
        public <C> TableData<TextColumnPojo> loadData(int startIndex, List<TableCriteria<C>> filteredColumns, List<String> sortedColumns, List<TableColumn.SortType> sortingOrders, int maxResult) {
            return new TableData<>(lstColumns, false, lstColumns.size());
        }

        @Override
        public List<TextColumnPojo> update(List<TextColumnPojo> records) {
            return records;
        }

        @Override
        public List<TextColumnPojo> insert(List<TextColumnPojo> newRecords) {
            lstColumns.addAll(newRecords);
            return newRecords;
        }
    };

    public void reload() {
        tblTextColumn.reloadFirstPage();
    }

    public static class TextColumnPojo {
        private String maxLength;
        private String capitalize;
        private String maxCapitalize;

        public TextColumnPojo() {
        }


        public TextColumnPojo(String maxLength, String capitalize, String maxCapitalize) {
            this.maxLength = maxLength;
            this.capitalize = capitalize;
            this.maxCapitalize = maxCapitalize;
        }

        public String getMaxLength() {
            return maxLength;
        }

        public void setMaxLength(String maxLength) {
            this.maxLength = maxLength;
        }

        public String getCapitalize() {
            return capitalize;
        }

        public void setCapitalize(String capitalize) {
//			capitalize.setCapitalize(true);
            this.capitalize = capitalize;
        }

        public String getMaxCapitalize() {
            return maxCapitalize;
        }

        public void setMaxCapitalize(String maxCapitalize) {
            this.maxCapitalize = maxCapitalize;
        }

    }
}
