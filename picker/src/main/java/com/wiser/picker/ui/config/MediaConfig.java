package com.wiser.picker.ui.config;

import android.os.Parcel;
import android.os.Parcelable;

import com.wiser.picker.api.config.MediaConstants;

/**
 * @author Wiser
 * 
 *         媒体设置 配置
 */
public class MediaConfig implements Parcelable {

	public int		surplusCount;				// 剩余选择数量

	public int		spanCount;					// 展示列数

	public int		queryMode;					// 查询模式 查询图片或者查询视频或者查询图片和视频

	public int		showMode;					// 展示模式 camera是否显示或者只展示图片或者视频缩略图

	public String	folderPath;					// 文件夹路径

	public String	compressPath;				// 压缩路径

	public boolean	isCompress;					// 是否压缩

	public int		compressWidth;				// 压缩宽度

	public int		compressHeight;				// 压缩高度

	public int		compressQuality;			// 压缩质量

	public long		compressPhotoSize;			// 压缩图片大小 (kb)

	public boolean	isCameraCrop;				// 是否相机裁剪

	public int		cropWidth;					// 裁剪宽度

	public int		cropHeight;					// 裁剪高度

	public boolean	isFolderDisplay;			// 是否文件夹显示

	public long		queryPicLimitSize;			// 图片查询限制大小

	public long		queryVideoLimitSize;		// 视频查询限制大小

	public long		queryVideoLimitDuration;	// 视频查询限制时长

	public int		checkUiType;				// 选中媒体的Check样式

	public MediaConfig() {
		this(new Builder());
	}

	public MediaConfig(Builder builder) {
		this.surplusCount = builder.surplusCount;
		this.spanCount = builder.spanCount;
		this.queryMode = builder.queryMode;
		this.showMode = builder.showMode;
		this.folderPath = builder.folderPath;
		this.compressPath = builder.compressPath;
		this.isCompress = builder.isCompress;
		this.compressWidth = builder.compressWidth;
		this.compressHeight = builder.compressHeight;
		this.compressQuality = builder.compressQuality;
		this.compressPhotoSize = builder.compressPhotoSize;
		this.isCameraCrop = builder.isCameraCrop;
		this.cropWidth = builder.cropWidth;
		this.cropHeight = builder.cropHeight;
		this.isFolderDisplay = builder.isFolderDisplay;
		this.queryPicLimitSize = builder.queryPicLimitSize;
		this.queryVideoLimitSize = builder.queryVideoLimitSize;
		this.queryVideoLimitDuration = builder.queryVideoLimitDuration;
		this.checkUiType = builder.checkUiType;
	}

	protected MediaConfig(Parcel in) {
		surplusCount = in.readInt();
		spanCount = in.readInt();
		queryMode = in.readInt();
		showMode = in.readInt();
		folderPath = in.readString();
		compressPath = in.readString();
		isCompress = in.readByte() != 0;
		compressWidth = in.readInt();
		compressHeight = in.readInt();
		compressQuality = in.readInt();
		compressPhotoSize = in.readLong();
		isCameraCrop = in.readByte() != 0;
		cropWidth = in.readInt();
		cropHeight = in.readInt();
		isFolderDisplay = in.readByte() != 0;
		queryPicLimitSize = in.readLong();
		queryVideoLimitSize = in.readLong();
		queryVideoLimitDuration = in.readLong();
		checkUiType = in.readInt();
	}

	public static final Creator<MediaConfig> CREATOR = new Creator<MediaConfig>() {

		@Override public MediaConfig createFromParcel(Parcel in) {
			return new MediaConfig(in);
		}

		@Override public MediaConfig[] newArray(int size) {
			return new MediaConfig[size];
		}
	};

	public Builder newBuilder() {
		return new Builder(this);
	}

	@Override public int describeContents() {
		return 0;
	}

	@Override public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(surplusCount);
		dest.writeInt(spanCount);
		dest.writeInt(queryMode);
		dest.writeInt(showMode);
		dest.writeString(folderPath);
		dest.writeString(compressPath);
		dest.writeByte((byte) (isCompress ? 1 : 0));
		dest.writeInt(compressWidth);
		dest.writeInt(compressHeight);
		dest.writeInt(compressQuality);
		dest.writeLong(compressPhotoSize);
		dest.writeByte((byte) (isCameraCrop ? 1 : 0));
		dest.writeInt(cropWidth);
		dest.writeInt(cropHeight);
		dest.writeByte((byte) (isFolderDisplay ? 1 : 0));
		dest.writeLong(queryPicLimitSize);
		dest.writeLong(queryVideoLimitSize);
		dest.writeLong(queryVideoLimitDuration);
		dest.writeInt(checkUiType);
	}

	public static class Builder {

		private int		surplusCount	= MediaConstants.DEFAULT_SURPLUS_COUNT;

		private int		spanCount		= MediaConstants.DEFAULT_SPAN_COUNT;

		private int		queryMode		= MediaConstants.LOAD_LOCAL_SYS_PHOTO_MODE;	// 查询模式 默认只查图片

		private int		showMode		= MediaConstants.MEDIA_MODE;				// 展示模式 默认媒体模式没有相机

		private String	folderPath;

		private String	compressPath	= MediaConstants.DEFAULT_COMPRESS_PATH;		// 默认压缩路径

		private boolean	isCompress;

		private int		compressWidth;

		private int		compressHeight;

		private int		compressQuality;

		private long	compressPhotoSize;

		private boolean	isCameraCrop;

		private int		cropWidth;

		private int		cropHeight;

		private boolean	isFolderDisplay;

		public long		queryPicLimitSize;											// 图片查询限制大小

		public long		queryVideoLimitSize;										// 视频查询限制大小

		public long		queryVideoLimitDuration;									// 视频查询限制时长

		public int		checkUiType		= MediaConstants.CHECK_UI_DEFAULT_TYPE;		// 选中媒体的Check样式

		public Builder() {}

		public Builder(MediaConfig config) {
			surplusCount = config.surplusCount;
			spanCount = config.spanCount;
			queryMode = config.queryMode;
			showMode = config.showMode;
			folderPath = config.folderPath;
			compressPath = config.compressPath;
			isCompress = config.isCompress;
			compressWidth = config.compressWidth;
			compressHeight = config.compressHeight;
			compressQuality = config.compressQuality;
			compressPhotoSize = config.compressPhotoSize;
			isCameraCrop = config.isCameraCrop;
			cropWidth = config.cropWidth;
			cropHeight = config.cropHeight;
			isFolderDisplay = config.isFolderDisplay;
			queryPicLimitSize = config.queryPicLimitSize;
			queryVideoLimitSize = config.queryVideoLimitSize;
			queryVideoLimitDuration = config.queryVideoLimitDuration;
			checkUiType = config.checkUiType;
		}

		public Builder ofSurplusCount(int surplusCount) {
			this.surplusCount = surplusCount;
			return this;
		}

		public Builder ofSpanCount(int spanCount) {
			this.spanCount = spanCount;
			return this;
		}

		public Builder ofCheckUiNumType() {
			this.checkUiType = MediaConstants.CHECK_UI_NUM_TYPE;
			return this;
		}

		public Builder queryMode(int queryMode, String... folderPath) {
			this.queryMode = queryMode;
			if (folderPath != null && folderPath.length > 0) {
				this.folderPath = folderPath[0];
			}
			return this;
		}

		public Builder queryPic() {
			this.queryMode = MediaConstants.LOAD_LOCAL_SYS_PHOTO_MODE;
			return this;
		}

		public Builder queryPhotoBySize(long queryPicLimitSize) {
			this.queryMode = MediaConstants.LOAD_LOCAL_SYS_PHOTO_MODE;
			this.queryPicLimitSize = queryPicLimitSize;
			return this;
		}

		public Builder queryPhotoFolder(String folderPath) {
			this.queryMode = MediaConstants.LOAD_FOLDER_PHOTO_MODE;
			this.folderPath = folderPath;
			return this;
		}

		public Builder queryPhotoFolderBySize(String folderPath, long limitSize) {
			this.queryMode = MediaConstants.LOAD_FOLDER_PHOTO_MODE;
			this.folderPath = folderPath;
			this.queryPicLimitSize = limitSize;
			return this;
		}

		public Builder queryVideo() {
			this.queryMode = MediaConstants.LOAD_LOCAL_SYS_VIDEO_MODE;
			return this;
		}

		public Builder queryVideoBySize(long limitSize) {
			this.queryMode = MediaConstants.LOAD_LOCAL_SYS_VIDEO_MODE;
			this.queryVideoLimitSize = limitSize;
			return this;
		}

		public Builder queryVideoByDuration(long videoLimitDuration) {
			this.queryMode = MediaConstants.LOAD_LOCAL_SYS_VIDEO_MODE;
			this.queryVideoLimitDuration = videoLimitDuration;
			return this;
		}

		public Builder queryVideoFolder(String folderPath) {
			this.queryMode = MediaConstants.LOAD_FOLDER_ALL_MODE;
			this.folderPath = folderPath;
			return this;
		}

		public Builder queryVideoFolderModeBySize(String folderPath, long limitSize) {
			this.queryMode = MediaConstants.LOAD_FOLDER_ALL_MODE;
			this.folderPath = folderPath;
			this.queryVideoLimitSize = limitSize;
			return this;
		}

		public Builder queryVideoFolderByDuration(String folderPath, long videoLimitDuration) {
			this.queryMode = MediaConstants.LOAD_FOLDER_ALL_MODE;
			this.folderPath = folderPath;
			this.queryVideoLimitDuration = videoLimitDuration;
			return this;
		}

		public Builder queryAll() {
			this.queryMode = MediaConstants.LOAD_LOCAL_SYS_ALL_MODE;
			return this;
		}

		public Builder queryAllBySize(long picLimitSize, long videoLimitSize) {
			this.queryMode = MediaConstants.LOAD_LOCAL_SYS_ALL_MODE;
			this.queryPicLimitSize = picLimitSize;
			this.queryVideoLimitSize = videoLimitSize;
			return this;
		}

		public Builder queryAllBySizeAndDuration(long picLimitSize, long videoLimitDuration) {
			this.queryMode = MediaConstants.LOAD_LOCAL_SYS_ALL_MODE;
			this.queryPicLimitSize = picLimitSize;
			this.queryVideoLimitDuration = videoLimitDuration;
			return this;
		}

		public Builder queryAllByPicSize(long picLimitSize) {
			this.queryMode = MediaConstants.LOAD_LOCAL_SYS_ALL_MODE;
			this.queryPicLimitSize = picLimitSize;
			return this;
		}

		public Builder queryAllByVideoSize(long videoLimitSize) {
			this.queryMode = MediaConstants.LOAD_LOCAL_SYS_ALL_MODE;
			this.queryVideoLimitSize = videoLimitSize;
			return this;
		}

		public Builder queryAllByVideoDuration(long videoLimitDuration) {
			this.queryMode = MediaConstants.LOAD_LOCAL_SYS_ALL_MODE;
			this.queryVideoLimitDuration = videoLimitDuration;
			return this;
		}

		public Builder queryAllFolder(String folderPath) {
			this.queryMode = MediaConstants.LOAD_FOLDER_ALL_MODE;
			this.folderPath = folderPath;
			return this;
		}

		public Builder queryAllFolderBySize(String folderPath, long picLimitSize, long videoLimitSize) {
			this.queryMode = MediaConstants.LOAD_FOLDER_ALL_MODE;
			this.folderPath = folderPath;
			this.queryPicLimitSize = picLimitSize;
			this.queryVideoLimitSize = videoLimitSize;
			return this;
		}

		public Builder queryAllFolderBySizeAndDuration(String folderPath, long picLimitSize, long videoLimitDuration) {
			this.queryMode = MediaConstants.LOAD_FOLDER_ALL_MODE;
			this.folderPath = folderPath;
			this.queryPicLimitSize = picLimitSize;
			this.queryVideoLimitDuration = videoLimitDuration;
			return this;
		}

		public Builder queryAllFolderByPicSize(String folderPath, long picLimitSize) {
			this.queryMode = MediaConstants.LOAD_FOLDER_ALL_MODE;
			this.folderPath = folderPath;
			this.queryPicLimitSize = picLimitSize;
			return this;
		}

		public Builder queryAllFolderByVideoSize(String folderPath, long videoLimitSize) {
			this.queryMode = MediaConstants.LOAD_FOLDER_ALL_MODE;
			this.folderPath = folderPath;
			this.queryVideoLimitSize = videoLimitSize;
			return this;
		}

		public Builder queryAllFolderByVideoDuration(String folderPath, long videoLimitDuration) {
			this.queryMode = MediaConstants.LOAD_FOLDER_ALL_MODE;
			this.folderPath = folderPath;
			this.queryVideoLimitDuration = videoLimitDuration;
			return this;
		}

		public Builder ofCamera() {
			this.showMode = MediaConstants.CAMERA_MODE;
			return this;
		}

		public Builder ofCompressPath(String compressPath) {
			this.compressPath = compressPath;
			return this;
		}

		public Builder ofCompress() {
			isCompress = true;
			return this;
		}

		public Builder ofCompressParameter(int compressWidth, int compressHeight, int compressQuality, String compressPath) {
			this.compressWidth = compressWidth;
			this.compressHeight = compressHeight;
			this.compressQuality = compressQuality;
			this.compressPath = compressPath;
			return this;
		}

		public Builder ofCompressWidth(int compressWidth) {
			this.compressWidth = compressWidth;
			return this;
		}

		public Builder ofCompressHeight(int compressHeight) {
			this.compressHeight = compressHeight;
			return this;
		}

		public Builder ofCompressQuality(int compressQuality) {
			this.compressQuality = compressQuality;
			return this;
		}

		public Builder ofCompressPhotoSize(long compressPhotoSize) {
			this.compressPhotoSize = compressPhotoSize;
			return this;
		}

		public Builder ofCameraCrop() {
			isCameraCrop = true;
			return this;
		}

		public Builder ofCropWidth(int cropWidth) {
			this.cropWidth = cropWidth;
			return this;
		}

		public Builder ofCropHeight(int cropHeight) {
			this.cropHeight = cropHeight;
			return this;
		}

		public Builder ofFolder() {
			this.isFolderDisplay = true;
			return this;
		}

		public MediaConfig build() {
			return new MediaConfig(this);
		}
	}

}
