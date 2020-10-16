/*
 *  -------------------------------------------------------------------------------------
 *  Mi-Me Confidential
 *
 *  Copyright (C) 2017.  Shanghai Mi-Me Financial Information Service Co., Ltd.
 *  All rights reserved.
 *
 *  No part of this file may be reproduced or transmitted in any form or by any means,
 *  electronic, mechanical, photocopying, recording, or otherwise, without prior
 *  written permission of Shanghai Mi-Me Financial Information Service Co., Ltd.
 *  -------------------------------------------------------------------------------------
 */

package com.tianjian.loglibrary;

/**
 * Logger
 * log统一输出类.
 * <p>
 * Created by DaJie on 2020/3/22.
 **/
public class Logger {

    /**
     * log的开关.
     */
    private static boolean isDebug = BuildConfig.DEBUG;


    /**
     * 禁log，用于单元测试中.
     */
    public static void disableLogger() {
        isDebug = false;
    }

    /**
     * 设置log的TAG.
     **/
    public static void setTAG(String tag) {
        LogHelper.init(tag);
    }

    /**
     * i
     * 正常调试log输出.
     *
     * @param msg log的内容
     **/
    public static void i(String msg) {
        if (isDebug) {
            LogHelper.i(msg);
        }
    }

    /**
     * i
     * 正常调试log输出.
     *
     * @param tag log的tag
     * @param msg log的内容
     **/
    public static void i(String tag, String msg) {
        if (isDebug) {
            LogHelper.t(tag).i(msg);
        }
    }

    /**
     * d
     * debug log输出.
     *
     * @param msg log的内容
     **/
    public static void d(String msg) {
        if (isDebug) {
            LogHelper.d(msg);
        }
    }

    /**
     * d
     * debug log输出.
     *
     * @param tag log的tag
     * @param msg log的内容
     **/
    public static void d(String tag, String msg) {
        if (isDebug) {
            LogHelper.t(tag).d(msg);
        }
    }

    /**
     * w
     * 警告log输出.
     *
     * @param msg log的内容
     */
    public static void w(String msg) {
        if (isDebug) {
            LogHelper.w(msg);
        }
    }

    /**
     * w
     * 警告log输出.
     *
     * @param tag log的tag
     * @param msg log的内容
     */
    public static void w(String tag, String msg) {
        if (isDebug) {
            LogHelper.t(tag).w(msg);
        }
    }

    /**
     * e
     * 异常log输出.
     *
     * @param msg log的内容
     **/
    public static void e(String msg) {
        if (isDebug) {
            LogHelper.e(msg);
        }
    }

    /**
     * 异常log输出.
     *
     * @param msg log的内容
     * @param tr  异常信息
     **/
    public static void e(String msg, Throwable tr) {
        if (isDebug) {
            LogHelper.e(tr, msg);
        }
    }

    /**
     * e
     * 异常log输出.
     *
     * @param tag log的tag
     * @param msg log的内容
     **/
    public static void e(String tag, String msg) {
        if (isDebug) {
            LogHelper.t(tag).e(msg);
        }
    }

    public static void e(boolean isPartDebug, String tag, String msg) {
        if (isDebug && isPartDebug) {
            LogHelper.t(tag).e(msg);
        }
    }

    public static void e(boolean isPartDebug, String msg) {
        if (isDebug && isPartDebug) {
        }
    }

    /**
     * 打印json信息.
     *
     * @param msg json的内容
     */
    public static void json(String msg) {
        if (isDebug) {
            LogHelper.json(msg);
        }
    }

    /**
     * 打印json信息.
     *
     * @param tag log的tag
     * @param msg json的内容
     */
    public static void json(String tag, String msg) {
        if (isDebug) {
            LogHelper.t(tag).json(msg);
        }
    }

    /**
     * 打印xml信息.
     *
     * @param msg xml的内容
     */
    public static void xml(String msg) {
        if (isDebug) {
            LogHelper.xml(msg);
        }
    }

    /**
     * 打印xml信息.
     *
     * @param tag log的tag
     * @param msg xml的内容
     */
    public static void xml(String tag, String msg) {
        if (isDebug) {
            LogHelper.t(tag).xml(msg);
        }
    }
}
