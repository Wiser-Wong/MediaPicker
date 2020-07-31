package com.wiser.picker.api.permission;

import android.app.Activity;

import androidx.fragment.app.Fragment;

/**
 * @author Wiser
 *
 *         权限管理
 */
public interface IPermission {

	/**
	 * 请求权限
	 * 
	 * @param activity
	 * @param request
	 * @param permission
	 * @param ikmPermissionCallBack
	 */
	void requestPermission(Activity activity, int request, String permission, IPermissionCallBack ikmPermissionCallBack);

	/**
	 * 请求权限
	 *
	 * @param activity
	 * @param request
	 * @param permissions
	 * @param ikmPermissionCallBack
	 */
	void requestPermissions(Activity activity, int request, String[] permissions, IPermissionCallBack ikmPermissionCallBack);

	/**
	 * 请求权限
	 * 
	 * @param fragment
	 * @param request
	 * @param permission
	 * @param ikmPermissionCallBack
	 */
	void requestPermission(Fragment fragment, int request, String permission, IPermissionCallBack ikmPermissionCallBack);

	/**
	 * 请求权限
	 *
	 * @param fragment
	 * @param request
	 * @param permissions
	 * @param ikmPermissionCallBack
	 */
	void requestPermissions(Fragment fragment, int request, String[] permissions, IPermissionCallBack ikmPermissionCallBack);

	/**
	 * 获取权限成功
	 */
	void onPermission(int requestCode);

}
