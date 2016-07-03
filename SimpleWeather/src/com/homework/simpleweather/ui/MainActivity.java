package com.homework.simpleweather.ui;

import java.util.ArrayList;
import java.util.List;

import com.homework.simpleweather.R;
import com.homework.simpleweather.adapter.DailyListViewAdapter;
import com.homework.simpleweather.adapter.DrawerListViewAdapter;
import com.homework.simpleweather.adapter.LivingGridViewAdapter;
import com.homework.simpleweather.database.DBManager;
import com.homework.simpleweather.entity.CityWeather;
import com.homework.simpleweather.entity.DailyWeather;
import com.homework.simpleweather.entity.NowWeather;
import com.homework.simpleweather.entity.SuggestionInfo;
import com.homework.simpleweather.entity.SuggestionInfo.EachSuggestion;
import com.homework.simpleweather.entity.TodayWeather;
import com.homework.simpleweather.utils.JsonParseUtil;
import com.homework.simpleweather.utils.MyNetworkUtil;
import com.homework.simpleweather.utils.TransferPxAndDp;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
	// 工具栏模块
	private ActionBarDrawerToggle mActionBarDrawerToggle;
	private Toolbar mToolbar; // 工具栏
	private TextView mTvCityName; // 城市名
	private LinearLayout mDotsLayout;

	// 抽屉模块
	private DrawerLayout mDrawerLayout; // 抽屉
	private ListView mListView; // 城市列表
	private DrawerListViewAdapter mDrawerAdapter; // 城市列表数据适配器

	// 滑动视图模块
	private ViewPager mViewPager;
	private LayoutInflater mInflater;
	private PagerAdapter mPagerAdapter;

	// 数据模块
	private List<ImageView> dotsList; // 指示点容器
	private List<View> mViewList = new ArrayList<View>();; // 布局容器
	private List<TodayWeather> mTodayWeathers = new ArrayList<TodayWeather>(); // 所有城市当日天气容器

	// 数据库模块
	private DBManager mDbManager;

	private int[] bigWeatherIcon = new int[] { R.drawable.w0, R.drawable.w1, R.drawable.w2, R.drawable.w3,
			R.drawable.w4, R.drawable.w5, R.drawable.w6, R.drawable.w7, R.drawable.w8, R.drawable.w9, R.drawable.w10,
			R.drawable.w11, R.drawable.w12, R.drawable.w13, R.drawable.w14, R.drawable.w15, R.drawable.w16,
			R.drawable.w17, R.drawable.w18, R.drawable.w19, R.drawable.w20, R.drawable.w21, R.drawable.w22,
			R.drawable.w23, R.drawable.w24, R.drawable.w25, R.drawable.w26, R.drawable.w27, R.drawable.w28,
			R.drawable.w29, R.drawable.w30, R.drawable.w31, R.drawable.w32, R.drawable.w33, R.drawable.w34,
			R.drawable.w35, R.drawable.w36, R.drawable.w37, R.drawable.w38, R.drawable.w99 }; // 图片id
	private int[] smallWeatherIcon = new int[] { R.drawable.ww0, R.drawable.ww1, R.drawable.ww2, R.drawable.ww3,
			R.drawable.ww4, R.drawable.ww5, R.drawable.ww6, R.drawable.ww7, R.drawable.ww8, R.drawable.ww9,
			R.drawable.ww10, R.drawable.ww11, R.drawable.ww12, R.drawable.ww13, R.drawable.ww14, R.drawable.ww15,
			R.drawable.ww16, R.drawable.ww17, R.drawable.ww18, R.drawable.ww19, R.drawable.ww20, R.drawable.ww21,
			R.drawable.ww22, R.drawable.ww23, R.drawable.ww24, R.drawable.ww25, R.drawable.ww26, R.drawable.ww27,
			R.drawable.ww28, R.drawable.ww29, R.drawable.ww30, R.drawable.ww31, R.drawable.ww32, R.drawable.ww33,
			R.drawable.ww34, R.drawable.ww35, R.drawable.ww36, R.drawable.ww37, R.drawable.ww38, R.drawable.ww99, };
	private String[] mLivingItemName = new String[] { "晾晒建议", "洗车建议", "穿衣建议", "运动建议", "带伞建议", "紫外线防护建议" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 全屏设置
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);

		mDbManager = new DBManager(this);
		findViews();
		initToolbar();
		mDrawerAdapter = new DrawerListViewAdapter(this, mTodayWeathers, smallWeatherIcon);
		mListView.setAdapter(mDrawerAdapter);
		setListener();
		initCityArrayData();
		initPagesInViewPager();
		addDots();
	}

	/**
	 * 初始化工具栏
	 */
	private void initToolbar() {
		setSupportActionBar(mToolbar);
		mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open,
				R.string.close);
		mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
		getSupportActionBar().setHomeButtonEnabled(true); // 设置返回键可用
		getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 设置显示返回
		mActionBarDrawerToggle.syncState(); // 该方法将自动与actionbar关联，替换返回图标
	}

	/**
	 * 在ViewPager内添加布局
	 */
	private void initPagesInViewPager() {
		mViewList.clear(); // 清空内部所有数据
		mInflater = LayoutInflater.from(this);
		// 加载所有布局
		for (int i = 0; i < mTodayWeathers.size(); i++) {
			ScrollView eachPage = (ScrollView) mInflater.inflate(R.layout.viewpager_page, null);
			mViewList.add(eachPage);
			initEachPageData(eachPage, mTodayWeathers.get(i).getCityName());
		}

		mPagerAdapter = new PagerAdapter() {

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				return arg0 == arg1;
			}

			@Override
			public int getCount() {
				return mTodayWeathers.size();
			}

			@Override
			public void destroyItem(ViewGroup container, int position, Object object) {
				container.removeView(mViewList.get(position));
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				container.addView(mViewList.get(position));
				return mViewList.get(position);
			}
		};

		mViewPager.setAdapter(mPagerAdapter);
		mViewPager.setOffscreenPageLimit(3); // 设置预加载数量

		// 默认第一页
		mTvCityName.setText(mTodayWeathers.get(0).getCityName());
		mViewPager.setCurrentItem(0);
	}

	/**
	 * 添加指示点
	 */
	private void addDots() {
		mDotsLayout.removeAllViews();
		dotsList = new ArrayList<ImageView>();

		for (int i = 0; i < mTodayWeathers.size(); i++) {
			ImageView dotImageView = new ImageView(this);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT);
			if (i != 0) { // 设置指示点间距8dp
				params.setMargins(TransferPxAndDp.dp2Px(this, 8), 0, 0, 0);
			}
			dotImageView.setLayoutParams(params);
			dotImageView.setBackgroundResource(R.drawable.iv_header_selector); // 设置指示点
			dotImageView.setSelected(false);
			dotsList.add(dotImageView);
			mDotsLayout.addView(dotImageView);
		}
		dotsList.get(0).setSelected(true); // 默认第一个选中
	}

	/**
	 * 改变指示点的状态
	 */
	private void changeDotsState(int position) {
		for (ImageView iv : dotsList) {
			iv.setSelected(false);
		}
		dotsList.get(position).setSelected(true);
	}

	/**
	 * 初始化城市列表数据
	 */
	private void initCityArrayData() {
		mTodayWeathers.clear();
		mTodayWeathers.addAll(mDbManager.getAllTodayWeather()); // 添加入数据库中所有城市当日天气数据
	}

	private void findViews() {
		mToolbar = (Toolbar) findViewById(R.id.toolbar);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_main);
		mListView = (ListView) findViewById(R.id.lv_main_cityList);
		mDotsLayout = (LinearLayout) findViewById(R.id.ll_header_dots);
		mTvCityName = (TextView) findViewById(R.id.tv_header_cityName);
		mViewPager = (ViewPager) findViewById(R.id.vp_main_pages);
	}

	/**
	 * 设置监听器
	 */
	private void setListener() {
		// 给侧滑栏的listview设置监听
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// 获取点击的城市名字
				mDrawerLayout.closeDrawer(Gravity.START);// 点击后关闭抽屉
				mViewPager.setCurrentItem(position); // 设置对应页码显示的界面
			}
		});

		// 长按监听器
		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, long id) {
				new AlertDialog.Builder(MainActivity.this).setTitle("是否删除所选城市?")
						.setNegativeButton("取消", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 不做任何操作
					}
				}).setPositiveButton("确定", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (mTodayWeathers.size() == 1) {
							Toast.makeText(MainActivity.this, "需保留一个城市信息", Toast.LENGTH_SHORT).show();
						} else {
							TextView tvCityName = (TextView) view.findViewById(R.id.tv_drawer_cityName);// 获取城市名
							mDbManager.delete(tvCityName.getText().toString()); // 删除城市数据
							mViewList.remove(position);// 移除城市对应页卡
							mTodayWeathers.remove(position); // 移除对应数据
							mPagerAdapter.notifyDataSetChanged();
							mDrawerAdapter.notifyDataSetChanged();
							addDots();// 重新添加指示点
							mViewPager.setCurrentItem(0);
							mTvCityName.setText(mTodayWeathers.get(0).getCityName());
						}
					}
				}).show();
				return true;
			}
		});

		// viewpager设置监听器，监听页码变化
		mViewPager.addOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				String selectedCityName = mTodayWeathers.get(position).getCityName(); // 获取对应城市名
				mTvCityName.setText(selectedCityName);
				changeDotsState(position);
			}

			@Override
			public void onPageScrolled(int position, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int position) {
			}
		});

		//

	}

	/**
	 * 从数据库获取数据，并设置
	 */
	private void initEachPageData(View eachPage, String cityName) {
		CityWeather cityWeather = mDbManager.find(cityName);
		NowWeather nowWeather = cityWeather.getNowWeather();
		SuggestionInfo suggestionInfo = cityWeather.getSuggestionInfo();
		List<DailyWeather> dailyWeathers = cityWeather.getDaliyWeathers();

		// 获取名字对应的布局
		TextView tvTemp = (TextView) eachPage.findViewById(R.id.tv_main_temperature);
		TextView tvWeatherText = (TextView) eachPage.findViewById(R.id.tv_main_weatherText);
		ImageView ivWeatherIcon = (ImageView) eachPage.findViewById(R.id.iv_main_icon);
		GridView gvLiving = (GridView) eachPage.findViewById(R.id.gv_main_living);
		ListView lvDaily = (ListView) eachPage.findViewById(R.id.lv_main_dailyWeather);
		TextView tvDetailFeelsLike = (TextView) eachPage.findViewById(R.id.tv_detail_feelsLike);
		TextView tvDetailVisibility = (TextView) eachPage.findViewById(R.id.tv_detail_visibility);
		TextView tvDetailHumidity = (TextView) eachPage.findViewById(R.id.tv_detail_humidity);
		TextView tvDetailWindDirection = (TextView) eachPage.findViewById(R.id.tv_detail_windDirection);
		TextView tvDetailScale = (TextView) eachPage.findViewById(R.id.tv_detail_windScale);
		TextView tvTodayTemp = (TextView) eachPage.findViewById(R.id.tv_foot_today_temp);
		TextView tvTomoTemp = (TextView) eachPage.findViewById(R.id.tv_foot_tomo_temp);
		ImageView ivTodayIcon = (ImageView) eachPage.findViewById(R.id.iv_foot_today_iconDay);
		ImageView ivTomoIcon = (ImageView) eachPage.findViewById(R.id.iv_foot_tomo_iconDay);

		// 设置相关数据
		tvTemp.setText("" + nowWeather.getTemperature() + "°");
		tvWeatherText.setText(nowWeather.getWeatherText());
		ivWeatherIcon.setImageResource(bigWeatherIcon[nowWeather.getWeatherCode()]);
		tvTemp.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/roboto_thin.ttf")); // 改变温度文本的字体
		tvDetailFeelsLike.setText(nowWeather.getFeelsLike() + " °");
		tvDetailVisibility.setText(nowWeather.getVisibility() + " km");
		tvDetailHumidity.setText(nowWeather.getHumidity() + "%");
		tvDetailWindDirection.setText(nowWeather.getWindDirection() + "风");
		tvDetailScale.setText(nowWeather.getWindScale() + "级");
		tvTodayTemp.setText(dailyWeathers.get(0).getLow() + "~" + dailyWeathers.get(0).getHigh() + "℃");
		tvTomoTemp.setText(dailyWeathers.get(1).getLow() + "~" + dailyWeathers.get(1).getHigh() + "℃");
		ivTodayIcon.setImageResource(smallWeatherIcon[dailyWeathers.get(0).getCodeDay()]);
		ivTomoIcon.setImageResource(smallWeatherIcon[dailyWeathers.get(1).getCodeDay()]);

		// 创建生活指数容器
		final List<EachSuggestion> suggestionList = new ArrayList<SuggestionInfo.EachSuggestion>();
		// 添加数据
		suggestionList.add(suggestionInfo.getAiring());
		suggestionList.add(suggestionInfo.getCarWashing());
		suggestionList.add(suggestionInfo.getDressing());
		suggestionList.add(suggestionInfo.getSport());
		suggestionList.add(suggestionInfo.getUmbrella());
		suggestionList.add(suggestionInfo.getUv());

		gvLiving.setAdapter(new LivingGridViewAdapter(MainActivity.this, suggestionList));

		// 设置每一个item的监听器
		gvLiving.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// 加载对话框对应的布局
				LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(MainActivity.this)
						.inflate(R.layout.dialog_view, null);
				TextView tvName = (TextView) linearLayout.findViewById(R.id.tv_dialog_name);
				TextView tvDetail = (TextView) linearLayout.findViewById(R.id.tv_dialog_detail);
				tvName.setText(mLivingItemName[position]);
				tvDetail.setText(suggestionList.get(position).getDetails());
				AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).setView(linearLayout).show();
				// 设置对话框宽度
				android.view.WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
				params.width = TransferPxAndDp.dp2Px(MainActivity.this, 250); // 宽度250dp
				dialog.getWindow().setAttributes(params);
			}
		});

		lvDaily.setAdapter(new DailyListViewAdapter(MainActivity.this, dailyWeathers, smallWeatherIcon));
	}

	/**
	 * 获取城市对应序号
	 */
	private int getPositionOfCity(String cityName) {
		int position = 0;
		for (TodayWeather todayWeather : mTodayWeathers) {
			if (todayWeather.getCityName().equals(cityName)) {
				break;
			}
			position++;
		}
		return position;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		new MenuInflater(this).inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item_addCity:
			Intent addCityIntent = new Intent(this, AddCityActivity.class);
			startActivityForResult(addCityIntent, 0);
			break;
		default:
			break;
		}
		return true;
	}

	/*
	 * 处理添加城市界面返回的值
	 */
	@Override
	protected void onActivityResult(int arg0, int arg1, Intent intent) {
		if (intent != null) {
			String selectedCity = intent.getStringExtra("selected_city");
			initCityArrayData();
			initPagesInViewPager();
			addDots();
			mViewPager.setCurrentItem(getPositionOfCity(selectedCity));
			mDrawerAdapter.notifyDataSetChanged();
		}
	}

}
