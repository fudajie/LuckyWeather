package com.dajie.luckyweather.logic.network;

import com.tianjian.loglibrary.LogHelper;

/**
 * Created by DaJie on 2020/4/14
 */
public class OkgoLog {
    private static boolean d = true;
    public static String tag = "OkGo";

    public static void a(String var0, boolean var1) {
        tag = var0;
        d = var1;
        LogHelper.init(tag);
    }

    public static void d(String var0) {
        if (d) {
            LogHelper.d(var0);
        }

    }

    public static void a(String var0) {
        if (d) {
            LogHelper.i(var0, new Object[0]);
        }

    }

    public static void json(String var0) {
        if (d) {
            LogHelper.json(var0);
        }

    }

    public static void e(String var0) {
        if (d) {
            LogHelper.e(var0, new Object[0]);
        }

    }

    public static void a(Throwable var0) {
        if (d) {
            var0.printStackTrace();
        }

    }
}
