package com.wiser.picker.api.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Wiser
 * 
 *         媒体数据
 */
public class MediaData implements Parcelable {

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
	public int		mediaType;

	// 是否选中
	public boolean	isSelect;

	// 选中的号码
	public int		selectNum;

	// 展示模式 是否有相机
	public int		showMode;

	public MediaData() {}

	public MediaData(String path, String name, long dateTime, long size, long videoDuration, int width, int height, int mediaType) {
		this.path = path;
		this.name = name;
		this.dateTime = dateTime;
		this.size = size;
		this.videoDuration = videoDuration;
		this.width = width;
		this.height = height;
		this.mediaType = mediaType;
	}

	protected MediaData(Parcel in) {
		path = in.readString();
		name = in.readString();
		dateTime = in.readLong();
		size = in.readLong();
		videoDuration = in.readLong();
		width = in.readInt();
		height = in.readInt();
		mediaType = in.readInt();
	}

	public static final Creator<MediaData> CREATOR = new Creator<MediaData>() {

		@Override public MediaData createFromParcel(Parcel in) {
			return new MediaData(in);
		}

		@Override public MediaData[] newArray(int size) {
			return new MediaData[size];
		}
	};

	@Override public int describeContents() {
		return 0;
	}

	@Override public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(path);
		dest.writeString(name);
		dest.writeLong(dateTime);
		dest.writeLong(size);
		dest.writeLong(videoDuration);
		dest.writeInt(width);
		dest.writeInt(height);
		dest.writeInt(mediaType);
	}
}
