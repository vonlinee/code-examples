package io.maker.extension.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * dom4j会将整个xml加载至内存，并解析成一个document对象，但是可能会造成内存溢出现象。
 * <p>
 * 　　Document：表示整个xml文档。文档Document对象是通常被称为DOM树。
 * <p>
 * 　　Element：表示一个xml元素。Element对象有方法来操作其子元素，它的文本，属性和名称空间
 * <p>
 * 　　Attribute：表示元素的属性。属性有方法来获取和设置属性的值。它有父节点和属性类型。
 * <p>
 * 　　Node：代表元素，属性或者处理指令。
 */
public class Dom4jXMLSerializer {

    private static final Logger log = LoggerFactory.getLogger(Dom4jXMLSerializer.class);

    public static void main(String[] args) {

    }

    //${abc}正则
    public static String varRegex = "\\$\\{\\s*(\\w+)\\s*(([\\+\\-])\\s*(\\d+)\\s*)?\\}";

    /**
     * xml解析成document对象
     * @param xml
     * @return
     */
    public Document getDocument(String xml) {
        StringReader stringReader = new StringReader(xml);
        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            document = saxReader.read(stringReader);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document;
    }

    /**
     * xml与bean的相互转换
     * @param element
     * @param direction 1：java2xml，2：xml2java
     * @param obj
     */
    public void parseXml(Element element, String direction, Object obj) {
        //获取当前元素的所有子节点（在此我传入根元素）
        List<Element> elements = element.elements();
        //判断是否有子节点
        if (elements != null && elements.size() > 0) {
            //进入if说明有子节点
            for (Element e : elements) {
                //判断转换方向（1：java2xml；2：xml2java）
                if ("2".equals(direction)) { //这里是xml转bean
                    //声明Field
                    Field field = null;
                    try {
                        //反射获取属性
                        field = obj.getClass().getDeclaredField(e.getName());
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    //获取当前属性是否为list
                    if (field != null && List.class.getName().equals(field.getType().getName())) {
                        //反射获取set方法
                        Method method = this.getDeclaredMethod(obj, "set".concat(this.toUpperCaseFirstOne(e.getName())), new Class[]{List.class});
                        //声明临时list
                        List<Object> temList = new ArrayList<>();
                        if (method != null) {
                            try {
                                //反射调用obj的当前方法，可变参数为templist
                                method.invoke(obj, temList);
                            } catch (Exception e1) {
                                log.info("【{}】方法执行失败", method, e1);
                            }
                        }
                        //获取List的泛型参数类型
                        Type gType = field.getGenericType();
                        //判断当前类型是否为参数化泛型
                        if (gType instanceof ParameterizedType) {
                            //转换成ParameterizedType对象
                            ParameterizedType pType = (ParameterizedType) gType;
                            //获得泛型类型的泛型参数（实际类型参数)
                            Type[] tArgs = pType.getActualTypeArguments();
                            if (tArgs != null && tArgs.length > 0) {
                                //获取当前元素的所有子元素
                                List<Element> elementSubList = e.elements();
                                //遍历
                                for (Element e1 : elementSubList) {
                                    try {
                                        //反射创建对象
                                        Object tempObj = Class.forName(tArgs[0].getTypeName()).newInstance();
                                        temList.add(tempObj);
                                        //递归调用自身
                                        this.parseXml(e1, direction, tempObj);
                                    } catch (Exception e2) {
                                        log.error("【{}】对象构造失败", tArgs[0].getTypeName(), e2);
                                    }
                                }

                            }
                        }
                    } else {
                        //说明不是list标签，继续递归调用自身即可
                        this.parseXml(e, direction, obj);
                    }
                } else if ("1".equals(direction)) { //说明转换方向为：javabean转xml
                    //递归调用自身
                    this.parseXml(e, direction, obj);
                }
                //此时还在for循环遍历根元素的所有子元素
            }
        } else {
            //说明无子节点
            //获取当前元素的名称
            String nodeName = element.getName();
            //获取当前元素的对应的值
            String nodeValue = element.getStringValue();

            //判断转换方向：1：java2xml、2：xml2java
            if ("1".equals(direction))//java2xml
            {
                if (nodeValue != null && nodeValue.matches(varRegex)) {
                    /**
                     * 获取模板中各节点定义的变量名，例如<traceNo>${traceNo}</traceNo>
                     */
                    nodeValue = nodeValue.substring(nodeValue.indexOf("${") + 2, nodeValue.indexOf("}"));
                    Object value = null;
                    //根据解析出的变量名，调用obj对象的getXXX()方法获取变量值
                    Method method = this.getDeclaredMethod(obj, "get".concat(this.toUpperCaseFirstOne(nodeValue)), null);
                    if (method != null) {
                        try {
                            value = method.invoke(obj);
                        } catch (Exception e) {
                            log.error("方法【{}】调用异常", "get".concat(this.toUpperCaseFirstOne(nodeValue)));
                        }
                    }
                    //将变量值填充至xml模板变量名位置，例如<traceNo>${traceNo}</traceNo>
                    element.setText(value == null ? "" : value.toString());
                }
                //叶子节点
                log.debug("节点名【{}】，节点变量名【{}】", element.getName(), nodeValue);
            } else if ("2".equals(direction))//xml2java
            {
                if (nodeName != null && !"".equals(nodeName)) {
                    //根据xml节点名，调用obj对象的setXXX()方法为obj设置变量值
                    Method method = this.getDeclaredMethod(obj, "set".concat(this.toUpperCaseFirstOne(nodeName)), new Class[]{String.class});
                    if (method != null) {
                        try {
                            method.invoke(obj, nodeValue);
                        } catch (Exception e) {
                            log.error("方法【{}】调用异常", "set".concat(this.toUpperCaseFirstOne(nodeName)));
                        }
                    }
                }
            }
        }
    }

    private Method getDeclaredMethod(Object object, String methodName, Class<?>[] parameterTypes) {
        for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException e) {
                //Method 不在当前类定义, 继续向上转型
            }
        }
        return null;
    }

    private String toUpperCaseFirstOne(String s) {
        // 进行字母的ascii编码前移，效率要高于截取字符串进行转换的操作
        char[] cs = s.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }
}
