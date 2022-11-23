#### 属性

## event 类型：  [EventEmitter](https://github.com/wufuqi123/EventEmitter/blob/master/README.md)


#### 方法

## layoutManagerDefault() 返回类型：RecyclerView
    RecyclerView 默认的 layoutManager，为：线性垂直。

## linear() 返回类型：RecyclerView
   创建一个线性垂直的layoutManager

## linearVertical() 返回类型：RecyclerView
   创建一个线性垂直


## linearHorizontal() 返回类型：RecyclerView
   创建一个线性水平


## setViewData(viewData: List<[BaseBindingAdapterItem](https://github.com/wufuqi123/RecyclerViewBindingAdapter/blob/master/README_BaseBindingAdapterItem.md)>)
  设置渲染UI  并如果没有layoutManager 则使用线性垂直布局


## setViewData(viewData: List<[BaseBindingAdapterItem](https://github.com/wufuqi123/RecyclerViewBindingAdapter/blob/master/README_BaseBindingAdapterItem.md)>,isDefaultLayoutManager: Boolean)
  设置渲染UI


## addViewData(viewData: [BaseBindingAdapterItem](https://github.com/wufuqi123/RecyclerViewBindingAdapter/blob/master/README_BaseBindingAdapterItem.md))
  添加渲染ui


## addViewData(viewData: List<[BaseBindingAdapterItem](https://github.com/wufuqi123/RecyclerViewBindingAdapter/blob/master/README_BaseBindingAdapterItem.md)>)
  添加渲染ui


## addViewData(viewData: Array<[BaseBindingAdapterItem](https://github.com/wufuqi123/RecyclerViewBindingAdapter/blob/master/README_BaseBindingAdapterItem.md)>)
  添加渲染ui

## insertViewData(viewData: [BaseBindingAdapterItem](https://github.com/wufuqi123/RecyclerViewBindingAdapter/blob/master/README_BaseBindingAdapterItem.md))
  添加渲染ui


## insertViewData(index: Int,viewData: [BaseBindingAdapterItem](https://github.com/wufuqi123/RecyclerViewBindingAdapter/blob/master/README_BaseBindingAdapterItem.md))
  添加渲染ui


## insertViewData(viewData: List<[BaseBindingAdapterItem](https://github.com/wufuqi123/RecyclerViewBindingAdapter/blob/master/README_BaseBindingAdapterItem.md)>)
  添加渲染ui


## insertViewData(index: Int,viewData: List<[BaseBindingAdapterItem](https://github.com/wufuqi123/RecyclerViewBindingAdapter/blob/master/README_BaseBindingAdapterItem.md)>)
  添加渲染ui


## removeViewData(viewData: [BaseBindingAdapterItem](https://github.com/wufuqi123/RecyclerViewBindingAdapter/blob/master/README_BaseBindingAdapterItem.md))
  删除渲染ui


## removeViewData(position: Int)
  删除渲染ui


## clearViewData()
  清空渲染ui


## incrementViewDataNoRefresh(viewData: List<[BaseBindingAdapterItem](https://github.com/wufuqi123/RecyclerViewBindingAdapter/blob/master/README_BaseBindingAdapterItem.md)>)
  增量添加数据不刷新UI


## incrementRefresh()
  刷新增量的UI