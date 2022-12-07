package io.devpl.codegen.fxui.utils.maven;

import lombok.Data;

import javax.xml.bind.annotation.*;

@Data
@XmlRootElement(name = "dependency")
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
public class MavenDependency {

    @XmlElement(name = "groupId")
    private String groupId;

    @XmlElement(name = "artifactId")
    private String artifactId;

    @XmlElement(name = "version")
    private String version;
}
