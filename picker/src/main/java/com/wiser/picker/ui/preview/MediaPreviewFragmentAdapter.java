package com.wiser.picker.ui.preview;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.wiser.picker.api.config.MediaConstants;
import com.wiser.picker.api.model.MediaData;

import java.util.List;

/**
 * @author Wiser
 * 
 *         预览图片适配器
 */
public class MediaPreviewFragmentAdapter extends FragmentStatePagerAdapter {

	private List<MediaData> mediaDataList;

	MediaPreviewFragmentAdapter(FragmentManager fm, List<MediaData> mediaDataList) {
		super(fm);
		this.mediaDataList = mediaDataList;
	}

	@NonNull @Override public Fragment getItem(int i) {
		if (mediaDataList != null && mediaDataList.size() > i) {
			if (mediaDataList.get(i).mediaType == MediaConstants.MEDIA_PHOTO_TYPE) {
				return MediaPreviewPhotoFragment.newInstance(mediaDataList.get(i));
			} else {
				return MediaPreviewVideoFragment.newInstance(mediaDataList.get(i));
			}
		}
		return MediaPreviewPhotoFragment.newInstance(null);
	}

	@Override public int getCount() {
		return mediaDataList != null ? mediaDataList.size() : 0;
	}
}
