package com.juanasoapp.rickandmortychallenge.utils

import android.os.Build
import android.text.Html
import android.text.Spanned

fun setTextHTML(html: String): Spanned {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(html)
    }
}