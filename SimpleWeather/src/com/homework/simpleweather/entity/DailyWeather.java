package com.homework.simpleweather.entity;

/**
 * ����ÿ������
 */
public class DailyWeather {
	private String cityName; // ������
	private String date;// ����
	private String textDay; // ����������������
	private int codeDay; // ���������������
	private String textNight;// ���������������
	private int codeNight;// ��������������
	private int high; // ��������¶�
	private int low; // ��������¶�
	private String precip; // ���꼸��

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
