package com.tianjian.loglibrary;

import android.util.Log;

class LogA implements LogC {

    public void a(String var1, String var2) {
        Log.d(var1, var2);
    }

    public void b(String var1, String var2) {
        Log.e(var1, var2);
    }

    public void c(String var1, String var2) {
        Log.w(var1, var2);
    }

    public void d(String var1, String var2) {
        Log.i(var1, var2);
    }

    public void e(String var1, String var2) {
        Log.v(var1, var2);
    }

    public void f(String var1, String var2) {
        Log.wtf(var1, var2);
    }
}
