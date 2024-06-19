package cn.wufuqi.recyclerviewbindingadapter.itemuibean


import androidx.annotation.CallSuper
import androidx.databinding.BaseObservable
import androidx.databinding.ViewDataBinding
import cn.wufuqi.recyclerviewbindingadapter.adapter.RecyclerViewBindingAdapter
import cn.wufuqi.recyclerviewbindingadapter.utils.toTypeString

/**
 * 基本的 binding adapter  的  单个条目  bean对象
 */
abstract class BaseBindingAdapterItem : BaseObservable() {
    /**
     * 当前的item 的 位置
     */
    var currItemPosition = 0


    /**
     * 总共的item的数量
     */
    var itemCount = 0

    /**
     * adapter  设置 setBindingData  true 后  此对象不为空
     */
    var mBinding: ViewDataBinding? = null

    /**
     * 获取 xml 的布局id
     */
    abstract fun getViewXmlLayout(): Int


    open fun onBindView(binding: ViewDataBinding,bindingHolder: RecyclerViewBindingAdapter.BindingHolder) {

    }

    /**
     * 当前条目是否为最顶部的item
     */
    fun isTopItem(): Boolean = currItemPosition == 0

    /**
     * 当前条目是否为最底部的item
     */
    fun isBottomItem(): Boolean = currItemPosition == itemCount - 1


    override fun toString(): String {
        return toTypeString()
    }


    /**
     * 销毁
     */
    @CallSuper
    open fun destroy() {
        mBinding = null
    }


}