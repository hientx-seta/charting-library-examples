package com.tradingview.android

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.GridLayout
import android.widget.LinearLayout

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webView)
        initWebView()

        val buttonContainer = findViewById<GridLayout>(R.id.buttonsContainer)
        val htmlFiles = listOf(
            "Chart.html", "Mini-Chart.html", "Overview-Chart.html",
            "Single-Ticker.html", "Symbol-Info.html", "Technical-Analysis.html",
            "Ticker.html", "Ticker-Tape.html"
        )

        for (file in htmlFiles) {
            val button = Button(this).apply {
                text = file
                layoutParams = GridLayout.LayoutParams().apply {
                    width = 0
                    height = ViewGroup.LayoutParams.WRAP_CONTENT
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                    }
                }
                setOnClickListener {
                    loadHtmlFile(file)
                }
            }
            buttonContainer.addView(button)
        }
    }

    private fun initWebView() {
        webView.settings.javaScriptEnabled = true
        webView.settings.allowFileAccessFromFileURLs = true
        webView.settings.textZoom = 100
        webView.webViewClient = WebViewClient()
    }

    private fun loadHtmlFile(fileName: String) {
        val url = "file:///android_asset/charting_library/$fileName"
        webView.loadUrl(url)
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        initWebView()
//    }
//
//    private fun initWebView() {
//        val webView = WebView(this)
//        webView.settings.javaScriptEnabled = true
//        webView.settings.allowFileAccessFromFileURLs = true
//
//        // to prevent scaling text
//        // see https://github.com/tradingview/charting_library/issues/3267#issuecomment-415031298
//        webView.settings.textZoom = 100
//
//        val chartingLibraryUrl = "file:///android_asset/charting_library/Chart.html" //Mini-Chart  Technical-Analysis
//        val jsBridge = JSApplicationBridge(this)
//        webView.addJavascriptInterface(jsBridge, "ApplicationBridge")
//
//        webView.webViewClient = object : WebViewClient() {
//            override fun onPageFinished(view: WebView?, url: String?) {
//                super.onPageFinished(view, url)
//
//                if (!url.equals(chartingLibraryUrl)) {
//                    return
//                }
//
//                webView.evaluateJavascript("""
//                    tvWidget.onChartReady(function() {
//                        tvWidget.chart().onIntervalChanged().subscribe(
//                            null,
//                            function(interval) {
//                                ApplicationBridge.onIntervalChanged(interval);
//                            }
//                        );
//                    });
//                """) {
//                    // do nothing
//                }
//            }
//        }
//
//        // uncomment next line if you want to debug WebView in Chrome DevTools
//        // WebView.setWebContentsDebuggingEnabled(true)
//
//        webView.loadUrl(chartingLibraryUrl)
//        setContentView(webView)
//    }
}
