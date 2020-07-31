package com.wiser.picker.api.core.query;

import android.content.Context;

import com.wiser.picker.api.model.MediaData;
import com.wiser.picker.api.task.OnLoadMediaListener;

import java.util.List;

/**
 * @author Wiser
 * 
 *         媒体查询管理接口
 */
public interface IMediaQueryManage {

	/**
	 * 加载本地系统图片
	 *
	 * @return
	 */
	List<MediaData> loadLocalSystemPhotos(Context context);

	/**
	 * 加载本地系统视频
	 *
	 * @return
	 */
	List<MediaData> loadLocalSystemVideos(Context context);

	/**
	 * 加载本地系统视频和图片
	 *
	 * @return
	 */
	List<MediaData> loadLocalSystemAllMedias(Context context);

	/**
	 * 加载所有图片
	 *
	 * @return
	 */
	List<MediaData> loadAllPhotos(Context context);

	/**
	 * 加载所有视频
	 *
	 * @return
	 */
	List<MediaData> loadAllVideos(Context context);

	/**
	 * 加载所有图片和视频
	 *
	 * @return
	 */
	List<MediaData> loadAllMedias(Context context);

	/**
	 * 加载指定路径图片
	 *
	 * @return
	 */
	List<MediaData> loadFolderPathPhotos(String folderPath);

	/**
	 * 加载指定路径视频
	 *
	 * @return
	 */
	List<MediaData> loadFolderPathVideos(String folderPath);

	/**
	 * 加载指定路径图片和视频
	 *
	 * @return
	 */
	List<MediaData> loadFolderPathAllMedias(String folderPath);

	/**
	 * 异步加载图片和视频
	 * 
	 * @param context
	 * @param mode
	 *            加载方式
	 * @param onLoadMediaListener
	 *            加载监听
	 * @param folderPath
	 *            文件夹路径
	 */
	void loadMediasTask(Context context, int mode, OnLoadMediaListener onLoadMediaListener, String... folderPath);

}
