package org.example;

import com.panemu.tiwulfx.control.anno.TableViewColumn;
import lombok.Data;

import javax.persistence.*;

/**
 * 字段信息
 */
@Data
@Entity(name = "field_info")
public class FieldInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @TableViewColumn(name = "修饰符")
    @Column(name = "modifier", nullable = true)
    private String modifier;

    @Column(name = "data_type", nullable = true)
    @TableViewColumn(name = "数据类型")
    private String dataType;

    @Column(name = "name", nullable = true)
    @TableViewColumn(name = "名称")
    private String name;

    @Column(name = "remarks", nullable = true)
    @TableViewColumn(name = "备注")
    private String remarks;
}
