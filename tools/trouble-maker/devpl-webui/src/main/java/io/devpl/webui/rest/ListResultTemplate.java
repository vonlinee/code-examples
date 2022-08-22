package io.devpl.webui.rest;

import lombok.Data;

import java.util.List;

@Data
public class ListResultTemplate extends ResultTemplate {

    private int pageIndex = 0;

    private int pageSize = -1;

    private List<?> data;

    @Override
    public String serialize() {
        return "";
    }
}
