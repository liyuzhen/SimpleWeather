package com.homework.simpleweather.entity;

import java.util.List;

/**
 * ���ɳ�������������Ϣ
 */
public class CityWeather {
	private NowWeather nowWeather; // ��������״��
	private SuggestionInfo suggestionInfo; // ����ָ��
	private List<DailyWeather> daliyWeathers; // ÿ������

	public List<DailyWeather> getDaliyWeathers() {
		return daliyWeathers;
	}

	public void setDaliyWeathers(List<DailyWeather> daliyWeathers) {
		this.daliyWeathers = daliyWeathers;
	}

	public NowWeather getNowWeather() {
		return nowWeather;
	}

	public SuggestionInfo getSuggestionInfo() {
		return suggestionInfo;
	}

	public void setNowWeather(NowWeather nowWeather) {
		this.nowWeather = nowWeather;
	}

	public void setSuggestionInfo(SuggestionInfo suggestionInfo) {
		this.suggestionInfo = suggestionInfo;
	}

}
