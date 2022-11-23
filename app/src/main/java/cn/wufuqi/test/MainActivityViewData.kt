package cn.wufuqi.test

import androidx.databinding.BaseObservable
import cn.wufuqi.test.item_data.TextItemViewData

class MainActivityViewData : BaseObservable() {

    val data = mutableListOf<TextItemViewData>()

    init {
        for (i in 0..100) {
            data.add(TextItemViewData())
        }
        notifyChange()
    }

}