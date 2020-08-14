package com.wiser.picker.ui.core;

import android.app.Activity;

import com.wiser.picker.ui.select.MediaSelectActivity;
import com.wiser.picker.ui.config.MediaConfig;

/**
 * @author Wiser
 * 
 *         媒体管理
 */
public class MediaUiManage implements IMediaUiManage {

	private IMediaUiBind iMediaUiBind;

	@Override public void init(IMediaUiBind iMediaUiBind) {
		this.iMediaUiBind = iMediaUiBind;
	}

	@Override public IMediaUiBind bind() {
		if (iMediaUiBind != null) return iMediaUiBind;
		return IMediaUiBind.defaultBind;
	}

	@Override public void unBind() {
		this.iMediaUiBind = null;
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
