package com.openclassrooms.realestatemanager.fragment.list;


import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;

public class AppartmentViewHolder extends RecyclerView.ViewHolder{


    public TextView nameAppartment;

    public TextView addressAppartment;

    public TextView surfaceAppartment;

    public TextView priceAppartment;

    public TextView statusAppartment;

    public TextView descriptionAppartment;

    public TextView dateOnTheMarket;

    public TextView agent;

    public TextView numberPieces;

    public TextView pointsInterest;

    public ImageView picture;

    public AppartmentViewHolder(View itemView){
        super(itemView);

        nameAppartment = itemView.findViewById(R.id.nameAppItem);
        addressAppartment = itemView.findViewById(R.id.addressAppItem);
        surfaceAppartment = itemView.findViewById(R.id.surfaceAppItem);
        priceAppartment = itemView.findViewById(R.id.priceAppItem);
        picture = itemView.findViewById(R.id.imageAppartmentItem);


    }
}
