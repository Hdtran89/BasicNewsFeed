package com.example.newsfeed.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.InputStream;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    private ImageView imageView;
    private ProgressBar progressBar;
    private Context context;

    public DownloadImageTask(ImageView imageView, ProgressBar progressBar, Context context) {
        this.imageView = imageView;
        this.progressBar = progressBar;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), bitmap);
        //imageView.setImageBitmap(bitmap);
        imageView.setBackground(bitmapDrawable);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        progressBar.setVisibility(View.GONE);
    }
}
