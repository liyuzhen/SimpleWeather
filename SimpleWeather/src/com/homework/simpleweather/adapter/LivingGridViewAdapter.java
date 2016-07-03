package com.homework.simpleweather.adapter;

import java.util.List;

import com.homework.simpleweather.R;
import com.homework.simpleweather.entity.SuggestionInfo.EachSuggestion;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 生活指数模块的适配器
 */
public class LivingGridViewAdapter extends BaseAdapter {

	private List<EachSuggestion> mSuggestionList; // 生活指数容器
	private int[] iconIdArray = new int[] { R.drawable.main_living_item1, R.drawable.main_living_item2,
			R.drawable.main_living_item3, R.drawable.main_living_item4, R.drawable.main_living_item5,
			R.drawable.main_living_item6 }; // 图标数组
	private Context context;
	private LayoutInflater mInflater;

	public LivingGridViewAdapter(Context context, List<EachSuggestion> mSuggestionList) {
		this.mSuggestionList = mSuggestionList;
		this.context = context;
	}

	@Override
	public int getCount() {
		return mSuggestionList.size();
	}

	@Override
	public Object getItem(int position) {
		return mSuggestionList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		mInflater = LayoutInflater.from(context);
		convertView = mInflater.inflate(R.layout.main_gridview_item, null); // 加载布局
		ImageView icon = (ImageView) convertView.findViewById(R.id.iv_item_icon);
		TextView breifText = (TextView) convertView.findViewById(R.id.tv_item_breif);
		icon.setImageResource(iconIdArray[position]);
		breifText.setText(mSuggestionList.get(position).getBrief());
		return convertView;
	}
}
