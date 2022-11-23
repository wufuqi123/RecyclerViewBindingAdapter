package cn.wufuqi.recyclerviewbindingadapter.utils

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import cn.wufuqi.recyclerviewbindingadapter.extend.setViewData
import cn.wufuqi.recyclerviewbindingadapter.itemuibean.BaseBindingAdapterItem

object ViewBindingExtend {

    @BindingAdapter("maxLines")
    @JvmStatic
    fun maxLines(view: TextView, maxLines: Int) {
        view.maxLines = maxLines
    }

    @BindingAdapter("layout_width")
    @JvmStatic
    fun layoutWidth(view: View, w: Int) {
        view.layoutParams.width = w
        view.layoutParams = view.layoutParams
    }

    @BindingAdapter("width")
    @JvmStatic
    fun width(view: View, w: Int) {
        view.layoutParams.width = w
        view.layoutParams = view.layoutParams
    }

    @BindingAdapter("layout_marginLeft")
    @JvmStatic
    fun layoutMarginLeft(view: View, l: Int) {
        if (view.layoutParams is ViewGroup.MarginLayoutParams) {
            val lp = view.layoutParams as ViewGroup.MarginLayoutParams
            lp.leftMargin = l
            view.layoutParams = lp
        }
    }

    @BindingAdapter("layout_marginRight")
    @JvmStatic
    fun layoutMarginRight(view: View, r: Int) {
        if (view.layoutParams is ViewGroup.MarginLayoutParams) {
            val lp = view.layoutParams as ViewGroup.MarginLayoutParams
            lp.rightMargin = r
            view.layoutParams = lp
        }
    }

    @BindingAdapter("layout_marginTop")
    @JvmStatic
    fun layoutMarginTop(view: View, t: Int) {
        if (view.layoutParams is ViewGroup.MarginLayoutParams) {
            val lp = view.layoutParams as ViewGroup.MarginLayoutParams
            lp.topMargin = t
            view.layoutParams = lp
        }
    }

    @BindingAdapter("layout_marginBottom")
    @JvmStatic
    fun layoutMarginBottom(view: View, b: Int) {
        if (view.layoutParams is ViewGroup.MarginLayoutParams) {
            val lp = view.layoutParams as ViewGroup.MarginLayoutParams
            lp.bottomMargin = b
            view.layoutParams = lp
        }
    }

    @BindingAdapter("widthdp")
    @JvmStatic
    fun widthdp(view: View, w: Int) {
        view.layoutParams.width = (w * 1.0f).dp2px().toInt()
        view.layoutParams = view.layoutParams
    }

    @BindingAdapter("layout_height")
    @JvmStatic
    fun layoutHeight(view: View, h: Int) {
        view.layoutParams.height = h
        view.layoutParams = view.layoutParams
    }

    @BindingAdapter("height")
    @JvmStatic
    fun height(view: View, h: Int) {
        view.layoutParams.height = h
        view.layoutParams = view.layoutParams
    }

    @BindingAdapter("heightdp")
    @JvmStatic
    fun heightdp(view: View, h: Int) {
        view.layoutParams.height = (h * 1.0f).dp2px().toInt()
        view.layoutParams = view.layoutParams
    }

    @SuppressLint("NotifyDataSetChanged")
    @BindingAdapter("adapter")
    @JvmStatic
    fun adapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
        if (view.adapter == adapter) return
        view.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    @BindingAdapter("layoutManagerObject")
    @JvmStatic
    fun layoutManagerObject(view: RecyclerView, layoutManager: RecyclerView.LayoutManager) {
        //注释掉  以防性能消耗
        if (view.layoutManager == null) {
            view.layoutManager = layoutManager
        }
    }

//    @BindingAdapter("image")
//    @JvmStatic
//    fun setImage(view: ImageView, bitmap: Bitmap) {
//        Glide.with(view.context).load(bitmap).into(view)
//    }
//
//    @BindingAdapter("src")
//    @JvmStatic
//    fun setSrc(view: ImageView, bitmap: Bitmap) {
//        Glide.with(view.context).load(bitmap).into(view)
//    }


//    @BindingAdapter("image")
//    @JvmStatic
//    fun setImage(view: ImageView, resId: Int) {
//        Glide.with(view.context).load(resId).into(view)
//    }

    @BindingAdapter("background")
    @JvmStatic
    fun setBackground(view: View, @DrawableRes resId: Int) {
        view.setBackgroundResource(resId)
    }

    @BindingAdapter("background")
    @JvmStatic
    fun setBackground(view: View, drawable: Drawable) {
        view.background = drawable
    }

    @BindingAdapter("background")
    @JvmStatic
    fun setBackground(view: View, bitmap: Bitmap) {
        view.background = BitmapDrawable(bitmap)
    }

//    @BindingAdapter("src")
//    @JvmStatic
//    fun setSrc(view: ImageView, resId: Int) {
//        Glide.with(view.context).load(resId).into(view)
//    }

//    @BindingAdapter("srcCircularBeadTen")
//    @JvmStatic
//    fun srcCircularBeadTen(view: ImageView, resId: Int) {
//        val rc = RoundedCorners(10f.dp2px().toInt())
//        val options = RequestOptions.bitmapTransform(rc)
//        Glide.with(view.context).load(resId).apply(options).into(view)
//    }
//
//    @BindingAdapter("srcCircularBeadTen")
//    @JvmStatic
//    fun srcCircularBeadTen(view: ImageView, url: String) {
//        val rc = RoundedCorners(10f.dp2px().toInt())
//        val options = RequestOptions.bitmapTransform(rc)
//        Glide.with(view.context).load(url).apply(options).into(view)
//    }
//
//
//    @BindingAdapter("imageCircularBeadTen")
//    @JvmStatic
//    fun imageCircularBeadTen(view: ImageView, resId: Int) {
//        val rc = RoundedCorners(10f.dp2px().toInt())
//        val options = RequestOptions.bitmapTransform(rc)
//        Glide.with(view.context).load(resId).apply(options).into(view)
//    }
//
//    @BindingAdapter("imageCircularBeadTen")
//    @JvmStatic
//    fun imageCircularBeadTen(view: ImageView, url: String) {
//        val rc = RoundedCorners(10f.dp2px().toInt())
//        val options = RequestOptions.bitmapTransform(rc)
//        Glide.with(view.context).load(url).apply(options).into(view)
//    }
//
//    @BindingAdapter("image")
//    @JvmStatic
//    fun setImage(view: ImageView, url: String) {
//        Glide.with(view.context).load(url).into(view)
//    }
//
//    @BindingAdapter("src")
//    @JvmStatic
//    fun setSrc(view: ImageView, url: String) {
//        Glide.with(view.context).load(url).into(view)
//    }

    @BindingAdapter("textStyle")
    @JvmStatic
    fun setTextStyle(text: TextView, type: Int) {
        text.setTypeface(null, type)
    }

    @BindingAdapter("visibility")
    @JvmStatic
    fun visibility(view: View, visibility: Boolean) {
        view.visibility = if (visibility) View.VISIBLE else View.GONE
    }

    @BindingAdapter("visibility")
    @JvmStatic
    fun visibility(view: View, visibility: Int) {
        view.visibility = visibility
    }


    @BindingAdapter("enabled")
    @JvmStatic
    fun enabled(view: View, enabled: Boolean) {
        view.isEnabled = enabled
    }

    @BindingAdapter("textColor")
    @JvmStatic
    fun textColor(view: TextView, textColor: Int) {
        view.setTextColor(textColor)
    }

    @BindingAdapter("textColorHint")
    @JvmStatic
    fun textColorHint(view: TextView, textColor: Int) {
        view.setHintTextColor(textColor)
    }


    @BindingAdapter("view_data")
    @JvmStatic
    fun setViewData(view: RecyclerView, viewData: List<BaseBindingAdapterItem>) {
        view.setViewData(viewData)
    }
}