package io.maker.extension.freemarker;

import freemarker.cache.*;
import freemarker.template.*;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FreeMarker {

    private static final Version VERSION = Configuration.VERSION_2_3_31;

    private final Configuration config;

    public FreeMarker() {
        this.config = new Configuration(VERSION);
        config.setDefaultEncoding("UTF-8"); //这个一定要设置，不然在生成的页面中会乱码
        config.setObjectWrapper(new DefaultObjectWrapper(VERSION));
    }

    public FreeMarker(File templateLocation) throws IOException {
        this();
        TemplateLoader[] loaders = new TemplateLoader[]{new FileTemplateLoader(), new ClassTemplateLoader(), new ByteArrayTemplateLoader()};
        this.config.setTemplateLoader(new MultiTemplateLoader(loaders));
        try {
            config.setDirectoryForTemplateLoading(templateLocation);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final Map<String, FileTemplateLoader> fileTemplateLoaders = new LinkedHashMap<>();

    public void addTemplateLocation(File dir) {
        try {
            FileTemplateLoader loader = new FileTemplateLoader(dir);
            fileTemplateLoaders.put(dir.getAbsolutePath(), loader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Template getTemplate(String name) throws IOException {
        Template template = config.getTemplate(name);
        if (template == null) {
            for (FileTemplateLoader loader : fileTemplateLoaders.values()) {
                try {
                    File templateSource = (File) loader.findTemplateSource(name);
                    if (templateSource != null) {
                        template = new Template(name, name, new BufferedReader(new FileReader(templateSource)), config);
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return template;
    }

    public File process(String templateName, Object dataModel, File output, Charset charset) {
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(output), charset)) {
            Template template = getTemplate(templateName);
            template.process(dataModel, writer);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
        return output;
    }

    public File process(String templateName, Map<String, Object> dataModel, File output) {
        return process(templateName, dataModel, output, StandardCharsets.UTF_8);
    }

    public File process(String templateName, Object dataModel, File output) {
        return process(templateName, dataModel, output, StandardCharsets.UTF_8);
    }

    public static void main(String[] args) throws IOException {
        String projectRootPath = new File("").getAbsolutePath();
        Path path = Paths.get(projectRootPath, "mybatis-generator", "src/main/resources/templates/freemarker");

        FreeMarker freeMarker = new FreeMarker(path.toFile());

        File file = freeMarker.process("static", prepareData(), new File("success.html"));
        System.out.println("恭喜，生成成功~~");
    }

    private static Map<String, Object> prepareData() {
        //创建一个合适的Configration对象  获取或创建一个模版。
        Map<String, Object> paramMap = new HashMap<>();
        //自定义标签解析
        paramMap.put("content", new ContentDirective());
        paramMap.put("description", "我正在学习使用Freemarker生成静态文件！");
        List<String> nameList = new ArrayList<String>();
        nameList.add("陈靖仇");
        nameList.add("玉儿");
        nameList.add("宇文拓");
        paramMap.put("nameList", nameList);
        Map<String, Object> weaponMap = new HashMap<String, Object>();
        weaponMap.put("first", "轩辕剑");
        weaponMap.put("second", "崆峒印");
        weaponMap.put("third", "女娲石");
        weaponMap.put("fourth", "神农鼎");
        weaponMap.put("fifth", "伏羲琴");
        weaponMap.put("sixth", "昆仑镜");
        weaponMap.put("seventh", null);
        paramMap.put("weaponMap", weaponMap);
        return paramMap;
    }

    public static Version getVersion() {
        return VERSION;
    }
}