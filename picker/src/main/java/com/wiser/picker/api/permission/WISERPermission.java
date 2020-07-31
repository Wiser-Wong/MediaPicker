package com.wiser.picker.api.permission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Wiser
 * 
 *         权限管理
 */
public class WISERPermission implements IPermission {

	protected Map<Integer, IPermissionCallBack> hashMap = new ConcurrentHashMap<>();

	/**
	 * 请求权限
	 * 
	 * @param activity
	 * @param request
	 * @param permission
	 * @param ikmPermissionCallBack
	 */
	@Override
    public void requestPermission(Activity activity, int request, String permission, IPermissionCallBack ikmPermissionCallBack) {
		permission(activity, ikmPermissionCallBack, request, permission);
	}

	/**
	 * 请求权限
	 *
	 * @param activity
	 * @param request
	 * @param permissions
	 * @param ikmPermissionCallBack
	 */
	@Override
    public void requestPermissions(Activity activity, int request, String[] permissions, IPermissionCallBack ikmPermissionCallBack) {
		permissions(activity, ikmPermissionCallBack, request, permissions);
	}

	/**
	 * 请求权限
	 * 
	 * @param fragment
	 * @param request
	 * @param permission
	 * @param ikmPermissionCallBack
	 */
	@Override
    public void requestPermission(Fragment fragment, int request, String permission, IPermissionCallBack ikmPermissionCallBack) {
		permission(fragment, ikmPermissionCallBack, request, permission);
	}

	/**
	 * 请求权限
	 *
	 * @param fragment
	 * @param request
	 * @param permissions
	 * @param ikmPermissionCallBack
	 */
	@Override
    public void requestPermissions(Fragment fragment, int request, String[] permissions, IPermissionCallBack ikmPermissionCallBack) {
		permissions(fragment, ikmPermissionCallBack, request, permissions);
	}

	/**
	 * 获取权限成功
	 */
	@Override
    public void onPermission(int requestCode) {
		IPermissionCallBack iPermissionCallBack = hashMap.get(requestCode);
		if (iPermissionCallBack != null) {
			hashMap.remove(requestCode);
			iPermissionCallBack.hadPermissionResult();
		}
	}

	/**
	 * 获取权限
	 *
	 * @param activity
	 * @param iPermissionCallBack
	 * @param request
	 * @param permission
	 */
	protected void permission(final Activity activity, final IPermissionCallBack iPermissionCallBack, final int request, final String permission) {
		if (activity == null) {
			return;
		}
		// 如果小于 6.0
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
			if (iPermissionCallBack != null) {
				iPermissionCallBack.hadPermissionResult();
			}
		} else {
			if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
				if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
					hashMap.put(request, iPermissionCallBack);
					ActivityCompat.requestPermissions(activity, new String[] { permission }, request);
				} else {
					hashMap.put(request, iPermissionCallBack);
					activity.requestPermissions(new String[] { permission }, request);
				}
			} else {
				if (iPermissionCallBack != null) {
					iPermissionCallBack.hadPermissionResult();
				}
			}
		}
	}

	/**
	 * 获取权限
	 *
	 * @param activity
	 * @param iPermissionCallBack
	 * @param request
	 * @param permissions
	 */
	protected void permissions(final Activity activity, final IPermissionCallBack iPermissionCallBack, final int request, final String[] permissions) {
		if (activity == null) {
			return;
		}
		// 如果小于 6.0
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
			if (iPermissionCallBack != null) {
				iPermissionCallBack.hadPermissionResult();
			}
		} else {
			ArrayList<String> arrayList = new ArrayList<>();
			for (String permission: permissions) {
				if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
					arrayList.add(permission);
				}
			}

			if (arrayList.size() > 0) {
				hashMap.put(request, iPermissionCallBack);
				activity.requestPermissions(permissions, request);
			} else {
				if (iPermissionCallBack != null) {
					iPermissionCallBack.hadPermissionResult();
				}
			}
		}
	}
	/**
	 * 获取权限
	 *
	 * @param fragment
	 * @param iPermissionCallBack
	 * @param request
	 * @param permission
	 */
	protected void permission(final Fragment fragment, final IPermissionCallBack iPermissionCallBack, final int request, final String permission) {
		if (fragment == null || fragment.getActivity() == null) {
			return;
		}
		// 如果小于 6.0
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
			if (iPermissionCallBack != null) {
				iPermissionCallBack.hadPermissionResult();
			}
		} else {
			if (ActivityCompat.checkSelfPermission(Objects.requireNonNull(fragment.getActivity()), permission) != PackageManager.PERMISSION_GRANTED) {
				if (ActivityCompat.shouldShowRequestPermissionRationale(fragment.getActivity(), permission)) {
					hashMap.put(request, iPermissionCallBack);
					ActivityCompat.requestPermissions(fragment.getActivity(), new String[] { permission }, request);
				} else {
					hashMap.put(request, iPermissionCallBack);
					fragment.requestPermissions(new String[] { permission }, request);
				}
			} else {
				if (iPermissionCallBack != null) {
					iPermissionCallBack.hadPermissionResult();
				}
			}
		}
	}

	/**
	 * 获取权限
	 *
	 * @param fragment
	 * @param iPermissionCallBack
	 * @param request
	 * @param permissions
	 */
	protected void permissions(final Fragment fragment, final IPermissionCallBack iPermissionCallBack, final int request, final String[] permissions) {
		if (fragment == null || fragment.getActivity() == null) {
			return;
		}
		// 如果小于 6.0
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
			if (iPermissionCallBack != null) {
				iPermissionCallBack.hadPermissionResult();
			}
		} else {
			ArrayList<String> arrayList = new ArrayList<>();
			for (String permission: permissions) {
				if (ActivityCompat.checkSelfPermission(fragment.getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
					arrayList.add(permission);
				}
			}

			if (arrayList.size() > 0) {
				hashMap.put(request, iPermissionCallBack);
				fragment.getActivity().requestPermissions(permissions, request);
			} else {
				if (iPermissionCallBack != null) {
					iPermissionCallBack.hadPermissionResult();
				}
			}
		}
	}

}
