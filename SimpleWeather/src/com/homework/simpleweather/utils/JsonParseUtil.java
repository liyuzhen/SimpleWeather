package com.homework.simpleweather.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.homework.simpleweather.database.DBOpenHelper;
import com.homework.simpleweather.entity.DailyWeather;
import com.homework.simpleweather.entity.NowWeather;
import com.homework.simpleweather.entity.SuggestionInfo;

import android.text.TextUtils;
import android.util.Log;

/**
 * 解析所有json数据的工具
 */
public class JsonParseUtil {

	/**
	 * @Fields PATH : TODO(用一句话描述这个变量表示什么)
	 */
	public static final String PATH = "path";
	public static final String DETAILS = "details";
	public static final String BRIEF = "brief";
	public static final String UV = "uv";
	public static final String UMBRELLA = "umbrella";
	public static final String SPORT = "sport";
	public static final String DRESSING = "dressing";
	public static final String CAR_WASHING = "car_washing";
	public static final String AIRING = "airing";
	public static final String SUGGESTION = "suggestion";
	public static final String WIND_SCALE = "wind_scale";
	public static final String WIND_SPEED = "wind_speed";
	public static final String WIND_DIRECTION = "wind_direction";
	public static final String VISIBILITY = "visibility";
	public static final String HUMIDITY = "humidity";
	public static final String FEELS_LIKE = "feels_like";
	public static final String TEMPERATURE = "temperature";
	public static final String CODE = "code";
	public static final String TEXT = "text";
	public static final String NOW = "now";
	public static final String NAME = "name";
	public static final String LOCATION = "location";
	public static final String RESULTS = "results";

	/**
	 * 解析当日天气json字符串
	 */
	public static NowWeather parseNowJson(String nowJson) {
		// 判断json字符串是否为空
		if (!TextUtils.isEmpty(nowJson)) {
			Log.d("煞笔", nowJson);
			try {
				NowWeather nowWeather = new NowWeather();
				JSONObject jsonObject = new JSONObject(nowJson);
				JSONObject cityJsonObject = (JSONObject) jsonObject.getJSONArray(RESULTS).get(0);
				nowWeather.setCityName(cityJsonObject.getJSONObject(LOCATION).getString(NAME));
				JSONObject nowJsonObject = cityJsonObject.getJSONObject(NOW);
				nowWeather.setWeatherText(nowJsonObject.getString(TEXT));
				nowWeather.setWeatherCode(nowJsonObject.getInt(CODE));
				nowWeather.setTemperature(nowJsonObject.getInt(TEMPERATURE));
				nowWeather.setFeelsLike(nowJsonObject.getInt(FEELS_LIKE));
				nowWeather.setHumidity(nowJsonObject.getInt(HUMIDITY));
				nowWeather.setVisibility(nowJsonObject.getDouble(VISIBILITY));
				nowWeather.setWindDirection(nowJsonObject.getString(WIND_DIRECTION));
				nowWeather.setWindSpeed(nowJsonObject.getString(WIND_SPEED));
				nowWeather.setWindScale(nowJsonObject.getString(WIND_SCALE));
				return nowWeather;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 解析生活指数json字符串
	 */
	public static SuggestionInfo parseSuggestionJson(String suggestionJson) {
		if (!TextUtils.isEmpty(suggestionJson)) {
			try {
				String[] strSuggestions = new String[] { AIRING, CAR_WASHING, DRESSING, SPORT, UMBRELLA, UV };
				SuggestionInfo suggestionInfo = new SuggestionInfo();
				JSONObject jsonObject = new JSONObject(suggestionJson);
				JSONObject infoJsonObject = (JSONObject) jsonObject.getJSONArray(RESULTS).get(0);
				JSONObject suggestionJsonObject = infoJsonObject.getJSONObject(SUGGESTION);
				for (String strSuggestion : strSuggestions) {
					JSONObject eachJsonObject = suggestionJsonObject.getJSONObject(strSuggestion);
					String brief = eachJsonObject.getString(BRIEF);
					String details = eachJsonObject.getString(DETAILS);
					// 逐个设置生活指数的简要建议和详细建议
					if (strSuggestion.equals(AIRING)) {
						suggestionInfo.getAiring().setBrief("晾晒\n" + brief);
						suggestionInfo.getAiring().setDetails(details);
					} else if (strSuggestion.equals(CAR_WASHING)) {
						suggestionInfo.getCarWashing().setBrief("洗车\n" + brief);
						suggestionInfo.getCarWashing().setDetails(details);
					} else if (strSuggestion.equals(DRESSING)) {
						suggestionInfo.getDressing().setBrief("穿衣\n" + brief);
						suggestionInfo.getDressing().setDetails(details);
					} else if (strSuggestion.equals(SPORT)) {
						suggestionInfo.getSport().setBrief("运动\n" + brief);
						suggestionInfo.getSport().setDetails(details);
					} else if (strSuggestion.equals(UMBRELLA)) {
						suggestionInfo.getUmbrella().setBrief("建议\n" + brief);
						suggestionInfo.getUmbrella().setDetails(details);
					} else {
						suggestionInfo.getUv().setBrief("紫外线强度\n" + brief);
						suggestionInfo.getUv().setDetails(details);
					}
				}
				return suggestionInfo;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 解析搜索城市结果返回的json字符串
	 */
	public static List<String> parseRelatedCityJson(String relatedCityJson) {
		if (!TextUtils.isEmpty(relatedCityJson)) {
			List<String> relatedCityNames = new ArrayList<String>();
			try {
				JSONObject jsonObject = new JSONObject(relatedCityJson);
				JSONArray resultsArray = jsonObject.getJSONArray(RESULTS);
				for (int i = 0; i < resultsArray.length(); i++) {
					JSONObject eachResult = (JSONObject) resultsArray.get(i);
					relatedCityNames.add(eachResult.getString(PATH));
					Log.e("text", eachResult.getString(PATH));
				}
				return relatedCityNames;
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return relatedCityNames;
		}
		return null;
	}

	/**
	 * 解析每日天气json字符串
	 */
	public static List<DailyWeather> parseDaliyWeatherJson(String daliyWeatherJson) {
		if (!TextUtils.isEmpty(daliyWeatherJson)) {
			List<DailyWeather> daliyWeathers = new ArrayList<DailyWeather>();
			try {
				JSONObject jsonObject = new JSONObject(daliyWeatherJson);
				JSONObject resultObject = (JSONObject) jsonObject.getJSONArray(RESULTS).get(0);
				String cityName = resultObject.getJSONObject(LOCATION).getString(NAME); // 获取城市名
				JSONArray daliyArray = resultObject.getJSONArray(DBOpenHelper.DAILY);
				for (int i = 0; i < daliyArray.length(); i++) {
					DailyWeather daliyWeather = new DailyWeather();
					JSONObject eachObject = (JSONObject) daliyArray.get(i);
					// 封装所有数据
					daliyWeather.setDate(eachObject.getString(DBOpenHelper.DATE));
					daliyWeather.setTextDay(eachObject.getString(DBOpenHelper.TEXT_DAY));
					daliyWeather.setCodeDay(eachObject.getInt(DBOpenHelper.CODE_DAY));
					daliyWeather.setTextNight(eachObject.getString(DBOpenHelper.TEXT_NIGHT));
					daliyWeather.setCodeNight(eachObject.getInt(DBOpenHelper.CODE_NIGHT));
					daliyWeather.setHigh(eachObject.getInt(DBOpenHelper.HIGH));
					daliyWeather.setLow(eachObject.getInt(DBOpenHelper.LOW));
					daliyWeather.setPrecip(eachObject.getString(DBOpenHelper.PRECIP));
					daliyWeather.setCityName(cityName);
					daliyWeathers.add(daliyWeather);
				}
				return daliyWeathers;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
