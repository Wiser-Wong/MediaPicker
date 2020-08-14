package com.wiser.picker.ui.select;

import com.wiser.picker.api.model.MediaData;
import com.wiser.picker.ui.config.MediaConfig;

import java.util.List;

/**
 * @author Wiser
 *
 *         媒体选择业务类
 */
public interface IMediaSelectBiz {

	/**
	 * 媒体选择页面传递的数据key
	 */
	String MEDIA_SELECT_PAGE_DATA_KEY = "media_select_page_data_key";

	/**
	 * 配置
	 * 
	 * @return
	 */
	MediaConfig config();

	/**
	 * 是否相机
	 * 
	 * @return
	 */
	boolean isCamera();

	/**
	 * 加载媒体文件
	 */
	void loadMediaData();

	/**
	 * 获取原媒体数据
	 * 
	 * @return
	 */
	List<MediaData> getSourceMediaDataList();

	/**
	 * 执行压缩图片
	 */
	void handleCompressPhoto();

	/**
	 * 完成
	 * 
	 * @param mediaDataList
	 *            回传数据
	 */
	void complete(List<MediaData> mediaDataList);

	/**
	 * 拍照回传结果数据
	 */
	void setCameraResult();

	/**
	 * 设置拍照本地文件路径
	 * 
	 * @param outFilePath
	 *            拍照输出路径
	 */
	void setOutFilePath(String outFilePath);

	/**
	 * 获取拍照本地文件路径
	 * 
	 * @return
	 */
	String getOutFilePath();

	/**
	 * 销毁
	 */
	void onDetach();
}
