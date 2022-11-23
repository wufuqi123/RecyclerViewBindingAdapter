package cn.wufuqi.recyclerviewbindingadapter.callbacks

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import cn.wufuqi.recyclerviewbindingadapter.utils.RecyclerViewBindingAdapterDestroyUtils

class SupportFragmentLifecycleCallbacks : FragmentManager.FragmentLifecycleCallbacks() {
    override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
        f.view?.let {
            RecyclerViewBindingAdapterDestroyUtils.destroyRecyclerViewBindingAdapter(
                it
            )
        }
        super.onFragmentViewDestroyed(fm, f)
    }
}