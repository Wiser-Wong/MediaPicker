package com.wiser.picker.ui.preview;

import com.wiser.picker.api.model.MediaData;
import com.wiser.picker.ui.config.MediaConfig;

import java.util.List;

/**
 * @author Wiser
 * 
 *         媒体预览接口方法
 */
public interface IMediaPreviewBiz {

	/**
	 * 获取媒体数据列表
	 * 
	 * @return
	 */
	List<MediaData> getMediaDataList();

	/**
	 * 获取选择了媒体数据列表
	 * 
	 * @return
	 */
	List<MediaData> getSelectDataList();

	/**
	 * 媒体配置参数
	 * 
	 * @return
	 */
	MediaConfig config();

	/**
	 * 初始化选择的媒体check UI
	 */
	void initSelectMedia();

	/**
	 * 选择媒体check点击
	 * 
	 * @param position
	 *            点击的位置
	 */
	void selectMediaClick(int position);

	/**
	 * 获取进入预览时的点击位置
	 * 
	 * @return
	 */
	int getIndex();

	/**
	 * 设置当前预览的位置
	 * 
	 * @param index
	 *            位置
	 */
	void setIndex(int index);

	/**
	 * 选择的数量
	 * 
	 * @return
	 */
	int getSelectCount();

	/**
	 * 取消选中号码的数量
	 * 
	 * @return
	 */
	int cancelSelectNum();

	/**
	 * 是否title隐藏
	 * 
	 * @return
	 */
	boolean isTitleHide();

	/**
	 * 设置title隐藏状态
	 * 
	 * @param isTitleHide
	 *            是否title隐藏
	 */
	void setTitleHide(boolean isTitleHide);

	/**
	 * 计算小图的位置
	 * 
	 * @param position
	 *            坐标位置
	 * @return
	 */
	int calculateSmallPhotoPosition(int position);

	/**
	 * 是否拍照模式
	 * 
	 * @return
	 */
	boolean isCamera();

	/**
	 * 处理压缩
	 */
	void handleCompress();

	/**
	 * 处理返回
	 */
	void handleBack();

	/**
	 * 销毁
	 */
	void detach();

}
