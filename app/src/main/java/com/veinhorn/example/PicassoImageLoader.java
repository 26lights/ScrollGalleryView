package com.veinhorn.example;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.veinhorn.scrollgalleryview.loader.MediaLoader;

import java.io.IOException;

/**
 * Author: Alexey Nevinsky
 * Date: 06.12.15 1:38
 */
public class PicassoImageLoader implements MediaLoader {

    private String url;
    private Context mContext;

    public PicassoImageLoader(String url) {
        this.url = url;
    }


    @Override
    public Bitmap getBitmap() {
        if(mContext == null) return null;
        try {
            return Picasso.with(mContext).load(url).get();
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public boolean isImage() {
        return true;
    }

    @Override
    public void loadMedia(Context context, final ImageView imageView, final MediaLoader.SuccessCallback callback) {
        mContext = context;
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.placeholder_image)
                .into(imageView, new ImageCallback(callback));
    }

    @Override
    public void loadThumbnail(Context context, ImageView thumbnailView, MediaLoader.SuccessCallback callback) {
        Picasso.with(context)
                .load(url)
                .resize(100, 100)
                .placeholder(R.drawable.placeholder_image)
                .centerInside()
                .into(thumbnailView, new ImageCallback(callback));
    }

    private static class ImageCallback implements Callback {

        private final MediaLoader.SuccessCallback callback;

        public ImageCallback(SuccessCallback callback) {
            this.callback = callback;
        }

        @Override
        public void onSuccess() {
            callback.onSuccess();
        }

        @Override
        public void onError() {

        }
    }
}
