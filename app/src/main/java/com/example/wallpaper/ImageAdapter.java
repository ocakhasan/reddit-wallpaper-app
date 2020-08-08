package com.example.wallpaper;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    Context context;
    List<Wallpaper> items;
    AdapterListener listener;

    public ImageAdapter(Context context, List<Wallpaper> items, AdapterListener listener) {
        this.context = context;
        this.items = items;
        this.listener = listener;
    }



    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.image_row, parent, false);

        ImageViewHolder holder = new ImageViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, final int position) {

        holder.lyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(items.get(position));
                Log.i("Hasan", "override is called");
            }
        });

        if(items.get(position).getBitmap()==null){
            new ImageDownloadTask(holder.img).execute(items.get(position));
        }else{
            holder.img.setImageBitmap(items.get(position).getBitmap());
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface AdapterListener{
        public void onClick(Wallpaper selectedWallpaper);
    }

    class ImageViewHolder extends RecyclerView.ViewHolder{

        ImageView img;
        ConstraintLayout lyt;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.row_img);
            lyt = itemView.findViewById(R.id.container);
        }
    }
}
