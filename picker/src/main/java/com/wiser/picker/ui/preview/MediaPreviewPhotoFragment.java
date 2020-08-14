package com.wiser.picker.ui.preview;

import com.bumptech.glide.Glide;
import com.wiser.picker.R;
import com.wiser.picker.api.config.MediaConstants;
import com.wiser.picker.api.model.MediaData;
import com.wiser.picker.ui.photoview.PhotoView;
import com.wiser.picker.ui.photoview.PhotoViewAttache;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

/**
 * @author Wiser
 *
 *         媒体图片单张预览Fragment
 */
public class MediaPreviewPhotoFragment extends Fragment {

	private FragmentActivity activity;

	@Override public void onAttach(Context context) {
		super.onAttach(context);
		activity = (FragmentActivity) context;
	}

	public static MediaPreviewPhotoFragment newInstance(MediaData mediaData) {
		MediaPreviewPhotoFragment fragment = new MediaPreviewPhotoFragment();
		Bundle bundle = new Bundle();
		bundle.putParcelable(MediaConstants.PREVIEW_SINGLE_PHOTO_KEY, mediaData);
		fragment.setArguments(bundle);
		return fragment;
	}

	@Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.media_photo_preview_frg, container, false);
		if (getArguments() != null && getActivity() != null) {
			MediaData mediaData = getArguments().getParcelable(MediaConstants.PREVIEW_SINGLE_PHOTO_KEY);
			if (mediaData != null) {
				Glide.with(getActivity()).load(mediaData.path != null ? mediaData.path : "").thumbnail(0.1f).into((PhotoView) view.findViewById(R.id.iv_preview_photo));
			}
		}

		((PhotoView) view).setOnViewTapListener(new PhotoViewAttache.OnViewTapListener() {

			@Override public void onViewTap(View view, float x, float y) {
				if (activity != null) {
					if (activity instanceof MediaPreviewActivity) {
						((MediaPreviewActivity) activity).updateTitleAnim();
					}
				}
			}
		});
		return view;
	}
}
