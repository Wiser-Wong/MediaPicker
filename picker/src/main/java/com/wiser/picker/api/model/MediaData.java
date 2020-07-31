package com.wiser.picker.api.model;

/**
 * @author Wiser
 * 
 *         媒体数据
 */
public class MediaData {

	// 图片或者视频路径
	public String	path;

	// 图片或者视频名称
	public String	name;

	// 图片或者视频日期
	public long		dateTime;

	// 图片或者视频大小
	public long		size;

	// 视频时间
	public long		videoDuration;

	// 宽
	public int		width;

	// 高
	public int		height;

	// 媒体模式
	public int		mode;

	public MediaData() {}

	public MediaData(String path, String name, long dateTime, long size, long videoDuration, int width, int height, int mode) {
		this.path = path;
		this.name = name;
		this.dateTime = dateTime;
		this.size = size;
		this.videoDuration = videoDuration;
		this.width = width;
		this.height = height;
		this.mode = mode;
	}
}
