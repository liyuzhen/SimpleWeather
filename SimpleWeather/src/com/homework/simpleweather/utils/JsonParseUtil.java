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
 * ��������json���ݵĹ���
 */
public class JsonParseUtil {

	/**
	 * @Fields PATH : TODO(��һ�仰�������������ʾʲô)
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
	 * ������������json�ַ���
	 */
	public static NowWeather parseNowJson(String nowJson) {
		// �ж�json�ַ����Ƿ�Ϊ��
		if (!TextUtils.isEmpty(nowJson)) {
			Log.d("ɷ��", nowJson);
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
	 * ��������ָ��json�ַ���
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
					// �����������ָ���ļ�Ҫ�������ϸ����
					if (strSuggestion.equals(AIRING)) {
						suggestionInfo.getAiring().setBrief("��ɹ\n" + brief);
						suggestionInfo.getAiring().setDetails(details);
					} else if (strSuggestion.equals(CAR_WASHING)) {
						suggestionInfo.getCarWashing().setBrief("ϴ��\n" + brief);
						suggestionInfo.getCarWashing().setDetails(details);
					} else if (strSuggestion.equals(DRESSING)) {
						suggestionInfo.getDressing().setBrief("����\n" + brief);
						suggestionInfo.getDressing().setDetails(details);
					} else if (strSuggestion.equals(SPORT)) {
						suggestionInfo.getSport().setBrief("�˶�\n" + brief);
						suggestionInfo.getSport().setDetails(details);
					} else if (strSuggestion.equals(UMBRELLA)) {
						suggestionInfo.getUmbrella().setBrief("����\n" + brief);
						suggestionInfo.getUmbrella().setDetails(details);
					} else {
						suggestionInfo.getUv().setBrief("������ǿ��\n" + brief);
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
	 * �����������н�����ص�json�ַ���
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
	 * ����ÿ������json�ַ���
	 */
	public static List<DailyWeather> parseDaliyWeatherJson(String daliyWeatherJson) {
		if (!TextUtils.isEmpty(daliyWeatherJson)) {
			List<DailyWeather> daliyWeathers = new ArrayList<DailyWeather>();
			try {
				JSONObject jsonObject = new JSONObject(daliyWeatherJson);
				JSONObject resultObject = (JSONObject) jsonObject.getJSONArray(RESULTS).get(0);
				String cityName = resultObject.getJSONObject(LOCATION).getString(NAME); // ��ȡ������
				JSONArray daliyArray = resultObject.getJSONArray(DBOpenHelper.DAILY);
				for (int i = 0; i < daliyArray.length(); i++) {
					DailyWeather daliyWeather = new DailyWeather();
					JSONObject eachObject = (JSONObject) daliyArray.get(i);
					// ��װ��������
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
