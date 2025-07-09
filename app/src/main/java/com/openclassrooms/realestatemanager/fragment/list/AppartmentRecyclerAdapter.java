package com.openclassrooms.realestatemanager.fragment.list;

import static android.content.ContentValues.TAG;

import android.content.Context;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.R;

import java.util.List;

public class AppartmentRecyclerAdapter extends RecyclerView.Adapter<AppartmentViewHolder> {

    private final Context context;

    private List<Appartment> appartments;

    private List<Image> images;


    public AppartmentRecyclerAdapter(Context context, List<Appartment> appartments, List<Image> images){
        this.context = context;
        this.appartments = appartments;
        this.images = images;
    }



    @Override
    public AppartmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appartment_item, parent, false);

        return new AppartmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AppartmentViewHolder holder, int position) {

        Appartment appartment = appartments.get(position);

        holder.nameAppartment.setText(appartment.getRealEstateType());
        holder.addressAppartment.setText(appartment.getRealEstateAddress());
        holder.surfaceAppartment.setText(String.valueOf(appartment.getRealEstateLivingSpace()));
        holder.priceAppartment.setText(String.valueOf(appartment.getRealEstatePrice()));

        /*if (appartment.getRealEstateStatusInterestSchool() == 1){
            holder.pointsInterest.setText("School");
        } //else holder.pointsInterest.setText("None Info");

        if (appartment.getRealEstateStatusInterestMarket() == 1){
            holder.pointsInterest.setText(holder.pointsInterest.getText() + " Market ");
        } //else holder.pointsInterest.setText(holder.pointsInterest.getText() + " No Market");

        if (appartment.getRealEstateStatusInterestPark() == 1){
            holder.pointsInterest.setText(holder.pointsInterest.getText() + " Park ");
        } //else holder.pointsInterest.setText(holder.pointsInterest.getText() + " No Park");*/

        //Uri returnPicture = Uri.parse(appartment.getRealEstateURLImage());

        Image imageToShow = null;

        for(int i = 0; i<images.size();i++){
            if(images.get(i).getIdApartment() == appartment.getRealEstateId()){
                imageToShow = images.get(i);
            }
        }

        Log.i(TAG, "ImageToShow Log: " + imageToShow.getImgUrl());

        Glide.with(holder.picture)
                .load(Uri.parse(imageToShow.getImgUrl()))
                .into(holder.picture);

        /*byte[] bitmap = appartment.getRealEstateURLImage().getBytes();
        Bitmap image = BitmapFactory.decodeByteArray(bitmap, 0, bitmap.length);
        holder.picture.setImageBitmap(image);*/


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Add the personal page for apartment

                Appartment resultAppartmentToSend = appartments.get(holder.getAdapterPosition());
                Intent intent = new Intent(context, DetailApartment.class);
                intent.putExtra("apartmentId", resultAppartmentToSend.getRealEstateId());
                intent.putExtra("apartmentType", resultAppartmentToSend.getRealEstateType());
                intent.putExtra("apartmentAddress", resultAppartmentToSend.getRealEstateAddress());
                intent.putExtra("apartmentLivingSpace", resultAppartmentToSend.getRealEstateLivingSpace());
                intent.putExtra("apartmentPrice", resultAppartmentToSend.getRealEstatePrice());
                intent.putExtra("apartmentStatus", resultAppartmentToSend.getRealEstateStatus());
                intent.putExtra("apartmentDescription", resultAppartmentToSend.getRealEstateDescription());
                intent.putExtra("apartmentDatePutOnSale", resultAppartmentToSend.getRealEstateDateOfPutOnSale());
                intent.putExtra("apartmentNameAgent", resultAppartmentToSend.getRealEstateNameAgent());
                intent.putExtra("apartmentNumberPieces", resultAppartmentToSend.getRealEstateNumberOfPiecies());
                intent.putExtra("apartmentStatusInterestSchool", resultAppartmentToSend.getRealEstateStatusInterestSchool());
                intent.putExtra("apartmentStatusInterestMarket", resultAppartmentToSend.getRealEstateStatusInterestMarket());
                intent.putExtra("apartmentLat", resultAppartmentToSend.getRealEstateLat());
                intent.putExtra("apartmentLng", resultAppartmentToSend.getRealEstateLng());
                intent.putExtra("apartmentPicture", resultAppartmentToSend.getRealEstateURLImage());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

    }

    public void updateRecyclerViewAdapter(List<Appartment> appartmentList){
        this.appartments = appartmentList;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
            return appartments.size();
    }
}
