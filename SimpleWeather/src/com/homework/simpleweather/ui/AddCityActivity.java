package com.homework.simpleweather.ui;

import java.util.ArrayList;
import java.util.List;

import com.homework.simpleweather.R;
import com.homework.simpleweather.adapter.AddRelatedCityAdapter;
import com.homework.simpleweather.database.DBManager;
import com.homework.simpleweather.entity.CityWeather;
import com.homework.simpleweather.utils.JsonParseUtil;
import com.homework.simpleweather.utils.MyNetworkUtil;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.util.Log;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 添加城市的界面
 */
public class AddCityActivity extends AppCompatActivity {

	private Toolbar mToolbar; // 工具栏
	private SearchView mSearchView; // 搜索栏
	private ListView mListView;// 显示查询结果数据列表
	private AddRelatedCityAdapter mAdapter; // 数据适配器
	private DBManager mDbManager; // 数据库管理者
	private List<String> mRelatedCityNames = new ArrayList<String>(); // 相关城市数据
	private LinearLayout mLLLoading; // 加载条
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				mRelatedCityNames.clear();
			} else if (msg.what == 1) {

			}
			mLLLoading.setVisibility(View.INVISIBLE); // 隐藏进度条
			mListView.setVisibility(View.VISIBLE); // 显示城市列表
			mAdapter.notifyDataSetChanged();
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_add);

		findViews();
		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 设置返回键
		getSupportActionBar().setTitle("添加城市");
		mSearchView.setIconified(false); // 设置不缩小为图标
		mSearchView.setQueryHint("请输入城市");// 设置提示文字
		setListener();
		mDbManager = new DBManager(this);
		mAdapter = new AddRelatedCityAdapter(this, mRelatedCityNames);
		mListView.setAdapter(mAdapter);

	}

	/**
	 * 设置监听器
	 */
	private void setListener() {
		mSearchView.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String arg0) {
				mListView.setVisibility(View.INVISIBLE); // 隐藏列表
				mLLLoading.setVisibility(View.VISIBLE); // 显示进度条
				getAllRelatedCityName(arg0);
				return true;
			}

			@Override
			public boolean onQueryTextChange(String arg0) {
				return false;
			}
		});

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String cityName = mRelatedCityNames.get(position).split(",")[0]; // 获取点击item的城市名
				if (mDbManager.find(cityName) != null) { // 查询是否已有城市
					Toast.makeText(AddCityActivity.this, "抱歉，不能重复添加城市", Toast.LENGTH_SHORT).show();
				} else {
					getDataFromNetwork(cityName);
					mSearchView.setQuery(cityName, false);
					mLLLoading.setVisibility(View.VISIBLE);
					mListView.setVisibility(View.INVISIBLE);
				}
			}
		});

		mListView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// 滑动取消搜索框焦点
				mSearchView.clearFocus();
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

			}
		});
	}

	public void getDataFromNetwork(String cityName) {
		// 异步从网络获取数据
		new AsyncTask<String, Void, CityWeather>() {

			@Override
			protected void onPostExecute(CityWeather cityWeather) {
				// 插入数据到数据库中
				mDbManager.insert(cityWeather);
				Intent intent = getIntent();
				Log.e("我问问", "intent.getIntExtra(SplashActivity.FIRST_IN, -1) = " + intent.getIntExtra(SplashActivity.FIRST_IN, -1));
				if (intent.getIntExtra(SplashActivity.FIRST_IN, -1) == SplashActivity.FIRST) {
					startActivity(new Intent(AddCityActivity.this, MainActivity.class));
				}else {
					// 给主界面返回城市名数据
					intent.putExtra("selected_city", cityWeather.getNowWeather().getCityName());
					setResult(0, intent);
				}
				finish();
			}

			@Override
			protected CityWeather doInBackground(String... params) {
				CityWeather cityWeather = new CityWeather();
				// 获取当日天气数据
				String strUrlNow = "https://api.thinkpage.cn/v3/weather/now.json?key=R13XUORJZR&location=" + params[0]
						+ "&language=zh-Hans&unit=c";
				String nowJson = MyNetworkUtil.getJsonData(strUrlNow);
				cityWeather.setNowWeather(JsonParseUtil.parseNowJson(nowJson));
				// 获取当日生活指数
				String strUrlSuggestion = "https://api.thinkpage.cn/v3/life/suggestion.json?key=R13XUORJZR&location="
						+ params[0] + "&language=zh-Hans";
				String suggestionJson = MyNetworkUtil.getJsonData(strUrlSuggestion);
				cityWeather.setSuggestionInfo(JsonParseUtil.parseSuggestionJson(suggestionJson));
				// 获取每日数据
				String strUrlDaily = "https://api.thinkpage.cn/v3/weather/daily.json?key=R13XUORJZR&location="
						+ params[0] + "&language=zh-Hans&unit=c&start=0&days=5";
				String dailyJson = MyNetworkUtil.getJsonData(strUrlDaily);
				cityWeather.setDaliyWeathers((JsonParseUtil.parseDaliyWeatherJson(dailyJson)));
				return cityWeather;
			}

		}.execute(cityName);
	}

	/**
	 * 从网络获取相关城市名字数据
	 */
	protected void getAllRelatedCityName(String arg0) {
		String strUrl = "https://api.thinkpage.cn/v3/location/search.json?key=R13XUORJZR&q=" + arg0;
		MySearchThread mySearchThread = new MySearchThread(strUrl);
		mySearchThread.start();
	}

	private void findViews() {
		mToolbar = (Toolbar) findViewById(R.id.toolbar);
		mSearchView = (SearchView) findViewById(R.id.sv_add_search);
		mListView = (ListView) findViewById(R.id.lv_add_cityList);
		mLLLoading = (LinearLayout) findViewById(R.id.ll_add_loading);
	}

	/**
	 * 响应Toolbar中的返回键
	 */
	@Override
	public Intent getSupportParentActivityIntent() {
		finish();
		return null;
	}

	private class MySearchThread extends Thread {
		private String strUrl;

		public MySearchThread(String strUrl) {
			this.strUrl = strUrl;
		}

		@Override
		public void run() {
			mRelatedCityNames.clear();
			List<String> dataList = JsonParseUtil.parseRelatedCityJson(MyNetworkUtil.getJsonData(strUrl));
			if (dataList == null) {
				mHandler.sendEmptyMessage(0);
			} else {
				mRelatedCityNames.addAll(dataList);
				mHandler.sendEmptyMessage(1);
			}
		}
	}

}
