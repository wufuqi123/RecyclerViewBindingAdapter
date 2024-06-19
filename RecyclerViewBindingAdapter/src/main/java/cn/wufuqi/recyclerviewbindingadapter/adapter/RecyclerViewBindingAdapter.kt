package cn.wufuqi.recyclerviewbindingadapter.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.wufuqi.eventemitter.EventEmitter
import cn.wufuqi.recyclerviewbindingadapter.RecyclerViewBindingAdapterManager
import cn.wufuqi.recyclerviewbindingadapter.error.DataBindingSearchMethodError
import cn.wufuqi.recyclerviewbindingadapter.error.NoInitializationError
import cn.wufuqi.recyclerviewbindingadapter.error.NotFondDataBindingDataError
import cn.wufuqi.recyclerviewbindingadapter.itemuibean.BaseBindingAdapterItem
import cn.wufuqi.recyclerviewbindingadapter.itemuibean.BaseEventBindingAdapterItem
import java.util.*
import kotlin.collections.ArrayList


/**
 *
 * 如果使用  BaseDataBindingActivity 或 BaseDataBindingFragment  不用关心内存泄露问题！！！！
 *
 * 请记住！！！！
 * 使用此Adapter   必须在activity或fragment的onDestroy内调用Adapter的  destroyEvent()方法
 *
 * 如果设置了  setBindingData  要调用destroy
 * 否则会造成严重的内存泄露
 * 重要！！！
 */
open class RecyclerViewBindingAdapter :
    RecyclerView.Adapter<RecyclerViewBindingAdapter.BindingHolder> {
    var mDatas: List<BaseBindingAdapterItem>

    var mConfigs: List<RecyclerViewConfig>? = null

    constructor(data: List<BaseBindingAdapterItem>? = null) : this(data, null)

    constructor(data: List<BaseBindingAdapterItem>?, configs: List<RecyclerViewConfig>?) {
        if(!RecyclerViewBindingAdapterManager.isInit){
            throw NoInitializationError("没有初始化SDK，请在 Application 的 onCreate 方法中调用 RecyclerViewBindingAdapterManager.init(application) 方法！！！")
        }

        mDatas = data ?: mutableListOf()
        mConfigs = configs
    }


    private val adapterEventList = mutableListOf<RecyclerViewBindingAdapter>()


    private var emptyLayoutId = -1

    /**
     * 是否打开空布局
     */
    var isOpenEmptyLayout = false

    /**
     * 是否销毁时清空 数据
     */
    var isDestroyClearData = true

    /**
     * 事件监听  分发
     */
    val event: EventEmitter by lazy {
        EventEmitter()
    }

    var mIncrementRefreshPositionStart = -1
    var mIncrementRefreshCount = 0


    companion object {
        val TAG = this::class.java.name
    }

    private var mIsBindingData = false


    /**
     * 设置后子条目  就可以使用  mBinding 对象
     *
     * 如果设置了  setBindingData  要调用destroy
     */
    open fun setBindingData(isBindingData: Boolean) {
        mIsBindingData = isBindingData
    }

    /**
     * 设置空的  layout布局
     */
    open fun setEmptyLayoutId(emptyLayoutId: Int) {
        this.emptyLayoutId = emptyLayoutId
    }

    override fun onCreateViewHolder(parent: ViewGroup, xmlLayout: Int): BindingHolder {
        if (xmlLayout == emptyLayoutId) {
            //如果是空view  则返回空
            val emptyView = LayoutInflater.from(parent.context).inflate(xmlLayout, parent, false)
            return EmptyHolder(emptyView)
        }
        //否则  返回正常view
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            xmlLayout,
            parent,
            false
        )
        onCreateBinding(binding)
        onCreateBinding(binding, xmlLayout)
        return BindingHolder(binding)
    }

    /**
     * 创建binding 时调用
     */
    open fun onCreateBinding(binding: ViewDataBinding) {
        if (mConfigs == null) {
            return
        }
        mConfigs?.forEach {
//            it.layout.copy
            createRecyclerViewBindingAdapter(binding, it.recyclerViewId, it.layout)
        }
    }

    /**
     * 创建binding 时调用
     */
    open fun onCreateBinding(binding: ViewDataBinding, xmlLayout: Int) {

    }


    /**
     * 调用  单个item时
     */
    open fun onItemBinding(binding: ViewDataBinding, position: Int) {
        if (mConfigs == null) {
            return
        }
        mConfigs?.forEach {
            val item = mDatas[position]
            val dataClass = item.javaClass
            val dataField = dataClass.getDeclaredField(it.itemListKeyName)
            dataField.isAccessible = true
            //更新  rvTabOne  的数据
            changeRecyclerViewBindingData(
                binding,
                it.recyclerViewId,
                dataField.get(item) as MutableList<BaseBindingAdapterItem>
            )
        }
    }

    /**
     * 通过  idName  获取  binding的实例对象
     *
     * @param idName binding.["正常获取view的字符串"]  idName 穿的参数为binding（konlin）能点出来的属性
     */
    inline fun <reified T> getBindingView(binding: ViewDataBinding, idName: String): T {
        val viewClass = binding.javaClass
        val vField = viewClass.getField(idName)
        val v = vField.get(binding)
        if (v is T) {
            return v
        }
        throw DataBindingSearchMethodError(TAG + "异常，${idName} 搜索不到${T::class.java.name}的返回值的方法！！")
    }


    fun bindRecyclerViewEvent(
        binding: ViewDataBinding,
        rvIdStr: String,
        eventName: String,
        position: Int
    ) {
        val rv = getBindingView<RecyclerView>(binding, rvIdStr)
        if (rv.adapter is RecyclerViewBindingAdapter) {
            bindRecyclerViewEvent(rv.adapter as RecyclerViewBindingAdapter, eventName, position)
        }
    }

    fun bindRecyclerViewEvent(
        adapter: RecyclerViewBindingAdapter,
        eventName: String,
        position: Int
    ) {
        adapter.event.off(eventName)
        adapter.event.on(eventName) {
            event.emit(eventName, position, *it.toTypedArray())
        }
        if (adapterEventList.indexOf(adapter) == -1) {
            adapterEventList.add(adapter)
        }
    }

    /**
     * 绑定数据
     */
    override fun onBindViewHolder(holder: BindingHolder, position: Int) {
        if (getItemViewType(position) == emptyLayoutId) {
            return
        }


        val item = mDatas[position]
        item.currItemPosition = position
        item.itemCount = itemCount
        if (mIsBindingData) {
            item.mBinding = holder.binding
        }
        //监听item的事件
        if (item is BaseEventBindingAdapterItem) {
            item.event.offAll()
            //把item的事件转发到adapte中
            item.initEventEmitNames().forEach {
                item.event.on(it) { args ->
                    event.emit(it, item.currItemPosition, *args.toTypedArray())
                }
            }
        }

        holder.bindData(item)
        item.onBindView(holder.binding,holder)
        item.notifyChange()


        onItemBinding(holder.binding, item.currItemPosition)

    }

    override fun getItemCount(): Int {
        if (mDatas.isEmpty() && isOpenEmptyLayout) {
            return 1
        }
        return mDatas.size
    }


    override fun getItemViewType(position: Int): Int {
        if (mDatas.isEmpty() && isOpenEmptyLayout) {
            return emptyLayoutId
        }
        return mDatas[position].getViewXmlLayout()
    }


    /**
     * 获取data的引用
     */
    fun getDatas(): List<BaseBindingAdapterItem> {
        return mDatas
    }


    /**
     * 创建一个RecyclerViewBindingAdapter
     * @param recyclerViewId binding.["正常获取view的字符串"]  idName 穿的参数为binding（konlin）能点出来的属性
     * @param orientation RecyclerView的方向  LinearLayoutManager.HORIZONTAL  LinearLayoutManager.VERTICAL
     */
    fun createRecyclerViewBindingAdapter(
        binding: ViewDataBinding,
        recyclerViewId: String,
        orientation: Int
    ) {
        //  标签一的  适配器
        val rv = getBindingView<RecyclerView>(binding, recyclerViewId)
        // 如果没有赋值adapter  则赋值
        rv.adapter = RecyclerViewBindingAdapter(mutableListOf())
        val linearLayoutManager = LinearLayoutManager(rv.context)
        linearLayoutManager.orientation = orientation
        rv.layoutManager = linearLayoutManager
    }


    /**
     * 网格布局模式
     */
    fun createGridRecyclerViewBindingAdapter(
        binding: ViewDataBinding,
        recyclerViewId: String,
        rowNum: Int
    ) {
        //  标签一的  适配器
        val rv = getBindingView<RecyclerView>(binding, recyclerViewId)
        // 如果没有赋值adapter  则赋值
        rv.adapter = RecyclerViewBindingAdapter(mutableListOf())
        val layoutManager = GridLayoutManager(rv.context, rowNum)
        rv.layoutManager = layoutManager
    }


    fun createRecyclerViewBindingAdapter(
        binding: ViewDataBinding,
        recyclerViewId: String,
        layoutManager: RecyclerView.LayoutManager
    ) {
        //  标签一的  适配器
        val rv = getBindingView<RecyclerView>(binding, recyclerViewId)
        // 如果没有赋值adapter  则赋值
        rv.adapter = RecyclerViewBindingAdapter(mutableListOf())
        rv.layoutManager = layoutManager
    }

    /**
     * 更新 RecyclerView  的数据
     * @param recyclerViewId binding.["正常获取view的字符串"]  idName 穿的参数为binding（konlin）能点出来的属性
     * @param datas 更新的数据
     */
    @SuppressLint("NotifyDataSetChanged")
    fun changeRecyclerViewBindingData(
        binding: ViewDataBinding,
        recyclerViewId: String,
        datas: MutableList<BaseBindingAdapterItem>
    ) {
        val rvTabOne = getBindingView<RecyclerView>(binding, recyclerViewId)
        val rvTabOneAdapter = rvTabOne.adapter
        ((rvTabOne.adapter as RecyclerViewBindingAdapter).mDatas as MutableList).clear()
        ((rvTabOne.adapter as RecyclerViewBindingAdapter).mDatas as MutableList).addAll(datas)
        rvTabOneAdapter?.notifyDataSetChanged()
    }


    /**
     * 重置位置
     */
    private fun resetPosition(){
        for(position in mDatas.indices){
            val item = mDatas[position]
            item.currItemPosition = position
            item.itemCount = itemCount
            item.notifyChange()
        }
    }

    open fun add(item: BaseBindingAdapterItem) {
        val position: Int = mDatas.size
        (mDatas as ArrayList).add(item)
        resetPosition()
        notifyItemInserted(position)
        clearRefresh()
    }

    @SuppressLint("NotifyDataSetChanged")
    open fun setData(datas: Collection<BaseBindingAdapterItem>) {
        this.mDatas = datas as List<BaseBindingAdapterItem>
        clearRefresh()
        notifyDataSetChanged()
    }

    open fun addAll(collection: Collection<BaseBindingAdapterItem>) {
        clearRefresh()
        val positionStart: Int = mDatas.size
        (mDatas as ArrayList).addAll(collection)
        resetPosition()
        notifyItemRangeInserted(positionStart, collection.size)
    }

    open fun addAll(t: Array<BaseBindingAdapterItem>) {
        clearRefresh()
        val positionStart: Int = mDatas.size
        Collections.addAll((mDatas as ArrayList), *t)
        resetPosition()
        notifyItemRangeInserted(positionStart, t.size)
    }

    open fun insertAll(collection: Collection<BaseBindingAdapterItem>) {
        insertAll(mDatas.size - 1, collection)
    }

    open fun insertAll(index: Int, collection: Collection<BaseBindingAdapterItem>) {
        val indexInt = when {
            index < 0 -> {
                0
            }
            index > mDatas.size - 1 -> {
                mDatas.size - 1
            }
            else -> {
                index
            }
        }
        clearRefresh()
        (mDatas as ArrayList).addAll(indexInt, collection)
        resetPosition()
        notifyItemRangeInserted(indexInt, collection.size)
    }

    open fun insert(item: BaseBindingAdapterItem) {
        insert(mDatas.size - 1, item)
    }

    open fun insert(index: Int, item: BaseBindingAdapterItem) {
        val indexInt = when {
            index < 0 -> {
                0
            }
            index > mDatas.size - 1 -> {
                mDatas.size - 1
            }
            else -> {
                index
            }
        }
        clearRefresh()
        (mDatas as ArrayList).add(indexInt, item)
        resetPosition()
        notifyItemInserted(indexInt)
    }

    open fun remove(item: BaseBindingAdapterItem) {
        clearRefresh()
        val position: Int = mDatas.indexOf(item)
        if (-1 == position) return
        (mDatas as ArrayList).remove(item)
        resetPosition()
        notifyItemRemoved(position)
    }

    open fun remove(position: Int) {
        clearRefresh()
        (mDatas as ArrayList).removeAt(position)
        resetPosition()
        notifyItemRemoved(position)
    }


    /**
     * 增量添加数据不刷新UI
     */
    open fun incrementDataNoRefresh(collection: Collection<BaseBindingAdapterItem>) {
        if (mIncrementRefreshPositionStart < 0) {
            mIncrementRefreshPositionStart = mDatas.size
            mIncrementRefreshCount = collection.size
        } else {
            mIncrementRefreshCount += collection.size
        }
        (mDatas as ArrayList).addAll(collection)
    }

    /**
     * 刷新UI
     */
    open fun incrementRefresh() {
        if (mIncrementRefreshPositionStart < 0) {
            return
        }
        resetPosition()
        notifyItemRangeInserted(mIncrementRefreshPositionStart, mIncrementRefreshCount)
        clearRefresh()
    }

    fun clearRefresh() {
        mIncrementRefreshPositionStart = -1
        mIncrementRefreshCount = 0
    }


    @SuppressLint("NotifyDataSetChanged")
    open fun clear() {
        clearRefresh()
        if (mDatas.isEmpty()) return
        (mDatas as ArrayList).clear()
        notifyDataSetChanged()
    }


    /**
     * 销毁事件
     *
     * 重要！！！！
     *
     * 必须调用此方法  必须在activity或fragment的onDestroy内调用
     * 否则会造成严重的内存泄露
     */
    fun destroyEvent() {
        //销毁被注册的事件
        event.offAll()
        //销毁  item的event事件   防止内存泄露
        mDatas.forEach {
            if (it is BaseEventBindingAdapterItem) {
                it.event.offAll()
            }
        }
        adapterEventList.forEach {
            it.destroyEvent()
        }
        adapterEventList.clear()
    }

    /**
     * 如果设置了  setBindingData  要调用destroy
     */
    fun destroy() {
        destroyEvent()
        mDatas.forEach {
            it.mBinding = null
            if (it is BaseEventBindingAdapterItem) {
                it.event.offAll()
            }
            it.destroy()
        }
        if (isDestroyClearData) {
            //清空数组  避免内存泄露
            if (mDatas is MutableList) {
                (mDatas as MutableList).clear()
            }
        }

    }

    /**
     * 空的  Holder
     */
    class EmptyHolder(var itemView: View) : BindingHolder(itemView) {

    }

    open class BindingHolder : RecyclerView.ViewHolder {
        lateinit var binding: ViewDataBinding

        constructor(binding: ViewDataBinding) : this(binding.root) {
            this.binding = binding
        }

        constructor(itemView: View) : super(itemView)

        fun bindData(item: BaseBindingAdapterItem) {
            val viewClass = binding.javaClass
            val kMethods = viewClass.methods
            var isSetView = false
            kMethods.forEach {
                val pts = it.parameterTypes
                if (pts.size == 1 && pts[0] == item::class.java) {

                    it.invoke(binding, item)
                    isSetView = true
                    return@forEach
                }
            }
            binding.executePendingBindings()
            if (!isSetView) {
                throw NotFondDataBindingDataError(TAG + "异常，动态绑定时未找到item的data\n传递的item类型为：${item::class.java.name}\n在xml布局中未找到！！！")
            }
        }
    }


    /**
     * RecyclerView  配置信息
     * @param recyclerViewId recyclerViewId binding.["正常获取view的字符串"]  idName 穿的参数为binding（konlin）能点出来的属性
     * @param itemListKeyName mDatas 对应的 binding数据列表的key名字   如 mDatas.tags  传的参数为 tags
     * @param layout RecyclerView的布局模式
     *
     */
    data class RecyclerViewConfig(
        val recyclerViewId: String,
        val itemListKeyName: String,
        val layout: RecyclerView.LayoutManager
    )

}