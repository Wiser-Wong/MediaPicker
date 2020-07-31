package com.wiser.picker.api.config;

/**
 * @author Wiser
 *
 *         媒体常量
 */
public interface MediaConstants {

	/******************* 权限码 ************************/
	// 权限请求码
	int	PERMISSION_MEDIA_REQUEST_CODE	= 1000;

	// 拍照权限请求码
	int	PERMISSION_CAMERA_REQUEST_CODE	= 1001;

	/******************* 获取数据方式 ************************/
	// 加载本地系统图片模式
	int	LOAD_LOCAL_SYS_PHOTO_MODE		= 2000;

	// 加载本地系统视频模式
	int	LOAD_LOCAL_SYS_VIDEO_MODE		= 2001;

	// 加载本地系统图片和视频模式
	int	LOAD_LOCAL_SYS_ALL_MODE			= 2002;

	// 加载指定文件夹下图片模式
	int	LOAD_FOLDER_PHOTO_MODE			= 2003;

	// 加载指定文件夹下视频模式
	int	LOAD_FOLDER_VIDEO_MODE			= 2004;

	// 加载指定文件夹下图片和视频模式
	int	LOAD_FOLDER_ALL_MODE			= 2005;

	/******************* 图片或者视频类型 ************************/
	// 图片
	int	MEDIA_PHOTO_TYPE				= 3000;

	// 视频
	int	MEDIA_VIDEO_TYPE				= 3001;
}
