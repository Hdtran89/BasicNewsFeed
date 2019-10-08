package com.example.newsfeed.ViewModel;

import android.graphics.Bitmap;
import android.widget.ProgressBar;

import com.example.newsfeed.Model.Image;
import com.example.newsfeed.Model.News;
import com.example.newsfeed.Network.DownloadImageTask;
import com.example.newsfeed.Network.RssFeedTask;
import com.example.newsfeed.Util.NewsFeedConstants;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.ViewModel;

public class NewsFeedViewModel extends ViewModel {
    public List<News> fetchNews(ProgressBar progressBar) throws ExecutionException, InterruptedException {
        return new RssFeedTask(progressBar).execute(NewsFeedConstants.NEWS_FEED_URL).get();
    }

    public Bitmap fetchImage(Image image, ProgressBar progressBar) throws ExecutionException, InterruptedException {
        return new DownloadImageTask(progressBar).execute(image.getLink()).get();
    }
}
