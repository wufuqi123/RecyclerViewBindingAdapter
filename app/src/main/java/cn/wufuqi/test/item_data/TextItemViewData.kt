package cn.wufuqi.test.item_data

import cn.wufuqi.recyclerviewbindingadapter.itemuibean.BaseEventBindingAdapterItem
import cn.wufuqi.test.R

class TextItemViewData(var index:Int) : BaseEventBindingAdapterItem() {


    override fun initEventEmitNames() = mutableListOf("itemDeleteClick")

    override fun getViewXmlLayout() = R.layout.item_text

    fun getText():String = "当前item为第：${currItemPosition}个,index:$index"

    fun itemDeleteClick(){
        event.emit("itemDeleteClick")
    }


}