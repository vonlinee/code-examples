package io.maker.gui;

import io.maker.base.io.FileUtils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class GUI extends Application {

    BorderPane root;

    private final File inputFile = new File("D:\\Temp\\input.txt");
    private final File outputFile = new File("D:\\Temp\\output.txt");

    private TxtThread outputTxtThread;
    private TxtThread inputTxtThread;

    private void openFileExpolrer(String path) {
        try {
            Runtime.getRuntime().exec("fileexplorer " + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() throws Exception {
        super.init();
        if (!inputFile.exists()) {
            Files.createFile(inputFile.toPath());
        }
        if (!outputFile.exists()) {
            Files.createFile(outputFile.toPath());
        }
        outputTxtThread = new TxtThread(outputFile);
        outputTxtThread.start();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = new BorderPane();
        initUI();
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    private void initUI() {
        //TOP
        VBox topRootVBox = new VBox();
        HBox hBox1 = new HBox();

        Button openInputFileBtn = new Button("打开输入");
        hBox1.getChildren().addAll(openInputFileBtn);
        openInputFileBtn.setOnAction(event -> {
            Process process = null;
            try {
                process = Runtime.getRuntime().exec("notepad " + inputFile.getAbsolutePath());
                int i = process.waitFor();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        HBox hBox2 = new HBox();

        HBox hBox3 = new HBox();

        Button btn1 = new Button("首字母大写");
        btn1.setOnAction(event -> {
            handle(HandleMethod.upperFirstCharacter);
            outputTxtThread.setOpenFile(true);
        });
        hBox3.getChildren().addAll(btn1);

        topRootVBox.getChildren().addAll(hBox1, hBox2, hBox3);
        root.setTop(topRootVBox);
        //CENTER

    }

    @SuppressWarnings("unchecked")
    List<String> readInput() throws IOException {
        return FileUtils.readLines(inputFile, "UTF-8");
    }

    final void handle(HandleMethod handleMethod) {
        try {
            writeOutput(handleMethod.apply(Lines.of(readInput())).getLines());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void writeOutput(List<String> output) throws IOException {
        FileUtils.writeLines(outputFile, "UTF-8", output);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
