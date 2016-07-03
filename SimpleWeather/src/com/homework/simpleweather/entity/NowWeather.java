package com.homework.simpleweather.entity;

/**
 * ʵ����������
 */
public class NowWeather {
	private String cityName; // ������
	private String weatherText; // ������������
	private int weatherCode; // �����������
	private int temperature; // �¶�
	private int feelsLike; // ����¶�
	private int humidity; // ���ʪ��
	private double visibility; // �ܼ���
	private String windDirection; // ����
	private String windSpeed; // ����
	private String windScale; // �����ȼ�

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
