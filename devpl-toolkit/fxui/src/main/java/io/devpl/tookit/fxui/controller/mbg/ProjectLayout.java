package io.devpl.tookit.fxui.controller.mbg;

import java.io.File;

public interface ProjectLayout {

    ProjectLayout MAVEN = new ProjectLayout() {

        @Override
        public String name() {
            return "MAVEN";
        }

        @Override
        public String chooseDirectory(String projectRoot, String... pathSegments) {
            return projectRoot + File.separator + pathSegments[0];
        }

        @Override
        public boolean support(String... pathSegments) {
            return false;
        }
    };

    String name();

    /**
     * @param projectRoot  项目根路径
     * @param pathSegments 路径片段，定义成可变参数增加灵活性
     * @return 存放的目录
     */
    String chooseDirectory(String projectRoot, String... pathSegments);

    boolean support(String... pathSegments);
}
