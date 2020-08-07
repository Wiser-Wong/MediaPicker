package com.wiser.picker.ui.weight;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * @author Wiser
 * 
 *         自定义IV
 */
public class SquaredImageView extends AppCompatImageView {

	public SquaredImageView(Context context) {
		super(context);
	}

	public SquaredImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
	}
}
