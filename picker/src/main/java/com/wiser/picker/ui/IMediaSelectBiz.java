package com.wiser.picker.ui;

import com.wiser.picker.api.model.MediaData;
import com.wiser.picker.ui.config.MediaConfig;

import java.util.List;

/**
 * @author Wiser
 *
 *         媒体选择业务类
 */
public interface IMediaSelectBiz {

	// 媒体选择页面传递的数据key
	String MEDIA_SELECT_PAGE_DATA_KEY = "media_select_page_data_key";

	// 配置
	MediaConfig config();

	// 加载媒体文件
	void loadMediaData();

	// 获取原媒体数据
	List<MediaData> getSourceMediaDataList();

	// 转换选择的媒体数据
	MediaData[] covertSelectData(List<MediaData> mediaDataList);

	// 执行压缩图片
	void handleCompressPhoto();

	// 拍照回传结果数据
	void setCameraResult();

	// 设置拍照本地文件路径
	void setOutFilePath(String outFilePath);

	// 获取拍照本地文件路径
	String getOutFilePath();

	void onDetach();
}
