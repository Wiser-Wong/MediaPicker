package com.wiser.picker.api.core.query;

import android.content.Context;

import com.wiser.picker.api.model.MediaData;
import com.wiser.picker.api.task.load.OnLoadMediaListener;

import java.util.List;

/**
 * @author Wiser
 * 
 *         媒体查询管理接口
 */
public interface IMediaQueryManage {

	/**
	 * 查询本地系统图片
	 *
	 * @return
	 */
	List<MediaData> queryLocalSystemPhotos(Context context);

	/**
	 * 查询本地系统图片
	 * 
	 * @param limitSize
	 *            限制大小 （kb）
	 * @return
	 */
	List<MediaData> queryLocalSystemPhotosBySize(Context context, long limitSize);

	/**
	 * 查询本地系统视频
	 *
	 * @return
	 */
	List<MediaData> queryLocalSystemVideos(Context context);

	/**
	 * 查询本地系统视频
	 * 
	 * @param limitSize
	 *            限制大小 （kb）
	 * @return
	 */
	List<MediaData> queryLocalSystemVideosBySize(Context context, long limitSize);

	/**
	 * 查询本地系统视频
	 * 
	 * @param limitDuration
	 *            限制大小 （s）
	 * @return
	 */
	List<MediaData> queryLocalSystemVideosByDuration(Context context, long limitDuration);

	/**
	 * 查询本地系统视频和图片
	 *
	 * @return
	 */
	List<MediaData> queryLocalSystemAllMedias(Context context);

	/**
	 * 查询本地系统视频和图片
	 * 
	 * @param picLimitSize
	 *            图片限制大小 （kb）
	 * @param videoLimitSize
	 *            视频限制大小 （kb）
	 * @return
	 */
	List<MediaData> queryLocalSystemAllMediasBySize(Context context, long picLimitSize, long videoLimitSize);

	/**
	 * 查询本地系统视频和图片
	 * 
	 * @param picLimitSize
	 *            图片限制大小 （kb）
	 * @param videoLimitDuration
	 *            视频限制时长（s）
	 * @return
	 */
	List<MediaData> queryLocalSystemAllMediasBySizeAndDuration(Context context, long picLimitSize, long videoLimitDuration);

	/**
	 * 查询本地系统视频和图片
	 *
	 * @param picLimitSize
	 *            图片限制大小 （kb）
	 * @return
	 */
	List<MediaData> queryLocalSystemAllMediasByPicSize(Context context, long picLimitSize);

	/**
	 * 查询本地系统视频和图片
	 *
	 * @param videoLimitSize
	 *            视频限制大小 （kb）
	 * @return
	 */
	List<MediaData> queryLocalSystemAllMediasByVideoSize(Context context, long videoLimitSize);

	/**
	 * 查询本地系统视频和图片
	 *
	 * @param videoLimitDuration
	 *            视频限制时长（s）
	 * @return
	 */
	List<MediaData> queryLocalSystemAllMediasByVideoDuration(Context context, long videoLimitDuration);

	/**
	 * 查询指定路径图片
	 *
	 * @return
	 */
	List<MediaData> queryFolderPathPhotos(String folderPath);

	/**
	 * 查询指定路径图片
	 * 
	 * @param limitSize
	 *            限制大小 （kb）
	 * @return
	 */
	List<MediaData> queryFolderPathPhotosBySize(String folderPath, long limitSize);

	/**
	 * 查询指定路径视频
	 *
	 * @return
	 */
	List<MediaData> queryFolderPathVideos(String folderPath);

	/**
	 * 查询指定路径视频
	 * 
	 * @param limitSize
	 *            限制大小 （kb）
	 * @return
	 */
	List<MediaData> queryFolderPathVideosBySize(String folderPath, long limitSize);

	/**
	 * 查询指定路径视频
	 *
	 * @param limitDuration
	 *            视频限制时长（s）
	 * @return
	 */
	List<MediaData> queryFolderPathVideosByDuration(String folderPath, long limitDuration);

	/**
	 * 查询指定路径图片和视频
	 *
	 * @return
	 */
	List<MediaData> queryFolderPathAllMedias(String folderPath);

	/**
	 * 查询指定路径图片和视频
	 * 
	 * @param picLimitSize
	 *            图片限制大小 （kb）
	 * @param videoLimitSize
	 *            视频限制大小 （kb）
	 * @return
	 */
	List<MediaData> queryFolderPathAllMediasBySize(String folderPath, long picLimitSize, long videoLimitSize);

	/**
	 * 查询指定路径图片和视频
	 *
	 * @param picLimitSize
	 *            图片限制大小 （kb）
	 * @param videoLimitDuration
	 *            视频限制时长（s）
	 * @return
	 */
	List<MediaData> queryFolderPathAllMediasBySizeAndDuration(String folderPath, long picLimitSize, long videoLimitDuration);

	/**
	 * 查询指定路径图片和视频
	 *
	 * @param picLimitSize
	 *            图片限制大小 （kb）
	 * @return
	 */
	List<MediaData> queryFolderPathAllMediasByPicSize(String folderPath, long picLimitSize);

	/**
	 * 查询指定路径图片和视频
	 *
	 * @param videoLimitSize
	 *            视频限制大小 （kb）
	 * @return
	 */
	List<MediaData> queryFolderPathAllMediasByVideoSize(String folderPath, long videoLimitSize);

	/**
	 * 查询指定路径图片和视频
	 *
	 * @param videoLimitDuration
	 *            视频限制时长（s）
	 * @return
	 */
	List<MediaData> queryFolderPathAllMediasByVideoDuration(String folderPath, long videoLimitDuration);

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

	/**
	 * 异步加载图片和视频
	 *
	 * @param context
	 * @param mode
	 *            加载方式
	 * @param picLimitSize
	 *            图片限制大小 （kb）
	 * @param videoLimitSize
	 *            视频限制大小 （kb）
	 * @param videoLimitDuration
	 *            视频限制时长（s）
	 * @param onLoadMediaListener
	 *            加载监听
	 * @param folderPath
	 *            文件夹路径
	 */
	void loadMediasTask(Context context, int mode, long picLimitSize, long videoLimitSize, long videoLimitDuration, OnLoadMediaListener onLoadMediaListener, String... folderPath);

}
