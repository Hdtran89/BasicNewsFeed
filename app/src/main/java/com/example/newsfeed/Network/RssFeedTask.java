package com.example.newsfeed.Network;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ProgressBar;

import com.example.newsfeed.Model.Image;
import com.example.newsfeed.Model.News;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class RssFeedTask extends AsyncTask<String, Void, List<News>> {

    private boolean isItem = false;
    private List<News> items;
    private Image image = new Image();
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
            Log.e("Test", e.toString());
        } catch (XmlPullParserException e) {
            Log.e("Test", e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<News> success) {
        // Turn off Progress Bar
        items.addAll(success);
        progressBar.setVisibility(View.GONE);
    }

    //Parse the Item correctly.
    public List<News> parseRssNews(InputStream inputStream) throws XmlPullParserException, IOException {
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
                    if (name.equalsIgnoreCase("item")) {
                        isItem = false;
                    }
                    continue;
                }

                if (eventType == XmlPullParser.START_TAG) {
                    if (name.equalsIgnoreCase("item")) {
                        isItem = true;
                        continue;
                    }
                }
                //Parse the Items
                Log.d("Test", "Parsing name ==> " + name);
                String result = "";
                if (xmlPullParser.next() == XmlPullParser.TEXT) {
                    result = xmlPullParser.getText();
                    xmlPullParser.nextTag();
                }
                if (name.equalsIgnoreCase("title")) {
                    news.setTitle(result);
                } else if (name.equalsIgnoreCase("link")) {
                    news.setLink(result);
                } else if (name.equalsIgnoreCase("description")) {
                    news.setDescription(result);
                } else if (name.equalsIgnoreCase("image")) {
                    result = xmlPullParser.getText();
                    xmlPullParser.nextTag();
                    if (result.equalsIgnoreCase("url")) {
                        image.setUrl(result);
                    } else if (result.equalsIgnoreCase("title")) {
                        image.setTitle(result);
                    } else if (result.equalsIgnoreCase("link")) {
                        image.setLink(result);
                    } else if (result.equalsIgnoreCase("width")) {
                        image.setWidth(result);
                    } else if (result.equalsIgnoreCase("height")) {
                        image.setHeight(result);
                    }
                }

                if (news != null || image != null) {
                    if (isItem) {
                        Image imageItem = new Image(image.getUrl(), image.getTitle(), image.getLink(), image.getWidth(), image.getHeight());
                        News item = new News(news.getTitle(), news.getLink(), news.getDescription(), imageItem);
                        items.add(item);
                    }

                    isItem = false;
                }
            }
            return items;
        } finally {
            inputStream.close();
        }
    }

}
