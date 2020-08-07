package com.wiser.picker.api.core.query;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.wiser.picker.api.config.MediaConstants;
import com.wiser.picker.api.model.MediaData;
import com.wiser.picker.api.task.load.LoadMediaTask;
import com.wiser.picker.api.task.load.OnLoadMediaListener;
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

	@Override public List<MediaData> queryLocalSystemPhotos(Context context) {
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

	@Override public List<MediaData> queryLocalSystemPhotosBySize(Context context, long limitSize) {
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
					if (limitSize == 0) {
						mediaDataList.add(new MediaData(path, name, dateTime, size, 0, width, height, MediaConstants.MEDIA_PHOTO_TYPE));
						continue;
					}
					if (size <= limitSize * 1000) {
						mediaDataList.add(new MediaData(path, name, dateTime, size, 0, width, height, MediaConstants.MEDIA_PHOTO_TYPE));
					}
				}
			} while (query.moveToNext());
			query.close();
		}
		return mediaDataList;
	}

	// 加载所有视频
	@Override public List<MediaData> queryLocalSystemVideos(Context context) {
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

	@Override public List<MediaData> queryLocalSystemVideosBySize(Context context, long limitSize) {
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
					if (limitSize == 0) {
						mediaDataList.add(new MediaData(path, name, dateTime, size, duration, width, height, MediaConstants.MEDIA_VIDEO_TYPE));
						continue;
					}
					if (size <= limitSize * 1000) {
						mediaDataList.add(new MediaData(path, name, dateTime, size, duration, width, height, MediaConstants.MEDIA_VIDEO_TYPE));
					}
				}
			} while (query.moveToNext());
			query.close();
		}
		return mediaDataList;
	}

	@Override public List<MediaData> queryLocalSystemVideosByDuration(Context context, long limitDuration) {
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
					if (limitDuration == 0) {
						mediaDataList.add(new MediaData(path, name, dateTime, size, duration, width, height, MediaConstants.MEDIA_VIDEO_TYPE));
						continue;
					}
					if (duration <= limitDuration * 1000) {
						mediaDataList.add(new MediaData(path, name, dateTime, size, duration, width, height, MediaConstants.MEDIA_VIDEO_TYPE));
					}
				}
			} while (query.moveToNext());
			query.close();
		}
		return mediaDataList;
	}

	// 加载所有图片和视频
	@Override public List<MediaData> queryLocalSystemAllMedias(Context context) {
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
				long duration = query.getLong(query.getColumnIndexOrThrow(PROJECTION[8]));
				// 非图片和视频跳过
				if (!String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE).equals(mimeType) && !String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO).equals(mimeType)) continue;
				if (FileHelper.fileExist(path)) {
					// 图片或者视频模式
					int mediaType = String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE).equals(mimeType) ? MediaConstants.MEDIA_PHOTO_TYPE : MediaConstants.MEDIA_VIDEO_TYPE;
					mediaDataList.add(new MediaData(path, name, dateTime, size, duration, width, height, mediaType));
				}
			} while (query.moveToNext());
			query.close();
		}
		return mediaDataList;
	}

	@Override public List<MediaData> queryLocalSystemAllMediasBySize(Context context, long picLimitSize, long videoLimitSize) {
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
				long duration = query.getLong(query.getColumnIndexOrThrow(PROJECTION[8]));
				// 非图片和视频跳过
				if (!String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE).equals(mimeType) && !String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO).equals(mimeType)) continue;
				if (FileHelper.fileExist(path)) {
					// 图片或者视频模式
					int mediaType = String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE).equals(mimeType) ? MediaConstants.MEDIA_PHOTO_TYPE : MediaConstants.MEDIA_VIDEO_TYPE;
					// 图片
					if (mediaType == MediaConstants.MEDIA_PHOTO_TYPE) {
						if (picLimitSize == 0) {
							mediaDataList.add(new MediaData(path, name, dateTime, size, duration, width, height, mediaType));
							continue;
						}
						if (size <= picLimitSize * 1000) {
							mediaDataList.add(new MediaData(path, name, dateTime, size, duration, width, height, mediaType));
						}
					}
					// 视频
					if (mediaType == MediaConstants.MEDIA_VIDEO_TYPE) {
						if (videoLimitSize == 0) {
							mediaDataList.add(new MediaData(path, name, dateTime, size, duration, width, height, mediaType));
							continue;
						}
						if (size <= videoLimitSize * 1000) {
							mediaDataList.add(new MediaData(path, name, dateTime, size, duration, width, height, mediaType));
						}
					}
				}
			} while (query.moveToNext());
			query.close();
		}
		return mediaDataList;
	}

	@Override public List<MediaData> queryLocalSystemAllMediasBySizeAndDuration(Context context, long picLimitSize, long videoLimitDuration) {
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
				long duration = query.getLong(query.getColumnIndexOrThrow(PROJECTION[8]));
				// 非图片和视频跳过
				if (!String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE).equals(mimeType) && !String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO).equals(mimeType)) continue;
				if (FileHelper.fileExist(path)) {
					// 图片或者视频模式
					int mediaType = String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE).equals(mimeType) ? MediaConstants.MEDIA_PHOTO_TYPE : MediaConstants.MEDIA_VIDEO_TYPE;
					// 图片
					if (mediaType == MediaConstants.MEDIA_PHOTO_TYPE) {
						if (picLimitSize == 0) {
							mediaDataList.add(new MediaData(path, name, dateTime, size, duration, width, height, mediaType));
							continue;
						}
						if (size <= picLimitSize * 1000) {
							mediaDataList.add(new MediaData(path, name, dateTime, size, duration, width, height, mediaType));
						}
					}
					// 视频
					if (mediaType == MediaConstants.MEDIA_VIDEO_TYPE) {
						if (videoLimitDuration == 0) {
							mediaDataList.add(new MediaData(path, name, dateTime, size, duration, width, height, mediaType));
							continue;
						}
						if (duration <= videoLimitDuration * 1000) {
							mediaDataList.add(new MediaData(path, name, dateTime, size, duration, width, height, mediaType));
						}
					}
				}
			} while (query.moveToNext());
			query.close();
		}
		return mediaDataList;
	}

	@Override public List<MediaData> queryLocalSystemAllMediasByPicSize(Context context, long picLimitSize) {
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
				long duration = query.getLong(query.getColumnIndexOrThrow(PROJECTION[8]));
				// 非图片和视频跳过
				if (!String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE).equals(mimeType) && !String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO).equals(mimeType)) continue;
				if (FileHelper.fileExist(path)) {
					// 图片或者视频模式
					int mediaType = String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE).equals(mimeType) ? MediaConstants.MEDIA_PHOTO_TYPE : MediaConstants.MEDIA_VIDEO_TYPE;
					// 图片
					if (mediaType == MediaConstants.MEDIA_PHOTO_TYPE) {
						if (picLimitSize == 0) {
							mediaDataList.add(new MediaData(path, name, dateTime, size, duration, width, height, mediaType));
							continue;
						}
						if (size <= picLimitSize * 1000) {
							mediaDataList.add(new MediaData(path, name, dateTime, size, duration, width, height, mediaType));
						}
					}
					// 视频
					if (mediaType == MediaConstants.MEDIA_VIDEO_TYPE) {
						mediaDataList.add(new MediaData(path, name, dateTime, size, duration, width, height, mediaType));
					}
				}
			} while (query.moveToNext());
			query.close();
		}
		return mediaDataList;
	}

	@Override public List<MediaData> queryLocalSystemAllMediasByVideoSize(Context context, long videoLimitSize) {
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
				long duration = query.getLong(query.getColumnIndexOrThrow(PROJECTION[8]));
				// 非图片和视频跳过
				if (!String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE).equals(mimeType) && !String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO).equals(mimeType)) continue;
				if (FileHelper.fileExist(path)) {
					// 图片或者视频模式
					int mediaType = String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE).equals(mimeType) ? MediaConstants.MEDIA_PHOTO_TYPE : MediaConstants.MEDIA_VIDEO_TYPE;
					// 图片
					if (mediaType == MediaConstants.MEDIA_PHOTO_TYPE) {
						mediaDataList.add(new MediaData(path, name, dateTime, size, duration, width, height, mediaType));
					}
					// 视频
					if (mediaType == MediaConstants.MEDIA_VIDEO_TYPE) {
						if (videoLimitSize == 0) {
							mediaDataList.add(new MediaData(path, name, dateTime, size, duration, width, height, mediaType));
							continue;
						}
						if (size <= videoLimitSize * 1000) {
							mediaDataList.add(new MediaData(path, name, dateTime, size, duration, width, height, mediaType));
						}
					}
				}
			} while (query.moveToNext());
			query.close();
		}
		return mediaDataList;
	}

	@Override public List<MediaData> queryLocalSystemAllMediasByVideoDuration(Context context, long videoLimitDuration) {
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
				long duration = query.getLong(query.getColumnIndexOrThrow(PROJECTION[8]));
				// 非图片和视频跳过
				if (!String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE).equals(mimeType) && !String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO).equals(mimeType)) continue;
				if (FileHelper.fileExist(path)) {
					// 图片或者视频模式
					int mediaType = String.valueOf(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE).equals(mimeType) ? MediaConstants.MEDIA_PHOTO_TYPE : MediaConstants.MEDIA_VIDEO_TYPE;
					// 图片
					if (mediaType == MediaConstants.MEDIA_PHOTO_TYPE) {
						mediaDataList.add(new MediaData(path, name, dateTime, size, duration, width, height, mediaType));
					}
					// 视频
					if (mediaType == MediaConstants.MEDIA_VIDEO_TYPE) {
						if (videoLimitDuration == 0) {
							mediaDataList.add(new MediaData(path, name, dateTime, size, duration, width, height, mediaType));
							continue;
						}
						if (duration <= videoLimitDuration * 1000) {
							mediaDataList.add(new MediaData(path, name, dateTime, size, duration, width, height, mediaType));
						}
					}
				}
			} while (query.moveToNext());
			query.close();
		}
		return mediaDataList;
	}

	@Override public List<MediaData> queryFolderPathPhotos(String folderPath) {
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

	@Override public List<MediaData> queryFolderPathPhotosBySize(String folderPath, long limitSize) {
		if (TextUtils.isEmpty(folderPath) || !FileHelper.isMounted()) return null;
		List<MediaData> mediaDataList = new ArrayList<>();
		File folderDir = new File(folderPath);
		if (folderDir.exists() && folderDir.isDirectory()) {
			File[] listFiles = folderDir.listFiles();
			if (listFiles != null && listFiles.length > 0) {
				for (File file : listFiles) {
					String path = file.getAbsolutePath();
					if (!TextUtils.isEmpty(path)) {
						long size = FileHelper.getFileSize(file);
						if (FileHelper.isPhotoFormat(path)) {
							if (limitSize == 0) {
								mediaDataList.add(new MediaData(path, file.getName(), file.lastModified(), size, 0, 0, 0, MediaConstants.MEDIA_PHOTO_TYPE));
								continue;
							}
							if (size <= limitSize * 1000) {
								mediaDataList.add(new MediaData(path, file.getName(), file.lastModified(), size, 0, 0, 0, MediaConstants.MEDIA_PHOTO_TYPE));
							}
						}
					}
				}
			}
		}
		return mediaDataList;
	}

	@Override public List<MediaData> queryFolderPathVideos(String folderPath) {
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
									videoMeasure != null ? videoMeasure[0] : 0, videoMeasure != null ? videoMeasure[1] : 0, MediaConstants.MEDIA_VIDEO_TYPE));
						}
					}
				}
			}
		}
		return mediaDataList;
	}

	@Override public List<MediaData> queryFolderPathVideosBySize(String folderPath, long limitSize) {
		if (TextUtils.isEmpty(folderPath) || !FileHelper.isMounted()) return null;
		List<MediaData> mediaDataList = new ArrayList<>();
		File folderDir = new File(folderPath);
		if (folderDir.exists() && folderDir.isDirectory()) {
			File[] listFiles = folderDir.listFiles();
			if (listFiles != null && listFiles.length > 0) {
				for (File file : listFiles) {
					String path = file.getAbsolutePath();
					if (!TextUtils.isEmpty(path)) {
						long size = FileHelper.getFileSize(file);
						if (FileHelper.isVideoFormat(path)) {
							int[] videoMeasure = FileHelper.getFileVideoMeasure(file);
							if (limitSize == 0) {
								mediaDataList.add(new MediaData(path, file.getName(), file.lastModified(), size, FileHelper.getFileVideoDuration(file), videoMeasure != null ? videoMeasure[0] : 0,
										videoMeasure != null ? videoMeasure[1] : 0, MediaConstants.MEDIA_VIDEO_TYPE));
								continue;
							}
							if (size <= limitSize * 1000) {
								mediaDataList.add(new MediaData(path, file.getName(), file.lastModified(), size, FileHelper.getFileVideoDuration(file), videoMeasure != null ? videoMeasure[0] : 0,
										videoMeasure != null ? videoMeasure[1] : 0, MediaConstants.MEDIA_VIDEO_TYPE));
							}
						}
					}
				}
			}
		}
		return mediaDataList;
	}

	@Override public List<MediaData> queryFolderPathVideosByDuration(String folderPath, long limitDuration) {
		if (TextUtils.isEmpty(folderPath) || !FileHelper.isMounted()) return null;
		List<MediaData> mediaDataList = new ArrayList<>();
		File folderDir = new File(folderPath);
		if (folderDir.exists() && folderDir.isDirectory()) {
			File[] listFiles = folderDir.listFiles();
			if (listFiles != null && listFiles.length > 0) {
				for (File file : listFiles) {
					String path = file.getAbsolutePath();
					if (!TextUtils.isEmpty(path)) {
						long size = FileHelper.getFileSize(file);
						if (FileHelper.isVideoFormat(path)) {
							int[] videoMeasure = FileHelper.getFileVideoMeasure(file);
							if (limitDuration == 0) {
								mediaDataList.add(new MediaData(path, file.getName(), file.lastModified(), size, FileHelper.getFileVideoDuration(file), videoMeasure != null ? videoMeasure[0] : 0,
										videoMeasure != null ? videoMeasure[1] : 0, MediaConstants.MEDIA_VIDEO_TYPE));
								continue;
							}
							if (size <= limitDuration * 1000) {
								mediaDataList.add(new MediaData(path, file.getName(), file.lastModified(), size, FileHelper.getFileVideoDuration(file), videoMeasure != null ? videoMeasure[0] : 0,
										videoMeasure != null ? videoMeasure[1] : 0, MediaConstants.MEDIA_VIDEO_TYPE));
							}
						}
					}
				}
			}
		}
		return mediaDataList;
	}

	@Override public List<MediaData> queryFolderPathAllMedias(String folderPath) {
		if (TextUtils.isEmpty(folderPath) || !FileHelper.isMounted()) return null;
		List<MediaData> mediaDataList = new ArrayList<>();
		File folderDir = new File(folderPath);
		if (folderDir.exists() && folderDir.isDirectory()) {
			File[] listFiles = folderDir.listFiles();
			if (listFiles != null && listFiles.length > 0) {
				for (File file : listFiles) {
					String path = file.getAbsolutePath();
					if (!TextUtils.isEmpty(path)) {
						// 图片
						if (FileHelper.isPhotoFormat(path)) {
							mediaDataList.add(new MediaData(path, file.getName(), file.lastModified(), FileHelper.getFileSize(file), 0, 0, 0, MediaConstants.MEDIA_PHOTO_TYPE));
						}
						// 视频
						if (FileHelper.isVideoFormat(path)) {
							int[] videoMeasure = FileHelper.getFileVideoMeasure(file);
							mediaDataList.add(new MediaData(path, file.getName(), file.lastModified(), FileHelper.getFileSize(file), FileHelper.getFileVideoDuration(file),
									videoMeasure != null ? videoMeasure[0] : 0, videoMeasure != null ? videoMeasure[1] : 0, MediaConstants.MEDIA_VIDEO_TYPE));
						}
					}
				}
			}
		}
		return mediaDataList;
	}

	@Override public List<MediaData> queryFolderPathAllMediasBySize(String folderPath, long picLimitSize, long videoLimitSize) {
		if (TextUtils.isEmpty(folderPath) || !FileHelper.isMounted()) return null;
		List<MediaData> mediaDataList = new ArrayList<>();
		File folderDir = new File(folderPath);
		if (folderDir.exists() && folderDir.isDirectory()) {
			File[] listFiles = folderDir.listFiles();
			if (listFiles != null && listFiles.length > 0) {
				for (File file : listFiles) {
					String path = file.getAbsolutePath();
					if (!TextUtils.isEmpty(path)) {
						long size = FileHelper.getFileSize(file);
						// 图片
						if (FileHelper.isPhotoFormat(path)) {
							if (picLimitSize == 0) {
								mediaDataList.add(new MediaData(path, file.getName(), file.lastModified(), size, 0, 0, 0, MediaConstants.MEDIA_PHOTO_TYPE));
								continue;
							}
							if (size <= picLimitSize * 1000) {
								mediaDataList.add(new MediaData(path, file.getName(), file.lastModified(), size, 0, 0, 0, MediaConstants.MEDIA_PHOTO_TYPE));
							}
							continue;
						}
						// 视频
						if (FileHelper.isVideoFormat(path)) {
							int[] videoMeasure = FileHelper.getFileVideoMeasure(file);
							if (videoLimitSize == 0) {
								mediaDataList.add(new MediaData(path, file.getName(), file.lastModified(), size, FileHelper.getFileVideoDuration(file), videoMeasure != null ? videoMeasure[0] : 0,
										videoMeasure != null ? videoMeasure[1] : 0, MediaConstants.MEDIA_VIDEO_TYPE));
								continue;
							}
							if (size <= videoLimitSize * 1000) {
								mediaDataList.add(new MediaData(path, file.getName(), file.lastModified(), size, FileHelper.getFileVideoDuration(file), videoMeasure != null ? videoMeasure[0] : 0,
										videoMeasure != null ? videoMeasure[1] : 0, MediaConstants.MEDIA_VIDEO_TYPE));
							}
						}
					}
				}
			}
		}
		return mediaDataList;
	}

	@Override public List<MediaData> queryFolderPathAllMediasBySizeAndDuration(String folderPath, long picLimitSize, long videoLimitDuration) {
		if (TextUtils.isEmpty(folderPath) || !FileHelper.isMounted()) return null;
		List<MediaData> mediaDataList = new ArrayList<>();
		File folderDir = new File(folderPath);
		if (folderDir.exists() && folderDir.isDirectory()) {
			File[] listFiles = folderDir.listFiles();
			if (listFiles != null && listFiles.length > 0) {
				for (File file : listFiles) {
					String path = file.getAbsolutePath();
					if (!TextUtils.isEmpty(path)) {
						long size = FileHelper.getFileSize(file);
						// 图片
						if (FileHelper.isPhotoFormat(path)) {
							if (picLimitSize == 0) {
								mediaDataList.add(new MediaData(path, file.getName(), file.lastModified(), size, 0, 0, 0, MediaConstants.MEDIA_PHOTO_TYPE));
								continue;
							}
							if (size <= picLimitSize * 1000) {
								mediaDataList.add(new MediaData(path, file.getName(), file.lastModified(), size, 0, 0, 0, MediaConstants.MEDIA_PHOTO_TYPE));
							}
							continue;
						}
						long duration = FileHelper.getFileVideoDuration(file);
						// 视频
						if (FileHelper.isVideoFormat(path)) {
							int[] videoMeasure = FileHelper.getFileVideoMeasure(file);
							if (videoLimitDuration == 0) {
								mediaDataList.add(new MediaData(path, file.getName(), file.lastModified(), size, duration, videoMeasure != null ? videoMeasure[0] : 0,
										videoMeasure != null ? videoMeasure[1] : 0, MediaConstants.MEDIA_VIDEO_TYPE));
								continue;
							}
							if (duration <= videoLimitDuration * 1000) {
								mediaDataList.add(new MediaData(path, file.getName(), file.lastModified(), size, duration, videoMeasure != null ? videoMeasure[0] : 0,
										videoMeasure != null ? videoMeasure[1] : 0, MediaConstants.MEDIA_VIDEO_TYPE));
							}
						}
					}
				}
			}
		}
		return mediaDataList;
	}

	@Override public List<MediaData> queryFolderPathAllMediasByPicSize(String folderPath, long picLimitSize) {
		if (TextUtils.isEmpty(folderPath) || !FileHelper.isMounted()) return null;
		List<MediaData> mediaDataList = new ArrayList<>();
		File folderDir = new File(folderPath);
		if (folderDir.exists() && folderDir.isDirectory()) {
			File[] listFiles = folderDir.listFiles();
			if (listFiles != null && listFiles.length > 0) {
				for (File file : listFiles) {
					String path = file.getAbsolutePath();
					if (!TextUtils.isEmpty(path)) {
						long size = FileHelper.getFileSize(file);
						if (FileHelper.isPhotoFormat(path)) {
							if (picLimitSize == 0) {
								mediaDataList.add(new MediaData(path, file.getName(), file.lastModified(), size, 0, 0, 0, MediaConstants.MEDIA_PHOTO_TYPE));
								continue;
							}
							if (size <= picLimitSize * 1000) {
								mediaDataList.add(new MediaData(path, file.getName(), file.lastModified(), size, 0, 0, 0, MediaConstants.MEDIA_PHOTO_TYPE));
							}
							continue;
						}
						if (FileHelper.isVideoFormat(path)) {
							int[] videoMeasure = FileHelper.getFileVideoMeasure(file);
							mediaDataList.add(new MediaData(path, file.getName(), file.lastModified(), size, FileHelper.getFileVideoDuration(file), videoMeasure != null ? videoMeasure[0] : 0,
									videoMeasure != null ? videoMeasure[1] : 0, MediaConstants.MEDIA_VIDEO_TYPE));
						}
					}
				}
			}
		}
		return mediaDataList;
	}

	@Override public List<MediaData> queryFolderPathAllMediasByVideoSize(String folderPath, long videoLimitSize) {
		if (TextUtils.isEmpty(folderPath) || !FileHelper.isMounted()) return null;
		List<MediaData> mediaDataList = new ArrayList<>();
		File folderDir = new File(folderPath);
		if (folderDir.exists() && folderDir.isDirectory()) {
			File[] listFiles = folderDir.listFiles();
			if (listFiles != null && listFiles.length > 0) {
				for (File file : listFiles) {
					String path = file.getAbsolutePath();
					if (!TextUtils.isEmpty(path)) {
						long size = FileHelper.getFileSize(file);
						if (FileHelper.isPhotoFormat(path)) {
							mediaDataList.add(new MediaData(path, file.getName(), file.lastModified(), size, 0, 0, 0, MediaConstants.MEDIA_PHOTO_TYPE));
							continue;
						}
						if (FileHelper.isVideoFormat(path)) {
							int[] videoMeasure = FileHelper.getFileVideoMeasure(file);
							if (videoLimitSize == 0) {
								mediaDataList.add(new MediaData(path, file.getName(), file.lastModified(), size, FileHelper.getFileVideoDuration(file), videoMeasure != null ? videoMeasure[0] : 0,
										videoMeasure != null ? videoMeasure[1] : 0, MediaConstants.MEDIA_VIDEO_TYPE));
								continue;
							}
							if (size <= videoLimitSize * 1000) {
								mediaDataList.add(new MediaData(path, file.getName(), file.lastModified(), size, FileHelper.getFileVideoDuration(file), videoMeasure != null ? videoMeasure[0] : 0,
										videoMeasure != null ? videoMeasure[1] : 0, MediaConstants.MEDIA_VIDEO_TYPE));
							}
						}
					}
				}
			}
		}
		return mediaDataList;
	}

	@Override public List<MediaData> queryFolderPathAllMediasByVideoDuration(String folderPath, long videoLimitDuration) {
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
							continue;
						}
						long duration = FileHelper.getFileVideoDuration(file);
						if (FileHelper.isVideoFormat(path)) {
							int[] videoMeasure = FileHelper.getFileVideoMeasure(file);
							if (videoLimitDuration == 0) {
								mediaDataList.add(new MediaData(path, file.getName(), file.lastModified(), FileHelper.getFileSize(file), duration, videoMeasure != null ? videoMeasure[0] : 0,
										videoMeasure != null ? videoMeasure[1] : 0, MediaConstants.MEDIA_VIDEO_TYPE));
								continue;
							}
							if (duration <= videoLimitDuration * 1000) {
								mediaDataList.add(new MediaData(path, file.getName(), file.lastModified(), FileHelper.getFileSize(file), duration, videoMeasure != null ? videoMeasure[0] : 0,
										videoMeasure != null ? videoMeasure[1] : 0, MediaConstants.MEDIA_VIDEO_TYPE));
							}
						}
					}
				}
			}
		}
		return mediaDataList;
	}

	@Override public void loadMediasTask(Context context, int mode, OnLoadMediaListener onLoadMediaListener, String... folderPath) {
		new LoadMediaTask(context, onLoadMediaListener, folderPath).execute((long) mode);
	}

	@Override public void loadMediasTask(Context context, int mode, long queryPicLimitSize, long queryVideoLimitSize, long queryVideoLimitDuration, OnLoadMediaListener onLoadMediaListener, String... folderPath) {
		new LoadMediaTask(context, onLoadMediaListener, folderPath).execute((long) mode, queryPicLimitSize, queryVideoLimitSize, queryVideoLimitDuration);
	}

}
