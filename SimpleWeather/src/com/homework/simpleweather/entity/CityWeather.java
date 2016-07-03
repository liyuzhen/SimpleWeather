package com.homework.simpleweather.entity;

import java.util.List;

/**
 * 集成城市所有天气信息
 */
public class CityWeather {
	private NowWeather nowWeather; // 当天天气状况
	private SuggestionInfo suggestionInfo; // 生活指数
	private List<DailyWeather> daliyWeathers; // 每日天气

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
