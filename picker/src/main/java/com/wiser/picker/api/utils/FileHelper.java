package com.wiser.picker.api.utils;

import android.media.MediaPlayer;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Wiser
 * 
 *         文件帮助类
 */
public class FileHelper {

	/**
	 * 检测SD卡状态判断SdCard存在并且是可用的
	 *
	 * @return
	 */
	public static boolean isMounted() {
		// 判断SdCard是否存在并且是可用的
		return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable();
	}

	/**
	 * 获取指定文件大小
	 * 
	 * @param file
	 * @return
	 */
	public static long getFileSize(File file) {
		long size = 0;
		if (file != null && file.exists()) {
			FileInputStream fis;
			try {
				fis = new FileInputStream(file);
				size = fis.available();
				fis.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return size;
	}

	/**
	 * 获取视频文件时长
	 * 
	 * @param file
	 * @return
	 */
	public static long getFileVideoDuration(File file) {
		MediaPlayer mediaPlayer = new MediaPlayer();
		try {
			mediaPlayer.setDataSource(file.getPath());
			mediaPlayer.prepare();
			return mediaPlayer.getDuration();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				mediaPlayer.release();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	/**
	 * 获取视频文件宽高
	 *
	 * @param file
	 * @return
	 */
	public static int[] getFileVideoMeasure(File file) {
		MediaPlayer mediaPlayer = new MediaPlayer();
		try {
			mediaPlayer.setDataSource(file.getPath());
			mediaPlayer.prepare();
			return new int[] { mediaPlayer.getVideoWidth(), mediaPlayer.getVideoHeight() };
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				mediaPlayer.release();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 判断文件存在不存在
	 *
	 * @param path
	 *            路径
	 * @return 结果
	 */
	public static boolean fileExist(String path) {
		return !TextUtils.isEmpty(path) && new File(path).exists();
	}

	/**
	 * 是否是图片格式
	 * 
	 * @param path
	 *            图片地址
	 * @return
	 */
	public static boolean isPhotoFormat(String path) {
		// 获取拓展名
		String fileEnd = path.substring(path.lastIndexOf(".") + 1).toLowerCase();
		return fileEnd.equals("jpg") || fileEnd.equals("png") || fileEnd.equals("jpeg");
	}

	/**
	 * 是否是视频格式
	 *
	 * @param path
	 *            图片地址
	 * @return
	 */
	public static boolean isVideoFormat(String path) {
		// 获取拓展名
		String fileEnd = path.substring(path.lastIndexOf(".") + 1).toLowerCase();
		return fileEnd.equals("mp4");
	}
}
