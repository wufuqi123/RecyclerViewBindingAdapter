package cn.wufuqi.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.wufuqi.recyclerviewbindingadapter.RecyclerViewBindingAdapterManager
import cn.wufuqi.recyclerviewbindingadapter.extend.setViewData
import cn.wufuqi.recyclerviewbindingadapter.utils.viewBindingActivity
import cn.wufuqi.test.databinding.ActivityMainBinding
import cn.wufuqi.test.item_data.TextItemViewData

class MainActivity : AppCompatActivity() {
    private val mBinding by viewBindingActivity(ActivityMainBinding::bind)


    private val mTest1Fragment = Test1Fragment()
    private val mTest2Fragment = Test1Fragment()

    private var isOneF = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        mBinding.data = MainActivityViewData()


        supportFragmentManager.beginTransaction().add(R.id.fl, mTest1Fragment)
            .add(R.id.fl, mTest2Fragment).replace(R.id.fl, mTest1Fragment).commit()


        mBinding.btn.setOnClickListener {
            isOneF = !isOneF
            supportFragmentManager.beginTransaction()
                .replace(R.id.fl, if (isOneF) mTest1Fragment else mTest2Fragment).commit()
        }
    }
}