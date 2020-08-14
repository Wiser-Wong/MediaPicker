package com.wiser.picker.api.task.compress;

import android.os.AsyncTask;
import android.text.TextUtils;

import androidx.fragment.app.FragmentActivity;

import com.wiser.picker.api.config.MediaConstants;
import com.wiser.picker.api.model.MediaData;
import com.wiser.picker.api.utils.FileHelper;
import com.wiser.picker.ui.preview.MediaPreviewActivity;
import com.wiser.picker.ui.select.MediaSelectActivity;
import com.wiser.picker.ui.config.MediaConfig;
import com.wiser.picker.ui.utils.BitmapTool;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wiser
 *
 *         压缩图片异步
 */
public class CompressPhotoTask extends AsyncTask<MediaData, Void, Boolean> {

	private WeakReference<FragmentActivity>	reference;

	private OnCompressListener				onCompressListener;

	private MediaConfig						config;

	private List<MediaData>					mediaDataList;

	public CompressPhotoTask(FragmentActivity activity, OnCompressListener onCompressListener) {
		if (activity == null) return;
		reference = new WeakReference<>(activity);
		if (activity instanceof MediaSelectActivity) {
			config = ((MediaSelectActivity) activity).getBiz() != null ? ((MediaSelectActivity) activity).getBiz().config() : null;
			this.onCompressListener = onCompressListener;
			mediaDataList = new ArrayList<>();
		}
		if (activity instanceof MediaPreviewActivity) {
			config = ((MediaPreviewActivity) activity).getBiz() != null ? ((MediaPreviewActivity) activity).getBiz().config() : null;
			this.onCompressListener = onCompressListener;
			mediaDataList = new ArrayList<>();
		}
	}

	@Override protected void onPreExecute() {
		super.onPreExecute();
		if (onCompressListener != null) onCompressListener.compressLoading();
	}

	@Override protected Boolean doInBackground(MediaData... mediaDataList) {
		if (config != null) {
			for (MediaData mediaData : mediaDataList) {
				if (TextUtils.isEmpty(config.compressPath)) continue;
				// 根据size压缩 size单位是kb 加入10kb换算成b就是10*1000b
				if (config.compressPhotoSize > 0) {
					mediaData.path = BitmapTool.compressForSize(config.compressPath, new File(mediaData.path).getName(), mediaData.path, config.compressPhotoSize);
				} else { // 根据质量和宽高压缩
					mediaData.path = BitmapTool.compress(config.compressPath, new File(mediaData.path).getName(), mediaData.path,
							config.compressQuality == 0 ? MediaConstants.DEFAULT_COMPRESS_QUALITY : config.compressQuality,
							config.compressWidth == 0 ? MediaConstants.DEFAULT_COMPRESS_WIDTH : config.compressWidth,
							config.compressHeight == 0 ? MediaConstants.DEFAULT_COMPRESS_HEIGHT : config.compressHeight);
				}
				File file = new File(mediaData.path);
				int[] videoMeasure = FileHelper.getFileVideoMeasure(file);
				mediaData.size = FileHelper.getFileSize(file);
				mediaData.width = videoMeasure != null ? videoMeasure[0] : 0;
				mediaData.height = videoMeasure != null ? videoMeasure[1] : 0;
				this.mediaDataList.add(mediaData);
			}
		}
		return true;
	}

	@Override protected void onPostExecute(Boolean aBoolean) {
		super.onPostExecute(aBoolean);
		if (aBoolean && onCompressListener != null) {
			onCompressListener.compressSuccess(mediaDataList);
		}
		onDetach();
	}

	private void onDetach() {
		if (reference != null) reference.clear();
		reference = null;
		if (mediaDataList != null) mediaDataList.clear();
		mediaDataList = null;
		config = null;
		onCompressListener = null;
	}
}
