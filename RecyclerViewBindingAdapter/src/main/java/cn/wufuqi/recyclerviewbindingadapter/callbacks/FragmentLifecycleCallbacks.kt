package cn.wufuqi.recyclerviewbindingadapter.callbacks

import android.app.FragmentManager
import android.os.Build
import androidx.annotation.RequiresApi
import cn.wufuqi.recyclerviewbindingadapter.utils.RecyclerViewBindingAdapterDestroyUtils

@RequiresApi(Build.VERSION_CODES.O)
class FragmentLifecycleCallbacks : FragmentManager.FragmentLifecycleCallbacks() {

    override fun onFragmentViewDestroyed(
        fm: android.app.FragmentManager?,
        f: android.app.Fragment?
    ) {
        f?.view?.let {
            RecyclerViewBindingAdapterDestroyUtils.destroyRecyclerViewBindingAdapter(
                it
            )
        }
        super.onFragmentViewDestroyed(fm, f)
    }
}