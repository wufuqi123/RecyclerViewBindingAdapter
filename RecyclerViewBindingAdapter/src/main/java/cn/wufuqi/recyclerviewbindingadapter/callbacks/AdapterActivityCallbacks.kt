package cn.wufuqi.recyclerviewbindingadapter.callbacks

import android.app.Activity
import android.app.Application
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import cn.wufuqi.recyclerviewbindingadapter.utils.RecyclerViewBindingAdapterDestroyUtils

class AdapterActivityCallbacks : Application.ActivityLifecycleCallbacks {

    private val mFragmentLifecycleCallbacksMap = mutableMapOf<Activity, Any>()

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (activity is AppCompatActivity) {
            val supportFragmentLifecycleCallbacks = SupportFragmentLifecycleCallbacks()
            mFragmentLifecycleCallbacksMap[activity] = supportFragmentLifecycleCallbacks
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(
                supportFragmentLifecycleCallbacks,
                true
            )
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val fragmentLifecycleCallbacks = FragmentLifecycleCallbacks()
                mFragmentLifecycleCallbacksMap[activity] = fragmentLifecycleCallbacks
                activity.fragmentManager.registerFragmentLifecycleCallbacks(
                    fragmentLifecycleCallbacks,
                    true
                )
            }
        }
    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {
        RecyclerViewBindingAdapterDestroyUtils.destroyActivity(activity)
        val fragmentLifecycleCallbacks = mFragmentLifecycleCallbacksMap[activity]
        if (fragmentLifecycleCallbacks is SupportFragmentLifecycleCallbacks) {
            if(activity is AppCompatActivity){
                activity.supportFragmentManager.unregisterFragmentLifecycleCallbacks(
                    fragmentLifecycleCallbacks
                )
            }
        } else {
            if (fragmentLifecycleCallbacks is FragmentLifecycleCallbacks) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    activity.fragmentManager.unregisterFragmentLifecycleCallbacks(
                        fragmentLifecycleCallbacks
                    )
                }
            }
        }
        mFragmentLifecycleCallbacksMap.remove(activity)
    }
}