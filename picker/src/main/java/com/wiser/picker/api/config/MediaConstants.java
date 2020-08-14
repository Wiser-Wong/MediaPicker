package com.wiser.picker.api.config;

import android.os.Environment;

/**
 * @author Wiser
 *
 *         媒体常量
 */
public interface MediaConstants {

	/******************* 权限码 ************************/
	// 权限请求码
	int		PERMISSION_MEDIA_REQUEST_CODE		= 1000;

	// 拍照权限请求码
	int		PERMISSION_CAMERA_REQUEST_CODE		= 1001;

	/******************* 获取数据方式 ************************/
	// 加载本地系统图片模式
	int		LOAD_LOCAL_SYS_PHOTO_MODE			= 2000;

	// 加载本地系统视频模式
	int		LOAD_LOCAL_SYS_VIDEO_MODE			= 2001;

	// 加载本地系统图片和视频模式
	int		LOAD_LOCAL_SYS_ALL_MODE				= 2002;

	// 加载指定文件夹下图片模式
	int		LOAD_FOLDER_PHOTO_MODE				= 2003;

	// 加载指定文件夹下视频模式
	int		LOAD_FOLDER_VIDEO_MODE				= 2004;

	// 加载指定文件夹下图片和视频模式
	int		LOAD_FOLDER_ALL_MODE				= 2005;

	/******************* 图片或者视频类型 ************************/
	// 图片
	int		MEDIA_PHOTO_TYPE					= 3000;

	// 视频
	int		MEDIA_VIDEO_TYPE					= 3001;

	/******************** 图库或者拍照 ***************************************/
	// 拍照
	int		CAMERA_MODE							= 4000;

	// 媒体
	int		MEDIA_MODE							= 4001;

	/********************* 默认配置常量 ************************************************/
	// 默认展示列数
	int		DEFAULT_SPAN_COUNT					= 3;

	// 默认剩余选择数量
	int		DEFAULT_SURPLUS_COUNT				= 9;

	// 相机拍照请求码
	int		CAMERA_REQUEST_CODE					= 5000;

	// 裁剪请求码
	int		CROP_REQUEST_CODE					= 5001;

	// 默认压缩路径
	String	DEFAULT_COMPRESS_PATH				= Environment.getExternalStorageDirectory() + "/compress";

	// 默认压缩质量
	int		DEFAULT_COMPRESS_QUALITY			= 100;

	// 默认压缩宽
	int		DEFAULT_COMPRESS_WIDTH				= 480;

	// 默认压缩高
	int		DEFAULT_COMPRESS_HEIGHT				= 800;

	// 默认裁剪宽
	int		DEFAULT_CROP_WIDTH					= 800;

	// 默认裁剪高
	int		DEFAULT_CROP_HEIGHT					= 800;

	/**************************** 跳转参数Key值 *********************************/

	// 选择的媒体集合数据Key
	String	INTENT_SELECT_MEDIA_KEY				= "INTENT_SELECT_MEDIA_KEY";

	// 预览图片传递的所有媒体数据key
	String	PREVIEW_MEDIA_ALL_DATA_KEY			= "PREVIEW_MEDIA_ALL_DATA_KEY";

	// 预览图片传递的选择的媒体数据key
	String	PREVIEW_MEDIA_SELECT_DATA_KEY		= "PREVIEW_MEDIA_SELECT_DATA_KEY";

	// 预览媒体配置key
	String	PREVIEW_MEDIA_CONFIG_KEY			= "PREVIEW_MEDIA_CONFIG_KEY";

	// 预览单张图片Key
	String	PREVIEW_SINGLE_PHOTO_KEY			= "PREVIEW_SINGLE_PHOTO_KEY";

	// 预览单一视频Key
	String	PREVIEW_SINGLE_VIDEO_KEY			= "PREVIEW_SINGLE_VIDEO_KEY";

	// 预览媒体位置key
	String	PREVIEW_MEDIA_INDEX_KEY				= "PREVIEW_MEDIA_INDEX_KEY";

	// 剩余可选数量Key
	String	SURPLUS_COUNT_KEY					= "SURPLUS_COUNT_KEY";

	// 配置参数key
	String	MEDIA_CONFIG_KEY					= "MEDIA_CONFIG_KEY";

	// 预览类型key
	String	PREVIEW_TYPE_KEY					= "PREVIEW_TYPE_KEY";

	// 预览完成key
	String	PREVIEW_MEDIA_COMPLETE_KEY			= "PREVIEW_MEDIA_COMPLETE_KEY";

	// 预览完成value 用于判断是点击完成从预览返回到选择页面的
	int		PREVIEW_MEDIA_COMPLETE_VALUE		= 9000;

	/************************ 跳转请求码 ***************************************/

	// 跳转图片选择页请求码
	int		INTENT_SELECT_MEDIA_REQUEST_CODE	= 6000;

	// 跳转图片预览页请求码
	int		INTENT_PREVIEW_MEDIA_REQUEST_CODE	= 6001;

	/************************ 选中媒体check样式类型 ***************************************/
	// 选中图片Check UI 号码类型
	int		CHECK_UI_NUM_TYPE					= 7000;

	// 选中图片Check UI默认类型
	int		CHECK_UI_DEFAULT_TYPE				= 7001;

	/************************* 预览方式类型 *************************************/
	// 预览图片模式
	int		PREVIEW_PHOTO_TYPE					= 8000;

	// 预览按钮模式
	int		PREVIEW_BTN_TYPE					= 8001;

}
