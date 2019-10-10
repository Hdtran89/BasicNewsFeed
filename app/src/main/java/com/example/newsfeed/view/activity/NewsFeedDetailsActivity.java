package com.example.newsfeed.view.activity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.newsfeed.R;

import static com.example.newsfeed.util.NewsFeedConstants.NEWS_FEED_ITEM_WEB_LINK;

public class NewsFeedDetailsActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        String webUrl = "";
        if (extras != null) {
            webUrl = extras.getString(NEWS_FEED_ITEM_WEB_LINK);
        }

        RelativeLayout relativeLayout = new RelativeLayout(this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        relativeLayout.setLayoutParams(layoutParams);
        WebView webView = new WebView(this);
        webView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(webUrl);
        webView.setHorizontalScrollBarEnabled(true);
        relativeLayout.addView(webView);
        setContentView(relativeLayout);

        getSupportActionBar().setTitle(R.string.toolbar_title);
    }
}
