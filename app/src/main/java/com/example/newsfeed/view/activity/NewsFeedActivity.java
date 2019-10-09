package com.example.newsfeed.view.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
        getSupportActionBar().setTitle(R.string.toolbar_title);

        setUpView();

        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleSmall);

        viewModel = ViewModelProviders.of(this).get(NewsFeedViewModel.class);

        getNews();
    }

    private void setUpView() {
        relativeLayout = new RelativeLayout(this);
        relativeLayout.setId(R.id.news_feed_layout);
        RelativeLayout.LayoutParams relativeLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        relativeLayout.setLayoutParams(relativeLayoutParams);

        recyclerView = new RecyclerView(this);
        RecyclerView.LayoutParams recyclerViewParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        recyclerView.setVerticalScrollBarEnabled(true);
        recyclerView.setLayoutParams(recyclerViewParams);

        relativeLayout.addView(recyclerView);
        setContentView(relativeLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.refreshBtn:
                getNews();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
