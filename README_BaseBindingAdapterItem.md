
基本的 binding adapter  的  单个条目  bean对象


BaseBindingAdapterItem 抽象类， 继承 BaseObservable


#### 属性


## currItemPosition 类型：Int
    当前的item 的 位置



## itemCount 类型：Int
    总共的item的数量


## mBinding 类型：ViewDataBinding
    adapter  设置 setBindingData  true 后  此对象不为空
    //不推荐使用


#### 方法

## getViewXmlLayout() 返回类型：Int
   获取 xml 的布局id

   抽象方法


## onBindView(binding: ViewDataBinding)
    adapter  设置 setBindingData  true 后 会被调用
    //不推荐使用


## isTopItem() 返回类型：Boolean
    当前条目是否为最顶部的item


## isBottomItem() 返回类型：Boolean
    当前条目是否为最底部的item


## toString() 返回类型：String
    返回带类型的String字符串



## destroy()
    销毁
