package io.devpl.webui.rest;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class ListResultTemplate extends ResultTemplate {

    private PageInfo pageInfo;

    private List<?> data;

    @Override
    public String serialize() {
        return "";
    }
}
