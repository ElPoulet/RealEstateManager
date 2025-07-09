package com.openclassrooms.realestatemanager.fragment.list.embedding;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.fragment.list.Image;

import java.util.List;

public class MyAdapterSlider extends RecyclerView.Adapter<MyAdapterSlider.ViewHolder> {

    private List<Image> dataList;
    private Context context;

    public MyAdapterSlider(List<Image> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.slider_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.i(TAG, "Test getImg in AdapterSlide: " + dataList.get(position).getImgUrl());

        Uri uriImage = Uri.parse(dataList.get(position).getImgUrl());

        Log.i(TAG, "Test URI in AdapterSlide: " + uriImage);


        Glide.with(context)
                .load(uriImage)
                .dontAnimate()
                .listener(new RequestListener<Drawable>(){
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.e("GlideError", "Image load failed for: " + model);
                        if (e != null) {
                            for (Throwable cause : e.getRootCauses()) {
                                Log.e("GlideRootCause", "Root Cause: " + cause);
                            }
                        }
                        return false; // Allow Glide's error handling to occur
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        Log.e("GlideSuccess", "Image loaded successfully for: " + model);
                        return false; // Allow Glide to display the image
                    }
                })
                .into(holder.imageView);


        //holder.imageView.setImageURI(uriImage);

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.detail_apartment_picture);
        }
    }
}
