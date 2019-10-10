package com.example.newsfeed.view.activity;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsfeed.R;
import com.example.newsfeed.model.News;
import com.example.newsfeed.view.adapter.NewsAdapter;
import com.example.newsfeed.viewmodel.NewsFeedViewModel;

import java.util.List;
import java.util.concurrent.ExecutionException;
import static com.example.newsfeed.util.NewsFeedConstants.TYPE_MAIN_NEWS;
import static com.example.newsfeed.util.NewsFeedConstants.TYPE_NEWS_ITEM;
public class NewsFeedActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private NewsFeedViewModel viewModel;
    private RecyclerView recyclerView;

    private NewsAdapter newsAdapter;
    private ProgressDialog progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle(R.string.toolbar_title);

        setUpView();

        viewModel = ViewModelProviders.of(this).get(NewsFeedViewModel.class);

        getNews();

    }

    private void setUpView() {
        linearLayout = new LinearLayout(this);
        linearLayout.setId(R.id.news_feed_layout);
        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(linearLayoutParams);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        recyclerView = new RecyclerView(this);
        RecyclerView.LayoutParams recyclerViewParams = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT);
        recyclerView.setVerticalScrollBarEnabled(true);
        recyclerView.setLayoutParams(recyclerViewParams);

        linearLayout.addView(recyclerView);

        progressBar = new ProgressDialog(this);

        setContentView(linearLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refreshBtn:
                getNews();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getNews() {
        try {
            progressBar.show();
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
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        if(getResources().getConfiguration().screenLayout < Configuration.SCREENLAYOUT_SIZE_LARGE){
            gridLayoutManager = new GridLayoutManager(this, 3);
        }

        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (newsAdapter.getItemViewType(position)){
                    case TYPE_MAIN_NEWS:
                        if(getResources().getConfiguration().screenLayout < Configuration.SCREENLAYOUT_SIZE_LARGE){
                            return 3;
                        }
                        return 2;
                    case TYPE_NEWS_ITEM:
                        return 1;
                    default:
                        return 1;
                }
            }
        });

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(newsAdapter);
        newsAdapter.notifyDataSetChanged();
    }
}
