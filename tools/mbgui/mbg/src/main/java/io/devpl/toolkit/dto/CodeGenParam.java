package io.devpl.toolkit.dto;

import lombok.Data;

import java.util.List;

/**
 * 代码生成所需参数
 */
@Data
public class CodeGenParam {

    private List<String> tables;

    private GenSetting genSetting;
}
