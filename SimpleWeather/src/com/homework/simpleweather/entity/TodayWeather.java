/**  
 * @Title: TodayWeather.java 
 * @Package com.homework.simpleweather.entity 
 * @Description: TODO(用一句话描述该文件做什么)
 */
package com.homework.simpleweather.entity;

/**
 * 当日天气情况，包含城市名,最高温，最低温，天气图标
 */
public class TodayWeather {
	private String cityName; // 城市名
	private int weatherCode; // 天气代码
	private int high; // 最高温
	private int low; // 最低温

	public String getCityName() {
		return cityName;
	}

	public int getWeatherCode() {
		return weatherCode;
	}

	public int getHigh() {
		return high;
	}

	public int getLow() {
		return low;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public void setWeatherCode(int weatherCode) {
		this.weatherCode = weatherCode;
	}

	public void setHigh(int high) {
		this.high = high;
	}

	public void setLow(int low) {
		this.low = low;
	}
}
