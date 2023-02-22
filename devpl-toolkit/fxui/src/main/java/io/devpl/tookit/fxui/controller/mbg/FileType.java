package io.devpl.tookit.fxui.controller.mbg;

public enum FileType {

    JAVA("java"),
    XML("xml");

    final String extension;

    FileType(String extension) {
        this.extension = extension;
    }
}
