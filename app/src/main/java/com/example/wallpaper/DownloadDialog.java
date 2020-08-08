package com.example.wallpaper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


public class DownloadDialog extends DialogFragment {

    ImageView imageView;
    private String subreddit_name;
    private String username;
    private String url_of_post;


    public DownloadDialog(ImageView imageView, String subreddit_name, String username, String url_of_post) {
        this.imageView = imageView;
        this.subreddit_name = subreddit_name;
        this.username = username;
        this.url_of_post = url_of_post;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final String url_post = "https://www.reddit.com" + url_of_post;

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        AlertDialog alertDialog = builder.setTitle("from "+ this.subreddit_name).setMessage("username: " + this.username)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "No Clicked", Toast.LENGTH_SHORT).show();
                dismiss();
            }
        }).setPositiveButton("Go To Post", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url_post));
                        startActivity(browserIntent);
                    }
                }).create();
        return alertDialog;
    }
}
