package com.jcwl.jdshop;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;

import com.jcwl.jdshop.a.Activity_A;
import com.jcwl.jdshop.b.Activity_B;
import com.jcwl.jdshop.c.Activity_C;
import com.jcwl.jdshop.d.Activity_D;
import com.jcwl.jdshop.e.Activity_E;

public class MainActivity extends TabActivity {
	
	private static TabHost tabHost;
	private static ImageView a;
	private static ImageView b;
	private static ImageView c;
	private static ImageView d;
	private static ImageView e;
	private String CurrentNum="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //if(NetworkHandle.testNetUndo(this))new DetectionTask().execute(Constant.currentVersion);
        initView();
       
    }
    

	private void initView() {
		tabHost = getTabHost();
        tabHost.addTab(tabHost.newTabSpec("a").setIndicator("A").setContent(new Intent(this, Activity_A.class)));
        tabHost.addTab(tabHost.newTabSpec("b").setIndicator("B").setContent(new Intent(this, Activity_B.class)));
        tabHost.addTab(tabHost.newTabSpec("c").setIndicator("C").setContent(new Intent(this, Activity_C.class)));
        tabHost.addTab(tabHost.newTabSpec("d").setIndicator("D").setContent(new Intent(this, Activity_D.class)));
        tabHost.addTab(tabHost.newTabSpec("e").setIndicator("E").setContent(new Intent(this, Activity_E.class)));
        a=(ImageView) findViewById(R.id.radio_a);
        b=(ImageView) findViewById(R.id.radio_b);
        c=(ImageView) findViewById(R.id.radio_c);
        d=(ImageView) findViewById(R.id.radio_d);
        e=(ImageView) findViewById(R.id.radio_e);
	}

	public void onNavClick(View v){
		toNormalNav();
		switch (v.getId()) {
		case R.id.radio_a:
			a.setBackgroundResource(R.drawable.nav_a_click);
			tabHost.setCurrentTabByTag("a");
			break;
		case R.id.radio_b:
			toClickNav(b, R.drawable.nav_b_click);
			tabHost.setCurrentTabByTag("b");
			break;
		case R.id.radio_c:
			toClickNav(c, R.drawable.nav_c_click);
			tabHost.setCurrentTabByTag("c");
			break;
		case R.id.radio_d:
			toClickNav(d, R.drawable.nav_d_click);
			tabHost.setCurrentTabByTag("d");
			break;
		case R.id.radio_e:
			toClickNav(e, R.drawable.nav_e_click);
			tabHost.setCurrentTabByTag("e");
			break;

		default:
			break;
		}
	}

	
	public static void goToCurrentTab(int currentitem){
		switch (currentitem) {
		case 2:
			toNormalNav();
			toClickNav(b, R.drawable.nav_b_click);
			tabHost.setCurrentTabByTag("b");
			break;
		default:
			break;
		}
	}
	
	
	private static void toNormalNav(){
		a.setBackgroundResource(R.drawable.nav_a_normal);
		b.setImageResource(R.drawable.nav_b_normal);
		c.setImageResource(R.drawable.nav_c_normal);
		d.setImageResource(R.drawable.nav_d_normal);
		e.setImageResource(R.drawable.nav_e_normal);
	}
	
	private static void toClickNav(ImageView view,int drawable){
		view.setImageResource(drawable);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	};
	
}