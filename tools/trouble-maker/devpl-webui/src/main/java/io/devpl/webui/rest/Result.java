package io.devpl.webui.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;

/**
 * 抽象结果类
 * @param <T>
 */
public abstract class Result<T> implements Serializable {

    /**
     * 是否输出异常栈到返回结果
     */
    protected static final boolean STACK_TRACE_ENABLED = true;

    public Result() {
        this(System.currentTimeMillis());
    }

    public Result(long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * 结果生成的时间戳
     */
    protected final long timestamp;

    public long getTimestamp() {
        return timestamp;
    }

    public static String throwToStr(Throwable t) {
        if (t == null) {
            return null;
        }
        try {
            StringWriter sw = new StringWriter();
            Throwable var2 = null;
            Object var5;
            try {
                PrintWriter pw = new PrintWriter(sw, true);
                Throwable var4 = null;
                try {
                    t.printStackTrace(pw);
                    pw.flush();
                    sw.flush();
                    var5 = sw.getBuffer().toString();
                } catch (Throwable var30) {
                    var5 = var30;
                    var4 = var30;
                    throw var30;
                } finally {
                    if (pw != null) {
                        if (var4 != null) {
                            try {
                                pw.close();
                            } catch (Throwable var29) {
                                var4.addSuppressed(var29);
                            }
                        } else {
                            pw.close();
                        }
                    }
                }
            } catch (Throwable var32) {
                var2 = var32;
                throw var32;
            } finally {
                if (sw != null) {
                    if (var2 != null) {
                        try {
                            sw.close();
                        } catch (Throwable var28) {
                            var2.addSuppressed(var28);
                        }
                    } else {
                        sw.close();
                    }
                }
            }
            return (String) var5;
        } catch (IOException var34) {
            return "";
        }
    }
}
