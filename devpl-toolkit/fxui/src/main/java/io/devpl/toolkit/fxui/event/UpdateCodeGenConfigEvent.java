package io.devpl.toolkit.fxui.event;

import io.devpl.toolkit.fxui.model.props.GenericConfiguration;
import lombok.Data;

@Data
public class UpdateCodeGenConfigEvent {

    private GenericConfiguration generatorConfig;

    public UpdateCodeGenConfigEvent(GenericConfiguration generatorConfig) {
        this.generatorConfig = generatorConfig;
    }
}
