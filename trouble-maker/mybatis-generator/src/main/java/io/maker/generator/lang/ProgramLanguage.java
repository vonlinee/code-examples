package io.maker.generator.lang;

/**
 * 编程语言
 */
public enum ProgramLanguage {

    JAVA("Java"),
    C("C"),
    CPLUSPLUS("C++"),
    RUST("Rust"),
    GOLANG("Golang"),
    PYTHON("Python");

    private final String name;

    ProgramLanguage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
