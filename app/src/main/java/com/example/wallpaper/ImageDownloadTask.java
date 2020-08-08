package com.example.wallpaper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageDownloadTask extends AsyncTask<Wallpaper, Void, Bitmap> {

    ImageView imageView;

    public ImageDownloadTask(ImageView imgView) {
        this.imageView = imgView;
    }

    @Override
    protected Bitmap doInBackground(Wallpaper... newsItems) {

        Wallpaper current = newsItems[0];
        Bitmap bitmap = null;

        try {

            URL url = new URL(current.getThumbnail());
            InputStream is = new BufferedInputStream(url.openStream());

            bitmap = BitmapFactory.decodeStream(is);
            current.setBitmap(bitmap);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }
}
