package org.example;

import com.panemu.tiwulfx.control.anno.TableViewColumn;
import lombok.Data;

/**
 * 字段信息
 */
@Data
public class FieldInfo {

    @TableViewColumn(name = "修饰符")
    private String modifier;

    @TableViewColumn(name = "数据类型")
    private String dataType;

    @TableViewColumn(name = "名称")
    private String name;

    @TableViewColumn(name = "备注")
    private String remarks;
}
