package cn.wufuqi.recyclerviewbindingadapter

import android.app.Application
import cn.wufuqi.recyclerviewbindingadapter.callbacks.AdapterActivityCallbacks

object RecyclerViewBindingAdapterManager {

    var isInit = false
        private set

    fun init(application: Application){
        if(isInit){
            return
        }
        isInit = true

        application.registerActivityLifecycleCallbacks(AdapterActivityCallbacks())
    }

}