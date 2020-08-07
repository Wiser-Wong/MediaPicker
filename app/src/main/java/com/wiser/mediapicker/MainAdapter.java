package com.wiser.mediapicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wiser.picker.api.model.MediaData;
import com.wiser.picker.ui.weight.SquaredImageView;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainHolder> {

	private Context			context;

	private List<MediaData>	list;

	public MainAdapter(Context context) {
		this.context = context;
	}

	public void setItems(List<MediaData> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	@NonNull @Override public MainHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new MainHolder(LayoutInflater.from(context).inflate(R.layout.select_item, parent, false));
	}

	@Override public void onBindViewHolder(@NonNull MainHolder holder, int position) {
		Glide.with(holder.ivSelectPic.getContext()).load(list.get(position).path).thumbnail(0.1f).centerCrop().into(holder.ivSelectPic);
	}

	@Override public int getItemCount() {
		return list != null ? list.size() : 0;
	}

	static class MainHolder extends RecyclerView.ViewHolder {

		private SquaredImageView ivSelectPic;

		public MainHolder(@NonNull View itemView) {
			super(itemView);
			ivSelectPic = itemView.findViewById(R.id.iv_select_photo);
		}
	}
}
