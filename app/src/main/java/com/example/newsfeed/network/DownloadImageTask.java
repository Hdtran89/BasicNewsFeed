package com.example.newsfeed.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.InputStream;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    private ImageView imageView;
    //private ProgressBar progressBar;

    public DownloadImageTask(ImageView imageView, ProgressBar progressBar) {
        this.imageView = imageView;
        //this.progressBar = progressBar;
    }

//    public DownloadImageTask(ProgressBar progressBar) {
//        this.progressBar = progressBar;
//    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        progressBar.setVisibility(View.VISIBLE);
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
        imageView.setImageBitmap(bitmap);
        //progressBar.setVisibility(View.GONE);
    }
}
