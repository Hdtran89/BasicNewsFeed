package com.example.newsfeed.view.adapter;

import android.view.View;
import android.view.ViewGroup;
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

    private List<News> newsList;

    public NewsAdapter(List<News> newsList) {
        this.newsList = newsList;
    }

    private View titleTextView;
    private View imageView;
    private View descriptionTextView;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout linearLayout = new LinearLayout(parent.getContext());
        switch (viewType) {
            case TYPE_MAIN_NEWS:
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                linearLayout.setId(R.id.main_news_layout);

                titleTextView = new TextView(parent.getContext());
                titleTextView.setId(R.id.main_news_title);
                imageView = new ImageView(parent.getContext());
                imageView.setId(R.id.main_news_image);
                descriptionTextView = new TextView(parent.getContext());
                descriptionTextView.setId(R.id.main_news_description);

                return new MainNewsViewHolder(linearLayout);
            case TYPE_NEWS_ITEM:
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                linearLayout.setId(R.id.item_news_layout);

                titleTextView = new TextView(parent.getContext());
                titleTextView.setId(R.id.news_title);
                imageView = new ImageView(parent.getContext());
                imageView.setId(R.id.news_image);

                return new NewsViewHolder(linearLayout);
        }

        return new NewsViewHolder(linearLayout);
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
            LinearLayout linearLayout = (LinearLayout) itemView;

            imageView = new ImageView(itemView.getContext());
            titleText = new TextView(itemView.getContext());
            progressBar = new ProgressBar(itemView.getContext());

            setUpNewsItemView(linearLayout);
            itemView.setOnClickListener(this);
        }

        public void setNewsItem(News news) {
            new DownloadImageTask(imageView, progressBar).execute(news.getMediaContent());
            titleText.setText(news.getTitle());
        }

        @Override
        public void onClick(View v) {
            //Handler
        }

        public void setUpNewsItemView(LinearLayout linearLayout) {
            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams titleTextParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//            LinearLayout.LayoutParams progressBarParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            linearLayout.addView(imageView, imageParams);
            linearLayout.addView(titleText, titleTextParams);
//            linearLayout.addView(progressBar, progressBarParams);

            titleText.setTextSize(12.f);
            titleText.setTextColor(itemView.getResources().getColor(R.color.colorAccent));
        }
    }

    public static class MainNewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;
        private TextView titleText;
        private TextView descriptionText;
        private ProgressBar progressBar;

        public MainNewsViewHolder(View itemView) {
            super(itemView);
            LinearLayout linearLayout = new LinearLayout(itemView.getContext());

            imageView = new ImageView(itemView.getContext());
            titleText = new TextView(itemView.getContext());
            progressBar = new ProgressBar(itemView.getContext());
            descriptionText = new TextView(itemView.getContext());

            setUpMainNewsItemView(linearLayout);

            itemView.setOnClickListener(this);
        }

        public void setUpMainNewsItemView(LinearLayout linearLayout) {

            LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams titleTextParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams descriptionTextParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//            LinearLayout.LayoutParams progressBarParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            linearLayout.addView(imageView, imageParams);
            linearLayout.addView(titleText, titleTextParams);
            linearLayout.addView(descriptionText, descriptionTextParams);
//            linearLayout.addView(progressBar, progressBarParams);
            titleText.setTextSize(12.f);
            titleText.setTextColor(linearLayout.getResources().getColor(R.color.colorAccent));
            descriptionText.setTextColor(linearLayout.getResources().getColor(R.color.colorAccent));
        }

        public void setMainNewsItem(News news) {
            new DownloadImageTask(imageView, progressBar).execute(news.getMediaContent());
            titleText.setText(news.getTitle());
            descriptionText.setText(news.getDescription());
        }

        @Override
        public void onClick(View v) {
            //Handler
        }
    }
}
