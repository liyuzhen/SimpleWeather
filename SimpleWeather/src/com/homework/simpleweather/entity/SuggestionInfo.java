package com.homework.simpleweather.entity;

/**
 * ����ָ��
 */
public class SuggestionInfo {
	private EachSuggestion airing = new EachSuggestion(); // ��ɹ
	private EachSuggestion carWashing = new EachSuggestion(); // ϴ��
	private EachSuggestion dressing = new EachSuggestion(); // ����
	private EachSuggestion sport = new EachSuggestion(); // �˶�
	private EachSuggestion umbrella = new EachSuggestion(); // ��ɡ
	private EachSuggestion uv = new EachSuggestion(); // ������

	public class EachSuggestion {
		private String brief; // ��Ҫ����
		private String details; // ��ϸ����

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
