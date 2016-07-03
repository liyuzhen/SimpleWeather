package com.homework.simpleweather.ui;

import java.util.List;

import com.homework.simpleweather.R;
import com.homework.simpleweather.database.DBManager;
import com.homework.simpleweather.entity.CityWeather;
import com.homework.simpleweather.utils.JsonParseUtil;
import com.homework.simpleweather.utils.MyNetworkUtil;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 欢迎界面
 */
public class SplashActivity extends AppCompatActivity {
	/**
	 * @Fields FIRST_IN : TODO(用一句话描述这个变量表示什么)
	 */
	public static final String FIRST_IN = "FirstIn";
	private TextView mTvTitle; // 标题
	private TextView mTvAuthor; // 作者
	private RelativeLayout mRlBackground;
	private boolean hasNoData = false; // 标志数据库有没有数据
	private DBManager mDBManager; // 数据库操作类
	public static final int FIRST = 1;

	private List<String> mCityNames;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_splash);

		mRlBackground = (RelativeLayout) findViewById(R.id.rl_splash_bg);
		mTvTitle = (TextView) findViewById(R.id.tv_splash_title);
		mTvAuthor = (TextView) findViewById(R.id.tv_splash_author);
		mDBManager = new DBManager(this);
		// 设置字体
		mTvTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/FangZheng.TTF"));
		mTvAuthor.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/FangZheng.TTF"));

		// 查看有没有数据
		mCityNames = mDBManager.getAllCityName();
		hasNoData = mCityNames.size() == 0;
		
		// 设置动画
		Animation scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_splash);
		scaleAnimation.setFillAfter(true);
		mRlBackground.startAnimation(scaleAnimation);
		scaleAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				if (!hasNoData) {
					Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
					startActivity(mainIntent);
					finish();
				} else {
					Intent addIntent = new Intent(SplashActivity.this, AddCityActivity.class);
					addIntent.putExtra(FIRST_IN, FIRST);
					startActivity(addIntent);
					finish();
				}

			}
		});

	}
}
