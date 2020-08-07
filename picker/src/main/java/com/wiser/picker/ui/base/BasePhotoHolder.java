package com.wiser.picker.ui.base;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Wiser
 * 
 *         Holder base
 */
public abstract class BasePhotoHolder<T> extends RecyclerView.ViewHolder {

	private BasePhotoAdapter adapter;

	public BasePhotoHolder(@NonNull View itemView) {
		super(itemView);
	}

	public void setAdapter(BasePhotoAdapter adapter) {
		this.adapter = adapter;
	}

	public BasePhotoAdapter adapter() {
		return adapter;
	}

	public abstract void bindData(T t, int position);

}
