package android.com.mmassignment.util;

import android.com.mmassignment.R;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

/**
 * Created by Ajit on 6/13/2016.
 */

public enum ImageLoaderOptions {
    INSTANCE;

    public DisplayImageOptions getDisplayImageOptions() {
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.android_news_icon)
                .showImageForEmptyUri(R.color.colorTabTextNormal)
                .showImageOnFail(R.color.colorTabTextNormal)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();
    }
}
