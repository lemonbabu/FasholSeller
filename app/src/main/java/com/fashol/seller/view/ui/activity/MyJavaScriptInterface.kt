package com.fashol.seller.view.ui.activity

import android.text.Html
import android.util.Log
import android.webkit.JavascriptInterface

class MyJavaScriptInterface {
    @SuppressWarnings("unused")
    @JavascriptInterface
    fun processHTML(html: String) {
        Log.d("processed html", html)
        val OauthFetcher = Thread {
            var oAuthDetails: String? = null
            oAuthDetails = Html.fromHtml(html).toString()
            Log.d("oAuthDetails", oAuthDetails)
        }
        OauthFetcher.start()
    }
}