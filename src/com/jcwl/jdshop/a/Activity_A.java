package com.jcwl.jdshop.a;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.jcwl.jdshop.Activity_Web;
import com.jcwl.jdshop.BackActivity;
import com.jcwl.jdshop.MainActivity;
import com.jcwl.jdshop.R;

public class Activity_A extends BackActivity {

	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_a);
		initWebView();
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void initWebView() {
		webView = (WebView) findViewById(R.id.webview);
		webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		WebSettings settings = webView.getSettings();
		settings.setUseWideViewPort(false);
		settings.setLoadWithOverviewMode(true);
		settings.setSupportZoom(false);
		settings.setJavaScriptEnabled(true);
		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
			}
		});
		webView.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				Intent intent = null;
				if (url.startsWith("app:")) {
					/*String value[] = url.split(":");
					if (value[1].equals("wodeshoucang")) {
						intent = new Intent(Activity_A.this,
								Activity_love.class);
						intent.putExtra("hid", false);
					} else if (value[1].equals("wodedingdan")) {
						intent = new Intent(Activity_A.this,
								Activity_order.class);
					} else if (value[1].equals("wodejifen")) {
						intent = new Intent(Activity_A.this,
								Activity_integrallog.class);
					} else if (value[1].equals("wodehongbao")) {
						intent = new Intent(Activity_A.this,
								Activity_redbag.class);
					}*/
				} else {
					intent = new Intent(Activity_A.this, Activity_Web.class);
					intent.putExtra("url", url);
				}
				if (intent != null)
					startActivity(intent);
				return true;
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
			}
		});
		webView.loadUrl("http://m.jd.com");
	}


	@Override
	protected void onResume() {
		super.onResume();
		try {
			//city.setText(Constant.gpsCity);
		} catch (Exception e) {
		}
	}

	public void onProductTypeClick(View v) {
		MainActivity.goToCurrentTab(2);
	}
	
	public void onBarcodeClick(View v) {
		
	}

	public void onSearchClick(View v) {
		/*String input = ((EditText) findViewById(R.id.search_edit)).getText()
				.toString();
		Intent intent = new Intent(this, Activity_SearchResult.class);
		intent.putExtra("key", input);
		startActivity(intent);*/
	}

}