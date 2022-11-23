package cn.wufuqi.recyclerviewbindingadapter.utils

import android.app.Application
import android.content.Context


val currentApplication by lazy {
    Class.forName("android.app.ActivityThread").getMethod("currentApplication")
        .invoke(null, null) as Application
}


/**
 * 获取文字的缩放比
 */
fun getFontDensity(context: Context = currentApplication): Float {
    return context.resources.displayMetrics.scaledDensity
}

/**
 * 获取像素的缩放比
 */
fun getDensity(context: Context = currentApplication): Float {
    return context.resources.displayMetrics.density
}


/**
 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
 */
fun Float.dp2px(): Float {
    return this * getDensity() + 0.5f
}

/**
 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
 */
fun Float.px2dp(): Float {
    return this / getDensity() + 0.5f
}


/**
 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
 */
fun Int.dp2px(): Int {
    return this.toFloat().dp2px().toInt()
}

/**
 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
 */
fun Int.px2dp(): Int {
    return this.toFloat().px2dp().toInt()
}


/**
 * 根据手机的分辨率从 sp 的单位 转成为 px(像素)
 */
fun Float.sp2px(): Float {
    return getFontDensity() * this + 0.5f
}


/**
 * 根据手机的分辨率从 px(像素) 的单位 转成为 sp
 */
fun Float.px2sp(): Float {
    return this / getFontDensity() + 0.5f
}


/**
 * 根据手机的分辨率从 sp 的单位 转成为 px(像素)
 */
fun Int.sp2px(): Int {
    return this.toFloat().sp2px().toInt()
}


/**
 * 根据手机的分辨率从 px(像素) 的单位 转成为 sp
 */
fun Int.px2sp(): Int {
    return this.toFloat().px2sp().toInt()
}