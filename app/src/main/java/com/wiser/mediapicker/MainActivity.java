package com.wiser.mediapicker;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.wiser.picker.api.config.MediaConstants;
import com.wiser.picker.api.core.MediaHelper;
import com.wiser.picker.api.model.MediaData;
import com.wiser.picker.api.permission.IPermissionCallBack;
import com.wiser.picker.api.permission.WISERPermission;
import com.wiser.picker.api.task.OnLoadMediaListener;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

	private WISERPermission permission;

	@Override protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		permission = new WISERPermission();

		permission.requestPermissions(this, MediaConstants.PERMISSION_MEDIA_REQUEST_CODE, new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE },
				new IPermissionCallBack() {

					@Override public void hadPermissionResult() {
						List<MediaData> photoMediaDataList = MediaHelper.mediaManage().loadLocalSystemPhotos(MainActivity.this);
						List<MediaData> videoMediaDataList = MediaHelper.mediaManage().loadLocalSystemVideos(MainActivity.this);
						List<MediaData> mediaDataList = MediaHelper.mediaManage().loadLocalSystemAllMedias(MainActivity.this);
						List<MediaData> folderPhotoMediaData = MediaHelper.mediaManage().loadFolderPathPhotos(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "DCIM/");
						List<MediaData> folderVideoMediaData = MediaHelper.mediaManage().loadFolderPathVideos(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "DCIM/SMScreenRecord/");
						MediaHelper.mediaManage().loadMediasTask(MainActivity.this, MediaConstants.LOAD_FOLDER_ALL_MODE, new OnLoadMediaListener() {
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
//                        for (int i = 0; i < photoMediaDataList.size(); i++) {
//                            System.out.println("图片：--->>" + photoMediaDataList.get(i).picPath + "size：---->>" + photoMediaDataList.get(i).picSize);
//                        }
						System.out.println("媒体图片-------------------" + photoMediaDataList.size());
//						for (int i = 0; i < videoMediaDataList.size(); i++) {
//							System.out.println("媒体视频：--->>" + videoMediaDataList.get(i).path + "----size：---->>" + videoMediaDataList.get(i).size + "----duration----->>" + videoMediaDataList.get(i).videoDuration);
//						}
						System.out.println("媒体视频-------------------" + videoMediaDataList.size());
//						for (int i = 0; i < mediaDataList.size(); i++) {
//							System.out.println("媒体视频：--->>" + mediaDataList.get(i).path + "----size：---->>" + mediaDataList.get(i).size + "----mode----->>" + mediaDataList.get(i).mode);
//						}
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
	}

	@Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
			permission.onPermission(requestCode);
		}
	}
}