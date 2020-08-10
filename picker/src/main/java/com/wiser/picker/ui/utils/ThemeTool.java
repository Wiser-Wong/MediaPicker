package com.wiser.picker.ui.utils;

import android.content.Context;
import android.content.res.TypedArray;

import com.wiser.picker.R;

/**
 * @author Wiser
 * 
 *         主题工具
 */
public class ThemeTool {

	/**
	 * 获取完成按钮未选中颜色
	 * 
	 * @param context
	 * @return
	 */
	public static int getThemeCompleteTextUnSelectColor(Context context) {
		if (context == null) return 0;
		int[] colors = { R.attr.themeCompleteTextUnSelectColor };
		TypedArray ta = context.obtainStyledAttributes(colors);
		int completeBtnUnSelectColor = ta.getColor(0, 0);
		ta.recycle();
		return completeBtnUnSelectColor;
	}

	/**
	 * 获取完成按钮选中颜色
	 * 
	 * @param context
	 * @return
	 */
	public static int getThemeCompleteTextSelectColor(Context context) {
		if (context == null) return 0;
		int[] colors = { R.attr.themeCompleteTextSelectColor };
		TypedArray ta = context.obtainStyledAttributes(colors);
		int completeBtnUnSelectColor = ta.getColor(0, 0);
		ta.recycle();
		return completeBtnUnSelectColor;
	}

	/**
	 * 获取完成按钮选中颜色
	 *
	 * @param context
	 * @return
	 */
	public static int getThemeCompleteBtnUnSelectBg(Context context) {
		if (context == null) return 0;
		int[] bgs = { R.attr.themeCompleteBtnUnSelectBg };
		TypedArray ta = context.obtainStyledAttributes(bgs);
		int completeBtnUnSelectBg = ta.getResourceId(0, 0);
		ta.recycle();
		return completeBtnUnSelectBg;
	}

	/**
	 * 获取完成按钮选中颜色
	 *
	 * @param context
	 * @return
	 */
	public static int getThemeCompleteBtnSelectBg(Context context) {
		if (context == null) return 0;
		int[] bgs = { R.attr.themeCompleteBtnSelectBg };
		TypedArray ta = context.obtainStyledAttributes(bgs);
		int completeBtnUnSelectBg = ta.getResourceId(0, 0);
		ta.recycle();
		return completeBtnUnSelectBg;
	}

	/**
	 * 获取未选择默认icon
	 *
	 * @param context
	 * @return
	 */
	public static int getThemeUnCheckDfIcon(Context context) {
		if (context == null) return 0;
		int[] bgs = { R.attr.themeUnCheckDfIcon };
		TypedArray ta = context.obtainStyledAttributes(bgs);
		int unCheckDfIcon = ta.getResourceId(0, 0);
		ta.recycle();
		return unCheckDfIcon;
	}

	/**
	 * 获取选择默认icon
	 *
	 * @param context
	 * @return
	 */
	public static int getThemeCheckDfIcon(Context context) {
		if (context == null) return 0;
		int[] bgs = { R.attr.themeCheckDfIcon };
		TypedArray ta = context.obtainStyledAttributes(bgs);
		int checkDfIcon = ta.getResourceId(0, 0);
		ta.recycle();
		return checkDfIcon;
	}

	/**
	 * 获取未选择号码icon
	 *
	 * @param context
	 * @return
	 */
	public static int getThemeUnCheckNumIcon(Context context) {
		if (context == null) return 0;
		int[] bgs = { R.attr.themeUnCheckNumIcon };
		TypedArray ta = context.obtainStyledAttributes(bgs);
		int unCheckNumIcon = ta.getResourceId(0, 0);
		ta.recycle();
		return unCheckNumIcon;
	}

	/**
	 * 获取选择号码icon
	 *
	 * @param context
	 * @return
	 */
	public static int getThemeCheckNumIcon(Context context) {
		if (context == null) return 0;
		int[] bgs = { R.attr.themeCheckNumIcon };
		TypedArray ta = context.obtainStyledAttributes(bgs);
		int checkNumIcon = ta.getResourceId(0, 0);
		ta.recycle();
		return checkNumIcon;
	}
}
