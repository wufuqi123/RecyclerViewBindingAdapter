package cn.wufuqi.recyclerviewbindingadapter.error

/**
 * 动态绑定时找不到  data 属性
 */
class NotFondDataBindingDataError:Error {
    constructor(message: String?):super(message)
}