package com.wiser.mediapicker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wiser.picker.api.config.MediaConstants;
import com.wiser.picker.lib.MediaHelper;
import com.wiser.picker.api.model.MediaData;
import com.wiser.picker.api.permission.IPermissionCallBack;
import com.wiser.picker.api.permission.WISERPermission;
import com.wiser.picker.api.task.load.OnLoadMediaListener;
import com.wiser.picker.ui.config.MediaConfig;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	private WISERPermission permission;

	private RecyclerView rlvSelect;

	private MainAdapter mainAdapter;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		rlvSelect = findViewById(R.id.rlv_select);
		rlvSelect.setLayoutManager(new GridLayoutManager(this,4));
		rlvSelect.setAdapter(mainAdapter = new MainAdapter(this));

		permission = new WISERPermission();

		permission.requestPermissions(this, MediaConstants.PERMISSION_MEDIA_REQUEST_CODE, new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE },
				new IPermissionCallBack() {

					@Override public void hadPermissionResult() {
						List<MediaData> photoMediaDataList = MediaHelper.mediaQueryManage().queryLocalSystemPhotosBySize(MainActivity.this,2000);
						List<MediaData> videoMediaDataList = MediaHelper.mediaQueryManage().queryLocalSystemVideosBySize(MainActivity.this,20000);
						List<MediaData> videoMediaDataListDur = MediaHelper.mediaQueryManage().queryLocalSystemVideosByDuration(MainActivity.this,25);
						List<MediaData> mediaDataList = MediaHelper.mediaQueryManage().queryLocalSystemAllMedias(MainActivity.this);
						List<MediaData> folderPhotoMediaData = MediaHelper.mediaQueryManage().queryFolderPathPhotos(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "DCIM/");
						List<MediaData> folderVideoMediaData = MediaHelper.mediaQueryManage().queryFolderPathVideos(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "DCIM/SMScreenRecord/");
						MediaHelper.mediaQueryManage().loadMediasTask(MainActivity.this, MediaConstants.LOAD_FOLDER_ALL_MODE, new OnLoadMediaListener() {
							@Override
							protected void onLoadMediaComplete(List<MediaData> mediaDataList) {
								super.onLoadMediaComplete(mediaDataList);
								if (mediaDataList == null) return;
								for (int i = 0; i < mediaDataList.size(); i++) {
									System.out.println("媒体文件夹视频和图片：--->>" + mediaDataList.get(i).path + "size：---->>" + mediaDataList.get(i).size + "----duration----->>" + mediaDataList.get(i).videoDuration + "----width----->>" + mediaDataList.get(i).width + "----height----->>" + mediaDataList.get(i).height);
								}
								System.out.println("媒体文件夹视频和图片-------------------" + mediaDataList.size());
							}
						});
						mainAdapter.setItems(mediaDataList);

//                        for (int i = 0; i < photoMediaDataList.size(); i++) {
//                            System.out.println("图片：--->>" + photoMediaDataList.get(i).path + "size：---->>" + photoMediaDataList.get(i).size);
//                        }
//						System.out.println("媒体图片-------------------" + photoMediaDataList.size());
//						for (int i = 0; i < videoMediaDataList.size(); i++) {
//							System.out.println("媒体视频：--->>" + videoMediaDataList.get(i).path + "----size：---->>" + videoMediaDataList.get(i).size + "----duration----->>" + videoMediaDataList.get(i).videoDuration);
//						}
						for (int i = 0; i < videoMediaDataListDur.size(); i++) {
							System.out.println("媒体视频：--->>" + videoMediaDataListDur.get(i).path + "----size：---->>" + videoMediaDataListDur.get(i).size + "----duration----->>" + videoMediaDataListDur.get(i).videoDuration);
						}
//						System.out.println("媒体视频-------------------" + videoMediaDataList.size());
						for (int i = 0; i < mediaDataList.size(); i++) {
							System.out.println("媒体视频和图片：--->>" + mediaDataList.get(i).path + "----size：---->>" + mediaDataList.get(i).size + "------duration------>>" + mediaDataList.get(i).videoDuration);
						}
						System.out.println("媒体视频和图片-------------------" + mediaDataList.size());
//						for (int i = 0; i < folderPhotoMediaData.size(); i++) {
//                            System.out.println("媒体文件夹图片：--->>" + folderPhotoMediaData.get(i).path + "size：---->>" + folderPhotoMediaData.get(i).size);
//                        }
						System.out.println("媒体文件夹图片-------------------" + folderPhotoMediaData.size());
//						for (int i = 0; i < folderVideoMediaData.size(); i++) {
//							System.out.println("媒体文件夹视频：--->>" + folderVideoMediaData.get(i).path + "size：---->>" + folderVideoMediaData.get(i).size + "----duration----->>" + videoMediaDataList.get(i).videoDuration + "----width----->>" + videoMediaDataList.get(i).width + "----height----->>" + videoMediaDataList.get(i).height);
//						}
						System.out.println("媒体文件夹视频-------------------" + folderVideoMediaData.size());

					}
				});

		findViewById(R.id.btn_media).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MediaHelper.mediaManage().selectPageSkip(MainActivity.this,new MediaConfig.Builder()
						.ofSpanCount(3)
						.ofSurplusCount(10)
						.ofCheckUiNumType()
						.queryAll()
						.ofCompress()
						.ofCompressParameter(100,100,100,Environment.getExternalStorageDirectory()+"/compress/photo")
						.ofCompressPhotoSize(60)
						.build());
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == MediaConstants.INTENT_SELECT_MEDIA_REQUEST_CODE) {
			if (data != null) {
				List<MediaData> mediaDataList = data.getParcelableArrayListExtra(MediaConstants.INTENT_SELECT_MEDIA_KEY);
				mainAdapter.setItems(mediaDataList);
			}
		}
	}

	@Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
			permission.onPermission(requestCode);
		}
	}
}