package com.cuongph.photofilter.utils;

import android.graphics.Bitmap;

import com.cuongph.photofilter.imagefilter.imageprocessors.Filter;


public class ThumbnailItem {
    public String filterName;
    public Bitmap image;
    public Filter filter;

    public ThumbnailItem() {
        image = null;
        filter = new Filter();
    }
}
