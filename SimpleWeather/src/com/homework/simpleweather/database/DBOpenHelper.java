package com.homework.simpleweather.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 创建数据库，构建表的类
 */
public class DBOpenHelper extends SQLiteOpenHelper {
	/**
	 * @Fields WHICH_DAY : TODO(用一句话描述这个变量表示什么)
	 */
	public static final String WHICH_DAY = "which_day";
	/**
	 * @Fields TABLE_DALIY_WEATHER : TODO(用一句话描述这个变量表示什么)
	 */
	public static final String TABLE_DALIY_WEATHER = "table_daliy_weather";
	// 数据库名字
	private static final String DATABASE_NAME = "WeatherDB.db";
	// 数据库版本数
	private static final int DATABASE_VERSION = 1;
	// 表名
	public static final String TABLE_CITY_WEATHER = "city_weather";
	public static final String ID = "_id";
	public static final String CITY_NAME = "city_name";
	public static final String WEATHER_TEXT = "weather_text";
	public static final String WEATHER_CODE = "weather_code";
	public static final String TEMPERATURE = "temperature";
	public static final String FEELS_LIKE = "feels_like";
	public static final String HUMIDITY = "humidity";
	public static final String VISIBILITY = "visibility";
	public static final String WIND_DIRECTION = "wind_direction";
	public static final String WIND_SPEED = "wind_speed";
	public static final String WIND_SCALE = "wind_scale";
	public static final String TABLE_LIVING_SUGGESTION = "living_suggestion";
	public static final String AIRING_BRIEF = "airing_brief";
	public static final String AIRING_DETAILS = "airing_details";
	public static final String CAR_WASHING_BRIEF = "car_washing_brief";
	public static final String CAR_WASHING_DETAILS = "car_washing_details";
	public static final String DRESSING_BRIEF = "dressing_brief";
	public static final String DRESSING_DETAILS = "dressing_details";
	public static final String SPORT_BRIEF = "sport_brief";
	public static final String SPORT_DETAILS = "sport_details";
	public static final String UMBRELLA_BRIEF = "umbrella_brief";
	public static final String UMBRELLA_DETAILS = "umbrella_details";
	public static final String UV_BRIEF = "uv_brief";
	public static final String UV_DETAILS = "uv_details";
	public static final String PRECIP = "precip";
	public static final String LOW = "low";
	public static final String HIGH = "high";
	public static final String CODE_NIGHT = "code_night";
	public static final String TEXT_NIGHT = "text_night";
	public static final String CODE_DAY = "code_day";
	public static final String TEXT_DAY = "text_day";
	public static final String DATE = "date";
	public static final String DAILY = "daily";

	public DBOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// 创建城市信息表
		db.execSQL("CREATE TABLE " + TABLE_CITY_WEATHER + "(" + ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
				+ CITY_NAME + " TEXT," + WEATHER_TEXT + " TEXT," + WEATHER_CODE + " INTEGER," + TEMPERATURE
				+ " INTEGER," + FEELS_LIKE + " INTEGER," + HUMIDITY + " INTEGER," + VISIBILITY + " DOUBLE,"
				+ WIND_DIRECTION + " TEXT," + WIND_SPEED + " TEXT," + WIND_SCALE + " TEXT," + AIRING_BRIEF + " TEXT,"
				+ AIRING_DETAILS + " TEXT," + CAR_WASHING_BRIEF + " TEXT," + CAR_WASHING_DETAILS + " TEXT,"
				+ DRESSING_BRIEF + " TEXT," + DRESSING_DETAILS + " TEXT," + SPORT_BRIEF + " TEXT," + SPORT_DETAILS
				+ " TEXT," + UMBRELLA_BRIEF + " TEXT," + UMBRELLA_DETAILS + " TEXT," + UV_BRIEF + " TEXT," + UV_DETAILS
				+ " TEXT)");

		// 创建每日天气情况表
		db.execSQL("CREATE TABLE " + TABLE_DALIY_WEATHER + "(" + ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
				+ CITY_NAME + " TEXT," + WHICH_DAY + " INTEGER," + TEXT_DAY + " TEXT," + CODE_DAY + " INTEGER,"
				+ TEXT_NIGHT + " TEXT," + CODE_NIGHT + " INTEGER," + HIGH + " INTEGER," + LOW + " INTEGER" + ")");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
