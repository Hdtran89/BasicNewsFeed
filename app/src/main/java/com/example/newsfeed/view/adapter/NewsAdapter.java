package com.example.newsfeed.view.adapter;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsfeed.R;
import com.example.newsfeed.model.News;
import com.example.newsfeed.network.DownloadImageTask;

import java.util.List;

import static com.example.newsfeed.util.NewsFeedConstants.TYPE_MAIN_NEWS;
import static com.example.newsfeed.util.NewsFeedConstants.TYPE_NEWS_ITEM;

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(News news);
    }

    private List<News> newsList;
    private OnItemClickListener listener;

    private View titleTextView;
    private View imageView;
    private View descriptionTextView;

    public NewsAdapter(List<News> newsList, OnItemClickListener listener) {
        this.newsList = newsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FrameLayout frameLayout = new FrameLayout(parent.getContext());
        ViewGroup.MarginLayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(8,8,8,8);

        switch (viewType) {
            case TYPE_MAIN_NEWS:
                frameLayout.setLayoutParams(layoutParams);
                frameLayout.setId(R.id.main_news_layout);

                titleTextView = new TextView(parent.getContext());
                titleTextView.setId(R.id.main_news_title);
                imageView = new ImageView(parent.getContext());
                imageView.setId(R.id.main_news_image);
                descriptionTextView = new TextView(parent.getContext());
                descriptionTextView.setId(R.id.main_news_description);

                return new MainNewsViewHolder(frameLayout);
            case TYPE_NEWS_ITEM:
                frameLayout.setLayoutParams(layoutParams);
                frameLayout.setId(R.id.item_news_layout);

                titleTextView = new TextView(parent.getContext());
                titleTextView.setId(R.id.news_title);
                imageView = new ImageView(parent.getContext());
                imageView.setId(R.id.news_image);

                return new NewsViewHolder(frameLayout);
        }

        return new NewsViewHolder(frameLayout);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case TYPE_MAIN_NEWS:
                MainNewsViewHolder mainNewsViewHolder = (MainNewsViewHolder) holder;
                mainNewsViewHolder.setMainNewsItem(newsList.get(position), listener);
                break;
            case TYPE_NEWS_ITEM:
                NewsViewHolder newsViewHolder = (NewsViewHolder) holder;
                newsViewHolder.setNewsItem(newsList.get(position), listener);
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
        }

        return TYPE_NEWS_ITEM;
    }

    private static class NewsViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView titleText;
        private ProgressBar progressBar;

        private NewsViewHolder(View itemView) {
            super(itemView);
            FrameLayout frameLayout = (FrameLayout) itemView;

            imageView = new ImageView(itemView.getContext());
            titleText = new TextView(itemView.getContext());
            progressBar = new ProgressBar(itemView.getContext(), null, android.R.attr.progressBarStyleSmall);

            setUpNewsItemView(frameLayout);
        }

        private void setNewsItem(final News news, final OnItemClickListener listener) {
            new DownloadImageTask(imageView, progressBar, itemView.getContext()).execute(news.getMediaContent());
            titleText.setText(news.getTitle());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(news);
                }
            });
        }

        private void setUpNewsItemView(FrameLayout parent) {
            LinearLayout linearLayout = new LinearLayout(parent.getContext());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            parent.addView(linearLayout);

            FrameLayout.LayoutParams progressBarParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            progressBarParams.gravity = Gravity.CENTER;
            parent.addView(progressBar, progressBarParams);

            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 300);
            LinearLayout.LayoutParams titleTextParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            linearLayout.addView(imageView, imageParams);
            linearLayout.addView(titleText, titleTextParams);

            titleText.setTextSize(16.f);
            titleText.setTextColor(itemView.getResources().getColor(R.color.colorAccent));
        }
    }

    private static class MainNewsViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView titleText;
        private TextView descriptionText;
        private ProgressBar progressBar;

        private MainNewsViewHolder(View itemView) {
            super(itemView);
            FrameLayout frameLayout = (FrameLayout) itemView;

            imageView = new ImageView(itemView.getContext());
            titleText = new TextView(itemView.getContext());
            progressBar = new ProgressBar(itemView.getContext(), null, android.R.attr.progressBarStyleSmall);
            descriptionText = new TextView(itemView.getContext());

            setUpMainNewsItemView(frameLayout);
        }

        private void setUpMainNewsItemView(FrameLayout parent) {
            LinearLayout linearLayout = new LinearLayout(itemView.getContext());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            parent.addView(linearLayout);

            FrameLayout.LayoutParams progressBarParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            progressBarParams.gravity = Gravity.CENTER;
            parent.addView(progressBar, progressBarParams);

            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams titleTextParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams descriptionTextParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            linearLayout.addView(imageView, imageParams);
            linearLayout.addView(titleText, titleTextParams);
            linearLayout.addView(descriptionText, descriptionTextParams);

            titleText.setTextSize(16.f);
            titleText.setTextColor(linearLayout.getResources().getColor(R.color.colorAccent));

            descriptionText.setTextSize(12.f);
            descriptionText.setTextColor(linearLayout.getResources().getColor(R.color.colorAccent));
            descriptionText.setMaxLines(2);

        }

        private void setMainNewsItem(final News news, final OnItemClickListener listener) {
            new DownloadImageTask(imageView, progressBar, itemView.getContext()).execute(news.getMediaContent());
            titleText.setText(news.getTitle());
            descriptionText.setText(news.getDescription());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(news);
                }
            });
        }
    }
}
