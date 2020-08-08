package com.example.wallpaper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements ImageAdapter.AdapterListener{

    RecyclerView lists;
    ImageAdapter adp;
    Spinner spinner;
    String selectedCategory;


    List<Wallpaper> all_items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String[] subreddits = { "earthporn", "wallpaper", "wallpapers"};
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        all_items = new ArrayList<>();
        lists = findViewById(R.id.recyclerView);
        spinner = findViewById(R.id.spinner);
        adp = new ImageAdapter(this, all_items, this);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Reddit Wallpaper App");

        lists.setLayoutManager(new LinearLayoutManager(this));
        lists.setAdapter(adp);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, subreddits);


// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = spinner.getSelectedItem().toString();
                all_items.clear();
                adp.notifyDataSetChanged();


                Log.i("Hasan", "category is " + selectedCategory);
                    JsonTask tsk = new JsonTask();
                    String subreddit_url = "https://www.reddit.com/r/" + selectedCategory + "/hot.json";
                    tsk.execute(subreddit_url);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                return;
            }
        });
    }

    @Override
    public void onClick(Wallpaper selectedWallpaper) {
        Log.i("Hasan", "image is clicked");
        Intent i = new Intent(this, DetailActivity.class);
        i.putExtra("selectedWallpaper", selectedWallpaper);
        startActivity(i);
    }


    class JsonTask extends AsyncTask<String,Void,String> {

        ProgressDialog dialog;                      //For showing the progress
        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setTitle("Loading");
            dialog.setMessage("Please wait");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            String data = "";
            Log.i("Hasan", " DO in bacgorund called");
            String urlStr = strings[0];
            Log.i("Hasan", " urlStr = " + urlStr);
            StringBuilder strBuilder = new StringBuilder();

            try {
                URL url = new URL(urlStr);

                Log.i("Hasan", " Came to Connection");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                Log.i("Hasan", " come to reader");
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                String line = "";
                while ((line = reader.readLine()) != null) {
                    Log.i("Hasan", "line = " + line);
                    strBuilder.append(line);
                }
            } catch (MalformedURLException e) {
                Log.i("Hasan", " Malformed exception");
                e.printStackTrace();
            } catch (IOException e) {
                Log.i("Hasan", "IoException Called");
                e.printStackTrace();
            }

            Log.i("Hasan", " string = " + strBuilder.toString());

            return strBuilder.toString();

        }

        public List<Wallpaper> parseArray(JSONArray array) throws JSONException {
            int len = array.length();
            Log.i("Hasan", " Len = "+ len);
            for(int i = 0; i < len; i++){
                JSONObject object = (JSONObject) array.get(i);
                JSONObject object_data = object.getJSONObject("data");

                String thumbnail_url = object_data.getString("thumbnail");
                if (!thumbnail_url.equals("default") && !thumbnail_url.equals("self")){
                    Log.i("Hasan", selectedCategory + " image read ");
                    String image_url = object_data.getString("url_overridden_by_dest");
                    String subreddit_name = object_data.getString("subreddit_name_prefixed");
                    String username = object_data.getString("author_fullname");
                    String url_of_post = object_data.getString("permalink");
                    Wallpaper curr = new Wallpaper(thumbnail_url, image_url, subreddit_name, username, url_of_post);
                    all_items.add(curr);
                }

            }
            adp.notifyDataSetChanged();

            return all_items;
        }






        @Override
        protected void onPostExecute(String s) {
            dialog.dismiss();

            try {
                JSONObject inputObj = new JSONObject(s);
                JSONArray data = inputObj.getJSONObject("data").getJSONArray("children");

                Log.i("Hasan", " data is -------"+ data);

                all_items = parseArray(data);

                adp.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
