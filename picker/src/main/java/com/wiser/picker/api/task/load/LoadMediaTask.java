package com.wiser.picker.api.task.load;

import android.content.Context;
import android.os.AsyncTask;

import com.wiser.picker.api.config.MediaConstants;
import com.wiser.picker.lib.MediaHelper;
import com.wiser.picker.api.model.MediaData;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @author Wiser
 * 
 *         加载媒体数据异步任务
 */
public class LoadMediaTask extends AsyncTask<Long, Void, List<MediaData>> {

	private WeakReference<Context>	reference;

	private OnLoadMediaListener		onLoadMediaListener;

	private String					folderPath;

	public LoadMediaTask(Context context, OnLoadMediaListener onLoadMediaListener, String... folderPath) {
		reference = new WeakReference<>(context);
		this.onLoadMediaListener = onLoadMediaListener;
		if (folderPath != null && folderPath.length > 0) {
			this.folderPath = folderPath[0];
		}
	}

	@Override protected void onPreExecute() {
		super.onPreExecute();
		if (onLoadMediaListener != null) onLoadMediaListener.onLoadMediaLoading();
	}

	@Override protected List<MediaData> doInBackground(Long... longs) {
		if (longs != null && longs.length > 0) {
			List<MediaData> mediaDataList;
			long mode = longs[0];
			long queryPicLimitSize = longs.length > 1 ? longs[1] : 0;
			long queryVideoLimitSize = longs.length > 2 ? longs[2] : 0;
			long queryVideoLimitDuration = longs.length > 3 ? longs[3] : 0;
			switch ((int) mode) {
				case MediaConstants.LOAD_LOCAL_SYS_PHOTO_MODE:// 本地系统相册
					mediaDataList = MediaHelper.mediaQueryManage().queryLocalSystemPhotosBySize(reference != null ? reference.get() : null, queryPicLimitSize);
					break;
				case MediaConstants.LOAD_LOCAL_SYS_VIDEO_MODE:// 本地系统视频
					if (queryVideoLimitSize > 0) {
						mediaDataList = MediaHelper.mediaQueryManage().queryLocalSystemVideosBySize(reference != null ? reference.get() : null, queryVideoLimitSize);
					} else if (queryVideoLimitDuration > 0) {
						mediaDataList = MediaHelper.mediaQueryManage().queryLocalSystemVideosByDuration(reference != null ? reference.get() : null, queryVideoLimitDuration);
					} else {
						mediaDataList = MediaHelper.mediaQueryManage().queryLocalSystemVideos(reference != null ? reference.get() : null);
					}
					break;
				case MediaConstants.LOAD_LOCAL_SYS_ALL_MODE:// 本地系统图片和视频
					if (queryVideoLimitSize > 0) {
						mediaDataList = MediaHelper.mediaQueryManage().queryLocalSystemAllMediasBySize(reference != null ? reference.get() : null, queryPicLimitSize, queryVideoLimitSize);
					} else if (queryVideoLimitDuration > 0) {
						mediaDataList = MediaHelper.mediaQueryManage().queryLocalSystemAllMediasBySizeAndDuration(reference != null ? reference.get() : null, queryPicLimitSize,
								queryVideoLimitDuration);
					} else {
						mediaDataList = MediaHelper.mediaQueryManage().queryLocalSystemAllMediasByPicSize(reference != null ? reference.get() : null, queryPicLimitSize);
					}
					break;
				case MediaConstants.LOAD_FOLDER_PHOTO_MODE:// 本地文件夹图片
					mediaDataList = MediaHelper.mediaQueryManage().queryFolderPathPhotosBySize(folderPath, queryPicLimitSize);
					break;
				case MediaConstants.LOAD_FOLDER_VIDEO_MODE:// 本地文件夹视频
					if (queryVideoLimitSize > 0) {
						mediaDataList = MediaHelper.mediaQueryManage().queryFolderPathVideosBySize(folderPath, queryVideoLimitSize);
					} else if (queryVideoLimitDuration > 0) {
						mediaDataList = MediaHelper.mediaQueryManage().queryFolderPathVideosByDuration(folderPath, queryVideoLimitDuration);
					} else {
						mediaDataList = MediaHelper.mediaQueryManage().queryFolderPathVideos(folderPath);
					}
					break;
				case MediaConstants.LOAD_FOLDER_ALL_MODE:// 本地文件夹图片和视频
					if (queryVideoLimitSize > 0) {
						mediaDataList = MediaHelper.mediaQueryManage().queryFolderPathAllMediasBySize(folderPath, queryPicLimitSize, queryVideoLimitSize);
					} else if (queryVideoLimitDuration > 0) {
						mediaDataList = MediaHelper.mediaQueryManage().queryFolderPathAllMediasBySizeAndDuration(folderPath, queryPicLimitSize, queryVideoLimitDuration);
					} else {
						mediaDataList = MediaHelper.mediaQueryManage().queryFolderPathAllMediasByPicSize(folderPath, queryPicLimitSize);
					}
					break;
				default:
					throw new IllegalStateException("图片选择库没有查询到该模式 value: " + mode);
			}
			return mediaDataList;
		}
		return null;
	}

	@Override protected void onPostExecute(List<MediaData> mediaData) {
		super.onPostExecute(mediaData);
		if (onLoadMediaListener != null) onLoadMediaListener.onLoadMediaComplete(mediaData);
		onDetach();
	}

	private void onDetach() {
		if (reference != null) reference.clear();
		reference = null;
		onLoadMediaListener = null;
	}

}
