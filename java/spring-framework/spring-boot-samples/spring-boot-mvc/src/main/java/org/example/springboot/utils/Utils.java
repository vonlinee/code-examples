package org.example.springboot.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Utils {

    public static String nowTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    public static void delay(int seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将异常转为字符串
     * @param t
     * @return
     */
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
                return (String) var5;
            } catch (IOException var34) {
                return "";
            }
        }
    }
}
