package com.wiser.picker.lib;

import com.wiser.picker.api.core.query.IMediaQueryManage;
import com.wiser.picker.api.core.query.MediaQueryManage;
import com.wiser.picker.ui.core.IMediaUiManage;
import com.wiser.picker.ui.core.MediaUiManage;
import com.wiser.picker.ui.core.IMediaUiBind;

/**
 * @author Wiser
 * 
 *         媒体帮助类
 */
public final class MediaHelper {

	// 媒体管理类
	private static IMediaUiManage mediaUiManage = null;

	/**
	 * 媒体查询管理
	 * 
	 * @return
	 */
	public static IMediaQueryManage mediaQueryManage() {
		return new MediaQueryManage();
	}

	// 扩展功能
	public static Bind newBind() {
		return new Bind();
	}

	public static class Bind {

		IMediaUiBind iMediaUiBind;

		public Bind setBind(IMediaUiBind iMediaUiBind) {
			this.iMediaUiBind = iMediaUiBind;
			return this;
		}

		// 注入
		public void inject() {
			if (this.iMediaUiBind == null) this.iMediaUiBind = IMediaUiBind.defaultBind;

			mediaUiManage = new MediaUiManage();
			mediaUiManage.init(iMediaUiBind);
		}
	}

	/**
	 * 媒体Ui管理
	 * 
	 * @return
	 */
	public static IMediaUiManage mediaUiManage() {
		if (mediaUiManage != null) return mediaUiManage;
		return new MediaUiManage();
	}

}
