package samples;

import org.mybatis.generator.api.ProgressCallback;

public class DefaultProgressCallback implements ProgressCallback {

    @Override
    public void introspectionStarted(int totalTasks) {
        System.out.println("开始获取表信息");
    }

    @Override
    public void generationStarted(int totalTasks) {
        System.out.println("开始生成");
    }

    @Override
    public void saveStarted(int totalTasks) {
        System.out.println("开始保存文件");
    }

    @Override
    public void startTask(String taskName) {
        System.out.println("开始任务" + taskName);
    }

    @Override
    public void done() {
        System.out.println("完成");
    }

    @Override
    public void checkCancel() throws InterruptedException {
        System.out.println("检查取消");
    }
}
