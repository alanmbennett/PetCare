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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BusinessRecyclerViewAdapter extends RecyclerView.Adapter<BusinessRecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<Business> mData;

    public BusinessRecyclerViewAdapter(Context mContext, ArrayList<Business> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public BusinessRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater mInflator = LayoutInflater.from(mContext);
        view = mInflator.inflate(R.layout.cardview_business, viewGroup, false);

        return new BusinessRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusinessRecyclerViewAdapter.MyViewHolder myViewHolder, final int i) {
        myViewHolder.tv_business_name.setText(mData.get(i).getName());
        myViewHolder.tv_business_address.setText(mData.get(i).getAddress());
        String imgUrl = "https://www.akc.org/wp-content/themes/akc/component-library/assets/img/welcome.jpg";
        //mData.get(i).getImage_url();
        Picasso.get().load(imgUrl).into(myViewHolder.img_item_thumbnail);
        //Set Click Listener for card view

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_business_name, tv_business_address;
        ImageView img_item_thumbnail;
        CardView cardView;


        public MyViewHolder(View itemView){
            super(itemView);
            tv_business_name = (TextView) itemView.findViewById(R.id.business_name);
            tv_business_address = (TextView) itemView.findViewById(R.id.business_address);
            img_item_thumbnail = (ImageView) itemView.findViewById(R.id.business_img);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
        }
    }

}
