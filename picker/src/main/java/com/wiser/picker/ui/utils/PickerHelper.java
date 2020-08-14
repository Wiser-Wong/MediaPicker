package com.wiser.picker.ui.utils;

import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.wiser.picker.api.model.MediaData;

import java.util.List;

/**
 * @author Wiser
 *
 *         帮助类
 */
public class PickerHelper {

	/**
	 * 全透状态栏
	 */
	public static void setStatusBarFullTransparent(FragmentActivity activity) {
		if (activity == null) return;
		if (Build.VERSION.SDK_INT >= 21) {// 21表示5.0
			Window window = activity.getWindow();
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(Color.TRANSPARENT);
		} else if (Build.VERSION.SDK_INT >= 19) {// 19表示4.4
			activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// 虚拟键盘也透明
			// getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
	}

	// 转换数据
	public static MediaData[] covertData(List<MediaData> mediaDataList) {
		if (mediaDataList == null) return null;
		MediaData[] selectData = new MediaData[mediaDataList.size()];
		for (int i = 0; i < mediaDataList.size(); i++) {
			if (mediaDataList.get(i) == null) continue;
			selectData[i] = mediaDataList.get(i);
		}
		return selectData;
	}

}
