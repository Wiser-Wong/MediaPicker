package com.wiser.picker.ui.core;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.fragment.app.FragmentActivity;

import com.wiser.picker.api.model.MediaData;
import com.wiser.picker.api.task.compress.OnCompressListener;
import com.wiser.picker.ui.config.MediaConfig;

import java.util.List;

/**
 * @author Wiser
 * 
 *         媒体扩展接口
 */
public interface IMediaUiBind {

	/**
	 * 主题
	 * 
	 * @return
	 */
	int customTheme();

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
	void completeStateUiChange(TextView tvComplete, int selectCount, int surplusCount, boolean isEnable);

	/**
	 * 加载图片
	 *
	 * @param ivPic
	 *            图片控件
	 * @param path
	 *            图片路径
	 */
	void loadPic(AppCompatImageView ivPic, String path);

	/**
	 * 图片预览点击
	 *
	 * @param activity
	 * @param mediaDataList
	 *            媒体源数据集合
	 * @param selectMediaDataList
	 *            媒体选择数据集合
	 * @param surplusCount
	 *            剩余数量
	 * @param config
	 *            配置参数
	 * @param previewType
	 *            预览类型
	 * @param index
	 *            点击的位置
	 */
	void onPreviewClick(Activity activity, List<MediaData> mediaDataList, List<MediaData> selectMediaDataList, MediaConfig config, int previewType, int surplusCount, int index);

	/**
	 * 最大选择Toast提示
	 *
	 * @param context
	 *            参数
	 * @param surplusCount
	 *            剩余数量
	 */
	void maxSelectToastTip(Context context, int surplusCount);

	/**
	 * 自定义Loading
	 * 
	 * @param isDismiss
	 *            是否消失
	 */
	void customLoading(FragmentActivity activity, boolean isDismiss);

	/**
	 * 压缩选择的媒体数据
	 * 
	 * @param activity
	 * @param config
	 *            压缩参数
	 * @param mediaDataList
	 *            压缩数据
	 * @param onCompressListener
	 *            压缩监听
	 */
	void compressMediaData(Activity activity, MediaConfig config, List<MediaData> mediaDataList, OnCompressListener onCompressListener);

	/**
	 * 默认扩展
	 */
	IMediaUiBind defaultBind = new MediaUiBind();
}
