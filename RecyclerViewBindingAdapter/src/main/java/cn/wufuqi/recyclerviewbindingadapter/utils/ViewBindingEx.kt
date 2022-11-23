package cn.wufuqi.recyclerviewbindingadapter.utils


import android.R
import androidx.databinding.ViewDataBinding
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.annotation.MainThread
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


inline fun <A : AppCompatActivity, V : ViewDataBinding> viewBindingActivity(
    crossinline viewBinder: (View) -> V
) = ActivityViewBindingProperty { activity: A ->
    viewBinder(
        activity.window.decorView.findViewById<ViewGroup>(R.id.content)[0]
    )
}


class ActivityViewBindingProperty<in A : AppCompatActivity, out V : ViewDataBinding>(
    private val viewBinder: (A) -> V
) : ReadOnlyProperty<A, V> {

    private var viewBinding: V? = null

    @MainThread
    override fun getValue(thisRef: A, property: KProperty<*>): V {
        // 已经绑定，直接返回
        viewBinding?.let { return it }

        // Use viewLifecycleOwner.lifecycle other than lifecycle
        val lifecycle = thisRef.lifecycle
        val viewBinding = viewBinder(thisRef)
        if (lifecycle.currentState == Lifecycle.State.DESTROYED) {
            Log.w(
                "", "Access to viewBinding after Lifecycle is destroyed or hasn't created yet. " +
                        "The instance of viewBinding will be not cached."
            )
            // We can access to ViewBinding after Fragment.onDestroyView(), but don't save it to prevent memory leak
        } else {
            lifecycle.addObserver(ClearOnDestroyLifecycleObserver())
            this.viewBinding = viewBinding
        }
        return viewBinding
    }

    @MainThread
    fun clear() {
        viewBinding = null
    }

    private inner class ClearOnDestroyLifecycleObserver : LifecycleObserver {

        private val mainHandler = Handler(Looper.getMainLooper())

        @MainThread
        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy(owner: LifecycleOwner) {
            owner.lifecycle.removeObserver(this)
            mainHandler.post { clear() }
        }
    }
}

inline fun <F : Fragment, V : ViewDataBinding> viewBindingFragment(
    crossinline viewBinder: (View) -> V,
    crossinline viewProvider: (F) -> View = { fragment -> fragment.view!! }
) = FragmentViewBindingProperty { fragment: F ->
    viewBinder(viewProvider(fragment))
}


/**
 * @param viewBinder 创建绑定类对象
 */
class FragmentViewBindingProperty<in F : Fragment, out V : ViewDataBinding>(
    private val viewBinder: (F) -> V
) : ReadOnlyProperty<F, V> {

    private var viewBinding: V? = null

    @MainThread
    override fun getValue(thisRef: F, property: KProperty<*>): V {
        // 已经绑定，直接返回
        viewBinding?.let { return it }

        // Use viewLifecycleOwner.lifecycle other than lifecycle
        val lifecycle = thisRef.viewLifecycleOwner.lifecycle
        val viewBinding = viewBinder(thisRef)
        if (lifecycle.currentState == Lifecycle.State.DESTROYED) {
            Log.w(
                "", "Access to viewBinding after Lifecycle is destroyed or hasn't created yet. " +
                        "The instance of viewBinding will be not cached."
            )
            // We can access to ViewBinding after Fragment.onDestroyView(), but don't save it to prevent memory leak
        } else {
            lifecycle.addObserver(ClearOnDestroyLifecycleObserver())
            this.viewBinding = viewBinding
        }
        return viewBinding
    }

    @MainThread
    fun clear() {
        viewBinding = null
    }

    private inner class ClearOnDestroyLifecycleObserver : LifecycleObserver {

        private val mainHandler = Handler(Looper.getMainLooper())

        @MainThread
        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy(owner: LifecycleOwner) {
            owner.lifecycle.removeObserver(this)
            mainHandler.post { clear() }
        }
    }
}