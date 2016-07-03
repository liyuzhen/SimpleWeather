package com.homework.simpleweather.utils;

import android.content.Context;

/**
 * ������λdp��px��ת��
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