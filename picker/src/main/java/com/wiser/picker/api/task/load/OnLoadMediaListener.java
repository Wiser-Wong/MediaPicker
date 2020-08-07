package com.wiser.picker.api.task.load;

import com.wiser.picker.api.model.MediaData;

import java.util.List;

/**
 * @author Wiser
 * 
 *         加载媒体异步监听
 */
public abstract class OnLoadMediaListener {

	protected void onLoadMediaLoading() {}

	protected void onLoadMediaComplete(List<MediaData> mediaDataList) {}
}