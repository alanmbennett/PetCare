package com.alanmbennett.petcare;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private List<Pet> mData;

    public RecyclerViewAdapter(Context mContext, List<Pet> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater mInflator = LayoutInflater.from(mContext);
        view = mInflator.inflate(R.layout.cardview_pet, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.tv_item_title.setText(mData.get(i).getName());
        myViewHolder.img_item_thumbnail.setImageResource(mData.get(i).getThumbnail());
        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, PetProfileActivity.class);

                //passing data to the item profile
                intent.putExtra("Title", mData.get(i).getName());
                intent.putExtra("Age", mData.get(i).getAge());
                intent.putExtra("Weight", mData.get(i).getWeight());
                intent.putExtra("Thumbnail", mData.get(i).getThumbnail());
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

        TextView tv_item_title;
        ImageView img_item_thumbnail;
        CardView cardView;


        public MyViewHolder(View itemView){
            super(itemView);

            tv_item_title = (TextView) itemView.findViewById(R.id.pet_title_id);
            img_item_thumbnail = (ImageView) itemView.findViewById(R.id.pet_img_id);
            cardView = (CardView) itemView.findViewById(R.id.cardview_id);
        }
    }
}
