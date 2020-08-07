package com.wiser.picker.ui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;

import com.wiser.picker.R;
import com.wiser.picker.api.config.MediaConstants;
import com.wiser.picker.lib.MediaHelper;
import com.wiser.picker.api.model.MediaData;
import com.wiser.picker.api.utils.DateHelper;
import com.wiser.picker.ui.base.BasePhotoAdapter;
import com.wiser.picker.ui.base.BasePhotoHolder;
import com.wiser.picker.ui.weight.SquareFrameLayout;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wiser
 * 
 *         图片选择适配器
 */
public class MediaSelectAdapter extends BasePhotoAdapter<MediaData, BasePhotoHolder> {

	private int				selectCount;	// 选择的数量

	private List<MediaData>	selectData;		// 选择的数据

	private int				surplusCount;	// 剩余可选数量

	private int				cancelSelectNum;// 取消选中号吗

	public List<MediaData> getSelectData() {
		return selectData;
	}

	public int getSelectCount() {
		return selectCount;
	}

	// 重置数据
	public void resetData() {
		this.selectCount = 0;
		if (selectData != null) selectData.clear();
	}

	public MediaSelectAdapter(Context context) {
		super(context);
		if (this.selectData == null) this.selectData = new ArrayList<>();
		selectCount = selectData.size();
		surplusCount = ((MediaSelectActivity) context).getBiz().config().surplusCount;
	}

	public void setData(List<MediaData> mediaData, List<MediaData> selectData, int count) {
		this.selectData = selectData;
		if (this.selectData == null) this.selectData = new ArrayList<>();
		this.selectCount = count;
		setItems(mediaData);
	}

	public void setSelectData(List<MediaData> selectData) {
		this.selectData = selectData;
	}

	@Override public int getItemViewType(int position) {
		if (getItem(position).showMode == MediaConstants.CAMERA_MODE) {
			return MediaConstants.CAMERA_MODE;
		}
		return super.getItemViewType(position);
	}

	@Override public BasePhotoHolder<MediaData> newViewHolder(ViewGroup viewGroup, int type) {
		if (type == MediaConstants.CAMERA_MODE) return new CameraHolder(inflate(R.layout.media_camera_layout, viewGroup));
		return new PhotoSelectHolder(inflate(R.layout.media_select_item, viewGroup));
	}

	class PhotoSelectHolder extends BasePhotoHolder<MediaData> {

		AppCompatImageView	ivSelectPhoto;			// 图片

		TextView			tvSelectCheck;			// check控件

		SquareFrameLayout	flCover;

		TextView			tvMediaVideoDuration;	// 视频时间

		PhotoSelectHolder(@NonNull View itemView) {
			super(itemView);
			ivSelectPhoto = itemView.findViewById(R.id.iv_media_select_photo);
			tvSelectCheck = itemView.findViewById(R.id.tv_media_select_check);
			flCover = itemView.findViewById(R.id.fl_media_cover);
			tvMediaVideoDuration = itemView.findViewById(R.id.tv_media_video_duration);
		}

		@Override public void bindData(final MediaData mediaData, final int position) {
			if (mediaData == null) return;

			final IMediaSelectBiz iMediaSelectBiz = ((MediaSelectActivity) getContext()).getBiz();
			if (iMediaSelectBiz != null) {
				// 预览图片
				ivSelectPhoto.setOnClickListener(new View.OnClickListener() {

					@Override public void onClick(View v) {
						// 预览图片
						MediaHelper.mediaManage().bind().onPreviewClick(v, getItems(), getSelectData(), ((MediaSelectActivity) getContext()).getBiz().config().surplusCount, position);
					}
				});
			}

			// 加载图片
			MediaHelper.mediaManage().bind().loadPic(ivSelectPhoto, mediaData.path);

			// 视频或者图片
			if (mediaData.mediaType == MediaConstants.MEDIA_VIDEO_TYPE) {
				tvMediaVideoDuration.setVisibility(View.VISIBLE);
				tvMediaVideoDuration.setText(DateHelper.getTimes(mediaData.videoDuration, "mm:ss"));
			} else {
				tvMediaVideoDuration.setVisibility(View.GONE);
			}

			// 选择
			if (mediaData.isSelect) {
				// 默认选中UI
				if (((MediaSelectActivity) getContext()).getBiz().config().checkUiType == MediaConstants.CHECK_UI_DEFAULT_TYPE) {
					tvSelectCheck.setBackgroundResource(R.drawable.media_selected);
				} else { // 选中号码展示UI
					tvSelectCheck.setBackgroundResource(R.drawable.media_selected_num);
					// 如果选择的号码大于取消选中时的号吗 都要减去1 然后更新数据
					if (cancelSelectNum > 0 && mediaData.selectNum > cancelSelectNum) {
						mediaData.selectNum = mediaData.selectNum - 1;
					}
					// 设置选择的号码
					tvSelectCheck.setText(MessageFormat.format("{0}", mediaData.selectNum));
				}
				flCover.setVisibility(View.VISIBLE);
			} else {
				// 默认选中UI类型
				if (((MediaSelectActivity) getContext()).getBiz().config().checkUiType == MediaConstants.CHECK_UI_DEFAULT_TYPE) {
					tvSelectCheck.setBackgroundResource(R.drawable.media_unselected);
				} else { // 选中号码展示的UI类型
					tvSelectCheck.setBackgroundResource(R.drawable.media_unselected_num);
					tvSelectCheck.setText("");
				}
				flCover.setVisibility(View.GONE);
			}

			if (surplusCount <= 0) return;
			tvSelectCheck.setOnClickListener(new View.OnClickListener() {

				@Override public void onClick(View v) {
					if (selectCount >= surplusCount && !mediaData.isSelect) {
						MediaHelper.mediaManage().bind().maxSelectToastTip(getContext(), surplusCount);
						return;
					}
					MediaData mediaData1 = getItem(getAdapterPosition());
					if (mediaData1.isSelect) {
						// 设置取消选中的号吗
						cancelSelectNum = mediaData1.selectNum;
						if (selectCount > 0) {
							selectCount--;
						}
						mediaData1.isSelect = false;
						// 清理取消选中的号吗
						mediaData1.selectNum = 0;
						remove(mediaData1);
					} else {
						// 重置取消选择的号吗
						cancelSelectNum = 0;
						selectCount++;
						mediaData1.isSelect = true;
						// 设置选中的号吗
						mediaData1.selectNum = selectCount;
						selectData.add(mediaData1);
					}
					getItems().set(position, mediaData1);
					// 默认选中UI类型
					if (((MediaSelectActivity) getContext()).getBiz().config().checkUiType == MediaConstants.CHECK_UI_DEFAULT_TYPE) {
						notifyItemChanged(position);
					} else { // 选中号码展示的UI类型
						notifyDataSetChanged();
					}
					((MediaSelectActivity) getContext()).updateBtnStateUi(selectCount);
				}
			});

		}
	}

	// 相册
	class CameraHolder extends BasePhotoHolder<MediaData> {

		TextView tvCamera;

		CameraHolder(@NonNull View itemView) {
			super(itemView);
			tvCamera = itemView.findViewById(R.id.tv_camera_photo);
		}

		@Override public void bindData(MediaData mediaData, int position) {
			itemView.setOnClickListener(new View.OnClickListener() {

				@Override public void onClick(View v) {
					((MediaSelectActivity) getContext()).requestCameraPermission();
				}
			});
		}
	}

	public void onDetach() {
		if (selectData != null) selectData.clear();
		selectData = null;
		if (getItems() != null) getItems().clear();
		selectCount = 0;
		surplusCount = 0;
		cancelSelectNum = 0;
	}

	// 移除选择
	private void remove(MediaData mediaData) {
		if (mediaData == null || selectData == null || selectData.size() == 0) return;
		for (MediaData model : selectData) {
			if (model == null || model.path == null) continue;
			if (model.path.equals(mediaData.path)) {
				selectData.remove(model);
				break;
			}
		}
	}

}
