package com.tianjian.loglibrary;


public final class LogHelper {

    public static final int DEBUG = 3;
    public static final int ERROR = 6;
    public static final int ASSERT = 7;
    public static final int INFO = 4;
    public static final int VERBOSE = 2;
    public static final int WARN = 5;
    private static final String DEFAULT_TAG = "my_log";
    private static Printer printer = new LogD();


    public static LogE init() {
        return init("my_log");
    }

    public static LogE init(String var0) {
        printer = new LogD();
        return printer.init(var0);
    }

    public static void resetSettings() {
        printer.resetSettings();
    }

    public static Printer t(String var0) {
        return printer.t(var0, printer.getSettings().a1());
    }

    public static Printer t(int var0) {
        return printer.t((String) null, var0);
    }

    public static Printer t(String var0, int var1) {
        return printer.t(var0, var1);
    }

    public static void log(int var0, String var1, String var2, Throwable var3) {
        printer.log(var0, var1, var2, var3);
    }

    public static void d(String var0, Object... var1) {
        printer.d(var0, var1);
    }

    public static void d(Object var0) {
        printer.d(var0);
    }

    public static void e(String var0, Object... var1) {
        printer.e((Throwable) null, var0, var1);
    }

    public static void e(Throwable var0, String var1, Object... var2) {
        printer.e(var0, var1, var2);
    }

    public static void i(String var0, Object... var1) {
        printer.i(var0, var1);
    }

    public static void v(String var0, Object... var1) {
        printer.v(var0, var1);
    }

    public static void w(String var0, Object... var1) {
        printer.w(var0, var1);
    }

    public static void wtf(String var0, Object... var1) {
        printer.wtf(var0, var1);
    }

    public static void json(String var0) {
        printer.json(var0);
    }

    public static void xml(String var0) {
        printer.xml(var0);
    }

}
