package io.maker.base.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONReader;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

/**
 * @author vonline
 * @version Jan 8, 2021 12:46:51 PM
 * @description 用于处理UWP版本客户端下载文件的重命名，因为下载的视频名字是
 * 假设视频合集的编号是69742084，那么下载后的视频保存目录如下：
 * 69742084
 * ----1                           # 第一个视频（P1）
 * ----69742084.info           # JSON文件，存放视频合集信息及本集视频信息
 * ----69742084_1.xml          # 弹幕文件
 * ----69742084_1_0.mp4        # 单集视频
 * ----2
 * ----69742084.info
 * ----69742084_2.xml
 * ----9742084_2_0.mp4
 * <p>
 * ......  以此类推
 * 可以看到很不方便，因此
 */
public class BilibiliClientHelper extends Application {

    //B站客户端视频文件下载保存根目录
    private static final String BILIBILI_VIDEO_HOME = "D:\\Download\\BiliBili";
    //上一次操作的目录
    private File bilibili_home_video_dir = null;
    //控件
    private Button chooseDirBtn;
    private Button renameVideoBtn;
    private Button moveVideoBtn;
    private Button clearInfoBtn;
    private Button renameRootDirBtn;
    private Stage primaryStage;
    private ListView<String> infoListView;
    private ObservableList<Node> childrens;
    private TextField field1;
    private TextField field2;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("重命名BiliBili客户端下载的视频文件");
        VBox root = new VBox();
        childrens = root.getChildren();
        root.setSpacing(10);

        //配置区
        VBox configVBox = new VBox();
        configVBox.setSpacing(5);

        HBox config1 = new HBox();
        config1.setSpacing(5);
        config1.setAlignment(Pos.BASELINE_LEFT);
        Label label1 = new Label("B站下载根目录:");
        field1 = new TextField(BILIBILI_VIDEO_HOME);
        config1.getChildren().addAll(label1, field1);

        HBox config2 = new HBox();
        config2.setSpacing(5);
        config2.setAlignment(Pos.BASELINE_LEFT);
        Label label2 = new Label("操作根目录:");
        field2 = new TextField(BILIBILI_VIDEO_HOME);
        field2.setPrefWidth(300.0);
        config2.getChildren().addAll(label2, field2);

        configVBox.getChildren().addAll(config1, config2);

        //操作区
        HBox operationHBox = new HBox();
        operationHBox.setSpacing(10);
        chooseDirBtn = new Button("选择视频文件根目录");
        renameVideoBtn = new Button("重命名");
        moveVideoBtn = new Button("移动");
        clearInfoBtn = new Button("清空日志信息");
        renameRootDirBtn = new Button("重命名根目录");
        operationHBox.getChildren().addAll(chooseDirBtn, renameVideoBtn, moveVideoBtn, clearInfoBtn, renameRootDirBtn);

        //信息显示区
        infoListView = new ListView<>();

        //全局事件注册
        registerEventListener();

        childrens.add(configVBox);
        childrens.add(operationHBox);
        childrens.add(infoListView);
        Scene scene = new Scene(root, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    //事件注册中心
    private void registerEventListener() {
        chooseDirBtn.setOnAction(event -> {
            if (bilibili_home_video_dir == null) {
                bilibili_home_video_dir = new File(BILIBILI_VIDEO_HOME);
            }
            DirectoryChooser directoryChooser = new DirectoryChooser();
            directoryChooser.setInitialDirectory(bilibili_home_video_dir);
            directoryChooser.setTitle("选择视频文件所在的根目录");
            File dir = directoryChooser.showDialog(primaryStage);
            if (dir != null) {
                field2.setText(dir.getAbsolutePath());
            }
        });
        renameVideoBtn.setOnAction(event -> renameVideo(new File(field2.getText())));
        moveVideoBtn.setOnAction(event -> {
            infoListView.getItems().add("开始移动");
            moveVideo(new File(field2.getText()));
        });
        clearInfoBtn.setOnAction(event -> infoListView.getItems().clear());
        renameRootDirBtn.setOnAction(event -> {
            File root = new File(field2.getText());
            String parent = bilibili_home_video_dir.getParent();
            File[] files = bilibili_home_video_dir.listFiles();
            if (files == null) {
                return;
            }
            for (File file : files) {
                if (file.isDirectory()) {
                    File[] listFiles = file.listFiles();
                    for (File listFile : listFiles) {
                        String title = getJsonValue(listFile, "Title");
                        if (title != null && !title.equals("")) {
                            File newFile = new File(parent + File.separator + title);
                            boolean result = root.renameTo(newFile);
                            if (result) {
                                infoListView.getItems().add("修改根目录成功");
                                return;
                            }
                        }
                    }
                }
            }
        });
    }

    /**
     * 移动所有视频文件到根目录
     * @param rootDir
     */
    public void moveVideo(File rootDir) {
        String absolutePath = rootDir.getAbsolutePath();
        File[] dirList = rootDir.listFiles();
        if (dirList == null) {
            return;
        }
        for (File dir : dirList) {
            if (!dir.isDirectory()) {
                continue;
            }
            infoListView.getItems().add("进入目录：" + dir.getName());
            File[] files = dir.listFiles();
            if (files == null) {
                continue;
            }
            for (File file : files) {
                String fileName = file.getName();
                if (fileName.endsWith(".mp4")) {
                    boolean result = file.renameTo(new File(absolutePath + File.separator + fileName));
                    if (result) {
                        infoListView.getItems().add("移动 " + file.getAbsolutePath() + "到" + absolutePath + "成功");
                    }
                }
            }
        }
    }

    public void renameVideo(File rootDir) {
        File[] dirList = rootDir.listFiles();
        if (dirList == null) {
            return;
        }
        for (File dir : dirList) {
            if (!dir.isDirectory()) {
                continue;
            }
            File[] files = dir.listFiles();
            if (files == null || files.length > 0) {
                infoListView.getItems().add("进入目录：" + dir.getName());
            } else {
                infoListView.getItems().add("进入目录：" + dir.getName() + ",目录为空");
            }
            File infoFile = null, videoFile = null;
            assert files != null;
            for (File file : files) {
                String fileName = file.getName();
                if (fileName.endsWith(".info")) {
                    infoFile = file;
                    continue;
                }
                if (fileName.endsWith(".mp4")) {
                    videoFile = file;
                    boolean result = renameVideoFile(videoFile, infoFile);
                    if (result) {
                        infoListView.getItems().add("\t重命名 " + fileName + " 成功!!!");
                    }
                }
            }
        }
        //重命名根目录

    }

    public boolean renameVideoFile(File videoFile, File infoFile) {
        String partName = getPartName(infoFile);
        String dir = videoFile.getParent();
        return videoFile.renameTo(new File(dir + File.separator + partName + ".mp4"));
    }

    /**
     * 读取JSON文件，获取视频名字
     * @param videoFile MP4视频文件
     * @return
     */
    public String getPartName(File videoFile) {
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(videoFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Objects.requireNonNull(fileReader);
        JSONReader reader = new JSONReader(fileReader);
        JSONObject json = (JSONObject) reader.readObject();
        Object partName = json.get("PartName");
        reader.close();
        try {
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (String) partName;
    }

    /**
     * 获取根目录名字：json文件中title字段
     * @param infoFile
     * @return
     */
    public String getTitle(File infoFile) {
        FileReader reader = null;
        try {
            reader = new FileReader(infoFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Objects.requireNonNull(reader);
        JSONReader jsonReader = new JSONReader(reader);
        JSONObject json = (JSONObject) jsonReader.readObject();
        Object title = json.get("Title");
        jsonReader.close();
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (String) title;
    }

    /**
     * 从JSON文件中获取值
     * @param infoFile
     * @param jsonKey
     * @return
     */
    public String getJsonValue(File infoFile, String jsonKey) {
        FileReader reader = null;
        try {
            reader = new FileReader(infoFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JSONReader jsonReader = new JSONReader(Objects.requireNonNull(reader));
        JSONObject json = (JSONObject) jsonReader.readObject();
        Object title = json.get(jsonKey);
        jsonReader.close();
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (String) title;
    }
}
