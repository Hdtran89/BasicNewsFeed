package com.example.newsfeed.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.newsfeed.model.News;
import com.example.newsfeed.network.DownloadImageTask;
import com.example.newsfeed.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_MAIN_NEWS = 1;
    private static final int TYPE_NEWS_ITEM = 2;

    private List<News> newsList;

    public NewsAdapter(List<News> newsList) {
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = new View(parent.getContext());
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        switch (viewType) {
            case TYPE_MAIN_NEWS:
                return new MainNewsViewHolder(view);
            case TYPE_NEWS_ITEM:
                return new NewsViewHolder(view);
        }

        return new NewsViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_MAIN_NEWS:
                MainNewsViewHolder mainNewsViewHolder = (MainNewsViewHolder) holder;
                mainNewsViewHolder.setMainNewsItem(newsList.get(position));
                break;
            case TYPE_NEWS_ITEM:
                NewsViewHolder newsViewHolder = (NewsViewHolder) holder;
                newsViewHolder.setNewsItem(newsList.get(position));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_MAIN_NEWS;
        } else {
            return TYPE_NEWS_ITEM;
        }
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;
        private TextView titleText;
        private ProgressBar progressBar;

        public NewsViewHolder(View itemView) {
            super(itemView);
            imageView = new ImageView(itemView.getContext());
            titleText = new TextView(itemView.getContext());
            progressBar = new ProgressBar(itemView.getContext());

            LinearLayout linearLayout = new LinearLayout(itemView.getContext());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayout.setId(R.id.item_news_layout);

            setUpNewsItemView(linearLayout);

            itemView.setOnClickListener(this);
        }

        public void setUpNewsItemView(LinearLayout linearLayout) {

            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams titleTextParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams progressBarParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            linearLayout.addView(imageView, imageParams);
            linearLayout.addView(titleText, titleTextParams);
            linearLayout.addView(progressBar, progressBarParams);
        }

        public void setNewsItem(News news) {
            //new DownloadImageTask(imageView, progressBar).execute(news.getMediaContent());
            titleText.setText(news.getTitle());
        }

        @Override
        public void onClick(View v) {
            //Handler
        }
    }

    public static class MainNewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;
        private TextView titleText;
        private TextView descriptionText;
        private ProgressBar progressBar;

        public MainNewsViewHolder(View itemView) {
            super(itemView);

            imageView = new ImageView(itemView.getContext());
            titleText = new TextView(itemView.getContext());
            progressBar = new ProgressBar(itemView.getContext());
            descriptionText = new TextView(itemView.getContext());

            LinearLayout linearLayout = new LinearLayout(itemView.getContext());
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setId(R.id.main_news_layout);

            setUpMainNewsItemView(linearLayout);

            itemView.setOnClickListener(this);
        }

        public void setUpMainNewsItemView(LinearLayout linearLayout) {

            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams titleTextParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams descriptionTextParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams progressBarParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            linearLayout.addView(imageView, imageParams);
            linearLayout.addView(titleText, titleTextParams);
            linearLayout.addView(descriptionText, descriptionTextParams);
            linearLayout.addView(progressBar, progressBarParams);
        }

        public void setMainNewsItem(News news) {
            //new DownloadImageTask(imageView, progressBar).execute(news.getMediaContent());
            titleText.setText(news.getTitle());
            descriptionText.setText(news.getDescription());
        }

        @Override
        public void onClick(View v) {
            //Handler
        }
    }
}
