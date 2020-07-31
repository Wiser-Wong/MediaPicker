package com.wiser.picker.api.core;

import com.wiser.picker.api.core.query.IMediaQueryManage;
import com.wiser.picker.api.core.query.MediaQueryManage;

/**
 * @author Wiser
 * 
 *         媒体帮助类
 */
public final class MediaHelper {

	private static IMediaQueryManage mediaManage = null;

	public static IMediaQueryManage mediaManage() {
		if (mediaManage == null) synchronized (MediaQueryManage.class) {
			if (mediaManage == null) mediaManage = new MediaQueryManage();
		}
		return mediaManage;
	}

}
