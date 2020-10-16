package com.dajie.luckyweather.logic.network;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;

/**
 * 请求数据日志打印
 * Created by DaJie on 2020/4/14
 */
public class HttpLoggingInterceptor implements Interceptor {
    private static final Charset a = Charset.forName("UTF-8");
    private volatile HttpLoggingInterceptor.Level interceptorLevel;
    private java.util.logging.Level logLevel;
    private Logger logger;

    public HttpLoggingInterceptor(String var1) {
        this.interceptorLevel = Level.BODY;
        this.logger = Logger.getLogger(var1);
    }

    public void setLevel(HttpLoggingInterceptor.Level var1) {
        this.interceptorLevel = var1;
    }

    public void setLogLevel(java.util.logging.Level var1) {
        this.logLevel = var1;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request var2 = chain.request();
        if (this.interceptorLevel == HttpLoggingInterceptor.Level.NONE) {
            return chain.proceed(var2);
        } else {
            this.a(var2, chain.connection());
            long var3 = System.nanoTime();

            Response var5;
            try {
                var5 = chain.proceed(var2);
            } catch (Exception var8) {
                OkgoLog.e("<-- HTTP FAILED: " + var8);
                throw var8;
            }

            long var6 = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - var3);
            return this.a(var5, var6);
        }
    }



    private void a(Request var1, Connection var2) {
        boolean var3 = this.interceptorLevel == HttpLoggingInterceptor.Level.BODY;
        boolean var4 = this.interceptorLevel == HttpLoggingInterceptor.Level.BODY || this.interceptorLevel== HttpLoggingInterceptor.Level.HEADERS;
        RequestBody var5 = var1.body();
        boolean var6 = var5 != null;
        Protocol var7 = var2 != null ? var2.protocol() : Protocol.HTTP_1_1;
        StringBuilder var8 = new StringBuilder();

        try {
            String var9 = "--> " + var1.method() + ' ' + var1.url() + ' ' + var7 + " \n";
            var8.append(var9);
            if (var4) {
                Headers var10 = var1.headers();
                int var11 = 0;

                for(int var12 = var10.size(); var11 < var12; ++var11) {
                    var8.append("\t" + var10.name(var11) + ": " + var10.value(var11) + " \n");
                }

                var8.append(" ");
                if (var3 && var6) {
                    if (a(var5.contentType())) {
                        this.a(var1, var8);
                    } else {
                        var8.append("\tbody: maybe [file part] , too large too print , ignored! \n");
                    }
                }
            }
        } catch (Exception var16) {
            OkgoLog.a(var16);
        } finally {
            var8.append("\n --> END " + var1.method());
            OkgoLog.a(var8.toString());
        }

    }


    private Response a(Response var1, long var2) {
        Response.Builder var4 = var1.newBuilder();
        Response var5 = var4.build();
        ResponseBody var6 = var5.body();
        boolean var7 = this.interceptorLevel == HttpLoggingInterceptor.Level.BODY;
        boolean var8 = this.interceptorLevel == HttpLoggingInterceptor.Level.BODY || this.interceptorLevel == HttpLoggingInterceptor.Level.HEADERS;
        boolean var9 = false;
        StringBuilder var10 = new StringBuilder();

        try {
            var10.append("<-- " + var5.code() + ' ' + var5.message() + ' ' + var5.request().url() + " (" + var2 + "ms） \n");
            if (var8) {
                Headers var11 = var5.headers();
                int var12 = 0;

                for(int var13 = var11.size(); var12 < var13; ++var12) {
                    var10.append("\t" + var11.name(var12) + ": " + var11.value(var12) + " \n");
                }

                var10.append(" ");
                if (var7 && HttpHeaders.hasBody(var5)) {
                    if (a(var6.contentType())) {
                        String var19 = var6.string();
                        var10.append("\t\nbody:" + var19 + " \n");
                        var6 = ResponseBody.create(var6.contentType(), var19);
                        Response var20 = var1.newBuilder().body(var6).build();
                        return var20;
                    }

                    var10.append("\tbody: maybe [file part] , too large too print , ignored!");
                }
            }
        } catch (Exception var17) {
            var10.append("<-- END HTTP");
            OkgoLog.e(var10.toString());
            var9 = true;
        } finally {
            if (!var9) {
                var10.append("<-- END HTTP");
                OkgoLog.json(var10.toString());
            }

        }

        return var1;
    }


    private static boolean a(MediaType var0) {
        if (var0 == null) {
            return false;
        } else if (var0.type() != null && var0.type().equals("text")) {
            return true;
        } else {
            String var1 = var0.subtype();
            if (var1 != null) {
                var1 = var1.toLowerCase();
                if (var1.contains("x-www-form-urlencoded") || var1.contains("json") || var1.contains("xml") || var1.contains("html")) {
                    return true;
                }
            }

            return false;
        }
    }


    private void a(Request var1, StringBuilder var2) {
        try {
            Request var3 = var1.newBuilder().build();
            Buffer var4 = new Buffer();
            var3.body().writeTo(var4);
            Charset var5 = a;
            MediaType var6 = var3.body().contentType();
            if (var6 != null) {
                var5 = var6.charset(a);
            }

            var2.append("\tbody: " + var4.readString(var5));
        } catch (Exception var7) {
            var7.printStackTrace();
        }

    }

    public static enum Level {
        NONE,
        BASIC,
        HEADERS,
        BODY;

        private Level() {
        }
    }
}
