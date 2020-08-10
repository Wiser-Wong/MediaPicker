package com.wiser.picker.ui;

import android.content.Intent;
import android.os.Parcelable;

import com.wiser.picker.api.config.MediaConstants;
import com.wiser.picker.api.model.MediaData;
import com.wiser.picker.api.task.compress.OnCompressListener;
import com.wiser.picker.api.task.load.OnLoadMediaListener;
import com.wiser.picker.api.utils.FileHelper;
import com.wiser.picker.lib.MediaHelper;
import com.wiser.picker.ui.config.MediaConfig;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wiser
 *
 *         媒体选择业务类
 */
public class MediaSelectBiz implements IMediaSelectBiz {

	private WeakReference<MediaSelectActivity>	reference;

	// 媒体参数配置
	private MediaConfig							mediaConfig;

	// 拍照输出路径
	private String								outFilePath;

	// 原数据
	private List<MediaData>						sourceMediaDataList;

	public MediaSelectBiz(MediaSelectActivity ui) {
		reference = new WeakReference<>(ui);
		mediaConfig = (ui != null && ui.getIntent() != null && ui.getIntent().getExtras() != null) ? (MediaConfig) ui.getIntent().getExtras().getParcelable(MEDIA_SELECT_PAGE_DATA_KEY) : null;
	}

	private MediaSelectActivity ui() {
		if (reference != null && reference.get() != null) return reference.get();
		return null;
	}

	// 配置参数对象
	@Override public MediaConfig config() {
		if (mediaConfig != null) return mediaConfig;
		return new MediaConfig();
	}

	// 加载媒体数据
	@Override public void loadMediaData() {
		MediaHelper.mediaQueryManage().loadMediasTask(ui(), config().queryMode, config().queryPicLimitSize, config().queryVideoLimitSize, config().queryVideoLimitDuration, new OnLoadMediaListener() {

			@Override protected void onLoadMediaLoading() {
				super.onLoadMediaLoading();
				// 显示查询loading
				MediaHelper.mediaUiManage().bind().customLoading(ui(), false);

			}

			@Override protected void onLoadMediaComplete(List<MediaData> mediaDataList) {
				// 消失查询loading
				MediaHelper.mediaUiManage().bind().customLoading(ui(), true);
				// 赋值
				MediaSelectBiz.this.sourceMediaDataList = mediaDataList;
				setCameraData(mediaDataList);
			}
		});
	}

	// 获取原媒体数据
	@Override public List<MediaData> getSourceMediaDataList() {
		return sourceMediaDataList;
	}

	// 转换选择的数据
	@Override public MediaData[] covertSelectData(List<MediaData> mediaDataList) {
		if (mediaDataList == null) return null;
		MediaData[] selectData = new MediaData[mediaDataList.size()];
		for (int i = 0; i < mediaDataList.size(); i++) {
			if (mediaDataList.get(i) == null) continue;
			selectData[i] = mediaDataList.get(i);
		}
		return selectData;
	}

	// 执行压缩图片
	@Override public void handleCompressPhoto() {
		MediaSelectActivity ui = ui();
		if (ui != null) {
			if (config().isCompress) { // 启动压缩
				MediaHelper.mediaUiManage().bind().compressMediaData(ui, config(), ui.getMediaSelectAdapter() != null ? ui.getMediaSelectAdapter().getSelectData() : null, new OnCompressListener() {

					@Override public void compressSuccess(List<MediaData> list) {
						super.compressSuccess(list);
						complete(list);
					}
				});
			} else { // 完成
				complete(ui.getMediaSelectAdapter() != null ? ui.getMediaSelectAdapter().getSelectData() : null);
			}
		}
	}

	// 完成
	private void complete(List<MediaData> mediaDataList) {
		MediaSelectActivity ui = ui();
		if (ui != null) {
			Intent intent = new Intent();
			if (ui.getMediaSelectAdapter() != null) {
				intent.putParcelableArrayListExtra(MediaConstants.INTENT_SELECT_MEDIA_KEY, (ArrayList<? extends Parcelable>) mediaDataList);
			}
			ui.setResult(MediaConstants.INTENT_SELECT_MEDIA_REQUEST_CODE, intent);
			ui.finish();
		}
	}

	// 设置拍照数据之后填充适配器
	private void setCameraData(List<MediaData> mediaDataList) {
		if (config().showMode == MediaConstants.CAMERA_MODE) {
			MediaData cameraData = new MediaData();
			cameraData.showMode = MediaConstants.CAMERA_MODE;
			mediaDataList.add(0, cameraData);
		}
		MediaSelectActivity ui = ui();
		if (ui != null) ui.setMediaDataAdapter(mediaDataList);
	}

	// 回传拍照数据结果
	@Override public void setCameraResult() {
		MediaSelectActivity ui = ui();
		if (ui != null) {
			List<MediaData> mediaDataList = new ArrayList<>();
			File file = new File(outFilePath);
			int[] videoMeasure = FileHelper.getFileVideoMeasure(file);
			mediaDataList.add(new MediaData(outFilePath, file.getName(), file.lastModified(), FileHelper.getFileSize(file), 0, videoMeasure != null ? videoMeasure[0] : 0,
					videoMeasure != null ? videoMeasure[1] : 0, MediaConstants.MEDIA_PHOTO_TYPE));
			Intent intent = new Intent();
			intent.putParcelableArrayListExtra(MediaConstants.INTENT_SELECT_MEDIA_KEY, (ArrayList<? extends Parcelable>) mediaDataList);
			ui.setResult(MediaConstants.INTENT_SELECT_MEDIA_REQUEST_CODE, intent);
			ui.finish();
		}
	}

	// 获取拍照输入路径
	@Override public String getOutFilePath() {
		return outFilePath;
	}

	// 设置拍照输出路径
	@Override public void setOutFilePath(String outFilePath) {
		this.outFilePath = outFilePath;
	}

	// 销毁
	@Override public void onDetach() {
		if (reference != null) reference.clear();
		reference = null;
		mediaConfig = null;
	}
}
