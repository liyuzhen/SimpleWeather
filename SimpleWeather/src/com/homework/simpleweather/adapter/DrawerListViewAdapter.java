package com.homework.simpleweather.adapter;

import java.util.List;

import com.homework.simpleweather.R;
import com.homework.simpleweather.entity.TodayWeather;
import com.homework.simpleweather.ui.MainActivity;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/*
 * �����б�������
 */
public class DrawerListViewAdapter extends BaseAdapter {
	private LayoutInflater inflater; // ���ּ�����
	private Context context;
	private List<TodayWeather> todayWeathers; // ������������
	private int[] smallWeatherIcon; // ����ͼ���Ӧid

	public DrawerListViewAdapter(Context context, List<TodayWeather> todayWeathers, int[] smallWeatherIcon) {
		this.context = context;
		this.todayWeathers = todayWeathers;
		this.smallWeatherIcon = smallWeatherIcon;
	}

	@Override
	public int getCount() {
		return todayWeathers.size();
	}

	@Override
	public Object getItem(int position) {
		return todayWeathers.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			inflater = LayoutInflater.from(context);
			// ���ز���
			convertView = inflater.inflate(R.layout.main_drawer_item, null);
		}
		// ��ʼ���ؼ�
		ImageView ivIcon = (ImageView) convertView.findViewById(R.id.iv_drawer_icon);
		TextView tvCityName = (TextView) convertView.findViewById(R.id.tv_drawer_cityName);
		TextView tvTemp = (TextView) convertView.findViewById(R.id.tv_drawer_temp);
		// �����������
		ivIcon.setImageResource(smallWeatherIcon[todayWeathers.get(position).getWeatherCode()]);
		tvCityName.setText(todayWeathers.get(position).getCityName());
		tvTemp.setText(todayWeathers.get(position).getLow() + "~" + todayWeathers.get(position).getHigh() + "��");
		return convertView;
	}
}
