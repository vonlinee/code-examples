package editor;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.time.Duration;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.reactfx.Subscription;

public class MainPageController implements Initializable {

    @FXML
    Button saveit, saveas, open, gentime, search, replace;
    @FXML
    CodeArea codearea;
    @FXML
    BorderPane editpane;
    @FXML
    Label viewer;
    @FXML
    TextField input;
    @FXML
    SplitPane splitpane;

    private Stage stage;
    private Path path = null;
    private File file = null;
    private static final String sampleCode = String.join("\n", new String[]{"Hellow,World"});
    private static String tosearch = "";

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initCodeArea();
    }

    public void setEditor(TextEditor edt, Stage stage) {
        this.stage = stage;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    private void initCodeArea() {
        codearea.setPadding(new Insets(10, 8, 10, 8));
        codearea.setParagraphGraphicFactory(LineNumberFactory.get(codearea));
        // Subscription cleanupWhenNoLongerNeedIt = codearea.multiPlainChanges().successionEnds(Duration.ofMillis(256));
        codearea.replaceText(0, 0, sampleCode);
    }

    @FXML
    private void findsearch(ActionEvent event) {
        tosearch = input.getText();
        PATTERN = Pattern.compile("(?<KEYWORD>" + KEYWORD_PATTERN + ")" + "|(?<PAREN>" + PAREN_PATTERN + ")" + "|(?<BRACE>" + BRACE_PATTERN + ")" + "|(?<BRACKET>" + BRACKET_PATTERN + ")" + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")" + "|(?<STRING>" + STRING_PATTERN + ")" + "|(?<COMMENT>" + COMMENT_PATTERN + ")" + "|(?<TOSEARCH>" + tosearch + ")");
        codearea.appendText("\0");
    }

    @FXML
    private void findreplace(ActionEvent event) {
        TextInputDialog newString = new TextInputDialog("new String");
        newString.setTitle("Replace all the word" + input.getText());
        newString.setHeaderText("Please input the new word that you want you want to replace the old word \"" + input.getText() + "\"");
        Optional<String> newstring = newString.showAndWait();
        newstring.ifPresent(value -> {
            String context = codearea.getText();
            codearea.clear();
            codearea.appendText(context.replace(input.getText(), value));
        });
    }

    @FXML
    private void openaction(ActionEvent event) {
        codearea.clear();
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Editable File", "*.*");
        fileChooser.getExtensionFilters().add(extFilter);
        file = fileChooser.showOpenDialog(stage);
        path = Paths.get(file.getPath());
        String context;
        // 读入文件，这里使用了常规的readine方法，下面还有两种方案可供参考；
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            while ((context = reader.readLine()) != null) {
                codearea.appendText(context + "\n");
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    @FXML
    private void saveitaction(ActionEvent event) {
        try {
            savefile(path, codearea.getText());
        } catch (NullPointerException n) {

            ChoiceDialog<String> choice = new ChoiceDialog<>("to select", "to create");
            choice.setTitle("Find no file");
            choice.setHeaderText("elect a file existed or create a new one");
            Optional<String> result = choice.showAndWait();
            result.ifPresent(value -> {
                if (value.equals("to select")) {
                    saveasaction(event);
                } else {

                    final DirectoryChooser directoryChooser = new DirectoryChooser();
                    directoryChooser.setTitle("Select one Directories");
                    directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
                    File dir = directoryChooser.showDialog(stage);
                    if (dir != null) {
                        TextInputDialog input = new TextInputDialog("Hellow.txt");
                        input.setTitle("Create new file");
                        input.setHeaderText("Please input the filename of the new file you want to create");
                        Optional<String> filename = input.showAndWait();
                        filename.ifPresent(namevalue -> {
                            try {
                                path = Paths.get(dir.getPath() + File.separator + namevalue);
                                System.out.println(path);

                                Files.createFile(path);
                                savefile(path, codearea.getText());
                            } catch (FileAlreadyExistsException ex) {
                                Alert alert = new Alert(AlertType.ERROR);
                                alert.setTitle("File existed error");
                                alert.setHeaderText("Fail to create new file since it's existence");
                                alert.showAndWait();
                            } catch (IOException ex) {
                                Logger.getLogger(MainPageController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        });
                    } else {
                    }
                }
            });
        }
    }

    @FXML
    private void saveasaction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("files", "*.*"));
        file = fileChooser.showOpenDialog(stage);
        savefile(Paths.get(file.getPath()), codearea.getText());
    }

    private void savefile(Path path, String context) {
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            writer.write(context, 0, context.length());
        } catch (IOException x) {
            System.err.format("IOException Error : %s%n", x);
        }
    }

    @FXML
    private void genTime(ActionEvent event) {
        codearea.appendText(" [ " + DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, Locale.CHINESE)
                                              .format(new java.util.Date()).toString() + " ] ");
    }

    private static StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        while (matcher.find()) {
            String styleClass = matcher.group("KEYWORD") != null ? "keyword" : matcher.group("PAREN") != null ? "paren" : matcher.group("BRACE") != null ? "brace" : matcher.group("BRACKET") != null ? "bracket" : matcher.group("SEMICOLON") != null ? "semicolon" : matcher.group("STRING") != null ? "string" : matcher.group("COMMENT") != null ? "comment" : matcher.group("TOSEARCH") != null ? "tosearch" : null;
            /* never happens */
            assert styleClass != null;

            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }

    private static final String[] KEYWORDS = new String[]{"abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const", "continue", "default", "do", "double", "else", "enum", "extends", "final", "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long", "native", "new", "package", "private", "protected", "public", "return", "short", "static", "strictfp", "super", "switch", "synchronized", "this", "throw", "throws", "transient", "try", "void", "volatile", "while"};

    private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
    private static final String PAREN_PATTERN = "\\(|\\)";
    private static final String BRACE_PATTERN = "\\{|\\}";
    private static final String BRACKET_PATTERN = "\\[|\\]";
    private static final String SEMICOLON_PATTERN = "\\;";
    private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
    private static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";

    private static Pattern PATTERN = Pattern.compile("(?<KEYWORD>" + KEYWORD_PATTERN + ")" + "|(?<PAREN>" + PAREN_PATTERN + ")" + "|(?<BRACE>" + BRACE_PATTERN + ")" + "|(?<BRACKET>" + BRACKET_PATTERN + ")" + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")" + "|(?<STRING>" + STRING_PATTERN + ")" + "|(?<COMMENT>" + COMMENT_PATTERN + ")" + "|(?<TOSEARCH>" + tosearch + ")");

    /**
     * 问题是怎么把stream转为string;
     * @param file
     */
    public void readTextFile(String file) {
        try (Stream<String> stream = Files.lines(Paths.get(file))) {
            stream.forEach(System.out::println);// 输出重定向
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * en: This method reads all the documents in at one time. Using methods
     * such as readLine () requires repeated access to files, and every time
     * readLine () calls code conversion, which reduces the speed. Therefore, in
     * the case of known encoding, it is the fastest way to read the files into
     * memory first by byte stream, and then by one-time encoding conversion.
     * zh: 这个方法一次把文件全部读进来。用readline()之类的方法需要反复访问文件，每次readline()都会调用编码转换，
     * 降低了速度，所以，在已知编码的情况下，按字节流方式先将文件都读入内存，再一次性编码转换是最快的方式。
     * @param fileName
     * @return
     */
    public String readToString(String fileName) {
        String encoding = "UTF-8";
        File file = new File(fileName);
        byte[] filecontent = new byte[(int) file.length()];
        try (FileInputStream in = new FileInputStream(file)) {
            in.read(filecontent);
            return new String(filecontent, encoding);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用了string builder,可参考
     * @param fileName 文件名
     * @return 文本文件的文本
     * @throws IOException IOException
     */
    private static String readFileContent(String fileName) throws IOException {
        File file = new File(fileName);
        BufferedReader bf = new BufferedReader(new FileReader(file));
        String content = "";
        StringBuilder sb = new StringBuilder();
        while (true) {
            content = bf.readLine();
            if (content == null) {
                break;
            }
            sb.append(content.trim());
        }
        bf.close();
        return sb.toString();
    }
}