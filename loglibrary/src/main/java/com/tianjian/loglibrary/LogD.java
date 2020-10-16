package com.tianjian.loglibrary;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

final class LogD implements Printer {

    private String tag;
    private final ThreadLocal<String> a = new ThreadLocal();
    private final ThreadLocal<Integer> b = new ThreadLocal();
    private final LogE mLogE = new LogE();


    public LogD() {
        this.init("my_log");
    }

    public LogE init(String var1) {
        if (var1 == null) {
            throw new NullPointerException("tag may not be null");
        } else if (var1.trim().length() == 0) {
            throw new IllegalStateException("tag may not be empty");
        } else {
            this.tag = var1;
            return this.mLogE;
        }
    }

    public LogE getSettings() {
        return this.mLogE;
    }

    public Printer t(String var1, int var2) {
        if (var1 != null) {
            this.a.set(var1);
        }

        this.b.set(Integer.valueOf(var2));
        return this;
    }

    public void d(String var1, Object... var2) {
        this.a(3, (Throwable) null, var1, var2);
    }

    public void d(Object var1) {
        String var2;
        if (var1.getClass().isArray()) {
            var2 = Arrays.deepToString((Object[]) ((Object[]) var1));
        } else {
            var2 = var1.toString();
        }

        this.a(3, (Throwable) null, var2, new Object[0]);
    }

    public void e(String var1, Object... var2) {
        this.e((Throwable) null, var1, var2);
    }

    public void e(Throwable var1, String var2, Object... var3) {
        this.a(6, var1, var2, var3);
    }

    public void w(String var1, Object... var2) {
        this.a(5, (Throwable) null, var1, var2);
    }

    public void i(String var1, Object... var2) {
        this.a(4, (Throwable) null, var1, var2);
    }

    public void v(String var1, Object... var2) {
        this.a(2, (Throwable) null, var1, var2);
    }

    public void wtf(String var1, Object... var2) {
        this.a(7, (Throwable) null, var1, var2);
    }

    public void json(String var1) {
        if (LogB.isEmpty(var1)) {
            this.d("Empty/Null json content");
        } else {
            try {
                var1 = var1.trim();
                JSONObject var2;
                String var3;
                if (var1.contains("{")) {
                    var2 = new JSONObject(var1.substring(var1.indexOf("{")));
                    var3 = var2.toString(2);
                    this.d(var1.substring(0, var1.indexOf("{")) + "\n" + var3);
                    return;
                }

                if (var1.contains("[")) {
                    var2 = new JSONObject(var1.substring(var1.indexOf("[")));
                    var3 = var2.toString(2);
                    this.d(var1.substring(0, var1.indexOf("[")) + "\n" + var3);
                    return;
                }

                this.d(var1);
            } catch (JSONException var4) {
                this.d(var1);
            }

        }
    }

    public void xml(String var1) {
        if (LogB.isEmpty(var1)) {
            this.d("Empty/Null xml content");
        } else {
            try {
                StreamSource var2 = new StreamSource(new StringReader(var1));
                StreamResult var3 = new StreamResult(new StringWriter());
                Transformer var4 = TransformerFactory.newInstance().newTransformer();
                var4.setOutputProperty("indent", "yes");
                var4.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                var4.transform(var2, var3);
                this.d(var3.getWriter().toString().replaceFirst(">", ">\n"));
            } catch (TransformerException var5) {
                this.e("Invalid xml", new Object[0]);
            }

        }
    }

    public synchronized void log(int var1, String var2, String var3, Throwable var4) {
        if (this.mLogE.a3() != LogLevel.NONE) {
            if (var4 != null && var3 != null) {
                var3 = var3 + " : " + LogB.getStackTraceString(var4);
            }

            if (var4 != null && var3 == null) {
                var3 = LogB.getStackTraceString(var4);
            }

            if (var3 == null) {
                var3 = "No message/exception is set";
            }

            int var5 = this.a();
            if (LogB.isEmpty(var3)) {
                var3 = "Empty/NULL log message";
            }

            this.a(var1, var2);
            this.a(var1, var2, var5);
            byte[] var6 = var3.getBytes();
            int var7 = var6.length;
            if (var7 <= 4000) {
                if (var5 > 0) {
                    this.c(var1, var2);
                }

                this.a(var1, var2, var3);
                this.b(var1, var2);
            } else {
                if (var5 > 0) {
                    this.c(var1, var2);
                }

                for (int var8 = 0; var8 < var7; var8 += 4000) {
                    int var9 = Math.min(var7 - var8, 4000);
                    this.a(var1, var2, new String(var6, var8, var9));
                }

                this.b(var1, var2);
            }
        }
    }

    public void resetSettings() {
        this.mLogE.reset();
    }

    private synchronized void a(int var1, Throwable var2, String var3, Object... var4) {
        if (this.mLogE.a3() != LogLevel.NONE) {
            String var5 = this.getTag();
            String var6 = this.a(var3, var4);
            this.log(var1, var5, var6, var2);
        }
    }

    private void a(int var1, String var2) {
        this.b(var1, var2,
                "╔════════════════════════════════════════════════════════════════════════════════════════");
    }

    private void a(int var1, String var2, int var3) {
        StackTraceElement[] var4 = Thread.currentThread().getStackTrace();
        if (this.mLogE.a2()) {
            this.b(var1, var2, "║ Thread: " + Thread.currentThread().getName());
            this.c(var1, var2);
        }

        String var5 = "";
        int var6 = this.a(var4) + this.mLogE.b4();
        if (var3 + var6 > var4.length) {
            var3 = var4.length - var6 - 1;
        }

        for (int var7 = var3; var7 > 0; --var7) {
            int var8 = var7 + var6;
            if (var8 < var4.length) {
                StringBuilder var9 = new StringBuilder();
                var9.append("║ ").append(var5).append(this.a(var4[var8].getClassName())).append(
                        ".").append(var4[var8].getMethodName()).append(" ").append(" (").append(var4[var8].getFileName()).append(":").append(var4[var8].getLineNumber()).append(")");
                var5 = var5 + "   ";
                this.b(var1, var2, var9.toString());
            }
        }

    }

    private void b(int var1, String var2) {
        this.b(var1, var2,
                "╚════════════════════════════════════════════════════════════════════════════════════════");
    }

    private void c(int var1, String var2) {
        this.b(var1, var2,
                "╟────────────────────────────────────────────────────────────────────────────────────────");
    }

    private void a(int var1, String var2, String var3) {
        String[] var4 = var3.split(System.getProperty("line.separator"));
        String[] var5 = var4;
        int var6 = var4.length;

        for (int var7 = 0; var7 < var6; ++var7) {
            String var8 = var5[var7];
            this.b(var1, var2, "║ " + var8);
        }

    }

    private void b(int var1, String var2, String var3) {
        String var4 = this.b(var2);
        switch (var1) {
            case 2:
                this.mLogE.a5().e(var4, var3);
                break;
            case 3:
            default:
                this.mLogE.a5().a(var4, var3);
                break;
            case 4:
                this.mLogE.a5().d(var4, var3);
                break;
            case 5:
                this.mLogE.a5().c(var4, var3);
                break;
            case 6:
                this.mLogE.a5().b(var4, var3);
                break;
            case 7:
                this.mLogE.a5().f(var4, var3);
        }

    }

    private String a(String var1) {
        int var2 = var1.lastIndexOf(".");
        return var1.substring(var2 + 1);
    }

    private String b(String var1) {
        return !LogB.isEmpty(var1) && !LogB.equals(this.tag, var1) ? this.tag + "-" + var1 :
                this.tag;
    }

    private String getTag() {
        String var1 = (String) this.a.get();
        if (var1 != null) {
            this.a.remove();
            return var1;
        } else {
            return this.tag;
        }
    }

    private String a(String var1, Object... var2) {
        return var2 != null && var2.length != 0 ? String.format(var1, var2) : var1;
    }

    private int a() {
        Integer var1 = (Integer) this.b.get();
        int var2 = this.mLogE.a1();
        if (var1 != null) {
            this.b.remove();
            var2 = var1.intValue();
        }

        if (var2 < 0) {
            throw new IllegalStateException("methodCount cannot be negative");
        } else {
            return var2;
        }
    }

    private int a(StackTraceElement[] var1) {
        for (int var2 = 3; var2 < var1.length; ++var2) {
            StackTraceElement var3 = var1[var2];
            String var4 = var3.getClassName();
            if (!var4.equals(LogD.class.getName()) && !var4.equals(LogHelper.class.getName())) {
                --var2;
                return var2;
            }
        }

        return -1;
    }
}
