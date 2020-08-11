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

	public long		compressVideoSize;			// 压缩视频大小 (kb)

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
		this.compressVideoSize = builder.compressVideoSize;
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
		compressVideoSize = in.readLong();
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
		dest.writeLong(compressVideoSize);
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

		private int		surplusCount	= MediaConstants.DEFAULT_SURPLUS_COUNT;		// 剩余可选数量

		private int		spanCount		= MediaConstants.DEFAULT_SPAN_COUNT;		// 展示列数

		private int		queryMode		= MediaConstants.LOAD_LOCAL_SYS_PHOTO_MODE;	// 查询模式 默认只查图片

		private int		showMode		= MediaConstants.MEDIA_MODE;				// 展示模式 默认媒体模式没有相机

		private String	folderPath;													// 查询的文件夹路径

		private String	compressPath	= MediaConstants.DEFAULT_COMPRESS_PATH;		// 默认压缩路径

		private boolean	isCompress;													// 是否压缩

		private int		compressWidth;												// 压缩宽度

		private int		compressHeight;												// 压缩高度

		private int		compressQuality;											// 压缩质量

		private long	compressPhotoSize;											// 压缩图片大小

		private long	compressVideoSize;											// 压缩视频大小

		private boolean	isCameraCrop;												// 是否拍照裁剪

		private int		cropWidth;													// 裁剪宽度

		private int		cropHeight;													// 裁剪高度

		private boolean	isFolderDisplay;											// 是否显示文件夹分类

		public long		queryPicLimitSize;											// 图片查询限制大小

		public long		queryVideoLimitSize;										// 视频查询限制大小

		public long		queryVideoLimitDuration;									// 视频查询限制时长

		public int		checkUiType		= MediaConstants.CHECK_UI_DEFAULT_TYPE;		// 选中媒体的Check号码样式

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
			compressVideoSize = config.compressVideoSize;
			isCameraCrop = config.isCameraCrop;
			cropWidth = config.cropWidth;
			cropHeight = config.cropHeight;
			isFolderDisplay = config.isFolderDisplay;
			queryPicLimitSize = config.queryPicLimitSize;
			queryVideoLimitSize = config.queryVideoLimitSize;
			queryVideoLimitDuration = config.queryVideoLimitDuration;
			checkUiType = config.checkUiType;
		}

		/**
		 * 配置剩余可选数量
		 *
		 * @param surplusCount
		 *            数量
		 * @return
		 */
		public Builder ofSurplusCount(int surplusCount) {
			this.surplusCount = surplusCount;
			return this;
		}

		/**
		 * 配置展示列数
		 *
		 * @param spanCount
		 *            列数
		 * @return
		 */
		public Builder ofSpanCount(int spanCount) {
			this.spanCount = spanCount;
			return this;
		}

		/**
		 * 配置选中check号码样式
		 *
		 * @return
		 */
		public Builder ofCheckUiNumType() {
			this.checkUiType = MediaConstants.CHECK_UI_NUM_TYPE;
			return this;
		}

		/**
		 * 配置拍照
		 *
		 * @return
		 */
		public Builder ofCamera() {
			this.showMode = MediaConstants.CAMERA_MODE;
			return this;
		}

		/**
		 * 配置压缩路径
		 *
		 * @param compressPath
		 *            路径
		 * @return
		 */
		public Builder ofCompressPath(String compressPath) {
			this.compressPath = compressPath;
			return this;
		}

		/**
		 * 配置压缩
		 *
		 * @return
		 */
		public Builder ofCompress() {
			isCompress = true;
			return this;
		}

		/**
		 * 配置压缩参数，将参数整合成统一管理
		 *
		 * @param compressWidth
		 *            宽
		 * @param compressHeight
		 *            高
		 * @param compressQuality
		 *            质量（0-100）
		 * @param compressPath
		 *            路径
		 * @return
		 */
		public Builder ofCompressParameter(int compressWidth, int compressHeight, int compressQuality, String compressPath) {
			this.compressWidth = compressWidth;
			this.compressHeight = compressHeight;
			this.compressQuality = compressQuality;
			this.compressPath = compressPath;
			return this;
		}

		/**
		 * 配置压缩宽度
		 *
		 * @param compressWidth
		 *            宽度
		 * @return
		 */
		public Builder ofCompressWidth(int compressWidth) {
			this.compressWidth = compressWidth;
			return this;
		}

		/**
		 * 配置压缩高度
		 *
		 * @param compressHeight
		 *            高度
		 * @return
		 */
		public Builder ofCompressHeight(int compressHeight) {
			this.compressHeight = compressHeight;
			return this;
		}

		/**
		 * 配置压缩质量
		 *
		 * @param compressQuality
		 *            质量 0 - 100
		 * @return
		 */
		public Builder ofCompressQuality(int compressQuality) {
			this.compressQuality = compressQuality;
			return this;
		}

		/**
		 * 配置压缩图片大小
		 *
		 * @param compressPhotoSize
		 *            大小 单位（kb）
		 * @return
		 */
		public Builder ofCompressPhotoSize(long compressPhotoSize) {
			this.compressPhotoSize = compressPhotoSize;
			return this;
		}

		/**
		 * 配置压缩视频大小
		 *
		 * @param compressVideoSize
		 *            大小 单位（kb）
		 * @return
		 */
		public Builder ofCompressVideoSize(long compressVideoSize) {
			this.compressVideoSize = compressVideoSize;
			return this;
		}

		/**
		 * 配置拍照裁剪
		 *
		 * @return
		 */
		public Builder ofCameraCrop() {
			isCameraCrop = true;
			return this;
		}

		/**
		 * 配置拍照裁剪宽度
		 *
		 * @param cropWidth
		 *            宽度
		 * @return
		 */
		public Builder ofCropWidth(int cropWidth) {
			this.cropWidth = cropWidth;
			return this;
		}

		/**
		 * 配置拍照裁剪高度
		 *
		 * @param cropHeight
		 *            高度
		 * @return
		 */
		public Builder ofCropHeight(int cropHeight) {
			this.cropHeight = cropHeight;
			return this;
		}

		/**
		 * 配置文件夹分类样式
		 *
		 * @return
		 */
		public Builder ofFolder() {
			this.isFolderDisplay = true;
			return this;
		}

		/****************************** 查询配置 以下是配置查询只能单独配置一个，配置多个会以最后一个配置来查询 **************************************/

		/**
		 * 配置查询模式
		 *
		 * @param queryMode
		 *            模式
		 * @param folderPath
		 *            文件夹路径
		 * @return
		 */
		public Builder queryMode(int queryMode, String... folderPath) {
			this.queryMode = queryMode;
			if (folderPath != null && folderPath.length > 0) {
				this.folderPath = folderPath[0];
			}
			return this;
		}

		/**
		 * 查询本地所有图片
		 *
		 * @return
		 */
		public Builder queryPhoto() {
			this.queryMode = MediaConstants.LOAD_LOCAL_SYS_PHOTO_MODE;
			return this;
		}

		/**
		 * 查询本地所有视频
		 *
		 * @return
		 */
		public Builder queryVideo() {
			this.queryMode = MediaConstants.LOAD_LOCAL_SYS_VIDEO_MODE;
			return this;
		}

		/**
		 * 查询本地所有图片和视频
		 *
		 * @return
		 */
		public Builder queryAll() {
			this.queryMode = MediaConstants.LOAD_LOCAL_SYS_ALL_MODE;
			return this;
		}

		/**
		 * 查询文件夹所有图片
		 *
		 * @param folderPath
		 *            文件夹路径
		 * @return
		 */
		public Builder queryPhotoForFolder(String folderPath) {
			this.queryMode = MediaConstants.LOAD_FOLDER_PHOTO_MODE;
			this.folderPath = folderPath;
			return this;
		}

		/**
		 * 查询文件夹所有视频
		 *
		 * @param folderPath
		 *            文件夹路径
		 * @return
		 */
		public Builder queryVideoForFolder(String folderPath) {
			this.queryMode = MediaConstants.LOAD_FOLDER_ALL_MODE;
			this.folderPath = folderPath;
			return this;
		}

		/**
		 * 查询文件夹所有图片和视频
		 *
		 * @param folderPath
		 *            文件夹路径
		 * @return
		 */
		public Builder queryAllForFolder(String folderPath) {
			this.queryMode = MediaConstants.LOAD_FOLDER_ALL_MODE;
			this.folderPath = folderPath;
			return this;
		}

		/**
		 * 查询本地系统所有图片，通过size大小 单位（kb）
		 *
		 * @param queryPicLimitSize
		 *            大小限制 kb
		 * @return
		 */
		public Builder queryPhotoBySize(long queryPicLimitSize) {
			this.queryMode = MediaConstants.LOAD_LOCAL_SYS_PHOTO_MODE;
			this.queryPicLimitSize = queryPicLimitSize;
			return this;
		}

		/**
		 * 查询文件夹所有图片，通过size大小 单位（kb）
		 *
		 * @param folderPath
		 *            文件夹路径
		 * @param limitSize
		 *            大小限制 kb
		 * @return
		 */
		public Builder queryPhotoForFolderBySize(String folderPath, long limitSize) {
			this.queryMode = MediaConstants.LOAD_FOLDER_PHOTO_MODE;
			this.folderPath = folderPath;
			this.queryPicLimitSize = limitSize;
			return this;
		}

		/**
		 * 查询本地系统所有视频，通过size大小 单位（kb）
		 *
		 * @param limitSize
		 *            大小 kb
		 * @return
		 */
		public Builder queryVideoBySize(long limitSize) {
			this.queryMode = MediaConstants.LOAD_LOCAL_SYS_VIDEO_MODE;
			this.queryVideoLimitSize = limitSize;
			return this;
		}

		/**
		 * 查询本地系统所有视频，通过duration时长 单位（s）
		 *
		 * @param videoLimitDuration
		 *            时长 s
		 * @return
		 */
		public Builder queryVideoByDuration(long videoLimitDuration) {
			this.queryMode = MediaConstants.LOAD_LOCAL_SYS_VIDEO_MODE;
			this.queryVideoLimitDuration = videoLimitDuration;
			return this;
		}

		/**
		 * 查询文件夹所有视频，通过size大小 单位（kb）
		 *
		 * @param folderPath
		 *            文件夹路径
		 * @param limitSize
		 *            大小 kb
		 * @return
		 */
		public Builder queryVideoForFolderBySize(String folderPath, long limitSize) {
			this.queryMode = MediaConstants.LOAD_FOLDER_ALL_MODE;
			this.folderPath = folderPath;
			this.queryVideoLimitSize = limitSize;
			return this;
		}

		/**
		 * 查询文件夹所有视频，通过duration时长 单位（s）
		 * 
		 * @param folderPath
		 *            文件夹路径
		 * @param videoLimitDuration
		 *            视频限制时长 （s）
		 * @return
		 */
		public Builder queryVideoForFolderByDuration(String folderPath, long videoLimitDuration) {
			this.queryMode = MediaConstants.LOAD_FOLDER_ALL_MODE;
			this.folderPath = folderPath;
			this.queryVideoLimitDuration = videoLimitDuration;
			return this;
		}

		/**
		 * 查询本地系统所有图片和视频，通过图片size大小和视频size大小 单位（kb）
		 * 
		 * @param picLimitSize
		 *            图片限制大小（kb）
		 * @param videoLimitSize
		 *            视频限制大小（kb）
		 * @return
		 */
		public Builder queryAllBySize(long picLimitSize, long videoLimitSize) {
			this.queryMode = MediaConstants.LOAD_LOCAL_SYS_ALL_MODE;
			this.queryPicLimitSize = picLimitSize;
			this.queryVideoLimitSize = videoLimitSize;
			return this;
		}

		/**
		 * 查询本地系统所有图片和视频，通过图片size大小和视频duration时长 size单位（kb） duration时长单位（s）
		 * 
		 * @param picLimitSize
		 *            图片限制大小（kb）
		 * @param videoLimitDuration
		 *            视频限制时长（s）
		 * @return
		 */
		public Builder queryAllBySizeAndDuration(long picLimitSize, long videoLimitDuration) {
			this.queryMode = MediaConstants.LOAD_LOCAL_SYS_ALL_MODE;
			this.queryPicLimitSize = picLimitSize;
			this.queryVideoLimitDuration = videoLimitDuration;
			return this;
		}

		/**
		 * 查询本地系统所有图片和视频，通过图片size大小，视频无限制 单位（kb）
		 * 
		 * @param picLimitSize
		 *            图片限制大小（kb）
		 * @return
		 */
		public Builder queryAllByPicSize(long picLimitSize) {
			this.queryMode = MediaConstants.LOAD_LOCAL_SYS_ALL_MODE;
			this.queryPicLimitSize = picLimitSize;
			return this;
		}

		/**
		 * 查询本地系统所有图片和视频，通过视频size大小，图片无限制 单位（kb）
		 * 
		 * @param videoLimitSize
		 *            视频限制大小（kb）
		 * @return
		 */
		public Builder queryAllByVideoSize(long videoLimitSize) {
			this.queryMode = MediaConstants.LOAD_LOCAL_SYS_ALL_MODE;
			this.queryVideoLimitSize = videoLimitSize;
			return this;
		}

		/**
		 * 查询本地系统所有图片和视频，通过视频duration时长，图片无限制 单位（s）
		 * 
		 * @param videoLimitDuration
		 *            视频限制时长（s）
		 * @return
		 */
		public Builder queryAllByVideoDuration(long videoLimitDuration) {
			this.queryMode = MediaConstants.LOAD_LOCAL_SYS_ALL_MODE;
			this.queryVideoLimitDuration = videoLimitDuration;
			return this;
		}

		/**
		 * 查询文件夹所有图片和视频，通过图片size大小和视频size大小 单位（kb）
		 * 
		 * @param folderPath
		 *            文件夹路径
		 * @param picLimitSize
		 *            图片限制大小 （kb）
		 * @param videoLimitSize
		 *            视频限制大小 （kb）
		 * @return
		 */
		public Builder queryAllForFolderBySize(String folderPath, long picLimitSize, long videoLimitSize) {
			this.queryMode = MediaConstants.LOAD_FOLDER_ALL_MODE;
			this.folderPath = folderPath;
			this.queryPicLimitSize = picLimitSize;
			this.queryVideoLimitSize = videoLimitSize;
			return this;
		}

		/**
		 * 查询文件夹所有图片和视频，通过图片size大小和视频duration时长 size单位（kb） duration时长单位（s）
		 * 
		 * @param folderPath
		 *            文件夹路径
		 * @param picLimitSize
		 *            图片限制大小（kb）
		 * @param videoLimitDuration
		 *            视频限制时长 （s）
		 * @return
		 */
		public Builder queryAllForFolderBySizeAndDuration(String folderPath, long picLimitSize, long videoLimitDuration) {
			this.queryMode = MediaConstants.LOAD_FOLDER_ALL_MODE;
			this.folderPath = folderPath;
			this.queryPicLimitSize = picLimitSize;
			this.queryVideoLimitDuration = videoLimitDuration;
			return this;
		}

		/**
		 * 查询文件夹所有图片和视频，通过图片size大小，视频无限制 单位（kb）
		 *
		 * @param folderPath
		 *            文件夹路径
		 * @param picLimitSize
		 *            图片限制大小 （kb）
		 * @return
		 */
		public Builder queryAllForFolderByPicSize(String folderPath, long picLimitSize) {
			this.queryMode = MediaConstants.LOAD_FOLDER_ALL_MODE;
			this.folderPath = folderPath;
			this.queryPicLimitSize = picLimitSize;
			return this;
		}

		/**
		 * 查询文件夹所有图片和视频，通过视频size大小，图片无限制 单位（kb）
		 *
		 * @param folderPath
		 *            文件夹路径
		 * @param videoLimitSize
		 *            视频限制大小 （kb）
		 * @return
		 */
		public Builder queryAllForFolderByVideoSize(String folderPath, long videoLimitSize) {
			this.queryMode = MediaConstants.LOAD_FOLDER_ALL_MODE;
			this.folderPath = folderPath;
			this.queryVideoLimitSize = videoLimitSize;
			return this;
		}

		/**
		 * 查询文件夹所有图片和视频，通过视频duration时长，图片无限制 单位（s）
		 * 
		 * @param folderPath
		 *            文件夹路径
		 * @param videoLimitDuration
		 *            视频限制时长 （s）
		 * @return
		 */
		public Builder queryAllForFolderByVideoDuration(String folderPath, long videoLimitDuration) {
			this.queryMode = MediaConstants.LOAD_FOLDER_ALL_MODE;
			this.folderPath = folderPath;
			this.queryVideoLimitDuration = videoLimitDuration;
			return this;
		}

		public MediaConfig build() {
			return new MediaConfig(this);
		}
	}

}
