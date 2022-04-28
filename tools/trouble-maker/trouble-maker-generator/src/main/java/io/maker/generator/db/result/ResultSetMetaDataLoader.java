package io.maker.generator.db.result;

import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class ResultSetMetaDataLoader {

    public static List<ResultSetColumnMetadata> load(ResultSetMetaData rsmd) {
        return new ArrayList<>();
    }
}
