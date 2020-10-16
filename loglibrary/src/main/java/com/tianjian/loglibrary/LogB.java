package com.tianjian.loglibrary;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;

final class LogB {

    static boolean isEmpty(CharSequence var0) {
        return var0 == null || var0.length() == 0;
    }

    static boolean equals(CharSequence var0, CharSequence var1) {
        if (var0 == var1) {
            return true;
        } else {
            if (var0 != null && var1 != null) {
                int var2 = var0.length();
                if (var2 == var1.length()) {
                    if (var0 instanceof String && var1 instanceof String) {
                        return var0.equals(var1);
                    }

                    for (int var3 = 0; var3 < var2; ++var3) {
                        if (var0.charAt(var3) != var1.charAt(var3)) {
                            return false;
                        }
                    }

                    return true;
                }
            }

            return false;
        }
    }

    static String getStackTraceString(Throwable var0) {
        if (var0 == null) {
            return "";
        } else {
            for (Throwable var1 = var0; var1 != null; var1 = var1.getCause()) {
                if (var1 instanceof UnknownHostException) {
                    return "";
                }
            }

            StringWriter var2 = new StringWriter();
            PrintWriter var3 = new PrintWriter(var2);
            var0.printStackTrace(var3);
            var3.flush();
            return var2.toString();
        }
    }
}
