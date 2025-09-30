package com.panemu.tiwulfx.utils;

import javafx.beans.value.ObservableValue;

import java.util.StringJoiner;

public class FX {

    public static <T> void logChange(ObservableValue<? extends T> observable, T oldValue, T newValue) {
        System.out.println("O: " + observable + ", OV: " + oldValue + ", NV: " + newValue);
    }

    public static void log(Object... args) {
        StringJoiner sb = new StringJoiner(" ");
        for (Object arg : args) {
            sb.add(String.valueOf(arg));
        }
        System.out.println(sb);
    }

    public static void log(String msg, Object... args) {
        System.out.printf(msg + "%n", args);
    }
}
