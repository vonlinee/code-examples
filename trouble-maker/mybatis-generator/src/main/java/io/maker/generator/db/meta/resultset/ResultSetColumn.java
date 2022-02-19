package io.maker.generator.db.meta.resultset;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 包含数据和元数据信息
 */
@Data
public class ResultSetColumn {

    private List<ResultSetColumnMetadata> metadataList;
    private List<Map<String, Object>> data;

    public ResultSetColumnMetadata getMetadata(int columnIndex) {
        return metadataList.get(columnIndex);
    }

    public ResultSetColumnMetadata getMetadata(String columnName) {
        for (ResultSetColumnMetadata rsColMeta : metadataList) {
            if (rsColMeta.getColumnName().equalsIgnoreCase(columnName)) {
                return rsColMeta;
            }
        }
        return null;
    }
}
