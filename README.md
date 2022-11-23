```
   使用ViewBinding来做适配RecyclerView的万能adapter，不用写adapter，直接设置data数据，ui即可展示
```

#### 基础功能
1. 添加依赖

    请在 build.gradle 下添加依赖。

    ``` 
        implementation 'cn.wufuqi:RecyclerViewBindingAdapter:1.0.1'
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

3. 初始化SDK

    请在 Application 的 onCreate 方法中调用！！！

    ``` 
        RecyclerViewBindingAdapterManager.init(this)
    ``` 


4. 使用

    RecyclerView的实例对象直接调用 setViewData 方法即可完成UI展示

    ``` 
        recyclerView.setViewData(List<BaseBindingAdapterItem>)
    ``` 
