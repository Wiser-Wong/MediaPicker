package com.wiser.picker.api.task;

import com.wiser.picker.api.model.MediaData;

import java.util.List;

public abstract class OnLoadMediaListener {

    protected void onLoadMediaLoading(){}

    protected void onLoadMediaComplete(List<MediaData> mediaDataList){}
}