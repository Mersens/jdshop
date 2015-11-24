package com.jcwl.jdshop;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class BackActivity extends Activity {
	private int BACK=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Constant.getBuyerInfo(this);
	}
	private boolean backToFinish(){
		if(BACK==0){
			BACK++;
			Toast.makeText(BackActivity.this,"再按一次退出", Toast.LENGTH_SHORT).show();
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(1500);
						BACK--;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();
			return false;
		}else if(BACK==1){
			return true;
		}
		return true;
	}
	
	@Override
	public void onBackPressed() {
		if(backToFinish())finish();
	}
	
	public void setTopText(String text){
		//((TextView)findViewById(R.id.top_text)).setText(text);
	}
	
	
}