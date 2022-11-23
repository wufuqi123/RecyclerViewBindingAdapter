package cn.wufuqi.test

import android.app.Application
import cn.wufuqi.recyclerviewbindingadapter.RecyclerViewBindingAdapterManager

class MyApp:Application() {
    override fun onCreate() {
        super.onCreate()
        RecyclerViewBindingAdapterManager.init(this)
    }
}