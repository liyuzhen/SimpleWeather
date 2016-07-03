package com.homework.simpleweather.adapter;

import java.sql.ParameterMetaData;
import java.util.List;

import com.homework.simpleweather.R;
import com.homework.simpleweather.utils.TransferPxAndDp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * ��ӳ��н����������
 */
public class AddRelatedCityAdapter extends BaseAdapter {
	private List<String> relatedCityNames; // ��س�����
	private LayoutInflater inflater; // ���ּ�����
	private Context context;

	public AddRelatedCityAdapter(Context context, List<String> relatedCityNames) {
		this.context = context;
		this.relatedCityNames = relatedCityNames;
	}

	@Override
	public int getCount() {
		return relatedCityNames.size();
	}

	@Override
	public Object getItem(int position) {
		return relatedCityNames.get(position);
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
			convertView = inflater.inflate(R.layout.add_listview_item, null);
		}
		// ��ʼ���ؼ�����������
		TextView tv = (TextView) convertView.findViewById(R.id.tv_item_related);
		tv.setText(relatedCityNames.get(position));
		return convertView;
	}

}
