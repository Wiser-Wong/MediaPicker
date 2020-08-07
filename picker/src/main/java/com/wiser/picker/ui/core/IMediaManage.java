package com.wiser.picker.ui.core;

import android.app.Activity;

import com.wiser.picker.ui.config.MediaConfig;

/**
 * @author Wiser
 * 
 *         媒体管理
 */
public interface IMediaManage {

	/**
	 * 初始化
	 */
	void init(IMediaBind iMediaBind);

	/**
	 * 扩展
	 * 
	 * @return
	 */
	IMediaBind bind();

	/**
	 * 跳转选择媒体界面
	 * 
	 * @param activity
	 *            参数
	 * @param config
	 *            配置参数
	 */
	void selectPageSkip(Activity activity, MediaConfig config);

}
