package com.custombutton.utils

import android.content.Context

fun dpToPx(context: Context, dp: Int): Float {
    val density = context.resources.displayMetrics.density
    return (dp * density)
}