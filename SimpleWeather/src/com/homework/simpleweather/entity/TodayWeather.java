/**  
 * @Title: TodayWeather.java 
 * @Package com.homework.simpleweather.entity 
 * @Description: TODO(��һ�仰�������ļ���ʲô)
 */
package com.homework.simpleweather.entity;

/**
 * �����������������������,����£�����£�����ͼ��
 */
public class TodayWeather {
	private String cityName; // ������
	private int weatherCode; // ��������
	private int high; // �����
	private int low; // �����

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
