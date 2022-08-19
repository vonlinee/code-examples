package io.devpl.commons.web.rest;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;

public abstract class AbstractResult<T> implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8939479680323102941L;
	
	protected int code;
	protected String msg;
	protected T data;
	
	/**
	 * 时间戳
	 */
    protected long timestamp;
    
    /**
     * 异常调用栈
     */
    protected String exceptionStack;

    /**
     * 序列化
     * @return
     */
    protected abstract String serialize();

    public void setCode(int code) {
    	this.code = code;
    }
    
    public final int getCode() {
    	return code;
    }
    
    public final String getMessage() {
    	return msg;
    }
    
    public void setMessage(String message) {
    	this.msg = message;
    }

    public static String throwToStr(Throwable t) {
        if (t == null) {
            return null;
        } else {
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
                return (String)var5;
            } catch (IOException var34) {
                return "";
            }
        }
    }
}
