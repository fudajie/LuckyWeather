package com.tianjian.loglibrary;


public final class LogE {

    private int anInt = 1;
    private boolean aBoolean = false;
    private int anInt1 = 1;
    private LogC mLogC;
    private LogLevel mLogLevel;


    public LogE() {
        this.mLogLevel = LogLevel.FULL;
    }

    public int a1() {
        return this.anInt;
    }

    public boolean a2() {
        return this.aBoolean;
    }

    public LogLevel a3() {
        return this.mLogLevel;
    }

    public int b4() {
        return this.anInt1;
    }

    public LogC a5() {
        if (this.mLogC == null) {
            this.mLogC = new LogA();
        }

        return this.mLogC;
    }

    public void reset() {
        this.anInt = 2;
        this.anInt1 = 0;
        this.aBoolean = true;
        this.mLogLevel = LogLevel.FULL;
    }
}
