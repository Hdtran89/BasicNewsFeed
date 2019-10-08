package com.example.newsfeed.view.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.newsfeed.model.News;
import com.example.newsfeed.R;
import com.example.newsfeed.view.adapter.NewsAdapter;
import com.example.newsfeed.viewmodel.NewsFeedViewModel;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NewsFeedActivity extends AppCompatActivity {

    private RelativeLayout relativeLayout;
    private NewsFeedViewModel viewModel;
    private RecyclerView recyclerView;

    private NewsAdapter newsAdapter;
    private ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        relativeLayout = new RelativeLayout(this);
        relativeLayout.setId(R.id.news_feed_layout);
        setContentView(relativeLayout);

        setUpView();
        setUpToolBar();

        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleSmall);

        viewModel = ViewModelProviders.of(this).get(NewsFeedViewModel.class);
        getNews();
    }

    private void setUpView() {
        recyclerView = new RecyclerView(this);

        RelativeLayout.LayoutParams relativeLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        RelativeLayout.LayoutParams recyclerViewParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        relativeLayout.setLayoutParams(relativeLayoutParams);
        recyclerView.setLayoutParams(recyclerViewParams);

        relativeLayout.addView(recyclerView);
    }

    private void setUpToolBar() {
        Toolbar toolbar = new Toolbar(this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, 168);
        toolbar.setLayoutParams(layoutParams);
        toolbar.setPopupTheme(R.style.AppTheme);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        toolbar.setTitle(R.string.toolbar_title);
        toolbar.setVisibility(View.VISIBLE);

        relativeLayout.addView(toolbar, 0);
    }

    private void getNews() {
        try {
            setUpAdapter(viewModel.fetchNews(progressBar));
        } catch (ExecutionException e) {
            Log.e("Test", e.toString());
        } catch (InterruptedException e) {
            Log.e("Test", e.toString());
        }
    }

    private void setUpAdapter(List<News> list) {
        newsAdapter = new NewsAdapter(list);
        //Tablet 3 columns, Phone 2.
        if(getResources().getConfiguration().screenLayout < Configuration.SCREENLAYOUT_SIZE_LARGE){
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        }
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(newsAdapter);
        newsAdapter.notifyDataSetChanged();
    }
}
