package org.lancoo.crm.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Sort {
    @JsonProperty("unsorted")
    private boolean unsorted;

    @JsonProperty("empty")
    private boolean empty;

    @JsonProperty("sorted")
    private boolean sorted;
}