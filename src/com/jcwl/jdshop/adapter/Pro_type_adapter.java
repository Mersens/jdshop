package com.jcwl.jdshop.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcwl.jdshop.R;
import com.jcwl.jdshop.model.Type;


public class Pro_type_adapter extends BaseAdapter {
	// ����Context
		private LayoutInflater mInflater;
	    private ArrayList<Type> list;
	    private Context context;
	    private Type type;
		public Pro_type_adapter(Context context,ArrayList<Type> list){
			mInflater=LayoutInflater.from(context);
			this.list=list;
			this.context=context;
		}
		
		@Override
		public int getCount() {
			if(list!=null&&list.size()>0)
				return list.size();
			else
			    return 0;
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			final MyView view;
			if(convertView==null){
				view=new MyView();
				convertView=mInflater.inflate(R.layout.list_pro_type_item, null);
				view.icon=(ImageView)convertView.findViewById(R.id.typeicon);
				view.name=(TextView)convertView.findViewById(R.id.typename);
				convertView.setTag(view);
			}else{
				view=(MyView) convertView.getTag();
			}
			if(list!=null&&list.size()>0)
			{
				type=list.get(position);
				view.name.setText(type.getTypename());
				if(type.getTypename().contains("���԰칫")){
					view.icon.setBackgroundResource(R.drawable.diannao);
				}
				else if(type.getTypename().contains("������ױ")){
					view.icon.setBackgroundResource(R.drawable.huazhuang);
				}
				else if(type.getTypename().contains("Ьѥ���")){
					view.icon.setBackgroundResource(R.drawable.nvxie);
				}
				else if(type.getTypename().contains("����Ůװ")){
					view.icon.setBackgroundResource(R.drawable.nvzhuang);
				}
				else if(type.getTypename().contains("ͼ��")){
					view.icon.setBackgroundResource(R.drawable.shuji);
				}
				else if(type.getTypename().contains("�������")){
					view.icon.setBackgroundResource(R.drawable.wanju);
				}
				else if(type.getTypename().contains("������Ʒ")){
					view.icon.setBackgroundResource(R.drawable.yingshi);
				}
				else if(type.getTypename().contains("���÷���")){
					view.icon.setBackgroundResource(R.drawable.yiyong);
				}
				else if(type.getTypename().contains("Ʒ����װ")){
					view.icon.setBackgroundResource(R.drawable.nanzhuang);
				}
				else if(type.getTypename().contains("��������")){
					view.icon.setBackgroundResource(R.drawable.neiyi);
				}
				else if(type.getTypename().contains("���õ���")){
					view.icon.setBackgroundResource(R.drawable.dianqi);
				}
				else if(type.getTypename().contains("�ֻ�����")){
					view.icon.setBackgroundResource(R.drawable.shouji);
				}
				else{
					view.icon.setBackgroundResource(R.drawable.nvzhuang);
				}
			}
			
	        return convertView;
		}

		
		private class MyView{
			private ImageView icon;		
			private TextView name;
		}
		
}
