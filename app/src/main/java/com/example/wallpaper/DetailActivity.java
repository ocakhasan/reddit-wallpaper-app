package com.example.wallpaper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.icu.util.Output;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class DetailActivity extends AppCompatActivity {

    ImageView detail_img;
    Wallpaper selectedWallpaper;
    Button downloadbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        detail_img = findViewById(R.id.detail_img);
        selectedWallpaper = (Wallpaper)getIntent().getSerializableExtra("selectedWallpaper");
        Log.i("Hasan", "boilerplate code worked");
        final String image_url = selectedWallpaper.getUrl();
        downloadbutton = findViewById(R.id.downloadbutton);

        Picasso.get().load(image_url).into(detail_img);

        detail_img.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                DownloadDialog downloadDialog = new DownloadDialog(detail_img, selectedWallpaper.getSubreddit_name(), selectedWallpaper.getUsername(), selectedWallpaper.getUrl_of_post());
                downloadDialog.show(fragmentManager, "");

                return true;
            }
        });


        ActionBar currentActionBar = getSupportActionBar();
        currentActionBar.setHomeButtonEnabled(true);
        currentActionBar.setDisplayHomeAsUpEnabled(true);
        currentActionBar.setHomeAsUpIndicator(R.drawable.ic_keyboard_backspace_24px);
        currentActionBar.setTitle("Aaaa Şeron");

        downloadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Hasan", "Button Clicked");
                BitmapDrawable draw = (BitmapDrawable) detail_img.getDrawable();
                Bitmap bitmap = draw.getBitmap();

                FileOutputStream outStream = null;
                File sdCard = Environment.getExternalStorageDirectory();
                File dir = new File(sdCard.getAbsolutePath() + "/MyFolder");
                dir.mkdirs();
                String fileName = String.format("%d.jpg", System.currentTimeMillis());
                File outFile = new File(dir, fileName);
                try {
                    outStream = new FileOutputStream(outFile);
                    Log.i("Hasan", "downloaded");
                    Toast.makeText(DetailActivity.this, "Image Downloaded", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                try {
                    outStream.flush();
                    outStream.close();
                    finish();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            Log.i("Hasan", "home button clicked");
            finish();
            return true;
        }
        return false;
    }
}
