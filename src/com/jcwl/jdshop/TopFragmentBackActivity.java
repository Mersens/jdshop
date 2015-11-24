package com.jcwl.jdshop;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class TopFragmentBackActivity extends FragmentActivity {
	private int BACK=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
	
	
	public void onTopClick(View view){
		switch (view.getId()) {
		case R.id.top_left_btn:
			finish();
 			break;
		case R.id.top_right_btn:
			break;

		default:
			break;
		}
	}
	
	public void showMessageShort(String text){
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}
	
	public void showMessageLong(String text){
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}
	
	public void setTopText(String text){
		((TextView)findViewById(R.id.top_text)).setText(text);
	}
	
	private boolean backToFinish(){
		if(BACK==0){
			BACK++;
			Toast.makeText(TopFragmentBackActivity.this,"再按一次退出", Toast.LENGTH_SHORT).show();
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
	
}
