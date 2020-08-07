package com.wiser.picker.lib;

import com.wiser.picker.api.core.query.IMediaQueryManage;
import com.wiser.picker.api.core.query.MediaQueryManage;
import com.wiser.picker.ui.core.MediaManage;
import com.wiser.picker.ui.core.IMediaBind;

/**
 * @author Wiser
 * 
 *         媒体帮助类
 */
public final class MediaHelper {

	// 媒体查询管理类
	private static IMediaQueryManage	mediaQueryManage	= null;

	// 媒体管理类
	private static MediaManage			mediaManage			= null;

	/**
	 * 媒体查询管理
	 * 
	 * @return
	 */
	public static IMediaQueryManage mediaQueryManage() {
		if (mediaQueryManage == null) synchronized (MediaQueryManage.class) {
			if (mediaQueryManage == null) mediaQueryManage = new MediaQueryManage();
		}
		return mediaQueryManage;
	}

	// 扩展功能
	public static Bind newBind() {
		return new Bind();
	}

	public static class Bind {

		IMediaBind iMediaBind;

		public Bind setBind(IMediaBind iMediaBind) {
			this.iMediaBind = iMediaBind;
			return this;
		}

		// 注入
		public void inject() {
			if (this.iMediaBind == null) this.iMediaBind = IMediaBind.defaultBind;

			mediaManage = new MediaManage();
			mediaManage.init(iMediaBind);
		}
	}

	/**
	 * 选择媒体管理
	 * 
	 * @return
	 */
	public static MediaManage mediaManage() {
		if (mediaManage != null) return mediaManage;
		return new MediaManage();
	}

}
