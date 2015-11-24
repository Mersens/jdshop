package com.jcwl.jdshop;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class TopActivity extends Activity {
	
	public static final int RedColor=10,NormalColor=20;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Constant.getBuyerInfo(this);
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
	
	public void setTopText(String text){
		((TextView)findViewById(R.id.top_text)).setText(text);
		
	}
	
	public void initTopView(String text){
		setTopText(text);
	}
	
	public void showMessageShort(String text){
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}
	
	public void showMessageLong(String text){
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}
	
	public boolean isConnectionWeb(){
		//return NetworkHandle.testNetToast(this);
		return true;
	}
	
	public void debugLog(String msg){
		//if(Constant.isDebug)System.out.println(msg);
	}
	
	
}
