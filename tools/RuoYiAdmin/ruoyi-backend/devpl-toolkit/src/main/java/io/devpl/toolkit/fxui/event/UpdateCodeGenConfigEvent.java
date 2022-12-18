package io.devpl.toolkit.fxui.event;

import io.devpl.toolkit.fxui.config.CodeGenConfiguration;
import lombok.Data;

@Data
public class UpdateCodeGenConfigEvent {

    private CodeGenConfiguration generatorConfig;

    public UpdateCodeGenConfigEvent(CodeGenConfiguration generatorConfig) {
        this.generatorConfig = generatorConfig;
    }
}
