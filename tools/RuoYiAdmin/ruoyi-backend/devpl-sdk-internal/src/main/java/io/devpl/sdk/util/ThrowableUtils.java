package io.devpl.sdk.util;

import java.util.ArrayList;
import java.util.List;

public class ThrowableUtils {

    /**
     * 过滤栈帧
     * @param throwable
     * @param ignorePackagePrefix
     * @return
     */
    public static StackTraceElement[] getStackTrace(Throwable throwable, String ignorePackagePrefix) {
        List<StackTraceElement> stackTraceElementList = new ArrayList<>();
        StackTraceElement[] stackTraceElements = throwable.getStackTrace();
        for (int i = 0; i < stackTraceElements.length; i++) {
            if (stackTraceElements[i].getClassName().startsWith(ignorePackagePrefix)) {
                continue;
            }
            stackTraceElementList.add(stackTraceElements[i]);
        }
        return stackTraceElementList.toArray(new StackTraceElement[0]);
    }
}
