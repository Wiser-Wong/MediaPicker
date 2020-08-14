package com.wiser.picker.ui.preview;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.wiser.picker.R;
import com.wiser.picker.api.config.MediaConstants;
import com.wiser.picker.api.model.MediaData;
import com.wiser.picker.ui.config.MediaConfig;
import com.wiser.picker.ui.utils.CenterLayoutManager;
import com.wiser.picker.ui.utils.PickerHelper;
import com.wiser.picker.ui.utils.ThemeTool;
import com.wiser.picker.ui.weight.SquaredImageView;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Wiser
 * 
 *         媒体预览Activity
 */
public class MediaPreviewActivity extends FragmentActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {

	// 媒体预览轮播控件
	private ViewPager					vpPreview;

	// 预览页面title布局
	private ConstraintLayout			clPreviewTitle;

	// 预览页面bottom布局
	private LinearLayout				llPreviewBottom;

	// 小图预览布局
	private LinearLayout				llPreviewSmallPhoto;

	// 小图预览控件
	private RecyclerView				rlvPreviewSmall;

	// 预览完成按钮
	private TextView					tvPreviewComplete;

	// 预览选择checkbox
	private TextView					tvPreviewCheck;

	// 媒体预览轮播适配器
	private MediaPreviewFragmentAdapter	mediaPreviewFragmentAdapter;

	// 小图预览适配器
	private PreviewSmallPhotoAdapter	smallPhotoAdapter;

	// 居中布局管理器
	private CenterLayoutManager			centerLayoutManager;

	// 业务对象
	private IMediaPreviewBiz			iMediaPreviewBiz;

	public static void intent(Activity activity, List<MediaData> mediaDataList, List<MediaData> selectDataList, MediaConfig mediaConfig, int index) {
		if (activity == null) return;
		Intent intent = new Intent(activity, MediaPreviewActivity.class);
		Bundle bundle = new Bundle();
		bundle.putParcelableArrayList(MediaConstants.PREVIEW_MEDIA_ALL_DATA_KEY, (ArrayList<? extends Parcelable>) mediaDataList);
		bundle.putParcelableArrayList(MediaConstants.PREVIEW_MEDIA_SELECT_DATA_KEY, (ArrayList<? extends Parcelable>) selectDataList);
		bundle.putParcelable(MediaConstants.PREVIEW_MEDIA_CONFIG_KEY, mediaConfig);
		bundle.putInt(MediaConstants.PREVIEW_MEDIA_INDEX_KEY, index);
		intent.putExtras(bundle);
		activity.startActivityForResult(intent, MediaConstants.INTENT_PREVIEW_MEDIA_REQUEST_CODE);
	}

	@Override protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PickerHelper.setStatusBarFullTransparent(this);
		setContentView(R.layout.media_preview_act);

		vpPreview = findViewById(R.id.vp_preview);
		clPreviewTitle = findViewById(R.id.cl_preview_title);
		llPreviewBottom = findViewById(R.id.ll_preview_bottom);
		llPreviewSmallPhoto = findViewById(R.id.ll_preview_small);
		rlvPreviewSmall = findViewById(R.id.rlv_preview_small);
		SquaredImageView ivPreviewBack = findViewById(R.id.iv_preview_back);
		tvPreviewComplete = findViewById(R.id.tv_preview_complete);
		tvPreviewCheck = findViewById(R.id.tv_media_preview_check);

		ivPreviewBack.setOnClickListener(this);
		tvPreviewComplete.setOnClickListener(this);
		tvPreviewCheck.setOnClickListener(this);

		iMediaPreviewBiz = new MediaPreviewBiz(this);

		// ViewPager设置
		vpPreview.setAdapter(mediaPreviewFragmentAdapter = new MediaPreviewFragmentAdapter(getSupportFragmentManager(), iMediaPreviewBiz.getMediaDataList()));
		vpPreview.setOffscreenPageLimit(5);
		vpPreview.setCurrentItem(iMediaPreviewBiz.getIndex());
		vpPreview.addOnPageChangeListener(this);

		centerLayoutManager = new CenterLayoutManager(this);
		centerLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
		rlvPreviewSmall.setLayoutManager(centerLayoutManager);
		rlvPreviewSmall.setAdapter(smallPhotoAdapter = new PreviewSmallPhotoAdapter(this));
		smallPhotoAdapter.setIndex(iMediaPreviewBiz.getMediaDataList() != null && iMediaPreviewBiz.getMediaDataList().size() > iMediaPreviewBiz.getIndex()
				? iMediaPreviewBiz.getMediaDataList().get(iMediaPreviewBiz.getIndex()).position
				: -1);
		// 初始化预览选择的媒体
		iMediaPreviewBiz.initSelectMedia();
	}

	// 初始化已经选择的图片UI
	public void updateCheckUi(MediaData mediaData, boolean... isFirst) {
		if (mediaData == null) return;
		if (mediaData.isSelect) {
			// tvPreviewCheck.setBackgroundResource(R.drawable.media_selected);
			tvPreviewCheck.setBackgroundResource(ThemeTool.getThemeCheckNumIcon(this));
			// 设置选择的号码
			tvPreviewCheck.setText(MessageFormat.format("{0}", mediaData.selectNum));
		} else {
			// tvPreviewCheck.setBackgroundResource(R.drawable.media_unselected);
			tvPreviewCheck.setBackgroundResource(ThemeTool.getThemeUnCheckNumIcon(this));
			tvPreviewCheck.setText("");
		}

		// 更新底部按钮UI
		updateBtnStateUi();

		if (isFirst != null && isFirst.length > 0 && isFirst[0]) updateSmallCenterPosition(a());
	}

	private int a() {
		for (int i = 0; i < iMediaPreviewBiz.getSelectDataList().size(); i++) {
			if (iMediaPreviewBiz.getMediaDataList().get(iMediaPreviewBiz.getIndex()).position == iMediaPreviewBiz.getSelectDataList().get(i).position) {
				return i;
			}
		}
		return 0;
	}

	// 更新选择图片底部按钮UI
	public void updateBtnStateUi() {
		// 预览和完成
		if (iMediaPreviewBiz.getSelectCount() > 0) {
			// 完成
			tvPreviewComplete.setEnabled(true);
			tvPreviewComplete.setBackgroundResource(R.drawable.shape_select_finish_bg_st);
			tvPreviewComplete.setText(MessageFormat.format("完成({0}/{1})", iMediaPreviewBiz.getSelectCount(), iMediaPreviewBiz.config().surplusCount));
			tvPreviewComplete.setTextColor(Color.WHITE);
		} else {
			// 完成
			tvPreviewComplete.setEnabled(false);
			tvPreviewComplete.setBackgroundResource(R.drawable.shape_select_finish_bg_df);
			tvPreviewComplete.setText("完成");
			tvPreviewComplete.setTextColor(Color.parseColor("#50ffffff"));
		}
		// 更新小图
		updateSmallPhotoUi();
	}

	// 更新小图片展示
	private void updateSmallPhotoUi() {
		if (iMediaPreviewBiz.getSelectCount() > 0) {
			llPreviewSmallPhoto.setVisibility(View.VISIBLE);
		} else {
			llPreviewSmallPhoto.setVisibility(View.GONE);
		}
		smallPhotoAdapter.setItems(iMediaPreviewBiz.getSelectDataList());
	}

	// 更新小图预览位置处于中心位置
	private void updateSmallCenterPosition(int position) {
		// 自动滑动小图片预览到中心位置
		if (centerLayoutManager != null && rlvPreviewSmall != null && smallPhotoAdapter != null && position >= 0 && smallPhotoAdapter.getItemCount() > position)
			centerLayoutManager.smoothScrollToPosition(rlvPreviewSmall, new RecyclerView.State(), position);
	}

	// 根据小图片预览点击跳转相应位置
	public void setPageItem(MediaData mediaData, int position) {
		if (mediaData == null) return;
		if (iMediaPreviewBiz.isCamera()) {
			if (mediaPreviewFragmentAdapter.getCount() > mediaData.position && mediaData.position > 0) {
				int index = mediaData.position - 1;
				vpPreview.setCurrentItem(index, false);
			}
		} else {
			if (mediaPreviewFragmentAdapter.getCount() > mediaData.position) {
				vpPreview.setCurrentItem(mediaData.position, false);
			}
		}

		updateSmallCenterPosition(position);
	}

	@Override public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

	}

	@Override public void onPageSelected(int position) {
		// 重新设置当前页面位置
		iMediaPreviewBiz.setIndex(position);
		// 设置号码更新小图UI
		smallPhotoAdapter
				.setIndex(iMediaPreviewBiz.getMediaDataList() != null && iMediaPreviewBiz.getMediaDataList().size() > position ? iMediaPreviewBiz.getMediaDataList().get(position).position : -1);
		// 更新checkbox UI状态
		updateCheckUi(iMediaPreviewBiz.getMediaDataList().get(position));
		// 自动定位到小图片预览中心位置
		updateSmallCenterPosition(iMediaPreviewBiz.calculateSmallPhotoPosition(
				iMediaPreviewBiz.getMediaDataList() != null && iMediaPreviewBiz.getMediaDataList().size() > position ? iMediaPreviewBiz.getMediaDataList().get(position).position : -1));
	}

	@Override public void onPageScrollStateChanged(int state) {

	}

	// 更新title显示隐藏
	public void updateTitleAnim() {
		int animatorId;
		if (iMediaPreviewBiz.isTitleHide()) {
			iMediaPreviewBiz.setTitleHide(false);
			animatorId = R.animator.alpha_show;
		} else {
			iMediaPreviewBiz.setTitleHide(true);
			animatorId = R.animator.alpha_hide;
		}
		ObjectAnimator anim1 = (ObjectAnimator) AnimatorInflater.loadAnimator(this, animatorId);
		anim1.setTarget(clPreviewTitle);
		anim1.addListener(new AnimatorListenerAdapter() {

			@Override public void onAnimationStart(Animator animation) {
				super.onAnimationStart(animation);
				clPreviewTitle.setVisibility(View.VISIBLE);
			}

			@Override public void onAnimationEnd(Animator animation) {
				super.onAnimationEnd(animation);
				if (iMediaPreviewBiz.isTitleHide()) clPreviewTitle.setVisibility(View.GONE);
			}
		});
		anim1.start();
		ObjectAnimator anim2 = (ObjectAnimator) AnimatorInflater.loadAnimator(this, animatorId);
		anim2.setTarget(llPreviewBottom);
		anim2.addListener(new AnimatorListenerAdapter() {

			@Override public void onAnimationStart(Animator animation) {
				super.onAnimationStart(animation);
				llPreviewBottom.setVisibility(View.VISIBLE);
			}

			@Override public void onAnimationEnd(Animator animation) {
				super.onAnimationEnd(animation);
				if (iMediaPreviewBiz.isTitleHide()) llPreviewBottom.setVisibility(View.GONE);
			}
		});
		anim2.start();
	}

	public IMediaPreviewBiz getBiz() {
		return iMediaPreviewBiz;
	}

	@Override protected void onDestroy() {
		super.onDestroy();
		vpPreview = null;
		mediaPreviewFragmentAdapter = null;
		if (iMediaPreviewBiz != null) iMediaPreviewBiz.detach();
		iMediaPreviewBiz = null;
	}

	@Override public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.iv_preview_back) {// 返回
			iMediaPreviewBiz.handleBack();
		} else if (id == R.id.tv_media_preview_check) { // 选择
			iMediaPreviewBiz.selectMediaClick(iMediaPreviewBiz.getIndex());
		} else if (id == R.id.tv_preview_complete) { // 完成
			iMediaPreviewBiz.handleCompress();
		}
	}

	@Override public void onBackPressed() {
		iMediaPreviewBiz.handleBack();
		super.onBackPressed();
	}
}
