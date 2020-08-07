package com.wiser.picker.ui.core;

import android.app.Activity;

import com.wiser.picker.ui.MediaSelectActivity;
import com.wiser.picker.ui.config.MediaConfig;

/**
 * @author Wiser
 * 
 *         媒体管理
 */
public class MediaManage implements IMediaManage {

	private IMediaBind iMediaBind;

	@Override public void init(IMediaBind iMediaBind) {
		this.iMediaBind = iMediaBind;
	}

	@Override public IMediaBind bind() {
		if (iMediaBind != null) return iMediaBind;
		return IMediaBind.defaultBind;
	}

	/**
	 * 跳转到媒体选择页面
	 *
	 * @param activity
	 */
	@Override public void selectPageSkip(Activity activity, MediaConfig config) {
		MediaSelectActivity.intent(activity, config);
	}

}
