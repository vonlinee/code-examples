package org.mybatis.generator.utils;

/**
 * The result of a parse operation
 */
class Result {
    private final WindowsPathType type;

    private final String root;

    private final String path;

    Result(WindowsPathType type, String root, String path) {
        this.type = type;
        this.root = root;
        this.path = path;
    }

    /**
     * The path type
     */
    WindowsPathType type() {
        return type;
    }

    /**
     * The root component
     */
    String root() {
        return root;
    }

    /**
     * The normalized path (includes root)
     */
    String path() {
        return path;
    }
}