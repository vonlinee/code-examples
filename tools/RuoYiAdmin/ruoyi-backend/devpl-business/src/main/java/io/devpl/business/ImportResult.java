package io.devpl.business;

import java.util.List;

/**
 * 导入结果
 */
public class ImportResult<T> {

    /**
     * 导入成功条数
     */
    private int successCount;

    /**
     * 导入失败条数
     */
    private int failureCount;

    /**
     * 从Excel成功导入的数据，用于批量添加到数据库中
     */
    private List<T> dataList;

    /**
     * 导入失败信息，每行一个字符串
     */
    private List<String> errMsg;
}
