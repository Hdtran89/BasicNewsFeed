package com.example.newsfeed.viewmodel;

import android.widget.ProgressBar;

import androidx.lifecycle.ViewModel;

import com.example.newsfeed.model.News;
import com.example.newsfeed.network.RssFeedTask;
import com.example.newsfeed.util.NewsFeedConstants;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class NewsFeedViewModel extends ViewModel {
    public List<News> fetchNews(ProgressBar progressBar) throws ExecutionException, InterruptedException {
        return new RssFeedTask(progressBar).execute(NewsFeedConstants.NEWS_FEED_URL).get();
    }
}
