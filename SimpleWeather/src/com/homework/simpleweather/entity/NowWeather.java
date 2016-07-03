package com.homework.simpleweather.entity;

/**
 * 实况天气数据
 */
public class NowWeather {
	private String cityName; // 城市名
	private String weatherText; // 天气现象文字
	private int weatherCode; // 天气现象代码
	private int temperature; // 温度
	private int feelsLike; // 体感温度
	private int humidity; // 相对湿度
	private double visibility; // 能见度
	private String windDirection; // 风向
	private String windSpeed; // 风速
	private String windScale; // 风力等级

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getWeatherText() {
		return weatherText;
	}

	public void setWeatherText(String weatherText) {
		this.weatherText = weatherText;
	}

	public int getWeatherCode() {
		return weatherCode;
	}

	public void setWeatherCode(int weatherCode) {
		this.weatherCode = weatherCode;
	}

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	public int getFeelsLike() {
		return feelsLike;
	}

	public void setFeelsLike(int feelsLike) {
		this.feelsLike = feelsLike;
	}

	public int getHumidity() {
		return humidity;
	}

	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}

	public double getVisibility() {
		return visibility;
	}

	public void setVisibility(double visibility) {
		this.visibility = visibility;
	}

	public String getWindDirection() {
		return windDirection;
	}

	public void setWindDirection(String windDirection) {
		this.windDirection = windDirection;
	}

	public String getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(String windSpeed) {
		this.windSpeed = windSpeed;
	}

	public String getWindScale() {
		return windScale;
	}

	public void setWindScale(String windScale) {
		this.windScale = windScale;
	}

}
