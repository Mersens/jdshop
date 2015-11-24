package com.jcwl.jdshop;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

public class Activity_Web extends TopActivity {
	private WebView webView;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web);
		initTopView("");
		((ImageView)findViewById(R.id.top_right_btn)).setImageResource(R.drawable.close_icon);
		((ImageView)findViewById(R.id.top_right_btn)).setVisibility(View.VISIBLE);
		initWebView();
	}

	@SuppressLint("SetJavaScriptEnabled") private void initWebView() {
		webView=(WebView)findViewById(R.id.webview);
		webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		WebSettings settings = webView.getSettings();
		settings.setUseWideViewPort(false);
		settings.setLoadWithOverviewMode(true); 
		settings.setSupportZoom(false);
		settings.setJavaScriptEnabled(true);
		webView.setWebChromeClient(new WebChromeClient(){
			@Override
			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
				setTopText(title);
			}
		});
        webView.setWebViewClient(new WebViewClient(){
        	@Override
        	public void onPageStarted(WebView view, String url, Bitmap favicon) {
        		super.onPageStarted(view, url, favicon);
        	}
        	@Override
        	public void onPageFinished(WebView view, String url) {
        		super.onPageFinished(view, url);
        	}
        });
        webView.loadUrl(getIntent().getStringExtra("url"));
	}
	
	@Override
	public void onBackPressed() {
		if(webView.canGoBack()){
			webView.goBack();
			return;
		}else{
			super.onBackPressed();
		}
	}
	
	public void onTopClick(View view){
		switch (view.getId()) {
		case R.id.top_left_btn:
			if(webView.canGoBack()){
				webView.goBack();
				return;
			}else{
				finish();
			}
 			break;
		case R.id.top_right_btn:
			finish();
			break;
		default:
			break;
		}
	}
	
}