package cn.wufuqi.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cn.wufuqi.recyclerviewbindingadapter.extend.event
import cn.wufuqi.recyclerviewbindingadapter.extend.removeViewData
import cn.wufuqi.recyclerviewbindingadapter.extend.setViewData
import cn.wufuqi.recyclerviewbindingadapter.utils.viewBindingFragment
import cn.wufuqi.test.databinding.FragmentTest1Binding
import cn.wufuqi.test.item_data.TextItemViewData

class Test1Fragment : Fragment() {

    val data = mutableListOf<TextItemViewData>()
    private val mBinding by viewBindingFragment(FragmentTest1Binding::bind)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        for (i in 0..100) {
            data.add(TextItemViewData(i))
        }
        return inflater.inflate(R.layout.fragment_test_1, null)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.rvContent.setViewData(data)

        mBinding.rvContent.event.on("itemDeleteClick"){
            val position = it[0] as Int
            mBinding.rvContent.removeViewData(position)
        }
    }


}