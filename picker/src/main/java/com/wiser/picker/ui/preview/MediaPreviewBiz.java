package com.wiser.picker.ui.preview;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;

import com.wiser.picker.api.config.MediaConstants;
import com.wiser.picker.api.model.MediaData;
import com.wiser.picker.api.task.compress.OnCompressListener;
import com.wiser.picker.lib.MediaHelper;
import com.wiser.picker.ui.config.MediaConfig;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wiser
 * 
 *         媒体预览业务类
 */
public class MediaPreviewBiz implements IMediaPreviewBiz {

	private WeakReference<MediaPreviewActivity>	reference;

	// 点击预览的当前位置
	private int									index;

	// 媒体数据总集合
	private List<MediaData>						mediaDataList;

	// 媒体数据选择的集合
	private List<MediaData>						selectDataList;

	// 配置参数
	private MediaConfig							mediaConfig;

	// 选择数量
	private int									selectCount;

	// 剩余可选数量
	private int									surplusCount;

	// 是否title隐藏
	private boolean								isTitleHide;

	// 取消选中号吗
	private int									cancelSelectNum;

	public MediaPreviewBiz(MediaPreviewActivity ui) {
		reference = new WeakReference<>(ui);
		if (ui.getIntent() != null && ui.getIntent().getExtras() != null) {
			mediaDataList = ui.getIntent().getExtras().getParcelableArrayList(MediaConstants.PREVIEW_MEDIA_ALL_DATA_KEY);
			selectDataList = ui.getIntent().getExtras().getParcelableArrayList(MediaConstants.PREVIEW_MEDIA_SELECT_DATA_KEY);
			mediaConfig = ui.getIntent().getExtras().getParcelable(MediaConstants.PREVIEW_MEDIA_CONFIG_KEY);
			index = ui.getIntent().getExtras().getInt(MediaConstants.PREVIEW_MEDIA_INDEX_KEY);
			selectCount = selectDataList != null ? selectDataList.size() : 0;
			surplusCount = mediaConfig != null ? mediaConfig.surplusCount : MediaConstants.DEFAULT_SURPLUS_COUNT;

			// 处理携带拍照数据过来的集合
			if (mediaConfig != null && mediaConfig.showMode == MediaConstants.CAMERA_MODE && mediaDataList != null && mediaDataList.size() > 0) {
				mediaDataList.remove(0);
				if (index > 0) index--;
			}
		}
	}

	private MediaPreviewActivity ui() {
		if (reference != null && reference.get() != null) return reference.get();
		return null;
	}

	@Override public List<MediaData> getMediaDataList() {
		if (mediaDataList == null) mediaDataList = new ArrayList<>();
		return mediaDataList;
	}

	@Override public List<MediaData> getSelectDataList() {
		if (selectDataList == null) selectDataList = new ArrayList<>();
		return selectDataList;
	}

	@Override public MediaConfig config() {
		if (mediaConfig == null) mediaConfig = new MediaConfig();
		return mediaConfig;
	}

	@Override public void initSelectMedia() {
		if (mediaDataList == null || mediaDataList.size() == 0) return;
		MediaData mediaData = mediaDataList.get(index);
		MediaPreviewActivity ui = ui();
		if (ui != null && mediaData != null) ui.updateCheckUi(mediaData, true);
	}

	@Override public void selectMediaClick(int position) {
		MediaPreviewActivity ui = ui();
		if (ui == null || mediaDataList == null || mediaDataList.size() == 0) return;
		MediaData mediaData = mediaDataList.get(position);
		if (selectCount >= surplusCount && !mediaData.isSelect) {
			Toast.makeText(ui, "你最多只能选择" + surplusCount + "张图片", Toast.LENGTH_SHORT).show();
			return;
		}
		MediaData mediaData1 = mediaDataList.get(position);
		if (mediaData1.isSelect) {
			// 设置取消选中的号吗
			cancelSelectNum = mediaData1.selectNum;
			if (selectCount > 0) {
				selectCount--;
			}
			mediaData1.isSelect = false;
			// 清理取消选中的号吗
			mediaData1.selectNum = 0;
			remove(mediaData1);
		} else {
			// 重置取消选择的号吗
			cancelSelectNum = 0;
			selectCount++;
			mediaData1.isSelect = true;
			// 设置选中的号吗
			mediaData1.selectNum = selectCount;
			selectDataList.add(mediaData1);
		}
		mediaDataList.set(position, mediaData1);
		// 重新计算号码数
		calculateNum();
		// 更新check选择UI
		ui.updateCheckUi(mediaData1, mediaData1.isSelect);
	}

	// 计算号码数字
	private void calculateNum() {
		if (selectDataList != null && selectDataList.size() > 0) for (int i = 0; i < selectDataList.size(); i++) {
			int position = isCamera() ? selectDataList.get(i).position - 1 : selectDataList.get(i).position;
			if (mediaDataList.size() <= position) continue;
			MediaData mediaData = mediaDataList.get(position);
			if (mediaData != null && cancelSelectNum > 0 && mediaData.selectNum > cancelSelectNum) {
				mediaData.selectNum = mediaData.selectNum - 1;
				mediaDataList.set(position, mediaData);
			}
		}
	}

	@Override public int getIndex() {
		return index;
	}

	@Override public void setIndex(int index) {
		this.index = index;
	}

	@Override public int getSelectCount() {
		return selectDataList != null ? selectDataList.size() : 0;
	}

	@Override public int cancelSelectNum() {
		return cancelSelectNum;
	}

	@Override public boolean isTitleHide() {
		return isTitleHide;
	}

	@Override public void setTitleHide(boolean isTitleHide) {
		this.isTitleHide = isTitleHide;
	}

	@Override public boolean isCamera() {
		return mediaConfig != null && mediaConfig.showMode == MediaConstants.CAMERA_MODE;
	}

	@Override public int calculateSmallPhotoPosition(int position) {
		if (selectDataList == null || selectDataList.size() == 0) return -1;
		for (int i = 0; i < selectDataList.size(); i++) {
			if (position == selectDataList.get(i).position) return i;
		}
		return -1;
	}

	// 压缩
	@Override public void handleCompress() {
		MediaPreviewActivity ui = ui();
		if (ui != null) {
			if (config().isCompress) { // 启动压缩
				MediaHelper.mediaUiManage().bind().compressMediaData(ui, config(), getSelectDataList(), new OnCompressListener() {

					@Override public void compressSuccess(List<MediaData> list) {
						super.compressSuccess(list);
						complete(list);
					}
				});
			} else { // 完成
				complete(getSelectDataList());
			}
		}
	}

	// 处理返回
	@Override public void handleBack() {
		MediaPreviewActivity ui = ui();
		if (ui != null) {
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putParcelableArrayList(MediaConstants.PREVIEW_MEDIA_ALL_DATA_KEY, (ArrayList<? extends Parcelable>) getMediaDataList());
			bundle.putParcelableArrayList(MediaConstants.PREVIEW_MEDIA_SELECT_DATA_KEY, (ArrayList<? extends Parcelable>) getSelectDataList());
			intent.putExtras(bundle);
			ui.setResult(MediaConstants.INTENT_PREVIEW_MEDIA_REQUEST_CODE, intent);
			ui.finish();
		}
	}

	// 完成
	private void complete(List<MediaData> mediaDataList) {
		MediaPreviewActivity ui = ui();
		if (ui != null) {
			Intent intent = new Intent();
			Bundle bundle = new Bundle();
			bundle.putParcelableArrayList(MediaConstants.PREVIEW_MEDIA_SELECT_DATA_KEY, (ArrayList<? extends Parcelable>) mediaDataList);
			bundle.putInt(MediaConstants.PREVIEW_MEDIA_COMPLETE_KEY, MediaConstants.PREVIEW_MEDIA_COMPLETE_VALUE);
			intent.putExtras(bundle);
			ui.setResult(MediaConstants.INTENT_PREVIEW_MEDIA_REQUEST_CODE, intent);
			ui.finish();
		}
	}

	// 移除选择
	private void remove(MediaData mediaData) {
		if (mediaData == null || selectDataList == null || selectDataList.size() == 0) return;
		for (MediaData model : selectDataList) {
			if (model == null || model.path == null) continue;
			if (model.path.equals(mediaData.path)) {
				selectDataList.remove(model);
				break;
			}
		}
	}

	@Override public void detach() {
		if (mediaDataList != null) mediaDataList.clear();
		mediaDataList = null;
		if (reference != null) reference.clear();
		reference = null;
	}
}
