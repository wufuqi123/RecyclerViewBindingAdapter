package cn.wufuqi.recyclerviewbindingadapter.utils

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import androidx.recyclerview.widget.RecyclerView
import cn.wufuqi.recyclerviewbindingadapter.adapter.RecyclerViewBindingAdapter

object RecyclerViewBindingAdapterDestroyUtils {

    /**
     * 销毁activity
     */
    fun destroyActivity(activity: Activity) {
        destroyRecyclerViewBindingAdapter(
            (activity.findViewById<ViewGroup>(android.R.id.content)).getChildAt(
                0
            )
        )
    }


    /**
     * 销毁 RecyclerViewBindingAdapter
     */
    fun destroyRecyclerViewBindingAdapter(view: View) {
        if (view is RecyclerView) {
            if (view.adapter is RecyclerViewBindingAdapter) {
                (view.adapter as RecyclerViewBindingAdapter).destroy()
            }
            view.forEach {
                destroyRecyclerViewBindingAdapter(it)
            }
        } else if (view is ViewGroup) {
            view.forEach {
                destroyRecyclerViewBindingAdapter(it)
            }
        }
    }
}