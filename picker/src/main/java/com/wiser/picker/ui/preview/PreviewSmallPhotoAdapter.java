package com.wiser.picker.ui.preview;

import com.bumptech.glide.Glide;
import com.wiser.picker.R;
import com.wiser.picker.api.model.MediaData;
import com.wiser.picker.ui.base.BasePhotoAdapter;
import com.wiser.picker.ui.base.BasePhotoHolder;
import com.wiser.picker.ui.weight.SquareFrameLayout;
import com.wiser.picker.ui.weight.SquaredImageView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

/**
 * @author Wiser
 * 
 *         预览小图片数据展示
 */
public class PreviewSmallPhotoAdapter extends BasePhotoAdapter<MediaData, BasePhotoHolder> {

	private int index = -1;

	PreviewSmallPhotoAdapter(Context context) {
		super(context);
	}

	void setIndex(int index) {
		this.index = index;
		notifyDataSetChanged();
	}

	@Override public BasePhotoHolder newViewHolder(ViewGroup viewGroup, int type) {
		return new PreviewSmallHolder(inflate(R.layout.media_photo_small_item, viewGroup));
	}

	class PreviewSmallHolder extends BasePhotoHolder<MediaData> {

		SquaredImageView	ivSmallPhoto;

		SquareFrameLayout	flSmallFrame;

		PreviewSmallHolder(@NonNull View itemView) {
			super(itemView);
			ivSmallPhoto = itemView.findViewById(R.id.iv_small_photo);
			flSmallFrame = itemView.findViewById(R.id.fl_small_frame);
		}

		@Override public void bindData(final MediaData mediaData, final int position) {
			if (mediaData == null) return;
			Glide.with(ivSmallPhoto.getContext()).load(mediaData.path).thumbnail(0.1f).centerCrop().into(ivSmallPhoto);

			if (index == mediaData.position) flSmallFrame.setBackgroundResource(R.drawable.shape_small_frame_st);
			else flSmallFrame.setBackgroundResource(0);

			ivSmallPhoto.setOnClickListener(new View.OnClickListener() {

				@Override public void onClick(View v) {
					((MediaPreviewActivity) getContext()).setPageItem(mediaData, position);
				}
			});
		}
	}
}
