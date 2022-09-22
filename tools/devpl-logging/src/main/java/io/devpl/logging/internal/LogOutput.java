package io.devpl.logging.internal;

/**
 * 日志输出位置
 */
public enum LogOutput {

    /**
     * 输出到控制台
     */
    CONSOLE,

    /**
     * 输出到文件
     */
    FILE,

    /**
     * 输出到数据库
     */
    DB,

    /**
     * 输出到消息队列
     */
    MQ
}
