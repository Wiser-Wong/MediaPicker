package com.wiser.picker.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wiser.picker.BuildConfig;
import com.wiser.picker.R;
import com.wiser.picker.api.config.MediaConstants;
import com.wiser.picker.api.model.MediaData;
import com.wiser.picker.api.permission.IPermissionCallBack;
import com.wiser.picker.api.permission.WISERPermission;
import com.wiser.picker.lib.MediaHelper;
import com.wiser.picker.ui.config.MediaConfig;
import com.wiser.picker.ui.utils.CameraTool;
import com.wiser.picker.ui.utils.PickerHelper;
import com.wiser.picker.ui.utils.ThemeTool;

import java.io.File;
import java.text.MessageFormat;
import java.util.List;

/**
 * @author Wiser
 *
 *         媒体选择
 */
public class MediaSelectActivity extends FragmentActivity implements View.OnClickListener {

	private TextView			tvPhotoSelectFinish;	// 完成按钮控件

	private TextView			tvPhotoSelectPreview;	// 预览按钮控件

	private LinearLayout		llMediaFolderSelect;	// 媒体文件夹选择控件

	private IMediaSelectBiz		iMediaSelectBiz;		// 业务类

	private MediaSelectAdapter	mediaSelectAdapter;		// 适配器

	private WISERPermission		permission;

	public static void intent(Activity activity, MediaConfig mediaConfig) {
		if (activity == null) return;
		Intent intent = new Intent(activity, MediaSelectActivity.class);
		Bundle bundle = new Bundle();
		bundle.putParcelable(IMediaSelectBiz.MEDIA_SELECT_PAGE_DATA_KEY, mediaConfig);
		intent.putExtras(bundle);
		activity.startActivityForResult(intent, MediaConstants.INTENT_SELECT_MEDIA_REQUEST_CODE);
		activity.overridePendingTransition(R.anim.slide_bottom_in, R.anim.slide_bottom_out);
	}

	@Override protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 沉浸式
		PickerHelper.setStatusBarFullTransparent(this);
		// 主题自定义
		setTheme(MediaHelper.mediaUiManage().bind().customTheme());
		setContentView(R.layout.media_select_act);

		RecyclerView rlvPhotoSelect = findViewById(R.id.rlv_media_select);
		tvPhotoSelectFinish = findViewById(R.id.tv_media_select_finish);
		tvPhotoSelectPreview = findViewById(R.id.tv_media_select_preview);
		llMediaFolderSelect = findViewById(R.id.ll_media_folder_select);

		findViewById(R.id.tv_media_select_cancel).setOnClickListener(this);
		tvPhotoSelectPreview.setOnClickListener(this);
		tvPhotoSelectFinish.setOnClickListener(this);

		permission = new WISERPermission();
		iMediaSelectBiz = new MediaSelectBiz(this);

		rlvPhotoSelect.setLayoutManager(new GridLayoutManager(this, iMediaSelectBiz.config().spanCount));
		rlvPhotoSelect.setAdapter(mediaSelectAdapter = new MediaSelectAdapter(this));

		// 初始化文件夹选择UI
		initFolderSelectUi();

		// 请求权限
		requestMediaPermission();
	}

	// 请求权限
	private void requestMediaPermission() {
		permission.requestPermissions(this, MediaConstants.PERMISSION_MEDIA_REQUEST_CODE, new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE },
				new IPermissionCallBack() {

					@Override public void hadPermissionResult() {
						// 加载媒体数据
						iMediaSelectBiz.loadMediaData();
					}
				});
	}

	// 请求拍照权限
	public void requestCameraPermission() {
		permission.requestPermission(this, MediaConstants.PERMISSION_CAMERA_REQUEST_CODE, Manifest.permission.CAMERA, new IPermissionCallBack() {

			@Override public void hadPermissionResult() {
				// 拍照
				camera();
			}
		});
	}

	// 打开拍照
	private void camera() {
		File file = CameraTool.skipCamera(this);
		iMediaSelectBiz.setOutFilePath(file != null ? file.getAbsolutePath() : "");
	}

	// 设置媒体数据进行展示
	public void setMediaDataAdapter(List<MediaData> mediaDataList) {
		if (mediaSelectAdapter != null) mediaSelectAdapter.setItems(mediaDataList);
	}

	public MediaSelectAdapter getMediaSelectAdapter() {
		return mediaSelectAdapter;
	}

	@Override public void onClick(View view) {
		int id = view.getId();
		if (id == R.id.tv_media_select_cancel) {// 取消
			this.finish();
			overridePendingTransition(R.anim.slide_bottom_in, R.anim.slide_bottom_out);
		} else if (id == R.id.tv_media_select_preview) {// 预览
			MediaHelper.mediaUiManage().bind().onPreviewClick(view, iMediaSelectBiz.getSourceMediaDataList(), mediaSelectAdapter.getSelectData(), iMediaSelectBiz.config().surplusCount, 0);
		} else if (id == R.id.tv_media_select_finish) {// 完成
			iMediaSelectBiz.handleCompressPhoto();
		} else if (id == R.id.ll_media_folder_select) {// 全部相册
		}
	}

	// 文件夹选择显示隐藏判断
	private void initFolderSelectUi() {
		if (iMediaSelectBiz != null && iMediaSelectBiz.config().isFolderDisplay) {
			llMediaFolderSelect.setVisibility(View.VISIBLE);
		} else {
			llMediaFolderSelect.setVisibility(View.GONE);
		}
	}

	// 更新选择图片底部按钮UI
	public void updateBtnStateUi(int count) {
		// 预览和完成
		if (mediaSelectAdapter != null && mediaSelectAdapter.getSelectCount() > 0) {
			// 预览
			tvPhotoSelectPreview.setEnabled(true);
			tvPhotoSelectPreview.setTextColor(ThemeTool.getThemeCompleteTextSelectColor(this));
			tvPhotoSelectPreview.setText(MessageFormat.format("预览({0})", mediaSelectAdapter.getSelectCount()));
			// 完成
			tvPhotoSelectFinish.setEnabled(true);
			tvPhotoSelectFinish.setBackgroundResource(ThemeTool.getThemeCompleteBtnSelectBg(this));
			tvPhotoSelectFinish.setText(MessageFormat.format("完成({0}/{1})", count, iMediaSelectBiz.config().surplusCount));
			tvPhotoSelectFinish.setTextColor(ThemeTool.getThemeCompleteTextSelectColor(this));
		} else {
			// 预览
			tvPhotoSelectPreview.setEnabled(false);
			tvPhotoSelectPreview.setTextColor(ThemeTool.getThemeCompleteTextUnSelectColor(this));
			tvPhotoSelectPreview.setText("预览");
			// 完成
			tvPhotoSelectFinish.setEnabled(false);
			tvPhotoSelectFinish.setBackgroundResource(ThemeTool.getThemeCompleteBtnUnSelectBg(this));
			tvPhotoSelectFinish.setText("完成");
			tvPhotoSelectFinish.setTextColor(ThemeTool.getThemeCompleteTextUnSelectColor(this));
		}
	}

	public IMediaSelectBiz getBiz() {
		return iMediaSelectBiz;
	}

	@Override protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// 拍照
		if (resultCode == RESULT_OK && requestCode == MediaConstants.CAMERA_REQUEST_CODE) {
			if (iMediaSelectBiz.config().isCameraCrop) {
				Uri uri;
				if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) uri = Uri.fromFile(new File(iMediaSelectBiz.getOutFilePath()));
				else uri = FileProvider.getUriForFile(this, this.getPackageName().concat(".fileprovider"), new File(iMediaSelectBiz.getOutFilePath()));
				// 裁剪
				CameraTool.cropPhoto(this, uri, new File(iMediaSelectBiz.getOutFilePath()),
						iMediaSelectBiz.config().cropWidth > 0 ? iMediaSelectBiz.config().cropWidth : MediaConstants.DEFAULT_CROP_WIDTH,
						iMediaSelectBiz.config().cropHeight > 0 ? iMediaSelectBiz.config().cropHeight : MediaConstants.DEFAULT_CROP_HEIGHT, MediaConstants.CROP_REQUEST_CODE);
			} else {
				// 回传拍照的结果
				iMediaSelectBiz.setCameraResult();
				this.finish();
			}
		}

		// 裁剪
		if (requestCode == MediaConstants.CROP_REQUEST_CODE) {
			iMediaSelectBiz.setCameraResult();
			this.finish();
		}
	}

	@Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == MediaConstants.PERMISSION_MEDIA_REQUEST_CODE) {
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				this.permission.onPermission(requestCode);
			} else {
				Toast.makeText(this, "请打开权限", Toast.LENGTH_SHORT).show();
			}
		}
		// 拍照
		if (requestCode == MediaConstants.PERMISSION_CAMERA_REQUEST_CODE) {
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				this.permission.onPermission(requestCode);
			} else {
				Toast.makeText(this, "请打开拍照权限", Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override protected void onDestroy() {
		super.onDestroy();
		if (iMediaSelectBiz != null) iMediaSelectBiz.onDetach();
		iMediaSelectBiz = null;
		if (mediaSelectAdapter != null) mediaSelectAdapter.onDetach();
		mediaSelectAdapter = null;
		tvPhotoSelectFinish = null;
		tvPhotoSelectPreview = null;
		llMediaFolderSelect = null;
		permission = null;
	}
}
