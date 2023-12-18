```
   使用ViewBinding来做适配RecyclerView的万能adapter，不用写adapter，直接设置data数据，ui即可展示
```

#### 基础功能
1. 添加依赖

    请在 build.gradle 下添加依赖。

    ``` 
        implementation 'cn.wufuqi:RecyclerViewBindingAdapter:1.0.6'
    ```

2. 设置jdk8或更高版本

    因为本sdk使用了jdk8才能使用的 Lambda 表达式，所以要在 build.gradle 下面配置jdk8或以上版本。

    ``` 
    android {
        ....

        compileOptions {
            sourceCompatibility JavaVersion.VERSION_1_8
            targetCompatibility JavaVersion.VERSION_1_8
        }
        
    }
    ```

3. 设置打开 dataBinding

    请在使用模块和应用模块的模块打开 dataBinding 。
    如：app模块引用了 hello 模块，hello模块引用了 test 模块。而test模块使用了RecyclerViewBindingAdapter。
        引用链：app -> hello -> test

    这时需要将 app 、 hello 、 test 模块都要打开 dataBinding

    ```
        android {

            ....

            // 打开 dataBinding
            buildFeatures {
                dataBinding true
            }

        }

    ```

4. 打开 Androidx

    在 gradle.properties 文件下加入 android.useAndroidX=true 

    ```
        //打开 Androidx
        android.useAndroidX=true
    ```


5. 初始化SDK

    请在 Application 的 onCreate 方法中调用！！！

    ``` 
        RecyclerViewBindingAdapterManager.init(this)
    ``` 


6. 使用

    RecyclerView的实例对象直接调用 setViewData 方法即可完成UI展示

    ``` 
        recyclerView.setViewData(List<BaseBindingAdapterItem>)
    ``` 



#### 介绍

    本项目的 RecyclerView 的子条目主要使用 dataBinding 来使用。

    把UI逻辑直接写在data对象上，方便简单。
    
## 项目的简单使用

    一个简单渲染文本条目例子

1. 开始渲染

    ``` 
        // 创建 101 个子条目  TextItemViewData 则是带UI 和逻辑的对象
        val data = mutableListOf<TextItemViewData>()
        for (i in 0..100) {
            data.add(TextItemViewData())
        }

        //把子条目渲染到 recyclerView 上
        recyclerView.setViewData(List<BaseBindingAdapterItem>)
    ``` 

2. TextItemViewData 的写法

    继承 BaseBindingAdapterItem 类，实现 getViewXmlLayout() 方法，一个 item 就写好了。

    比如：TextItemViewData 类，继承了 BaseBindingAdapterItem 并实现 getViewXmlLayout() 方法。
        并写了一个给textview显示文本逻辑的方法 getText()。

    
    ```
        class TextItemViewData : BaseBindingAdapterItem() {
            
            // R.layout.item_text 此xml 必须是 dataBinding 类型
            override fun getViewXmlLayout() = R.layout.item_text

            //给 R.layout.item_text xml内的TextView文本赋值的
            fun getText():String = "当前item为第：${currItemPosition}个"
        }

    ```

3. R.layout.item_text 的写法

    需要符合 dataBinding 的写法。在外层包一个<layout>标签。
    并data的variable标签的type值一定是要 BaseBindingAdapterItem 的 getViewXmlLayout()方法和此xml一致的类。

    如：R.layout.item_text 的xml，getViewXmlLayout()一致的xml 是 TextItemViewData。


    ```
        <?xml version="1.0" encoding="utf-8"?>
        <layout xmlns:android="http://schemas.android.com/apk/res/android">

            <data>

                <variable
                    name="data"
                    type="cn.wufuqi.test.item_data.TextItemViewData" />
            </data>

            <androidx.appcompat.widget.AppCompatTextView
                android:layout_width="match_parent"
                android:layout_height="50dp"
                android:text="@{data.getText()}" />

        </layout>
    ```
    

这时一个RecyclerView就完成了。



## 事件的说明

    如果一个按钮点击后需要让外层接收呢？

    比如：我在item层的button点击了下，我想在包含RecyclerView控件的activity层去接收这个点击事件

    这时候我们的item 不能再继承 BaseBindingAdapterItem 类了，我们需要继承 BaseEventBindingAdapterItem 类了。


    BaseEventBindingAdapterItem 是带事件的类，可以满足事件的接收和发送。


1. activity 层
    ```
         recyclerView.event.on("itemClick"){
            //点击的第几个条目
            val position = it[0]
           
            //emit发送时的额外数据   it[1] it[2]  emit("itemClick",it[1],it[2].... )
        }
    ```

2. item 层

    ```
        class ButtonItemViewData : BaseEventBindingAdapterItem() {
            
            // R.layout.item_text 此xml 必须是 dataBinding 类型
            override fun getViewXmlLayout() = R.layout.item_button

            // 初始化要发送的事件名   
            // 必写 要发送那些事件  如：这里只发送了event.emit("itemClick")  ，则只需要写 mutableListOf("itemClick")
            override fun initEventEmitNames() = mutableListOf("itemClick")
            
            fun itemClick(v:View){
                event.emit("itemClick",value....)
            }
        }

    ```

3. xml 层

    ```
        <?xml version="1.0" encoding="utf-8"?>
        <layout xmlns:android="http://schemas.android.com/apk/res/android">

            <data>

                <variable
                    name="data"
                    type="cn.wufuqi.test.item_data.ButtonItemViewData" />
            </data>

            <Button
                android:layout_width="match_parent"
                android:layout_height="50dp"
                android:onClick="@{data.itemClick}" />

        </layout>
    ```

这一个点击事件就完成了。




#### dataBinding 额外API

    在 activity 拿 binding 对象：

    ```
        val mBinding by viewBindingActivity(ActivityMainBinding::bind)

        //ActivityMainBinding 为对应的activity xml的Binding对象
    ```


    在 fragment 拿 binding 对象：

    ```
        val mBinding by viewBindingFragment(FragmentTest1Binding::bind)

        //FragmentTest1Binding 为对应的fragment xml的Binding对象
    ```


#### API

    添加了 recyclerView 的扩展方法

    添加了 BaseBindingAdapterItem 和 BaseEventBindingAdapterItem

    添加了 dataBinding xml中的扩展方法


### [recyclerView 的扩展方法](https://github.com/wufuqi123/RecyclerViewBindingAdapter/blob/master/README_RecyclerView.md)

### [BaseBindingAdapterItem](https://github.com/wufuqi123/RecyclerViewBindingAdapter/blob/master/README_BaseBindingAdapterItem.md)

### [BaseEventBindingAdapterItem](https://github.com/wufuqi123/RecyclerViewBindingAdapter/blob/master/README_BaseEventBindingAdapterItem.md)


### [dataBinding xml中的扩展方法](https://github.com/wufuqi123/RecyclerViewBindingAdapter/blob/master/README_dataBinding.md)





