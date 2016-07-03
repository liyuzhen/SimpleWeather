package com.homework.simpleweather.entity;

/**
 * 生活指数
 */
public class SuggestionInfo {
	private EachSuggestion airing = new EachSuggestion(); // 晾晒
	private EachSuggestion carWashing = new EachSuggestion(); // 洗车
	private EachSuggestion dressing = new EachSuggestion(); // 穿衣
	private EachSuggestion sport = new EachSuggestion(); // 运动
	private EachSuggestion umbrella = new EachSuggestion(); // 雨伞
	private EachSuggestion uv = new EachSuggestion(); // 紫外线

	public class EachSuggestion {
		private String brief; // 简要建议
		private String details; // 详细建议

		public String getBrief() {
			return brief;
		}

		public void setBrief(String brief) {
			this.brief = brief;
		}

		public String getDetails() {
			return details;
		}

		public void setDetails(String details) {
			this.details = details;
		}
	}

	public EachSuggestion getAiring() {
		return airing;
	}

	public EachSuggestion getCarWashing() {
		return carWashing;
	}

	public EachSuggestion getDressing() {
		return dressing;
	}

	public EachSuggestion getSport() {
		return sport;
	}

	public EachSuggestion getUmbrella() {
		return umbrella;
	}

	public EachSuggestion getUv() {
		return uv;
	}
}
