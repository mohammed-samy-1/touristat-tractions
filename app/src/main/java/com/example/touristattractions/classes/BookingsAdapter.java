package com.example.touristattractions.classes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.touristattractions.DetailsActivity;
import com.example.touristattractions.R;
import com.example.touristattractions.interfaces.RemoveBookingInterface;

import java.util.List;

public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.ViewHolder> {

    private List<FeatureClass> featureClassList;
    Context context;
    RemoveBookingInterface removeBookingInterface;
    FeatureClass place;

    public BookingsAdapter(List<FeatureClass> featureClassList, RemoveBookingInterface removeBookingInterface, Context context)
    {
        this.featureClassList = featureClassList;
        this.removeBookingInterface = removeBookingInterface;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                                      .inflate(R.layout.bookings_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        place = featureClassList.get(position);
        holder.textHeadline.setText("Name: " + place.getProperties().getName());
        holder.textRate.setText("Rate: " + place.getProperties().getRate());

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                place = featureClassList.get(holder.getAdapterPosition());
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("xid", place.getProperties().getXid());
                context.startActivity(intent);
            }
        });

        holder.bookingBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                removeBookingInterface.removeBooking(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
//        if (featureClassList.size() == 0)
//        {
//            Toast.makeText(context, "No data", Toast.LENGTH_SHORT)
//                 .show();
//        }
        return featureClassList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textHeadline;
        public TextView textRate;
        Button bookingBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            textHeadline = itemView.findViewById(R.id.text_headline);
            textRate = itemView.findViewById(R.id.text_rate);
            bookingBtn = itemView.findViewById(R.id.cancelBookingBtn);
        }
    }
}

