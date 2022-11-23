package cn.wufuqi.recyclerviewbindingadapter.extend

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.wufuqi.eventemitter.EventEmitter
import cn.wufuqi.recyclerviewbindingadapter.adapter.RecyclerViewBindingAdapter
import cn.wufuqi.recyclerviewbindingadapter.itemuibean.BaseBindingAdapterItem

/**
 * 用来监听事件
 */
val RecyclerView.event: EventEmitter
    get() {
        if (this.adapter !is RecyclerViewBindingAdapter) {
            this.adapter = RecyclerViewBindingAdapter()
        }
        val adapter = this.adapter as RecyclerViewBindingAdapter
        layoutManagerDefault()
        return adapter.event
    }

fun RecyclerView.layoutManagerDefault(): RecyclerView {
    if (layoutManager == null) {
        linear()
    }
    return this
}

/**
 * 创建一个线性的
 */
fun RecyclerView.linear(): RecyclerView {
    return linearVertical()
}

/**
 * 创建一个线性垂直
 */
fun RecyclerView.linearVertical(): RecyclerView {
    layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    return this
}

/**
 * 创建一个线性水平
 */
fun RecyclerView.linearHorizontal(): RecyclerView {
    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    return this
}

/**
 * 设置渲染UI  并如果没有layoutManager 则使用线性垂直布局
 */
fun RecyclerView.setViewData(viewData: List<BaseBindingAdapterItem>) {
    setViewData(viewData, true)
}

/**
 * 设置渲染UI
 */
fun RecyclerView.setViewData(
    viewData: List<BaseBindingAdapterItem>,
    isDefaultLayoutManager: Boolean
) {
    if (this.adapter !is RecyclerViewBindingAdapter) {
        this.adapter = RecyclerViewBindingAdapter()
    }
    val adapter = this.adapter as RecyclerViewBindingAdapter
    if (isDefaultLayoutManager) {
        layoutManagerDefault()
    }
    adapter.setData(viewData)
}

/**
 * 添加渲染ui
 */
fun RecyclerView.addViewData(viewData: BaseBindingAdapterItem) {
    if (this.adapter !is RecyclerViewBindingAdapter) {
        this.adapter = RecyclerViewBindingAdapter()
    }
    val adapter = this.adapter as RecyclerViewBindingAdapter
    adapter.add(viewData)
}

/**
 * 添加渲染ui
 */
fun RecyclerView.addViewData(viewData: List<BaseBindingAdapterItem>) {
    if (this.adapter !is RecyclerViewBindingAdapter) {
        this.adapter = RecyclerViewBindingAdapter()
    }
    val adapter = this.adapter as RecyclerViewBindingAdapter
    adapter.addAll(viewData)
}

/**
 * 添加渲染ui
 */
fun RecyclerView.addViewData(viewData: Array<BaseBindingAdapterItem>) {
    if (this.adapter !is RecyclerViewBindingAdapter) {
        this.adapter = RecyclerViewBindingAdapter()
    }
    val adapter = this.adapter as RecyclerViewBindingAdapter
    adapter.addAll(viewData)
}

/**
 * 添加渲染ui
 */
fun RecyclerView.insertViewData(viewData: BaseBindingAdapterItem) {
    if (this.adapter !is RecyclerViewBindingAdapter) {
        this.adapter = RecyclerViewBindingAdapter()
    }
    val adapter = this.adapter as RecyclerViewBindingAdapter
    adapter.insert(viewData)
}

/**
 * 添加渲染ui
 */
fun RecyclerView.insertViewData(index: Int, viewData: BaseBindingAdapterItem) {
    if (this.adapter !is RecyclerViewBindingAdapter) {
        this.adapter = RecyclerViewBindingAdapter()
    }
    val adapter = this.adapter as RecyclerViewBindingAdapter
    adapter.insert(index, viewData)
}

/**
 * 添加渲染ui
 */
fun RecyclerView.insertViewData(viewData: List<BaseBindingAdapterItem>) {
    if (this.adapter !is RecyclerViewBindingAdapter) {
        this.adapter = RecyclerViewBindingAdapter()
    }
    val adapter = this.adapter as RecyclerViewBindingAdapter
    adapter.insertAll(viewData)
}

/**
 * 添加渲染ui
 */
fun RecyclerView.insertViewData(index: Int, viewData: List<BaseBindingAdapterItem>) {
    if (this.adapter !is RecyclerViewBindingAdapter) {
        this.adapter = RecyclerViewBindingAdapter()
    }
    val adapter = this.adapter as RecyclerViewBindingAdapter
    adapter.insertAll(index, viewData)
}

/**
 * 删除渲染ui
 */
fun RecyclerView.removeViewData(viewData: BaseBindingAdapterItem) {
    if (this.adapter !is RecyclerViewBindingAdapter) {
        this.adapter = RecyclerViewBindingAdapter()
    }
    val adapter = this.adapter as RecyclerViewBindingAdapter
    adapter.remove(viewData)
}

/**
 * 删除渲染ui
 */
fun RecyclerView.removeViewData(position: Int) {
    if (this.adapter !is RecyclerViewBindingAdapter) {
        this.adapter = RecyclerViewBindingAdapter()
    }
    val adapter = this.adapter as RecyclerViewBindingAdapter
    adapter.remove(position)
}

/**
 * 清空渲染ui
 */
fun RecyclerView.clearViewData() {
    if (this.adapter !is RecyclerViewBindingAdapter) {
        this.adapter = RecyclerViewBindingAdapter()
    }
    val adapter = this.adapter as RecyclerViewBindingAdapter
    adapter.clear()
}

/**
 * 增量添加数据不刷新UI
 */
fun RecyclerView.incrementViewDataNoRefresh(viewData: List<BaseBindingAdapterItem>) {
    if (this.adapter !is RecyclerViewBindingAdapter) {
        this.adapter = RecyclerViewBindingAdapter()
    }
    val adapter = this.adapter as RecyclerViewBindingAdapter
    adapter.incrementDataNoRefresh(viewData)
}

/**
 * 刷新增量的UI
 */
fun RecyclerView.incrementRefresh() {
    if (this.adapter !is RecyclerViewBindingAdapter) {
        this.adapter = RecyclerViewBindingAdapter()
    }
    val adapter = this.adapter as RecyclerViewBindingAdapter
    adapter.incrementRefresh()
}