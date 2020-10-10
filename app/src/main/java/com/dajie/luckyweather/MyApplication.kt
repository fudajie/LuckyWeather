package com.dajie.luckyweather

import android.app.Application
import android.content.Context

/**

 *  Created by DaJie on 2020/10/10

 */
class MyApplication : Application() {
    companion object {
        lateinit var context: Context//Application中的Context全局只会存在一份实例，并且在整个程序的生命周期内都不会回收，因此不存在内存泄漏风险
        const val TOKEN = "k2YpalwjFLs44jDl"//彩云天气的令牌
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}