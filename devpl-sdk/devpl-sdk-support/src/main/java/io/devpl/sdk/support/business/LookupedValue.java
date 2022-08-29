package io.devpl.sdk.support.business;

import lombok.Data;
import org.checkerframework.common.aliasing.qual.Unique;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Table(name = "t_lookup_value_list")
public class LookupedValue implements Serializable {

    private static final long serialVersionUID = 4201594290843529528L;

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "LOOKUP_VALUE_CODE")
    private String lookupValueCode;

    @Column(name = "LOOKUP_VALUE_NAME")
    private String lookupValueName;

    @Column(name = "IS_ENABLED")
    private Boolean isEnabled;

    @Column(name = "CREATED_TIME")
    private LocalDateTime cratedTime;

    @Column(name = "UPDATED_TIME")
    private LocalDateTime updatedTime;
}
