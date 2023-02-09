package samples.shape.svg;

import javafx.scene.layout.Region;
import javafx.scene.shape.SVGPath;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author guolinyuan
 */
public class SvgUtil {
    static {
        svgs = new HashMap<>();
        regions = new HashMap<>();
        loadGlyphToMM("samples/shape/svg/colors.svg");
    }

    private final static Map<String, SVGPath> svgs;
    private final static Map<String, Region> regions;
    private static final String defaultStyle = "-fx-background-color: #333333;-fx-pref-width: 15;-fx-pref-height: 15;-fx-scale-y: -1";

    /**
     * 将目标文件加载到内存中,并且转化为SVGPath对象
     * 可以通过{@link SvgUtil#getSvg(String)}访问到
     * 适用于glyph标签,"svg/iconfont.svg"
     */
    public static void loadGlyphToMM(String pathName) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            // 禁止DTD验证,防止网络阻塞
            builder.setEntityResolver((publicId, systemId) -> new InputSource(new StringReader("")));
            Document d = builder.parse(SvgUtil.class.getClassLoader().getResourceAsStream(pathName));
            NodeList list = d.getElementsByTagName("glyph");
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                String content = node.getAttributes().getNamedItem("d").getNodeValue();
                String name = node.getAttributes().getNamedItem("glyph-name").getNodeValue();
                SVGPath path = new SVGPath();
                path.setContent(content);
                svgs.put(name, path);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从单个svg文件的svg/path中读取path,
     * 文件名作为svg的key,需要拓展名为.svg结尾
     * @param pathName
     */
    public static void loadPathToMM(String pathName) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            // 禁止DTD验证,防止网络阻塞
            builder.setEntityResolver((publicId, systemId) -> new InputSource(new StringReader("")));
            Document d = builder.parse(SvgUtil.class.getClassLoader().getResourceAsStream(pathName));
            Node node = d.getElementsByTagName("path").item(0);
            String content = node.getAttributes().getNamedItem("d").getNodeValue();
            String name = pathName.substring(pathName.lastIndexOf("/") + 1, pathName.length() - 4);
            SVGPath path = new SVGPath();
            path.setContent(content);
            svgs.put(name, path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SVGPath getSvg(String name) {
        return svgs.get(name);
    }

    /**
     * 获取到默认的样式的region包裹的svg
     * 默认样式包含背景色,Y轴翻转,20x20的大小
     * 样式有缺陷时,可以调用重载方法完成
     * @param name
     * @return
     */
    public static Region getRegion(String name) {
        Region region = regions.get(name);
        if (region == null) {
            SVGPath path = getSvg(name);
            if (path != null) {
                region = new Region();
                region.setShape(path);
                region.setStyle(defaultStyle);
                return region;
            }
            return null;
        }
        return region;
    }

    public static Region getRegion(String name, String style) {
        Region region = getRegion(name);
        if (region != null) {
            region.setStyle(style);
            return region;
        }
        return null;
    }
}