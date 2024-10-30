package org.lancoo.crm.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
// @JsonInclude(JsonInclude.Include.NON_NULL) // 仅序列化非空字段
public class ServerResponse {
    @JsonProperty("content")
    private List<Server> content;

    @JsonProperty("pageable")
    private Pageable pageable;

    @JsonProperty("totalElements")
    private Long totalElements;

    @JsonProperty("totalPages")
    private Integer totalPages;

    @JsonProperty("last")
    private Boolean last;

    @JsonProperty("number")
    private Integer number;

    @JsonProperty("size")
    private Integer size;

    @JsonProperty("first")
    private Boolean first;

    @JsonProperty("empty")
    private Boolean empty;
    private String errMsg;

    @JsonProperty("dataTimePoint")
    private LocalDateTime dataTimePoint;
}