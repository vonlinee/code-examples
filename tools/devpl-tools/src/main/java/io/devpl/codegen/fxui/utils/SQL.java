package io.devpl.codegen.fxui.utils;

import java.util.ArrayList;
import java.util.List;

public final class SQL {

    private static final SQL instance = new SQL();

    private SQL() {

    }

    private final List<String> keys = new ArrayList<>();
    private final List<String> sqlStrings = new ArrayList<>();

    public static class SqlServer {
        public static final String SELECT = "SELECT name FROM sysobjects  WHERE xtype='u' OR xtype='v' ORDER BY name";
    }

    public static class Sqllite {
        public static final String SELECT = "SELECT name FROM sqlite_master;";
    }
}
