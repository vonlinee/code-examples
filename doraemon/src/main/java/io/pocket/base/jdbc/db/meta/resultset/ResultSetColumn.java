package io.pocket.base.jdbc.db.meta.resultset;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 包含数据和元数据信息
 */
public class ResultSetColumn {

    private List<ResultSetColumnMetadata> metadataList;
    private List<Map<String, Object>> data;

    public List<ResultSetColumnMetadata> getMetadataList() {
        return metadataList;
    }

    public void setMetadataList(List<ResultSetColumnMetadata> metadataList) {
        this.metadataList = metadataList;
    }

    public List<Map<String, Object>> getData() {
        return data;
    }

    public void setData(List<Map<String, Object>> data) {
        this.data = data;
    }

    public void addColumnMetadata(ResultSetColumnMetadata columnMetadata) {
        this.metadataList.add(columnMetadata);
    }

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
