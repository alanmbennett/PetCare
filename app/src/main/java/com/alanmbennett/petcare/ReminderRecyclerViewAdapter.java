package com.alanmbennett.petcare;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;



import java.util.ArrayList;

public class ReminderRecyclerViewAdapter extends RecyclerView.Adapter<ReminderRecyclerViewAdapter.MyViewHolder>{

    private Context mContext;
    private ArrayList<Reminder> mData;

    public ReminderRecyclerViewAdapter(Context mContext, ArrayList<Reminder> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public ReminderRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater mInflator = LayoutInflater.from(mContext);
        view = mInflator.inflate(R.layout.cardview_image, viewGroup, false);

        return new ReminderRecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReminderRecyclerViewAdapter.MyViewHolder myViewHolder, final int i) {

        Log.d("mData", mData.get(i).getTime());
        myViewHolder.tv_RTime.setText(mData.get(i).getTime());
        myViewHolder.tv_RReocurring.setText(mData.get(i).getReoccuring());
        myViewHolder.tv_RPetName.setText(mData.get(i).getPetName());
        myViewHolder.tv_RDescription.setText(mData.get(i).getDescription());
        myViewHolder.tv_RName.setText(mData.get(i).getTitle());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_RName, tv_RDescription, tv_RReocurring, tv_RPetName, tv_RTime;
        CardView cardView;


        public MyViewHolder(View itemView){
            super(itemView);
            tv_RPetName = (TextView) itemView.findViewById(R.id.rPet);
            tv_RName = (TextView) itemView.findViewById(R.id.rName);
            tv_RTime = (TextView) itemView.findViewById(R.id.rTime);
            tv_RDescription = (TextView) itemView.findViewById(R.id.rDescription);
            tv_RReocurring = (TextView) itemView.findViewById(R.id.rRecurring);
            cardView = (CardView) itemView.findViewById(R.id.rCard);
        }
    }

}
