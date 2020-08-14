package com.wiser.mediapicker;

import android.app.Activity;
import android.graphics.Color;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.FragmentActivity;

import com.wiser.picker.api.model.MediaData;
import com.wiser.picker.api.task.compress.OnCompressListener;
import com.wiser.picker.ui.config.MediaConfig;
import com.wiser.picker.ui.core.MediaUiBind;

import java.text.MessageFormat;
import java.util.List;

/**
 * @author Wiser
 * 
 *         自定义UI绑定
 */
public class CustomUiBind extends MediaUiBind {

	@Override public int customTheme() {
		return R.style.AppTheme;
	}

	@Override public void completeStateUiChange(TextView tvComplete, int selectCount, int surplusCount, boolean isEnable) {
		tvComplete.setEnabled(isEnable);
		if (isEnable) {
			tvComplete.setText(MessageFormat.format("完成({0})", selectCount));
			tvComplete.setTextColor(Color.parseColor("#00ff00"));
		} else {
			tvComplete.setText("完成");
			tvComplete.setTextColor(Color.parseColor("#7D00ff00"));
		}
	}

	@Override public void loadPic(AppCompatImageView ivPic, String path) {
		super.loadPic(ivPic, path);
	}

	@Override public void customLoading(FragmentActivity activity, boolean isDismiss) {
		super.customLoading(activity, isDismiss);
	}

	@Override public void onPreviewClick(Activity activity, List<MediaData> mediaDataList, List<MediaData> selectMediaDataList, MediaConfig config, int previewType, int surplusCount, int index) {
		super.onPreviewClick(activity, mediaDataList, selectMediaDataList, config, previewType, surplusCount, index);
	}

	@Override public void compressMediaData(Activity activity, MediaConfig config, List<MediaData> mediaDataList, OnCompressListener onCompressListener) {
		super.compressMediaData(activity, config, mediaDataList, onCompressListener);
	}
}
