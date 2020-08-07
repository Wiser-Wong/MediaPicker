package com.wiser.picker.ui.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;

import static android.os.Environment.MEDIA_MOUNTED;

/**
 * @author Wiser
 *
 *         文件路径
 */
public class FileTool {

	private static final String	JPEG_FILE_PREFIX			= "IMG_";

	private static final String	JPEG_FILE_SUFFIX			= ".jpg";

	private static final String	EXTERNAL_STORAGE_PERMISSION	= "android.permission.WRITE_EXTERNAL_STORAGE";

	// 创建临时文件
	public static File createTmpFile(Context context) {
		File dir;
		if (TextUtils.equals(Environment.getExternalStorageState(), Environment.MEDIA_MOUNTED)) {
			dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/Camera");
			if (!dir.exists()) {
				dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM + "/Camera");
				if (!dir.exists()) {
					dir = getCacheDirectory(context, true);
				}
			}
		} else {
			dir = getCacheDirectory(context, true);
		}
		try {
			return File.createTempFile(JPEG_FILE_PREFIX, JPEG_FILE_SUFFIX, dir);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static File getCacheDirectory(Context context, boolean preferExternal) {
		File appCacheDir = null;
		String externalStorageState;
		try {
			externalStorageState = Environment.getExternalStorageState();
		} catch (NullPointerException | IncompatibleClassChangeError e) { // (sh)it happens (Issue #660)
			externalStorageState = "";
		}
		if (preferExternal && MEDIA_MOUNTED.equals(externalStorageState) && hasExternalStoragePermission(context)) {
			appCacheDir = getExternalCacheDir(context);
		}
		if (appCacheDir == null) {
			appCacheDir = context.getCacheDir();
		}
		if (appCacheDir == null) {
			@SuppressLint("SdCardPath")
			String cacheDirPath = "/data/data/" + context.getPackageName() + "/cache/";
			appCacheDir = new File(cacheDirPath);
		}
		return appCacheDir;
	}

	private static File getExternalCacheDir(Context context) {
		File dataDir = new File(new File(Environment.getExternalStorageDirectory(), "Android"), "data");
		File appCacheDir = new File(new File(dataDir, context.getPackageName()), "cache");
		if (!appCacheDir.exists()) {
			if (!appCacheDir.mkdirs()) {
				return null;
			}
			try {
				// noinspection ResultOfMethodCallIgnored
				new File(appCacheDir, ".nomedia").createNewFile();
			} catch (IOException ignored) {
			}
		}
		return appCacheDir;
	}

	private static boolean hasExternalStoragePermission(Context context) {
		int perm = context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
		return perm == PackageManager.PERMISSION_GRANTED;
	}

	private static String getImagePath(Context context, Uri uri, String selection) {
		String path = null;
		Cursor cursor = context.getContentResolver().query(uri, null, selection, null, null);
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
			}
			cursor.close();
		}
		return path;
	}
}
