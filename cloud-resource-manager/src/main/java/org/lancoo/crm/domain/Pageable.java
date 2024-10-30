package org.lancoo.crm.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Pageable {
    @JsonProperty("sort")
    private Sort sort;

    @JsonProperty("offset")
    private int offset;

    @JsonProperty("pageNumber")
    private int pageNumber;

    @JsonProperty("pageSize")
    private int pageSize;

    @JsonProperty("paged")
    private boolean paged;

    @JsonProperty("unpaged")
    private boolean unpaged;
}