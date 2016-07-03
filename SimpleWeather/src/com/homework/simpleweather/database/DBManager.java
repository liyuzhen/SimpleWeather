package com.homework.simpleweather.database;

import java.util.ArrayList;
import java.util.List;

import com.homework.simpleweather.entity.CityWeather;
import com.homework.simpleweather.entity.DailyWeather;
import com.homework.simpleweather.entity.NowWeather;
import com.homework.simpleweather.entity.SuggestionInfo;
import com.homework.simpleweather.entity.TodayWeather;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.Time;

/**
 * 数据库管理类，实现增删查改功能
 */
public class DBManager {
	private DBOpenHelper mDBOpenHelper;
	private SQLiteDatabase mSQLiteDatabase;

	private String[] weekDays = new String[] { "一", "二", "三", "四", "五", "六", "天" };

	public DBManager(Context context) {
		mDBOpenHelper = new DBOpenHelper(context);
	}

	/*
	 * 增
	 */
	public void insert(CityWeather cityWeather) {
		mSQLiteDatabase = mDBOpenHelper.getWritableDatabase();
		if (mSQLiteDatabase != null) {
			NowWeather nowWeather = cityWeather.getNowWeather(); // 获取当前的天气数据对象
			SuggestionInfo suggestionInfo = cityWeather.getSuggestionInfo(); // 获取当前天气指数
			List<DailyWeather> daliyWeathers = cityWeather.getDaliyWeathers(); // 获取每日天气数据
			ContentValues contentValues = new ContentValues(); // 创建储存键值对的对象
			// 当前天气
			contentValues.put(mDBOpenHelper.CITY_NAME, nowWeather.getCityName());
			contentValues.put(mDBOpenHelper.WEATHER_TEXT, nowWeather.getWeatherText());
			contentValues.put(mDBOpenHelper.WEATHER_CODE, nowWeather.getWeatherCode());
			contentValues.put(mDBOpenHelper.TEMPERATURE, nowWeather.getTemperature());
			contentValues.put(mDBOpenHelper.FEELS_LIKE, nowWeather.getFeelsLike());
			contentValues.put(mDBOpenHelper.HUMIDITY, nowWeather.getHumidity());
			contentValues.put(mDBOpenHelper.VISIBILITY, nowWeather.getVisibility());
			contentValues.put(mDBOpenHelper.WIND_DIRECTION, nowWeather.getWindDirection());
			contentValues.put(mDBOpenHelper.WIND_SPEED, nowWeather.getWindSpeed());
			contentValues.put(mDBOpenHelper.WIND_SCALE, nowWeather.getWindScale());
			// 生活指数
			contentValues.put(mDBOpenHelper.AIRING_BRIEF, suggestionInfo.getAiring().getBrief());
			contentValues.put(mDBOpenHelper.AIRING_DETAILS, suggestionInfo.getAiring().getDetails());
			contentValues.put(mDBOpenHelper.CAR_WASHING_BRIEF, suggestionInfo.getCarWashing().getBrief());
			contentValues.put(mDBOpenHelper.CAR_WASHING_DETAILS, suggestionInfo.getCarWashing().getDetails());
			contentValues.put(mDBOpenHelper.DRESSING_BRIEF, suggestionInfo.getDressing().getBrief());
			contentValues.put(mDBOpenHelper.DRESSING_DETAILS, suggestionInfo.getDressing().getDetails());
			contentValues.put(mDBOpenHelper.SPORT_BRIEF, suggestionInfo.getSport().getBrief());
			contentValues.put(mDBOpenHelper.SPORT_DETAILS, suggestionInfo.getSport().getDetails());
			contentValues.put(mDBOpenHelper.UMBRELLA_BRIEF, suggestionInfo.getUmbrella().getBrief());
			contentValues.put(mDBOpenHelper.UMBRELLA_DETAILS, suggestionInfo.getUmbrella().getDetails());
			contentValues.put(mDBOpenHelper.UV_BRIEF, suggestionInfo.getUv().getBrief());
			contentValues.put(mDBOpenHelper.UV_DETAILS, suggestionInfo.getUv().getDetails());
			// 插入city_weather表中
			mSQLiteDatabase.insert(mDBOpenHelper.TABLE_CITY_WEATHER, null, contentValues);

			// 插入5天天气数据
			for (int i = 0; i < 5; i++) {
				ContentValues eachDayValues = new ContentValues();
				DailyWeather dailyWeather = daliyWeathers.get(i);
				eachDayValues.put(DBOpenHelper.CITY_NAME, dailyWeather.getCityName());
				eachDayValues.put(DBOpenHelper.WHICH_DAY, calcuDay(i));
				eachDayValues.put(DBOpenHelper.TEXT_DAY, dailyWeather.getTextDay());
				eachDayValues.put(DBOpenHelper.CODE_DAY, dailyWeather.getCodeDay());
				eachDayValues.put(DBOpenHelper.TEXT_NIGHT, dailyWeather.getTextNight());
				eachDayValues.put(DBOpenHelper.CODE_NIGHT, dailyWeather.getCodeNight());
				eachDayValues.put(DBOpenHelper.HIGH, dailyWeather.getHigh());
				eachDayValues.put(DBOpenHelper.LOW, dailyWeather.getLow());
				mSQLiteDatabase.insert(DBOpenHelper.TABLE_DALIY_WEATHER, null, eachDayValues);
			}

			mSQLiteDatabase.close();
		}
	}

	/**
	 * 计算今天星期几
	 */
	private String calcuDay(int i) {
		if (i == 0)
			return "今天";
		// 获取现在时间
		Time time = new Time();
		time.setToNow();
		int today = time.weekDay;
		StringBuilder weekDay = new StringBuilder();
		weekDay.append("星期");
		int day = today + i - 1;
		if (day > 6) {
			day -= 7;
		}
		weekDay.append(weekDays[day]);
		return weekDay.toString();
	}

	/*
	 * 删
	 */
	public void delete(String cityName) {
		mSQLiteDatabase = mDBOpenHelper.getWritableDatabase();
		if (mSQLiteDatabase != null) {
			// 删除城市对应条目
			mSQLiteDatabase.delete(mDBOpenHelper.TABLE_CITY_WEATHER, DBOpenHelper.CITY_NAME + " = ?",
					new String[] { cityName });
			mSQLiteDatabase.delete(mDBOpenHelper.TABLE_DALIY_WEATHER, DBOpenHelper.CITY_NAME + " = ?",
					new String[] { cityName });
			mSQLiteDatabase.close();
		}
	}

	/*
	 * 查
	 */
	public CityWeather find(String cityName) {
		CityWeather cityWeather = null;
		mSQLiteDatabase = mDBOpenHelper.getReadableDatabase(); // 获取可读的数据库
		if (mSQLiteDatabase != null) {
			Cursor cursor = mSQLiteDatabase.rawQuery(
					"select * from " + mDBOpenHelper.TABLE_CITY_WEATHER + " where " + mDBOpenHelper.CITY_NAME + " = ?",
					new String[] { cityName });
			while (cursor.moveToNext()) {
				cityWeather = new CityWeather();
				NowWeather nowWeather = new NowWeather();
				SuggestionInfo suggestionInfo = new SuggestionInfo();
				// 封装所有有关的数据
				nowWeather.setCityName(cityName);
				nowWeather.setWeatherText(cursor.getString(2));
				nowWeather.setWeatherCode(cursor.getInt(3));
				nowWeather.setTemperature(cursor.getInt(4));
				nowWeather.setFeelsLike(cursor.getInt(5));
				nowWeather.setHumidity(cursor.getInt(6));
				nowWeather.setVisibility(cursor.getDouble(7));
				nowWeather.setWindDirection(cursor.getString(8));
				nowWeather.setWindSpeed(cursor.getString(9));
				nowWeather.setWindScale(cursor.getString(10));
				suggestionInfo.getAiring().setBrief(cursor.getString(11));
				suggestionInfo.getAiring().setDetails(cursor.getString(12));
				suggestionInfo.getCarWashing().setBrief(cursor.getString(13));
				suggestionInfo.getCarWashing().setDetails(cursor.getString(14));
				suggestionInfo.getDressing().setBrief(cursor.getString(15));
				suggestionInfo.getDressing().setDetails(cursor.getString(16));
				suggestionInfo.getSport().setBrief(cursor.getString(17));
				suggestionInfo.getSport().setDetails(cursor.getString(18));
				suggestionInfo.getUmbrella().setBrief(cursor.getString(19));
				suggestionInfo.getUmbrella().setDetails(cursor.getString(20));
				suggestionInfo.getUv().setBrief(cursor.getString(21));
				suggestionInfo.getUv().setDetails(cursor.getString(22));

				cityWeather.setNowWeather(nowWeather);
				cityWeather.setSuggestionInfo(suggestionInfo);
				cursor.close(); // 关闭游标
			}

			cursor = mSQLiteDatabase.rawQuery(
					"select * from " + mDBOpenHelper.TABLE_DALIY_WEATHER + " where " + mDBOpenHelper.CITY_NAME + " = ?",
					new String[] { cityName });
			List<DailyWeather> dailyWeathers = new ArrayList<DailyWeather>();
			// 读取每日天气情况
			while (cursor.moveToNext()) {
				DailyWeather dailyWeather = new DailyWeather();
				dailyWeather.setCityName(cursor.getString(1));
				dailyWeather.setDate(cursor.getString(2));
				dailyWeather.setTextDay(cursor.getString(3));
				dailyWeather.setCodeDay(cursor.getInt(4));
				dailyWeather.setTextNight(cursor.getString(5));
				dailyWeather.setCodeNight(cursor.getInt(6));
				dailyWeather.setHigh(cursor.getInt(7));
				dailyWeather.setLow(cursor.getInt(8));
				dailyWeathers.add(dailyWeather);
			}
			if (cityWeather != null) {
				cityWeather.setDaliyWeathers(dailyWeathers);
			}

			mSQLiteDatabase.close();
		}
		return cityWeather;
	}

	/*
	 * 改
	 */
	public void update(CityWeather newCityWeather) {
		mSQLiteDatabase = mDBOpenHelper.getWritableDatabase();
		if (mSQLiteDatabase != null) {
			NowWeather newNowWeather = newCityWeather.getNowWeather();
			SuggestionInfo newSuggestionInfo = newCityWeather.getSuggestionInfo();
			List<DailyWeather> dailyWeathers = newCityWeather.getDaliyWeathers();
			String cityName = newNowWeather.getCityName();
			// 封装所有数据
			ContentValues contentValues = new ContentValues();
			contentValues.put(mDBOpenHelper.CITY_NAME, newNowWeather.getCityName());
			contentValues.put(mDBOpenHelper.WEATHER_TEXT, newNowWeather.getWeatherText());
			contentValues.put(mDBOpenHelper.WEATHER_CODE, newNowWeather.getWeatherCode());
			contentValues.put(mDBOpenHelper.TEMPERATURE, newNowWeather.getTemperature());
			contentValues.put(mDBOpenHelper.FEELS_LIKE, newNowWeather.getFeelsLike());
			contentValues.put(mDBOpenHelper.HUMIDITY, newNowWeather.getHumidity());
			contentValues.put(mDBOpenHelper.VISIBILITY, newNowWeather.getVisibility());
			contentValues.put(mDBOpenHelper.WIND_DIRECTION, newNowWeather.getWindDirection());
			contentValues.put(mDBOpenHelper.WIND_SPEED, newNowWeather.getWindSpeed());
			contentValues.put(mDBOpenHelper.WIND_SCALE, newNowWeather.getWindScale());
			// 生活指数
			contentValues.put(mDBOpenHelper.AIRING_BRIEF, newSuggestionInfo.getAiring().getBrief());
			contentValues.put(mDBOpenHelper.AIRING_DETAILS, newSuggestionInfo.getAiring().getDetails());
			contentValues.put(mDBOpenHelper.CAR_WASHING_BRIEF, newSuggestionInfo.getCarWashing().getBrief());
			contentValues.put(mDBOpenHelper.CAR_WASHING_DETAILS, newSuggestionInfo.getCarWashing().getDetails());
			contentValues.put(mDBOpenHelper.DRESSING_BRIEF, newSuggestionInfo.getDressing().getBrief());
			contentValues.put(mDBOpenHelper.DRESSING_DETAILS, newSuggestionInfo.getDressing().getDetails());
			contentValues.put(mDBOpenHelper.SPORT_BRIEF, newSuggestionInfo.getSport().getBrief());
			contentValues.put(mDBOpenHelper.SPORT_DETAILS, newSuggestionInfo.getSport().getDetails());
			contentValues.put(mDBOpenHelper.UMBRELLA_BRIEF, newSuggestionInfo.getUmbrella().getBrief());
			contentValues.put(mDBOpenHelper.UMBRELLA_DETAILS, newSuggestionInfo.getUmbrella().getDetails());
			contentValues.put(mDBOpenHelper.UV_BRIEF, newSuggestionInfo.getUv().getBrief());
			contentValues.put(mDBOpenHelper.UV_DETAILS, newSuggestionInfo.getUv().getDetails());

			mSQLiteDatabase.update(DBOpenHelper.TABLE_CITY_WEATHER, contentValues, DBOpenHelper.CITY_NAME + " = ?",
					new String[] { cityName });

			for (int i = 0; i < 5; i++) {
				ContentValues eachDayValues = new ContentValues();
				// 获取每日天气信息
				DailyWeather dailyWeather = dailyWeathers.get(i);
				eachDayValues.put(DBOpenHelper.CITY_NAME, dailyWeather.getCityName());
				eachDayValues.put(DBOpenHelper.WHICH_DAY, dailyWeather.getDate());
				eachDayValues.put(DBOpenHelper.TEXT_DAY, dailyWeather.getTextDay());
				eachDayValues.put(DBOpenHelper.CODE_DAY, dailyWeather.getCodeDay());
				eachDayValues.put(DBOpenHelper.TEXT_NIGHT, dailyWeather.getTextNight());
				eachDayValues.put(DBOpenHelper.CODE_NIGHT, dailyWeather.getCodeNight());
				eachDayValues.put(DBOpenHelper.HIGH, dailyWeather.getHigh());
				eachDayValues.put(DBOpenHelper.LOW, dailyWeather.getLow());
				mSQLiteDatabase.update(DBOpenHelper.TABLE_DALIY_WEATHER, eachDayValues,
						DBOpenHelper.CITY_NAME + " = ? and " + mDBOpenHelper.WHICH_DAY + " = ?",
						new String[] { cityName, calcuDay(i) });
			}
			mSQLiteDatabase.close();
		}
	}

	/*
	 * 获取数据库中所有城市名
	 */
	public List<String> getAllCityName() {
		List<String> cityNames = new ArrayList<String>();
		mSQLiteDatabase = mDBOpenHelper.getReadableDatabase();
		if (mSQLiteDatabase != null) {
			Cursor cursor = mSQLiteDatabase.query(mDBOpenHelper.TABLE_CITY_WEATHER, null, null, null, null, null, null);
			while (cursor.moveToNext()) {
				cityNames.add(cursor.getString(1)); // 获取第二列城市的数据
			}
			mSQLiteDatabase.close();
			return cityNames;
		}
		return null;
	}

	/**
	 * 获取当日天气情况(非实时)
	 */
	public List<TodayWeather> getAllTodayWeather() {
		List<TodayWeather> todayWeathers = new ArrayList<TodayWeather>();
		mSQLiteDatabase = mDBOpenHelper.getReadableDatabase();
		if (mSQLiteDatabase != null) {
			// 读取数据库中所有城市第一天的数据，即当天
			Cursor cursor = mSQLiteDatabase.rawQuery(
					"select * from " + DBOpenHelper.TABLE_DALIY_WEATHER + " where " + DBOpenHelper.WHICH_DAY + " = ?",
					new String[] { "今天" });
			while (cursor.moveToNext()) {
				TodayWeather todayWeather = new TodayWeather();
				todayWeather.setCityName(cursor.getString(1));
				todayWeather.setWeatherCode(cursor.getInt(4));
				todayWeather.setHigh(cursor.getInt(7));
				todayWeather.setLow(cursor.getInt(8));
				todayWeathers.add(todayWeather);
			}
			mSQLiteDatabase.close();
			return todayWeathers;
		}
		return null;
	}
}
