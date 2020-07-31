package com.wiser.picker.api.core.query;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.wiser.picker.api.config.MediaConstants;
import com.wiser.picker.api.model.MediaData;
import com.wiser.picker.api.task.LoadMediaTask;
import com.wiser.picker.api.task.OnLoadMediaListener;
import com.wiser.picker.api.utils.FileHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wiser
 *
 *         媒体查询配置接口类
 */
interface IMediaQueryConfig {

	/**
	 * 查询URI
	 */
	Uri			QUERY_URI					= MediaStore.Files.getContentUri("external");

	/**
	 * 表 - 列名
	 */
	String[]	PROJECTION					= { MediaStore.MediaColumns.DATA, MediaStore.MediaColumns.DISPLAY_NAME, MediaStore.Images.Media.DATE_MODIFIED, MediaStore.Files.FileColumns.MEDIA_TYPE,
			MediaStore.MediaColumns.SIZE, MediaStore.Files.FileColumns._ID, MediaStore.MediaColumns.WIDTH, MediaStore.MediaColumns.HEIGHT, MediaStore.MediaColumns.DURATION };

	/**
	 * order
	 */
	String		ORDER						= PROJECTION[2] + " DESC";

	/**
	 * selection Image
	 */
	String		SELECTION_IMAGE				= PROJECTION[4] + ">0 AND " + PROJECTION[3] + "=? OR " + PROJECTION[3] + "=? ";

	/**
	 * selection Video
	 */
	String		SELECTION_VIDEO				= PROJECTION[4] + ">0 AND " + PROJECTION[3] + "=? ";

	/**
	 * selection Image And Video
	 */
	String		SELECTION_IMAGE_AND_VIDEO	= PROJECTION[4] + ">0 AND " + PROJECTION[3] + "=? OR " + PROJECTION[3] + "=? ";

	/**
	 * 图片格式
	 */
	String[]	SELECTION_IMAGE_FORMAT		= { String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE) };

	/**
	 * 视频格式
	 */
	String[]	SELECTION_VIDEO_FORMAT		= { String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO) };

	/**
	 * 视频和图片格式
	 */
	String[]	SELECTION_ALL_FORMAT		= { String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE), String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO) };

}

/**
 * @author Wiser
 * 
 *         媒体查询管理类
 */
public final class MediaQueryManage implements IMediaQueryManage, IMediaQueryConfig {

	// 加载所有图片
	@Override public List<MediaData> loadLocalSystemPhotos(Context context) {
		if (context == null) return null;
		List<MediaData> mediaDataList = new ArrayList<>();
		ContentResolver contentResolver = context.getContentResolver();
		Cursor query = contentResolver.query(QUERY_URI, PROJECTION, SELECTION_IMAGE, SELECTION_IMAGE_FORMAT, ORDER);
		if (query != null && query.moveToFirst()) {
			do {
				String path = query.getString(query.getColumnIndexOrThrow(PROJECTION[0]));
				String name = query.getString(query.getColumnIndexOrThrow(PROJECTION[1]));
				long dateTime = query.getLong(query.getColumnIndexOrThrow(PROJECTION[2]));
				long size = query.getLong(query.getColumnIndexOrThrow(PROJECTION[4]));
				int width = query.getInt(query.getColumnIndexOrThrow(PROJECTION[6]));
				int height = query.getInt(query.getColumnIndexOrThrow(PROJECTION[7]));
				if (FileHelper.fileExist(path)) {
					mediaDataList.add(new MediaData(path, name, dateTime, size, 0, width, height, MediaConstants.MEDIA_PHOTO_TYPE));
				}
			} while (query.moveToNext());
			query.close();
		}
		return mediaDataList;
	}

	// 加载所有视频
	@Override public List<MediaData> loadLocalSystemVideos(Context context) {
		if (context == null) return null;
		List<MediaData> mediaDataList = new ArrayList<>();
		ContentResolver contentResolver = context.getContentResolver();
		Cursor query = contentResolver.query(QUERY_URI, PROJECTION, SELECTION_VIDEO, SELECTION_VIDEO_FORMAT, ORDER);
		if (query != null && query.moveToFirst()) {
			do {
				String path = query.getString(query.getColumnIndexOrThrow(PROJECTION[0]));
				String name = query.getString(query.getColumnIndexOrThrow(PROJECTION[1]));
				long dateTime = query.getLong(query.getColumnIndexOrThrow(PROJECTION[2]));
				long size = query.getLong(query.getColumnIndexOrThrow(PROJECTION[4]));
				int width = query.getInt(query.getColumnIndexOrThrow(PROJECTION[6]));
				int height = query.getInt(query.getColumnIndexOrThrow(PROJECTION[7]));
				long duration = query.getLong(query.getColumnIndexOrThrow(PROJECTION[8]));
				if (FileHelper.fileExist(path)) {
					mediaDataList.add(new MediaData(path, name, dateTime, size, duration, width, height, MediaConstants.MEDIA_VIDEO_TYPE));
				}
			} while (query.moveToNext());
			query.close();
		}
		return mediaDataList;
	}

	// 加载所有图片和视频
	@Override public List<MediaData> loadLocalSystemAllMedias(Context context) {
		if (context == null) return null;
		List<MediaData> mediaDataList = new ArrayList<>();
		ContentResolver contentResolver = context.getContentResolver();
		Cursor query = contentResolver.query(QUERY_URI, PROJECTION, SELECTION_IMAGE_AND_VIDEO, SELECTION_ALL_FORMAT, ORDER);
		if (query != null && query.moveToFirst()) {
			do {
				String path = query.getString(query.getColumnIndexOrThrow(PROJECTION[0]));
				String name = query.getString(query.getColumnIndexOrThrow(PROJECTION[1]));
				long dateTime = query.getLong(query.getColumnIndexOrThrow(PROJECTION[2]));
				String mimeType = query.getString(query.getColumnIndexOrThrow(PROJECTION[3]));
				long size = query.getLong(query.getColumnIndexOrThrow(PROJECTION[4]));
				int width = query.getInt(query.getColumnIndexOrThrow(PROJECTION[6]));
				int height = query.getInt(query.getColumnIndexOrThrow(PROJECTION[7]));
				long duration = query.getLong(query.getColumnIndexOrThrow(PROJECTION[7]));
				// 非图片和视频跳过
				if (!String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE).equals(mimeType) && !String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO).equals(mimeType)) continue;
				if (FileHelper.fileExist(path)) {
					// 图片或者视频模式
					int mode = String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE).equals(mimeType) ? MediaConstants.MEDIA_PHOTO_TYPE : MediaConstants.MEDIA_VIDEO_TYPE;
					mediaDataList.add(new MediaData(path, name, dateTime, size, duration, width, height, mode));
				}
			} while (query.moveToNext());
			query.close();
		}
		return mediaDataList;
	}

	@Override public List<MediaData> loadAllPhotos(Context context) {
		return null;
	}

	@Override public List<MediaData> loadAllVideos(Context context) {
		return null;
	}

	@Override public List<MediaData> loadAllMedias(Context context) {
		return null;
	}

	@Override public List<MediaData> loadFolderPathPhotos(String folderPath) {
		if (TextUtils.isEmpty(folderPath) || !FileHelper.isMounted()) return null;
		List<MediaData> mediaDataList = new ArrayList<>();
		File folderDir = new File(folderPath);
		if (folderDir.exists() && folderDir.isDirectory()) {
			File[] listFiles = folderDir.listFiles();
			if (listFiles != null && listFiles.length > 0) {
				for (File file : listFiles) {
					String path = file.getAbsolutePath();
					if (!TextUtils.isEmpty(path)) {
						if (FileHelper.isPhotoFormat(path)) {
							mediaDataList.add(new MediaData(path, file.getName(), file.lastModified(), FileHelper.getFileSize(file), 0, 0, 0, MediaConstants.MEDIA_PHOTO_TYPE));
						}
					}
				}
			}
		}
		return mediaDataList;
	}

	@Override public List<MediaData> loadFolderPathVideos(String folderPath) {
		if (TextUtils.isEmpty(folderPath) || !FileHelper.isMounted()) return null;
		List<MediaData> mediaDataList = new ArrayList<>();
		File folderDir = new File(folderPath);
		if (folderDir.exists() && folderDir.isDirectory()) {
			File[] listFiles = folderDir.listFiles();
			if (listFiles != null && listFiles.length > 0) {
				for (File file : listFiles) {
					String path = file.getAbsolutePath();
					if (!TextUtils.isEmpty(path)) {
						if (FileHelper.isVideoFormat(path)) {
							int[] videoMeasure = FileHelper.getFileVideoMeasure(file);
							mediaDataList.add(new MediaData(path, file.getName(), file.lastModified(), FileHelper.getFileSize(file), FileHelper.getFileVideoDuration(file),
									videoMeasure != null ? videoMeasure[0] : 0, videoMeasure != null ? videoMeasure[1] : 0, MediaConstants.MEDIA_PHOTO_TYPE));
						}
					}
				}
			}
		}
		return mediaDataList;
	}

	@Override public List<MediaData> loadFolderPathAllMedias(String folderPath) {
		if (TextUtils.isEmpty(folderPath) || !FileHelper.isMounted()) return null;
		List<MediaData> mediaDataList = new ArrayList<>();
		File folderDir = new File(folderPath);
		if (folderDir.exists() && folderDir.isDirectory()) {
			File[] listFiles = folderDir.listFiles();
			if (listFiles != null && listFiles.length > 0) {
				for (File file : listFiles) {
					String path = file.getAbsolutePath();
					if (!TextUtils.isEmpty(path)) {
						if (FileHelper.isVideoFormat(path) || FileHelper.isPhotoFormat(path)) {
							int[] videoMeasure = FileHelper.getFileVideoMeasure(file);
							mediaDataList.add(new MediaData(path, file.getName(), file.lastModified(), FileHelper.getFileSize(file), FileHelper.getFileVideoDuration(file),
									videoMeasure != null ? videoMeasure[0] : 0, videoMeasure != null ? videoMeasure[1] : 0, MediaConstants.MEDIA_PHOTO_TYPE));
						}
					}
				}
			}
		}
		return mediaDataList;
	}

	@Override public void loadMediasTask(Context context, int mode, OnLoadMediaListener onLoadMediaListener, String... folderPath) {
		new LoadMediaTask(context, onLoadMediaListener, folderPath).execute(mode);
	}

}
