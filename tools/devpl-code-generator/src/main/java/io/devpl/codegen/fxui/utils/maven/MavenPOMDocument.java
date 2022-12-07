package io.devpl.codegen.fxui.utils.maven;

import lombok.Data;

import javax.xml.bind.annotation.*;

/**
 * jaxb: <a href="https://zhuanlan.zhihu.com/p/343893930">...</a>
 */
@Data
@XmlRootElement(name = "project") // 将类或枚举类型映射到XML根元素。当使用@XmlRootElement时，其值在XML文档中表示为XML根元素。
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
public class MavenPOMDocument {

}
