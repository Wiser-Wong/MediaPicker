package com.wiser.picker.ui.preview;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.wiser.picker.api.config.MediaConstants;
import com.wiser.picker.api.model.MediaData;

/**
 * @author Wiser
 * 
 *         媒体视频单一预览Fragment
 */
public class MediaPreviewVideoFragment extends Fragment {

	public static MediaPreviewPhotoFragment newInstance(MediaData mediaData) {
		MediaPreviewPhotoFragment fragment = new MediaPreviewPhotoFragment();
		Bundle bundle = new Bundle();
		bundle.putParcelable(MediaConstants.PREVIEW_SINGLE_VIDEO_KEY, mediaData);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Override public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
}
