/**  
 * @Title: DailyListViewAdapter.java 
 * @Package com.homework.simpleweather.adapter 
 * @Description: TODO(��һ�仰�������ļ���ʲô)
 */
package com.homework.simpleweather.adapter;

import java.util.List;

import com.homework.simpleweather.R;
import com.homework.simpleweather.entity.DailyWeather;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * ����������ÿ����������
 */
public class DailyListViewAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater inflater; // ���ּ�����
	private List<DailyWeather> dailyWeathers; // ����ÿ������
	private int[] smallWeatherIcon; // ����ͼ���Ӧid����

	public DailyListViewAdapter(Context context, List<DailyWeather> dailyWeathers, int[] smallWeatherIcon) {
		this.context = context;
		this.dailyWeathers = dailyWeathers;
		this.smallWeatherIcon = smallWeatherIcon;
	}

	@Override
	public int getCount() {
		return dailyWeathers.size();
	}

	@Override
	public Object getItem(int position) {
		return dailyWeathers.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.main_listview_item, null);
		}
		// ��ʼ���ؼ�
		TextView tvDate = (TextView) convertView.findViewById(R.id.tv_daily_date);
		TextView tvTemp = (TextView) convertView.findViewById(R.id.tv_daily_temp);
		ImageView ivIconDay = (ImageView) convertView.findViewById(R.id.iv_daily_iconDay);
		ImageView ivIconNight = (ImageView) convertView.findViewById(R.id.iv_daily_iconNight);
		TextView tvTextDay = (TextView) convertView.findViewById(R.id.tv_daily_textDay);
		TextView tvTextNight = (TextView) convertView.findViewById(R.id.tv_daily_textNight);
		// ��������
		tvDate.setText("" + dailyWeathers.get(position).getDate());
		tvTemp.setText(dailyWeathers.get(position).getLow() + "~" + dailyWeathers.get(position).getHigh() + "��");
		ivIconDay.setImageResource(smallWeatherIcon[dailyWeathers.get(position).getCodeDay()]);
		ivIconNight.setImageResource(smallWeatherIcon[dailyWeathers.get(position).getCodeNight()]);
		tvTextDay.setText(dailyWeathers.get(position).getTextDay());
		tvTextNight.setText(dailyWeathers.get(position).getTextNight());
		return convertView;
	}
}
