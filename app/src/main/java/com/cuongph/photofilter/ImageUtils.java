package com.cuongph.photofilter;

import android.app.Activity;
import android.graphics.Bitmap;

public class ImageUtils {

    public static Bitmap resizeBitmap(int percent, Bitmap bitmap, Activity activity){
        final int scaleToUse = percent; // this will be our percentage

        int sizeY = activity.getWindowManager().getDefaultDisplay().getHeight() * scaleToUse / 100;
        int sizeX = bitmap.getWidth() * sizeY / bitmap.getHeight();
        Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, sizeX, sizeY, false);

        return newBitmap;
    }

}
