package com.panemu.tiwulfx.common;

import com.panemu.tiwulfx.control.LocalDateFieldController;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TiwulFXUtil {

    private static Locale loc = new Locale("en", "US");
    private static DateTimeFormatter dateTimeFormatter;
    private static DateFormat dateFormat;
    private static ResourceBundle literalBundle = ResourceBundle.getBundle("com.panemu.tiwulfx.res.literal", loc);
    public static int DEFAULT_LOOKUP_SUGGESTION_ITEMS = 10;
    public static int DEFAULT_LOOKUP_SUGGESTION_WAIT_TIMES = 500;

    /**
     * Global default value for the number of digits behind decimal. Used as default value by {@link com.panemu.tiwulfx.control.NumberField#setDigitBehindDecimal(int) NumberField}
     * and {@link com.panemu.tiwulfx.table.NumberColumn#setDigitBehindDecimal(int) NumberColumn}
     */
    public static int DEFAULT_DIGIT_BEHIND_DECIMAL = 2;

    /**
     * Global default value for {@link com.panemu.tiwulfx.table.TableControl#isUseBackgroundTaskToLoad()}. Default is FALSE
     */
    public static boolean DEFAULT_USE_BACKGROUND_TASK_TO_LOAD = false;
    /**
     * Global default value for {@link com.panemu.tiwulfx.table.TableControl#isUseBackgroundTaskToSave()}. Default is FALSE
     */
    public static boolean DEFAULT_USE_BACKGROUND_TASK_TO_SAVE = false;
    /**
     * Global default value for {@link com.panemu.tiwulfx.table.TableControl#isUseBackgroundTaskToDelete()}. Default is FALSE
     */
    public static boolean DEFAULT_USE_BACKGROUND_TASK_TO_DELETE = false;

    /**
     * Default value for {@link com.panemu.tiwulfx.control.NumberField#isNegativeAllowed()} and {@link com.panemu.tiwulfx.table.NumberColumn#isNegativeAllowed()}. Default is false
     */
    public static boolean DEFAULT_NEGATIVE_ALLOWED = false;
    public static int DURATION_FADE_ANIMATION = 300;
    /**
     * Default value is {@link ExportMode#CURRENT_PAGE}. To override the value for particular TableControl call
     * {@link com.panemu.tiwulfx.table.TableControl#setExportMode(com.panemu.tiwulfx.common.ExportMode) TableControl.setExportMode}
     */
    public static ExportMode DEFAULT_EXPORT_MODE = ExportMode.CURRENT_PAGE;
    private static final Logger logger = Logger.getLogger(TiwulFXUtil.class.getName());
    /**
     * The default value is taken from literal.properties file, key: label.null. Default is empty string.
     */
    public static String DEFAULT_NULL_LABEL = TiwulFXUtil.getLiteral("label.null");
    public static boolean DEFAULT_EMPTY_STRING_AS_NULL = true;
    public static boolean DEFAULT_CLOSE_ROW_BROWSER_ON_RELOAD = true;
    public static String DEFAULT_DATE_PROMPTEXT = "";
    private static String applicationId = ".tiwulfx";
    private static String configFileName = "conf.properties";
    private static final LiteralUtil literalUtil = new LiteralUtil(loc);
    private static GraphicFactory graphicFactory = new GraphicFactory();
    /**
     * Default number of rows displayed in {@link com.panemu.tiwulfx.table.TableControl TableControl}. Default is 500
     */
    public static int DEFAULT_TABLE_MAX_ROW = 500;

    private static ExceptionHandlerFactory exceptionHandlerFactory = new DefaultExceptionHandlerFactory();

    public static Locale getLocale() {
        return loc;
    }

    private static String getLocaleDatePattern() {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, loc);
        SimpleDateFormat simpleFormat = (SimpleDateFormat) dateFormat;
        return simpleFormat.toPattern();
    }

    /**
     * Get current DateTimeFormatter.
     * @return
     * @see #setDateFormat(java.lang.String)
     */
    public static DateTimeFormatter getDateFormatForLocalDate() {

        if (dateTimeFormatter == null) {
            dateTimeFormatter = DateTimeFormatter.ofPattern(getLocaleDatePattern());
        }
        return dateTimeFormatter;
    }

    /**
     * Get current DateFormat.
     * @return
     * @see #setDateFormat(java.lang.String)
     */
    public static DateFormat getDateFormatForJavaUtilDate() {
        if (dateFormat == null) {
            dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, getLocale());
        }
        return dateFormat;
    }

    /**
     * Set global default DateFormat
     * @param pattern
     */
    public static void setDateFormat(String pattern) {
        dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        dateFormat = new SimpleDateFormat(pattern);
        if (DEFAULT_DATE_PROMPTEXT == null || DEFAULT_DATE_PROMPTEXT.length() == 0) {
            DEFAULT_DATE_PROMPTEXT = ((SimpleDateFormat) dateFormat).toPattern();
        }
    }

    /**
     * Set active Locale. It will affect date format, decimal format and also language translation.
     * @param loc
     */
    public static void setLocale(Locale loc) {
        TiwulFXUtil.loc = loc;
        dateTimeFormatter = DateTimeFormatter.ofPattern(getLocaleDatePattern());
        literalBundle = literalUtil.changeLocale(loc);
    }

    /**
     * Add Resource Bundle for internationalization. The baseName argument should be a fully qualified class name.
     * <p>
     * For example: if the file name is language.properties and it is located in com.panemu.tiwulfx.res package then the correct value for basename is
     * "com.panemu.tiwulfx.res.language". Don't specify the extension, locale language or locale country.
     * @param baseName fully qualified class name.
     */
    public static void addLiteralBundle(String baseName) {
        literalBundle = literalUtil.addLiteral(baseName);
    }

    /**
     * Get decimal format derived from {@link #setLocale(java.util.Locale) Locale}
     * @return
     */
    public static DecimalFormat getDecimalFormat() {
        NumberFormat nf = NumberFormat.getNumberInstance(loc);
        return (DecimalFormat) nf;
    }

    /**
     * Get literal bundle.
     * @return
     * @see #addLiteralBundle(java.lang.String)
     */
    public static ResourceBundle getLiteralBundle() {
        return literalBundle;
    }

    /**
     * Override default literal bundle which is com.panemu.tiwulfx.res.literal
     * @param literalBundle
     */
    public static void setLiteralBundle(ResourceBundle literalBundle) {
        TiwulFXUtil.literalBundle = literalBundle;
    }

    /**
     * Get language translation of <code>key</code> based on current Locale
     * @param key string key
     * @return
     * @see #setLocale(java.util.Locale)
     */
    public static String getLiteral(String key) {
        try {
            return literalBundle.getString(key);
        } catch (Exception ex) {
            return key;
        }
    }

    /**
     * Get language translation of <code>key</code> based on current Locale with <code>param</code> parameters.
     * @param key
     * @return
     * @see #setLocale(java.util.Locale)
     */
    public static String getLiteral(String key, Object... param) {
        try {
            return MessageFormat.format(literalBundle.getString(key), param);
        } catch (Exception ex) {
            return key;
        }

    }

    /**
     * Convenient way to set tooltip with translated text.
     * @param control control
     * @param key     tooltip text
     */
    public static void setToolTip(Control control, String key) {
        control.setTooltip(new Tooltip(getLiteral(key)));
    }

    /**
     * Convenient way to open file using default operating system app.
     * @param fileToOpen file to open
     * @throws Exception
     */
    public static void openFile(String fileToOpen) throws Exception {
        if (isUnix()) {
            new Thread(() -> {
                try {
                    Desktop.getDesktop().open(new File(fileToOpen));
                } catch (Exception ex) {
                    logger.log(Level.SEVERE, ex.getMessage(), ex);
                }
            }).start();
        } else {
            Desktop.getDesktop().open(new File(fileToOpen));
        }
    }

    private static final String OS = System.getProperty("os.name").toLowerCase();

    /**
     * Check if the app is running on Windows OS
     * @return boolean
     */
    public static boolean isWindows() {
        return (OS.contains("win"));
    }

    /**
     * Check if the app is running on macOS
     * @return boolean
     */
    public static boolean isMac() {
        return (OS.contains("mac"));
    }

    /**
     * Check if the app is running on Linux or Unix
     * @return if the platform is unix, return true
     */
    public static boolean isUnix() {
        return (OS.contains("nix") || OS.contains("nux") || OS.indexOf("aix") > 0);
    }

    /**
     * Check if the app is running on Solaris OS
     * @return if the app is running on Solaris OS
     */
    public static boolean isSolaris() {
        return (OS.contains("sunos"));
    }

    /**
     * Convert LocalDate to Date
     * @param localDate LocalDate
     * @return Date
     */
    public static Date toDate(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        return Date.from(instant);
    }

    /**
     * Convert Date to LocalDate
     * @param date Date
     * @return LocalDate
     */
    public static LocalDate toLocalDate(Date date) {
        if (date == null) {
            return null;
        }
        Instant instant = Instant.ofEpochMilli(date.getTime());
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
    }

    /**
     * Application id will be the folder's name where the configuration file is stored inside user's home folder. The folder will be started with a dot.
     * The default configuration file name is conf.properties.
     * @param id id
     */
    public static void setApplicationId(String id) {
        setApplicationId(id, "conf.properties");
    }

    /**
     * Application id will be the folder's name where the configuration file is stored inside user's home folder.
     * The folder will be started with a dot. The configurationFileName is the filename of the configuration file
     * @param configurationFileName if null or empty default to conf.properties
     * @param id                    id
     */
    public static void setApplicationId(String id, String configurationFileName) {
        if (id != null && !id.contains(File.separator) && !id.contains(" ")) {
            applicationId = id;
        } else {
            throw new RuntimeException("Invalid application ID. It should not contains space and " + File.separator);
        }
        if (!applicationId.startsWith(".")) {
            applicationId = "." + applicationId;
        }
        if (configurationFileName == null || configurationFileName.trim().isEmpty()) {
            configurationFileName = configFileName;
        }

        if (!configurationFileName.contains(File.separator) && !configurationFileName.contains(" ")) {
            configFileName = configurationFileName;
        } else {
            throw new RuntimeException("Invalid configurationFileName. It should not contains space and " + File.separator);
        }

        if (!configFileName.endsWith(".properties")) {
            configFileName = configFileName + ".properties";
        }
        confFile = null;
    }

    /**
     * @return @see #setApplicationId(java.lang.String, java.lang.String)
     */
    public static String getApplicationId() {
        return applicationId;
    }

    public static String getConfigurationPath() throws IOException {
        String home = System.getProperty("user.home");
        String confPath = home + File.separator + applicationId;
        File _confFile = new File(confPath);
        boolean result = false;
        if (!_confFile.exists()) {
            result = _confFile.mkdirs();
        }
        confPath = home + File.separator + applicationId + File.separator + configFileName;
        _confFile = new File(confPath);
        if (!_confFile.exists()) {
            result = _confFile.createNewFile();
        }
        return confPath;
    }

    private static Properties loadProperties() {
        Properties p = new Properties();
        try {
            FileInputStream in = new FileInputStream(getConfigurationPath());
            p.load(in);
            in.close();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return p;

    }

    private static Properties confFile;

    private static Properties getConfigurations() {
        if (confFile == null) {
            confFile = loadProperties();
        }
        return confFile;
    }

    /**
     * 读属性
     * Read property from configuration file. See {@link #setApplicationId(String, String)}
     * <p>
     * @param propName propName
     * @return {@link String}
     */
    public static String readProperty(String propName) {
        return getConfigurations().getProperty(propName);
    }

    /**
     * 删除属性
     * Delete items from configuration file. See {@link #setApplicationId(String, String)}
     * <p>
     * @param propNames 道具名称
     * @throws Exception 异常
     */
    public synchronized static void deleteProperties(List<String> propNames) throws Exception {
        for (String propName : propNames) {
            getConfigurations().remove(propName);
        }
        writePropertiesToFile();
    }

    /**
     * 删除属性
     * Delete an item from configuration file. See {@link #setApplicationId(String, String)}
     * <p>
     * @param propName 道具名字
     * @throws Exception 异常
     */
    public synchronized static void deleteProperties(String propName) throws Exception {
        getConfigurations().remove(propName);
        writePropertiesToFile();
    }

    /**
     * Save values to configuration file. See {@link #setApplicationId(String, String)}
     * <p>
     * @param mapPropertyValue propertyName, Value
     */
    public synchronized static void writeProperties(Map<String, String> mapPropertyValue) throws Exception {
        Set<String> propNames = mapPropertyValue.keySet();
        for (String propName : propNames) {
            String value = mapPropertyValue.get(propName);
            getConfigurations().setProperty(propName, value);
        }
        writePropertiesToFile();
    }

    private synchronized static void writePropertiesToFile() {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(Objects.requireNonNull(getConfigurationPath()));
            getConfigurations().store(out, null);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, ex.getMessage(), ex);
        } finally {
            try {
                assert out != null;
                out.close();
            } catch (IOException ex) {
                logger.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }

    /**
     * 写属性
     * Save property-name's value to configuration file. See {@link #setApplicationId(String, String)}
     * <p>
     * @param propName 名称
     * @param value    值
     * @throws Exception 异常
     */
    public synchronized static void writeProperties(String propName, String value) throws Exception {
        getConfigurations().setProperty(propName, value);
        writePropertiesToFile();
    }

    private static Validator<Date> dateValidator = new FourDigitYearOfDateValidator();

    /**
     * Set global date validator. Default is {@link FourDigitYearOfDateValidator} This validator is used by DateField.
     * @param validator
     */
    public static void setDateValidator(Validator<Date> validator) {
        dateValidator = validator;
    }

    /**
     * Get global date validator. Default is {@link FourDigitYearOfDateValidator} This validator is used by DateField.
     * @return validator
     */
    public static Validator<Date> getDateValidator() {
        return dateValidator;
    }

    /**
     * Get default {@link ExceptionHandler} implementation. This implementation will be used in TiwulFX components when uncaught error happens. To override default implementation,
     * create a custom implementation of {@link ExceptionHandlerFactory} and set it to {@link #setExceptionHandlerFactory(ExceptionHandlerFactory)}
     * <p>
     * @return ExceptionHandler
     */
    public static ExceptionHandler getExceptionHandler() {
        return exceptionHandlerFactory.createExceptionHandler();
    }

    /**
     * Set {@link ExceptionHandlerFactory} to override the default {@link DefaultExceptionHandlerFactory}.
     * <p>
     * @param exceptionHandlerFactory the factory that is used by {@link #getExceptionHandler()}
     */
    public static void setExceptionHandlerFactory(ExceptionHandlerFactory exceptionHandlerFactory) {
        if (exceptionHandlerFactory == null) {
            throw new IllegalArgumentException("ExceptionHandlerFactory cannot be null");
        }
        TiwulFXUtil.exceptionHandlerFactory = exceptionHandlerFactory;
    }

    /**
     * Set default TiwulFX css style to a scene. The default is /com/panemu/tiwulfx/res/tiwulfx.css located inside tiwulfx jar file.
     * @param scene
     */
    public static void setTiwulFXStyleSheet(Scene scene) {
        scene.getStylesheets().add("/com/panemu/tiwulfx/res/tiwulfx.css");
    }

    /**
     * Set a class that responsible to provide graphics for TiwulFX UI components. Developer can override the default by extending {@link GraphicFactory} class and use the instance
     * as a parameter to call this method at the beginning of application launching process.
     * @param graphicFactory
     */
    public static void setGraphicFactory(GraphicFactory graphicFactory) {
        TiwulFXUtil.graphicFactory = graphicFactory;
    }

    /**
     * Get currently used {@link GraphicFactory} instance
     * @return
     */
    public static GraphicFactory getGraphicFactory() {
        return graphicFactory;
    }

    /**
     * Attach shortcut to increase or decrease date on dateField. It uses {@link DateEventHandler} class
     * <p>
     * @param dateField
     * @param dateController
     */
    public static void attachShortcut(DatePicker dateField, LocalDateFieldController dateController) {
        dateField.addEventFilter(KeyEvent.KEY_PRESSED, new DateEventHandler(dateField, dateController));
    }

    /**
     * Attach window listener to the window that own the {@code uiNode}. This method will wait until <code>uiNode</code> has a scene and the scene has a window. Then it will attach
     * the {@code listener} to the window object.
     * <pre>
     * <code>TiwulFXUtil.attachWindowListener(this, new WindowEventListener() {
     * 	   public void onWindowShown(WindowEvent event) {
     *       generateData(154);
     *       }
     *
     * 	   public void onWindowCloseRequest(WindowEvent event) {
     * 	      Answer answer = MessageDialogBuilder.confirmation().message("Are you sure to exit the application?").show(getScene().getWindow());
     * 	      if (answer != Answer.YES_OK) {
     * 	         event.consume();
     *          }
     *       }
     *
     *    });
     * </code>
     * </pre>
     * @param uiNode   node
     * @param listener WindowEventListener
     */
    public static void attachWindowListener(Node uiNode, WindowEventListener listener) {
        if (uiNode.getScene() == null) {
            uiNode.sceneProperty().addListener((ov, oldVal, newVal) -> {
                if (newVal != null) {
                    attachSceneListener(newVal, listener);
                }
            });
        } else {
            attachSceneListener(uiNode.getScene(), listener);
        }
    }

    private static void attachSceneListener(Scene scene, WindowEventListener listener) {
        if (scene.getWindow() == null) {
            scene.windowProperty().addListener((ov, oldVal, window) -> {
                if (window != null) {
                    attachWindowListener(window, listener);
                }
            });
        } else {
            attachWindowListener(scene.getWindow(), listener);
        }
    }

    private static void attachWindowListener(Window window, WindowEventListener listener) {
        window.addEventHandler(WindowEvent.ANY, (WindowEvent t) -> {
            if (t.getEventType() == WindowEvent.WINDOW_CLOSE_REQUEST) {
                listener.onWindowCloseRequest(t);
            } else if (t.getEventType() == WindowEvent.WINDOW_HIDDEN) {
                listener.onWindowHidden(t);
            } else if (t.getEventType() == WindowEvent.WINDOW_HIDING) {
                listener.onWindowHiding(t);
            } else if (t.getEventType() == WindowEvent.WINDOW_SHOWING) {
                listener.onWindowShowing(t);
            } else if (t.getEventType() == WindowEvent.WINDOW_SHOWN) {
                listener.onWindowShown(t);
            }
        });
    }

}
