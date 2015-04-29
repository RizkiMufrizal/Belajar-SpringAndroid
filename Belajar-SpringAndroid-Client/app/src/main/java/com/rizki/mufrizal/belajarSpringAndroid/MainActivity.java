package com.rizki.mufrizal.belajarSpringAndroid;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.rizki.mufrizal.belajarSpringAndroid.domain.Mahasiswa;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    protected Button button;
    protected EditText npm;
    protected EditText nama;
    protected EditText kelas;

    public static String POST(String url, Mahasiswa mahasiswa) {
        InputStream inputStream;
        String result = "";
        try {

            HttpClient httpclient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(url);

            String json;

            JSONObject jsonObject = new JSONObject();
            jsonObject.accumulate("npm", mahasiswa.getNpm());
            jsonObject.accumulate("nama", mahasiswa.getNama());
            jsonObject.accumulate("kelas", mahasiswa.getKelas());

            json = jsonObject.toString();

            StringEntity se = new StringEntity(json);

            httpPost.setEntity(se);

            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            HttpResponse httpResponse = httpclient.execute(httpPost);

            inputStream = httpResponse.getEntity().getContent();

            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        npm = (EditText) findViewById(R.id.npm);
        nama = (EditText) findViewById(R.id.nama);
        kelas = (EditText) findViewById(R.id.kelas);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
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

    //Batas GET

    @Override
    protected void onStart() {
        super.onStart();
        new HttpRequestTask().execute();
    }

    //Batas POST

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                final String url = "http://10.0.2.2:8080/mahasiswa";
                new HttpAsyncTask().execute(url);
        }
    }

    private class HttpRequestTask extends AsyncTask<Void, Void, Mahasiswa> {

        @Override
        protected Mahasiswa doInBackground(Void... params) {
            try {
                final String url = "http://10.0.2.2:8080/mahasiswa/58412085";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                return restTemplate.getForObject(url, Mahasiswa.class);
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Mahasiswa mahasiswa) {
            TextView npm = (TextView) findViewById(R.id.n);
            TextView nama = (TextView) findViewById(R.id.na);
            TextView kelas = (TextView) findViewById(R.id.k);
            npm.setText(mahasiswa.getNpm());
            nama.setText(mahasiswa.getNama());
            kelas.setText(mahasiswa.getKelas());
        }
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            Mahasiswa mahasiswa = new Mahasiswa();
            mahasiswa.setNpm(npm.getText().toString());
            mahasiswa.setNama(nama.getText().toString());
            mahasiswa.setKelas(kelas.getText().toString());

            return POST(urls[0], mahasiswa);
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getBaseContext(), "Data Sent!", Toast.LENGTH_LONG).show();
        }
    }
}
