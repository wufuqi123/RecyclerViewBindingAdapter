package cn.wufuqi.recyclerviewbindingadapter.itemuibean

import cn.wufuqi.eventemitter.EventEmitter


/**
 * 基本的 binding adapter  的  单个条目  bean对象
 *
 * 带事件
 */
abstract class BaseEventBindingAdapterItem : BaseBindingAdapterItem() {

    /**
     * 初始化要发送的事件名
     *
     * 必须在activity或fragment的onDestroy内调用Adapter的  destroyEvent()方法
     * 否则会造成严重的内存泄露
     * 重要！！！
     */
    abstract fun initEventEmitNames(): List<String>

    /**
     * 事件监听  分发
     */
    val event: EventEmitter by lazy {
        EventEmitter()
    }


    override fun destroy() {
        super.destroy()
        event.offAll()
    }
}