package com.jcwl.jdshop.b;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.jcwl.jdshop.R;
import com.jcwl.jdshop.TopFragmentBackActivity;
import com.jcwl.jdshop.fragment.Fragment_pro_type;

public class Activity_B extends TopFragmentBackActivity{
	private String toolsList[];
	private TextView toolsTextViews[];
	private View views[];
	private LayoutInflater inflater;
	private ScrollView scrollView;
	private int scrllViewWidth = 0, scrollViewMiddle = 0;
	private ViewPager shop_pager;
	private int currentItem=0;
	private ShopAdapter shopAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_b);
		scrollView=(ScrollView) findViewById(R.id.tools_scrlllview);
		shopAdapter=new ShopAdapter(getSupportFragmentManager());
		inflater=LayoutInflater.from(this);
		showToolsView();
		initPager();
	}
	
	/**
	 * ��̬������ʾitems�е�textview
	 */
	private void showToolsView() {
		toolsList=new String[]{"���÷���","����Ůװ","Ʒ����װ","��������","���õ���","�ֻ�����","���԰칫","������ױ","ĸӤƵ��","ʳ������","��ˮ����","�ҾӼҷ�","������Ʒ","Ьѥ���","�˶�����","ͼ��","�������","�ӱ�","�Ӽ�����","�鱦��Ʒ","������Ʒ","�Ҿ߽���","������Ȥ","Ӫ������","�ݳ���Ʒ","�������","���γ���"};
		LinearLayout toolsLayout=(LinearLayout) findViewById(R.id.tools);
		toolsTextViews=new TextView[toolsList.length];
		views=new View[toolsList.length];
		
		for (int i = 0; i < toolsList.length; i++) {
			View view=inflater.inflate(R.layout.item_b_top_nav_layout, null);
			view.setId(i);
			view.setOnClickListener(toolsItemListener);
			TextView textView=(TextView) view.findViewById(R.id.text);
			textView.setText(toolsList[i]);
			toolsLayout.addView(view);
			toolsTextViews[i]=textView;
			views[i]=view;
		}
		changeTextColor(0);
	}
	
	private View.OnClickListener toolsItemListener =new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			shop_pager.setCurrentItem(v.getId());
		}
	};
	
	
	
	/**
     * initPager<br/>
     * ��ʼ��ViewPager�ؼ��������
     */
	private void initPager() {
		shop_pager=(ViewPager)findViewById(R.id.goods_pager);
		shop_pager.setAdapter(shopAdapter);		
		shop_pager.setOnPageChangeListener(onPageChangeListener);
	}
	
	/**
	 * OnPageChangeListener<br/>
	 * ����ViewPagerѡ��仯�µ��¼�
	 */
	
	private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
		@Override
		public void onPageSelected(int arg0) {
			if(shop_pager.getCurrentItem()!=arg0)shop_pager.setCurrentItem(arg0);
			if(currentItem!=arg0){
				changeTextColor(arg0);
				changeTextLocation(arg0);
			}
			currentItem=arg0;
		}
		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}
		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	};
	
	
	/**
	 * ViewPager ����ѡ�
	 * @author Administrator
	 *
	 */
	private class ShopAdapter extends FragmentPagerAdapter {
		public ShopAdapter(FragmentManager fm) {
			super(fm);
		}
		@Override
		public Fragment getItem(int arg0) {
			Fragment fragment =new Fragment_pro_type();
			Bundle bundle=new Bundle();
			String str=toolsList[arg0];
			bundle.putString("typename",str);
			fragment.setArguments(bundle);
			return fragment;
		}
		
		@Override
		public int getCount() {
			return toolsList.length;
		}
	}
	
	
	/**
	 * �ı�textView����ɫ
	 * @param id
	 */
	private void changeTextColor(int id) {
		for (int i = 0; i < toolsTextViews.length; i++) {
			if(i!=id){
			   toolsTextViews[i].setBackgroundResource(android.R.color.transparent);
			   toolsTextViews[i].setTextColor(0xff000000);
			}
		}
		toolsTextViews[id].setBackgroundResource(android.R.color.white);
		toolsTextViews[id].setTextColor(0xffff5d5e);
	}
	
	
	/**
	 * �ı���Ŀλ��
	 * 
	 * @param clickPosition
	 */
	private void changeTextLocation(int clickPosition) {
		
		int x = (views[clickPosition].getTop() - getScrollViewMiddle() + (getViewheight(views[clickPosition]) / 2));
		scrollView.smoothScrollTo(0, x);
	}
	
	/**
	 * ����scrollview���м�λ��
	 * 
	 * @return
	 */
	private int getScrollViewMiddle() {
		if (scrollViewMiddle == 0)
			scrollViewMiddle = getScrollViewheight() / 2;
		return scrollViewMiddle;
	}
	
	/**
	 * ����ScrollView�Ŀ��
	 * 
	 * @return
	 */
	private int getScrollViewheight() {
		if (scrllViewWidth == 0)
			scrllViewWidth = scrollView.getBottom() - scrollView.getTop();
		return scrllViewWidth;
	}
	
	/**
	 * ����view�Ŀ��
	 * 
	 * @param view
	 * @return
	 */
	private int getViewheight(View view) {
		return view.getBottom() - view.getTop();
	}
	
}

