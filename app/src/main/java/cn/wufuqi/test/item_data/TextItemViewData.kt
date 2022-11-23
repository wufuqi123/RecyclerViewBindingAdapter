package cn.wufuqi.test.item_data

import cn.wufuqi.recyclerviewbindingadapter.itemuibean.BaseBindingAdapterItem
import cn.wufuqi.test.R

class TextItemViewData : BaseBindingAdapterItem() {

    override fun getViewXmlLayout() = R.layout.item_text

    fun getText():String = "当前item为第：${currItemPosition}个"
}