package io.devpl.tookit.utils;

import lombok.Data;

/**
 * 代码生成选项
 * 每个表都可以定制选项，针对表的选项
 */
@Data
public class TableGenOption {

    private boolean useSwagger;
    private boolean useLombok;
    private boolean useXmlMapper;
}
