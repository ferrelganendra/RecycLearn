package com.example.recyclearn.Activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.recyclearn.R

class VideoActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_video)

        val webView: WebView = findViewById(R.id.webView);
        val video: String = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/watch?v=bGc5lGxzMQw\" title=\"YouTube video player\" frameborder=\"0\" allow=\"accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share\" referrerpolicy=\"strict-origin-when-cross-origin\" allowfullscreen></iframe>";

        webView.loadData(video, "text/html", "UTF-8");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(WebChromeClient())

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.video)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}