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
package com.panemu.tiwulfx.demo.date;

import com.panemu.tiwulfx.common.TableCriteria;
import com.panemu.tiwulfx.common.TableData;
import com.panemu.tiwulfx.common.TiwulFXUtil;
import com.panemu.tiwulfx.table.DateColumn;
import com.panemu.tiwulfx.table.LocalDateColumn;
import com.panemu.tiwulfx.table.TableControl;
import com.panemu.tiwulfx.table.TableOperation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author amrullah <amrullah@panemu.com>
 */
public class FrmDateColumn extends VBox {

    @FXML
    private DateColumn clmDateYYYYMMDD;
    @FXML
    private DateColumn clmDateDDMMYYYY;
    @FXML
    private LocalDateColumn clmLocalDateYYYYMMDD;
    @FXML
    private LocalDateColumn clmLocalDateDDMMYYYY;
    @FXML
    private TableControl<DatePojo> tblDate;
    private final List<DatePojo> lstRecord = new ArrayList<>();

    public FrmDateColumn() {
        FXMLLoader fxmlLoader = new FXMLLoader(FrmDateColumn.class.getResource("FrmDateColumn.fxml"));
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

    public void reload() {
        tblDate.reloadFirstPage();
    }

    private void init() {
        clmDateYYYYMMDD.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        clmDateDDMMYYYY.setDateFormat(new SimpleDateFormat("dd/MM/yyyy"));
        clmLocalDateYYYYMMDD.setDateFormat(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        clmLocalDateDDMMYYYY.setDateFormat(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        lstRecord.add(new DatePojo(new Date(), LocalDate.now(), new Date(), LocalDate.now(), new Date(), LocalDate.now()));

        tblDate.setFooterVisiablity(false);

        tblDate.setRecordClass(DatePojo.class);
        tblDate.setController(controller);
    }

    private TableOperation<DatePojo> controller = new TableOperation<DatePojo>() {

        @Override
        public <C> TableData<DatePojo> loadData(int startIndex, List<TableCriteria<C>> filteredColumns, List<String> sortedColumns, List<TableColumn.SortType> sortingOrders, int maxResult) {
            return new TableData<>(lstRecord, false, lstRecord.size());
        }

        @Override
        public List<DatePojo> update(List<DatePojo> records) {
            System.out.println("About to save");
            for (DatePojo pojo : records) {
                System.out.println("record: " + pojo.toString());
            }
            return records;
        }

        @Override
        public List<DatePojo> insert(List<DatePojo> newRecords) {
            lstRecord.addAll(newRecords);
            return newRecords;
        }


    };

    public static class DatePojo {
        private Date dateDefault;
        private LocalDate localDateDefault;
        private Date date_yyyyMMdd;
        private LocalDate localDate_yyyyMMdd;
        private Date date_ddMMyyyy;
        private LocalDate localDate_ddMMyyyy;

        private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        public DatePojo() {
        }

        public DatePojo(Date dateDefault, LocalDate localDateDefault, Date date_yyyyMMdd, LocalDate localDate_yyyyMMdd, Date date_ddMMyyyy, LocalDate localDate_ddMMyyyy) {
            this.dateDefault = dateDefault;
            this.localDateDefault = localDateDefault;
            this.date_yyyyMMdd = date_yyyyMMdd;
            this.localDate_yyyyMMdd = localDate_yyyyMMdd;
            this.date_ddMMyyyy = date_ddMMyyyy;
            this.localDate_ddMMyyyy = localDate_ddMMyyyy;
        }

        public Date getDateDefault() {
            return dateDefault;
        }

        public void setDateDefault(Date dateDefault) {
            this.dateDefault = dateDefault;
        }

        public LocalDate getLocalDateDefault() {
            return localDateDefault;
        }

        public void setLocalDateDefault(LocalDate localDateDefault) {
            this.localDateDefault = localDateDefault;
        }

        public Date getDate_yyyyMMdd() {
            return date_yyyyMMdd;
        }

        public void setDate_yyyyMMdd(Date date_yyyyMMdd) {
            this.date_yyyyMMdd = date_yyyyMMdd;
        }

        public LocalDate getLocalDate_yyyyMMdd() {
            return localDate_yyyyMMdd;
        }

        public void setLocalDate_yyyyMMdd(LocalDate localDate_yyyyMMdd) {
            this.localDate_yyyyMMdd = localDate_yyyyMMdd;
        }

        public Date getDate_ddMMyyyy() {
            return date_ddMMyyyy;
        }

        public void setDate_ddMMyyyy(Date date_ddMMyyyy) {
            this.date_ddMMyyyy = date_ddMMyyyy;
        }

        public LocalDate getLocalDate_ddMMyyyy() {
            return localDate_ddMMyyyy;
        }

        public void setLocalDate_ddMMyyyy(LocalDate localDate_ddMMyyyy) {
            this.localDate_ddMMyyyy = localDate_ddMMyyyy;
        }

        @Override
        public String toString() {
            return "DatePojo{" + "dateDefault=" + df.format(dateDefault)
                    + ", localDateDefault=" + dtf.format(localDateDefault)
                    + ", date_yyyyMMdd=" + df.format(date_yyyyMMdd)
                    + ", localDate_yyyyMMdd=" + dtf.format(localDate_yyyyMMdd)
                    + ", date_ddMMyyyy=" + df.format(date_ddMMyyyy)
                    + ", localDate_ddMMyyyy=" + dtf.format(localDate_ddMMyyyy) + '}';
        }

    }

}
