package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.utils.FileUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainController implements Initializable {
	
	private File inputFile = new File("D:\\Temp\\input.txt");
	private File outputFile = new File("D:\\Temp\\output.txt");
	
	@FXML
	public BorderPane textHandlePane;
	public VBox topRootVBox;
	public HBox topHBox1;
	public HBox topHBox2;
	public HBox topHBox3;
	public Button chooseInputBtn;
	public Button chooseOutputBtn;

	public HBox bottomHBox;
	public VBox centerVBox;
	public TextField inputFileTextField;
	public TextField outputFileTextField;
	public TextField choseFileTextField;
	public Button choseFileBtn;
	public Button copyToClipboardBtn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TOP
		limitWidth(chooseInputBtn, 100, 100);
		limitWidth(chooseOutputBtn, 100, 100);
		limitWidth(choseFileBtn, 100, 100);
		topHBox1.prefHeightProperty().bind(chooseInputBtn.heightProperty());
		topHBox2.prefHeightProperty().bind(chooseOutputBtn.heightProperty());
		inputFileTextField.prefWidthProperty().bind(topHBox1.widthProperty().subtract(chooseInputBtn.widthProperty()));
		outputFileTextField.prefWidthProperty().bind(topHBox2.widthProperty().subtract(chooseOutputBtn.widthProperty()));
		// CENTER
		
		
	}

	private void limitWidth(Control control, double min, double max) {
		control.setMinWidth(min);
		control.setMaxWidth(max);
	}
	
	@SuppressWarnings("unused")
	private void limitHight(Control control, double min, double max) {
		control.setMinHeight(min);
		control.setMaxHeight(max);
	}
	
    public void chooseInputFile(MouseEvent mouseEvent) {
    	handle(HandleMethod.camelToUnderline);
    }
	
    public void chooseOutputFile(MouseEvent mouseEvent) {
    	
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
}
