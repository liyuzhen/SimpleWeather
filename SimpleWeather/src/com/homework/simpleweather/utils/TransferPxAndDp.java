package com.homework.simpleweather.utils;

import android.content.Context;

/**
 * 度量单位dp与px的转化
 */
public class TransferPxAndDp {
	public static int dp2Px(Context context, int dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static int px2Dp(Context context, int pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
}