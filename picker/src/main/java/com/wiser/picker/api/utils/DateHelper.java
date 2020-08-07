package com.wiser.picker.api.utils;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Wiser
 * 
 *         日期帮助类
 */
public class DateHelper {

	/**
	 * 根据long类型获取时间字符串
	 *
	 * @param mill
	 *            long时间
	 * @param pattern
	 *            模板
	 * @return
	 */
	public static String getTimes(long mill, String pattern) {
		Date date = new Date(mill);
		String dateStr = "";
		try {
			@SuppressLint("SimpleDateFormat")
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			// 进行格式化
			dateStr = sdf.format(date);
			return dateStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateStr;
	}

}
