/*
 * GPLv3 License
 *
 * Copyright (c) 2023-2025 4ra1n (Jar Analyzer Team)
 *
 * This project is distributed under the GPLv3 license.
 *
 * https://github.com/jar-analyzer/jar-analyzer/blob/master/LICENSE
 */

package me.n1ar4.log;

public class LogUtil {
    private static final int STACK_TRACE_INDEX = 5;

    public static String getClassName() {
        String fullClassName = Thread.currentThread()
                .getStackTrace()[STACK_TRACE_INDEX].getClassName();
        int lastDotIndex = fullClassName.lastIndexOf('.');
        if (lastDotIndex != -1) {
            return fullClassName.substring(lastDotIndex + 1);
        } else {
            return fullClassName;
        }
    }

    public static String getMethodName() {
        return Thread.currentThread()
                .getStackTrace()[STACK_TRACE_INDEX].getMethodName();
    }

    public static String getLineNumber() {
        return String.valueOf(Thread.currentThread()
                .getStackTrace()[STACK_TRACE_INDEX].getLineNumber());
    }
}
