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
 * ��ӭ����
 */
public class SplashActivity extends AppCompatActivity {
	/**
	 * @Fields FIRST_IN : TODO(��һ�仰�������������ʾʲô)
	 */
	public static final String FIRST_IN = "FirstIn";
	private TextView mTvTitle; // ����
	private TextView mTvAuthor; // ����
	private RelativeLayout mRlBackground;
	private boolean hasNoData = false; // ��־���ݿ���û������
	private DBManager mDBManager; // ���ݿ������
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
		// ��������
		mTvTitle.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/FangZheng.TTF"));
		mTvAuthor.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/FangZheng.TTF"));

		// �鿴��û������
		mCityNames = mDBManager.getAllCityName();
		hasNoData = mCityNames.size() == 0;
		
		// ���ö���
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
