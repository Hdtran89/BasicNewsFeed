package com.example.newsfeed.network;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ProgressBar;

import com.example.newsfeed.model.News;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.newsfeed.util.NewsFeedConstants.RSS_DESCRIPTION_TYPE;
import static com.example.newsfeed.util.NewsFeedConstants.RSS_ITEM_TYPE;
import static com.example.newsfeed.util.NewsFeedConstants.RSS_LINK_TYPE;
import static com.example.newsfeed.util.NewsFeedConstants.RSS_MEDIA_CONTENT_TYPE;
import static com.example.newsfeed.util.NewsFeedConstants.RSS_TITLE_TYPE;
import static com.example.newsfeed.util.NewsFeedConstants.RSS_URL_TYPE;

public class RssFeedTask extends AsyncTask<String, Void, List<News>> {

    private boolean isItem = false;
    private List<News> items;
    private News news = new News();
    private ProgressBar progressBar;

    public RssFeedTask(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected List<News> doInBackground(String... urls) {
        //
        try {
            URL url = new URL(urls[0]);
            InputStream inputStream = url.openConnection().getInputStream();
            items = parseRssNews(inputStream);
            return items;
        } catch (IOException e) {
            Log.e("Error", e.toString());
        } catch (XmlPullParserException e) {
            Log.e("Error", e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<News> success) {
        // Turn off Progress Bar
        progressBar.setVisibility(View.GONE);
    }

    //Parse the Item correctly.
    public List<News> parseRssNews(InputStream inputStream) throws XmlPullParserException, IOException {
        List<News> resultList = new ArrayList<>();
        try {
            XmlPullParser xmlPullParser = Xml.newPullParser();
            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            xmlPullParser.setInput(inputStream, null);

            xmlPullParser.nextTag();
            while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
                int eventType = xmlPullParser.getEventType();

                String name = xmlPullParser.getName();
                if (name == null)
                    continue;

                if (eventType == XmlPullParser.END_TAG) {
                    if (name.equalsIgnoreCase(RSS_ITEM_TYPE)) {
                        isItem = false;
                    }
                    continue;
                }

                if (eventType == XmlPullParser.START_TAG) {
                    if (name.equalsIgnoreCase(RSS_ITEM_TYPE)) {
                        isItem = true;
                        continue;
                    }
                }
                //Parse the Items
                String result = "";
                if (xmlPullParser.next() == XmlPullParser.TEXT) {
                    result = xmlPullParser.getText();
                    xmlPullParser.nextTag();
                }
                if (isItem) {
                    if (name.equalsIgnoreCase(RSS_TITLE_TYPE)) {
                        news.setTitle(result);
                    } else if (name.equalsIgnoreCase(RSS_LINK_TYPE)) {
                        news.setLink(result);
                    } else if (name.equalsIgnoreCase(RSS_DESCRIPTION_TYPE)) {
                        news.setDescription(result);
                    } else if (name.equalsIgnoreCase(RSS_MEDIA_CONTENT_TYPE)) {
                        news.setMediaContent(xmlPullParser.getAttributeValue(null, RSS_URL_TYPE));
                    }
                }

                if (news.getTitle() != null && news.getDescription() != null && news.getLink() != null && news.getMediaContent() != null) {
                    if (isItem) {
                        News item = new News(news.getTitle(), news.getDescription(), news.getLink(), news.getMediaContent());
                        resultList.add(item);
                    }

                    //Reset
                    news.setTitle(null);
                    news.setMediaContent(null);
                    news.setLink(null);
                    news.setDescription(null);
                    isItem = false;
                }
            }
            return resultList;
        } finally {
            inputStream.close();
        }
    }

}
