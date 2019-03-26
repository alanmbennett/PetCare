package com.alanmbennett.petcare;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class GalleryRecyclerViewAdapter extends RecyclerView.Adapter<GalleryRecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<String> mData;

    public GalleryRecyclerViewAdapter(Context mContext, ArrayList<String> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public GalleryRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater mInflator = LayoutInflater.from(mContext);
        view = mInflator.inflate(R.layout.cardview_image, viewGroup, false);

        return new GalleryRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryRecyclerViewAdapter .MyViewHolder myViewHolder, final int i) {
        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PetProfileActivity.class);

                //passing data to the item profile
                intent.putExtra("Thumbnail", mData.get(i));
                //start the activity
                mContext.startActivity(intent);
            }
        });
        //Set Click Listener for card view

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView img_item_thumbnail;
        CardView cardView;


        public MyViewHolder(View itemView){
            super(itemView);

            img_item_thumbnail = (ImageView) itemView.findViewById(R.id.gallery_img_id);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
        }
    }
}

