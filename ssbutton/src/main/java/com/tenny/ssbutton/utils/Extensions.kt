package com.tenny.ssbutton.utils

import android.content.res.Resources
import android.util.TypedValue

/**
 * Created by TennyQ on 2020/9/19
 */

private val displayMetrics = Resources.getSystem().displayMetrics

const val goldDivider = 0.618f

val Float.dp2px
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, displayMetrics)

val Int.dp2px
    get() = this.toFloat().dp2px