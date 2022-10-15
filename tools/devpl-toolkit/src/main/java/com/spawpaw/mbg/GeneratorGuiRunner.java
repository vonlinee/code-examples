package com.spawpaw.mbg;

import com.spawpaw.mbg.controller.CodeGenApplication;
import com.spawpaw.mbg.util.Constants;

import java.util.Locale;

/**
 * Created By spawpaw@hotmail.com 2018.1.20
 * Description:
 * 整个程序的入口
 *
 * @author BenBenShang spawpaw@hotmail.com
 */
public class GeneratorGuiRunner {
    public static void main(String[] args) {
        //set your language(only supports CHINESE or ENGLISH)
        Constants.setLocale(Locale.getDefault());
//        Constants.setLocale(Locale.CHINA);
//        Constants.setLocale(Locale.ENGLISH);
        CodeGenApplication.launchWindow(args);
    }
}
