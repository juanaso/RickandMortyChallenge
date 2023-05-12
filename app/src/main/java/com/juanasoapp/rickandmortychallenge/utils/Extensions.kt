package com.juanasoapp.rickandmortychallenge.utils

import android.content.Context
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.util.TypedValue
import androidx.core.content.ContentProviderCompat.requireContext


fun dpToPx(dp: Int, context: Context): Int {
    val displayMetrics = context.resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), displayMetrics).toInt()
}