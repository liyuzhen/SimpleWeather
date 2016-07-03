package com.homework.simpleweather.entity;

/**
 * 城市每日天气
 */
public class DailyWeather {
	private String cityName; // 城市名
	private String date;// 日期
	private String textDay; // 白天天气现象文字
	private int codeDay; // 白天天气现象代码
	private String textNight;// 晚间天气现象文字
	private int codeNight;// 晚间天气现象代码
	private int high; // 当天最高温度
	private int low; // 当天最低温度
	private String precip; // 降雨几率

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getDate() {
		return date;
	}

	public String getTextDay() {
		return textDay;
	}

	public int getCodeDay() {
		return codeDay;
	}

	public String getTextNight() {
		return textNight;
	}

	public int getCodeNight() {
		return codeNight;
	}

	public int getHigh() {
		return high;
	}

	public int getLow() {
		return low;
	}

	public String getPrecip() {
		return precip;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setTextDay(String textDay) {
		this.textDay = textDay;
	}

	public void setCodeDay(int codeDay) {
		this.codeDay = codeDay;
	}

	public void setTextNight(String textNight) {
		this.textNight = textNight;
	}

	public void setCodeNight(int codeNight) {
		this.codeNight = codeNight;
	}

	public void setHigh(int high) {
		this.high = high;
	}

	public void setLow(int low) {
		this.low = low;
	}

	public void setPrecip(String precip) {
		this.precip = precip;
	}

}
