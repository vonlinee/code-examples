package io.devpl.codegen.fxui.common.utils;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import org.mybatis.generator.api.ProgressCallback;

/**
 * Created by Owen on 6/21/16.
 */
public class UIProgressCallback extends Alert implements ProgressCallback {

    private final StringProperty progressText = new SimpleStringProperty();
    private final StringBuilder progressTextBuilder = new StringBuilder();

    public UIProgressCallback(AlertType alertType) {
        super(alertType);
        this.contentTextProperty().bindBidirectional(progressText);
        setResizable(true);
    }

    @Override
    public void introspectionStarted(int totalTasks) {
        progressTextBuilder.append("introspectionStarted => 开始代码检查\n");
        progressText.setValue(progressTextBuilder.toString());
    }

    @Override
    public void generationStarted(int totalTasks) {
        progressTextBuilder.append("generationStarted => 开始代码生成\n");
        progressText.setValue(progressTextBuilder.toString());
    }

    @Override
    public void saveStarted(int totalTasks) {
        progressTextBuilder.append("saveStarted => 开始保存生成的文件\n");
        progressText.setValue(progressTextBuilder.toString());
    }

    @Override
    public void startTask(String taskName) {
        progressTextBuilder.append("startTask => 代码生成任务开始" + taskName + "\n");
        progressText.setValue(progressTextBuilder.toString());
    }

    @Override
    public void done() {
        progressTextBuilder.append("Done => 代码生成完成\n");
        progressText.setValue(progressTextBuilder.toString());
    }

    @Override
    public void checkCancel() throws InterruptedException {
        progressTextBuilder.append("checkCancel => 取消\n");
        progressText.setValue(progressTextBuilder.toString());
    }
}
