package com.example.conga.parsexml;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;


public class MainActivity extends Activity {
    Button btn_weather;
    EditText editTextLocation;
    EditText editTextCurrency;
    EditText editTextTemp;
    EditText editTextHumidity;
    EditText editTextPressure;
    HandlerXML handlerXML;
    private String url1 = "http://api.openweathermap.org/data/2.5/weather?q=";
    private String url2 = "&mode=xml";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        editTextLocation = (EditText) findViewById(R.id.editTextLocation);
//        editTextCurrency = (EditText) findViewById(R.id.editTextCurrency);
//        editTextTemp= (EditText) findViewById(R.id.editTextTemp);
//        editTextHumidity = (EditText) findViewById(R.id.editTextHumidity);
//        editTextPressure = (EditText) findViewById(R.id.editTextPressure);
//        btn_weather = ( Button) findViewById(R.id.buttonWeather);
//        btn_weather.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String url = editTextLocation.getText().toString();
//                String finalUrl =  url1 + url +url2;
//
//          handlerXML = new HandlerXML(finalUrl);
//                handlerXML.fetchXML();
//                while(handlerXML.parsingComplete);
//                editTextCurrency.setText(handlerXML.getCountry());
//                editTextTemp.setText(handlerXML.getTemperature());
//                editTextHumidity.setText(handlerXML.getHumidity());
//                editTextPressure.setText(handlerXML.getPressure());
//            }
//        });
    }
    class HandlerXML extends AsyncTask<String , Integer , String>{

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }
    // Lay du lieu ve thong qu
        private String getXmlFromUrl(String urlString) {
            String xml = null;
            try {
                // defaultHttpClient lấy toàn bộ dữ liệu trong http đổ vào 1 chuỗi String
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(urlString);

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                xml = EntityUtils.toString(httpEntity, HTTP.UTF_8);
                // set UTF-8 cho ra chữ unikey
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // return XML
            return xml;
        }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
