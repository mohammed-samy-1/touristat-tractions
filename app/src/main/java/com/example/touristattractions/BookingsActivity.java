package com.example.touristattractions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.touristattractions.classes.BookingsAdapter;
import com.example.touristattractions.classes.FeatureClass;
import com.example.touristattractions.db.BookingDbHelper;
import com.example.touristattractions.interfaces.RemoveBookingInterface;

import java.util.List;

public class BookingsActivity extends AppCompatActivity implements RemoveBookingInterface
{

    RecyclerView recyclerView;
    BookingsAdapter bookingsAdapter;
    List<FeatureClass> featureClasses;
    BookingDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);


        recyclerView = findViewById(R.id.attractions_list);

        dbHelper = new BookingDbHelper(this);
        dbHelper.open();
        featureClasses = dbHelper.query();

        bookingsAdapter = new BookingsAdapter(featureClasses, this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(bookingsAdapter);
    }

    @Override
    public void removeBooking(int pos)
    {
        dbHelper.delete(featureClasses.get(pos)
                                      .getAnIntId());
        featureClasses.remove(pos);
        bookingsAdapter.notifyItemRemoved(pos);
        Toast.makeText(BookingsActivity.this, getString(R.string.success), Toast.LENGTH_SHORT)
             .show();
    }

    @Override
    protected void onDestroy()
    {
        dbHelper.close();
        super.onDestroy();
    }

}