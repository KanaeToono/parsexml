package com.example.conga.parsexml;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.apache.http.conn.ConnectTimeoutException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ConGa on 8/03/2016.
 */
public class XMLActivity extends Activity implements AdapterView.OnItemClickListener {
    ListView listView;
    SiteStackAdapter msiteStackAdapter;

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        SiteStackAdapter adapter = (SiteStackAdapter) adapterView.getAdapter();
        StackSite item = (StackSite) adapter.getItem(i);
        Uri uri = Uri.parse(item.getLink());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public class SiteStackAdapter extends ArrayAdapter<StackSite>  {
        public List<StackSite> listSitetack;
        private int resource;
        Context activity;
        private LayoutInflater inflater;
//        Dowloader dowloader;

        public SiteStackAdapter(Context activity, int resource, List<StackSite> objects) {
            super(activity, resource, objects);
            this.activity = activity;
               listSitetack = objects;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // return super.getView(position, convertView, parent);

            if (convertView == null) {
                convertView = inflater.inflate(resource, null);
            }
            //   TextView tvSite = (TextView) convertView.findViewById(R.id.textViewSite);
            TextView tvName = (TextView) convertView.findViewById(R.id.textViewName);
            TextView tvLink = (TextView) convertView.findViewById(R.id.textViewLink);
            TextView tvAbout = (TextView) convertView.findViewById(R.id.textViewAbout);
            ImageView image = (ImageView) convertView.findViewById(R.id.imageView);
            tvName.setText(listSitetack.get(position).getName());
            tvLink.setText(listSitetack.get(position).getLink());
            tvAbout.setText(listSitetack.get(position).getAbout());
            final ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);
            ImageLoader.getInstance().displayImage(listSitetack.get(position).getImageUrl(), image, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    progressBar.setVisibility(View.GONE);
                }
            });
            //dowloader.displayImage()

            return convertView;
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xml);
        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
     //   listView.setOnItemClickListener(new ListListener(rssReader.getItem(), this));
        //listView.setOnItemClickListener(new ListListener(new StackSite().getItem(), this));
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()

                .cacheInMemory(true)
                .cacheOnDisk(true)

                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())

                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ReadXML readXML = new ReadXML();
               readXML.execute("https://dl.dropboxusercontent.com/u/5724095/XmlParseExample/stacksites.xml");
             //   listView.setOnItemClickListener(new ListListener(), this));

            }
        });
    }

    public class ReadXML extends AsyncTask<String, Integer, List<StackSite>>  {

        static final String KEY_SITE = "site";
        static final String KEY_NAME = "name";
        static final String KEY_LINK = "link";
        static final String KEY_ABOUT = "about";
        static final String KEY_IMAGE = "image";

        @Override
        protected List<StackSite> doInBackground(String... strings) {
            HttpURLConnection httpURLConnection = null;
            //  BufferedReader buffered = null;
            StackSite curStackSite = null;
            String curText = "";
            List<StackSite> stackSites;
            stackSites = new ArrayList<StackSite>();
            BufferedReader buffered = null;

            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser xpp = factory.newPullParser();
                URL url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(1000);
                httpURLConnection.setConnectTimeout(1000);
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();
                InputStream inputStream = httpURLConnection.getInputStream();
                buffered = new BufferedReader(new InputStreamReader(inputStream));
                xpp.setInput(buffered);
                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    String tagname = xpp.getName();
                    switch (eventType) {
                        case XmlPullParser.START_TAG:
                            if (tagname.equalsIgnoreCase(KEY_SITE)) {
                                curStackSite = new StackSite();
                            }
                            break;
                        case XmlPullParser.TEXT:
                            curText = xpp.getText();
                            break;
                        case XmlPullParser.END_TAG:
                            if (tagname.equalsIgnoreCase(KEY_SITE)) {
                                stackSites.add(curStackSite);
                            } else if (tagname.equalsIgnoreCase(KEY_NAME)) {
                                curStackSite.setName(curText);
                            } else if (tagname.equalsIgnoreCase(KEY_LINK)) {
                                curStackSite.setLink(curText);
                            } else if (tagname.equalsIgnoreCase(KEY_ABOUT)) {
                                curStackSite.setAbout(curText);
                            } else if (tagname.equalsIgnoreCase(KEY_IMAGE)) {
                                curStackSite.setImageUrl(curText);
                            }
                            break;
                        default:
                            break;
                    }
                    eventType = xpp.next();
                }


            } catch (MalformedURLException | SocketTimeoutException | ConnectTimeoutException e) {
                e.printStackTrace();
                Log.d("requesr", "");
//                Toast.makeText(getApplicationContext(), "hahahha", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return stackSites;
        }

        @Override
        protected void onPostExecute(List<StackSite> stackSites) {
            super.onPostExecute(stackSites);
            //   ArrayAdapter<StackSite> adapter = new ArrayAdapter<StackSite>(XMLActivity.this, android.R.layout.simple_list_item_1, stackSites);
            SiteStackAdapter adapter = new SiteStackAdapter(getApplicationContext(), R.layout.customxml, stackSites);
            listView.setAdapter(adapter);


        }


    }


}
