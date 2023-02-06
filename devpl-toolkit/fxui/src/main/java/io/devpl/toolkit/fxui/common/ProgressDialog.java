package io.devpl.toolkit.fxui.common;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import org.mybatis.generator.api.ProgressCallback;

/**
 * 进度弹窗
 */
public class ProgressDialog extends Alert implements ProgressCallback {

    private final StringProperty progressText = new SimpleStringProperty();
    private final StringBuilder progressTextBuilder = new StringBuilder();

    public ProgressDialog(AlertType alertType) {
        super(alertType);
        this.contentTextProperty()
                .bindBidirectional(progressText);
        setResizable(true);
        setHeight(500.0);
        setWidth(800.0);
        setTitle("进度显示");

        showingProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                setContentText(""); // 清空文本
            }
        });
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

    public void closeIfShowing() {
        if (isShowing()) {
            this.close();
        }
    }
}
