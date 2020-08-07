package com.wiser.picker.api.task.compress;

import com.wiser.picker.api.model.MediaData;

import java.util.List;

/**
 * @author Wiser
 * 
 *         压缩异步监听
 */
public abstract class OnCompressListener {

	public void compressLoading() {}

	public void compressSuccess(List<MediaData> list) {}
}