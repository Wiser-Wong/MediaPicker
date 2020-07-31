package com.wiser.picker.api.task;

import android.content.Context;
import android.os.AsyncTask;

import com.wiser.picker.api.config.MediaConstants;
import com.wiser.picker.api.core.MediaHelper;
import com.wiser.picker.api.model.MediaData;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @author Wiser
 * 
 *         加载媒体数据异步任务
 */
public class LoadMediaTask extends AsyncTask<Integer, Void, List<MediaData>> {

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

	@Override protected List<MediaData> doInBackground(Integer... integers) {
		List<MediaData> mediaDataList;
		int mode = integers[0];
		switch (mode) {
			case MediaConstants.LOAD_LOCAL_SYS_PHOTO_MODE:// 本地系统相册
				mediaDataList = MediaHelper.mediaManage().loadLocalSystemPhotos(reference != null ? reference.get() : null);
				break;
			case MediaConstants.LOAD_LOCAL_SYS_VIDEO_MODE:// 本地系统视频
				mediaDataList = MediaHelper.mediaManage().loadLocalSystemVideos(reference != null ? reference.get() : null);
				break;
			case MediaConstants.LOAD_LOCAL_SYS_ALL_MODE:// 本地系统图片和视频
				mediaDataList = MediaHelper.mediaManage().loadLocalSystemAllMedias(reference != null ? reference.get() : null);
				break;
			case MediaConstants.LOAD_FOLDER_PHOTO_MODE:// 本地文件夹图片
				mediaDataList = MediaHelper.mediaManage().loadFolderPathPhotos(folderPath);
				break;
			case MediaConstants.LOAD_FOLDER_VIDEO_MODE:// 本地文件夹视频
				mediaDataList = MediaHelper.mediaManage().loadFolderPathVideos(folderPath);
				break;
			case MediaConstants.LOAD_FOLDER_ALL_MODE:// 本地文件夹图片和视频
				mediaDataList = MediaHelper.mediaManage().loadFolderPathAllMedias(folderPath);
				break;
			default:
				throw new IllegalStateException("图片选择库没有查询到该模式 value: " + mode);
		}
		return mediaDataList;
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
