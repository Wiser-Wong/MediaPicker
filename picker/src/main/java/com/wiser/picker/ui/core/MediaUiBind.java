package com.wiser.picker.ui.core;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.wiser.picker.R;
import com.wiser.picker.api.model.MediaData;
import com.wiser.picker.api.task.compress.CompressPhotoTask;
import com.wiser.picker.api.task.compress.OnCompressListener;
import com.wiser.picker.lib.MediaHelper;
import com.wiser.picker.ui.preview.MediaPreviewActivity;
import com.wiser.picker.ui.select.MediaSelectActivity;
import com.wiser.picker.ui.config.MediaConfig;
import com.wiser.picker.ui.utils.LoadingTool;
import com.wiser.picker.ui.utils.PickerHelper;
import com.wiser.picker.ui.utils.ThemeTool;

import java.text.MessageFormat;
import java.util.List;

/**
 * @author Wiser
 * 
 *         媒体扩展方法
 */
public class MediaUiBind implements IMediaUiBind {

	/**
	 * 自定义主题
	 * 
	 * @return
	 */
	@Override public int customTheme() {
		return R.style.MediaBaseTheme;
	}

	/**
	 * 完成状态UI改变
	 *
	 * @param tvComplete
	 *            完成按钮控件
	 * @param selectCount
	 *            选择的数量
	 * @param surplusCount
	 *            可选择的数量
	 * @param isEnable
	 *            是否可点击
	 */
	@Override public void completeStateUiChange(TextView tvComplete, int selectCount, int surplusCount, boolean isEnable) {
		tvComplete.setEnabled(isEnable);
		if (isEnable) {
			tvComplete.setBackgroundResource(ThemeTool.getThemeCompleteBtnSelectBg(tvComplete.getContext()));
			tvComplete.setText(MessageFormat.format("完成({0}/{1})", selectCount, surplusCount));
			tvComplete.setTextColor(ThemeTool.getThemeCompleteTextSelectColor(tvComplete.getContext()));
		} else {
			tvComplete.setBackgroundResource(ThemeTool.getThemeCompleteBtnUnSelectBg(tvComplete.getContext()));
			tvComplete.setText("完成");
			tvComplete.setTextColor(ThemeTool.getThemeCompleteTextUnSelectColor(tvComplete.getContext()));
		}
	}

	/**
	 * 加载图片
	 * 
	 * @param ivPic
	 *            图片控件
	 * @param path
	 *            图片路径
	 */
	@Override public void loadPic(AppCompatImageView ivPic, String path) {
		if (ivPic == null) return;
		// 加载图片
		Glide.with(ivPic.getContext()).load(path).thumbnail(0.1f).centerCrop().into(ivPic);
	}

	/**
	 * 图片预览点击
	 * 
	 * @param activity
	 * @param mediaDataList
	 *            媒体源数据集合
	 * @param selectMediaDataList
	 *            媒体选择数据集合
	 * @param config
	 *            配置参数
	 * @param surplusCount
	 *            剩余数量
	 * @param index
	 *            点击的位置
	 */
	@Override public void onPreviewClick(Activity activity, List<MediaData> mediaDataList, List<MediaData> selectMediaDataList, MediaConfig config, int previewType, int surplusCount, int index) {
		MediaPreviewActivity.intent(activity, mediaDataList, selectMediaDataList, config, index);
	}

	/**
	 * 最多选择媒体数量提示
	 * 
	 * @param context
	 *            参数
	 * @param surplusCount
	 *            剩余数量
	 */
	@Override public void maxSelectToastTip(Context context, int surplusCount) {
		if (context == null) return;
		Toast.makeText(context, "你最多只能选择" + surplusCount + "张图片", Toast.LENGTH_SHORT).show();
	}

	/**
	 * 自定义loading
	 * 
	 * @param activity
	 * @param isDismiss
	 *            是否消失
	 */
	@Override public void customLoading(FragmentActivity activity, boolean isDismiss) {
		if (isDismiss) LoadingTool.hideLoading(activity);
		else LoadingTool.showLoading(activity);
	}

	/**
	 * 压缩媒体数据
	 * 
	 * @param activity
	 * @param config
	 *            压缩参数
	 * @param mediaDataList
	 *            压缩数据
	 * @param onCompressListener
	 *            压缩监听
	 */
	@Override public void compressMediaData(final Activity activity, MediaConfig config, List<MediaData> mediaDataList, final OnCompressListener onCompressListener) {
		MediaData[] newMediaDataList = PickerHelper.covertData(mediaDataList);
		new CompressPhotoTask((FragmentActivity) activity, new OnCompressListener() {

			@Override public void compressLoading() {
				super.compressLoading();
				// 显示查询loading
				MediaHelper.mediaUiManage().bind().customLoading((FragmentActivity) activity, false);
			}

			@Override public void compressSuccess(List<MediaData> list) {
				super.compressSuccess(list);
				// 消失查询loading
				MediaHelper.mediaUiManage().bind().customLoading((FragmentActivity) activity, true);
				onCompressListener.compressSuccess(list);
			}
		}).execute(newMediaDataList);
	}
}
