package com.wiser.mediapicker;

import android.app.Activity;
import android.view.View;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.FragmentActivity;

import com.wiser.picker.api.model.MediaData;
import com.wiser.picker.api.task.compress.OnCompressListener;
import com.wiser.picker.ui.config.MediaConfig;
import com.wiser.picker.ui.core.MediaUiBind;

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

	@Override public void loadPic(AppCompatImageView ivPic, String path) {
		super.loadPic(ivPic, path);
	}

	@Override public void customLoading(FragmentActivity activity, boolean isDismiss) {
		super.customLoading(activity, isDismiss);
	}

	@Override public void onPreviewClick(View view, List<MediaData> mediaDataList, List<MediaData> selectMediaDataList, int surplusCount, int index) {
		super.onPreviewClick(view, mediaDataList, selectMediaDataList, surplusCount, index);
	}

	@Override public void compressMediaData(Activity activity, MediaConfig config, List<MediaData> mediaDataList, OnCompressListener onCompressListener) {
		super.compressMediaData(activity, config, mediaDataList, onCompressListener);
	}
}
