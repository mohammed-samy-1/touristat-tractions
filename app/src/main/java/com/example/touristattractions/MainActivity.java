package com.example.touristattractions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.touristattractions.api.FirstApiCall;
import com.example.touristattractions.classes.AttractionsAdapter;
import com.example.touristattractions.classes.FeatureClass;
import com.example.touristattractions.api.FirstResponse;
import com.example.touristattractions.db.BookingDbHelper;
import com.example.touristattractions.interfaces.MakeBookingInterface;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements MakeBookingInterface
{
    RecyclerView recyclerView;
    AttractionsAdapter attractionsAdapter;
    List<FeatureClass> featureClassList;
    private ProgressDialog progressDialog;
    String API_KEY = "5ae2e3f221c38a28845f05b6ae86057440a1ca4b6bffca68c6b7dd1a";
    BookingDbHelper dbHelper;
    double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.attractions_list);
        Intent intent = getIntent();
        latitude = intent.getDoubleExtra("lat", 52.768487);
        longitude = intent.getDoubleExtra("long", -1.834577);

        dbHelper = new BookingDbHelper(this);
        dbHelper.open();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading));

        featureClassList = new ArrayList<>();
        attractionsAdapter = new AttractionsAdapter(featureClassList, this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(attractionsAdapter);

        progressDialog.show();
        searchAttractions(latitude, longitude);
    }

    private void searchAttractions(double lat,double lng) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.opentripmap.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        FirstApiCall service = retrofit.create(FirstApiCall.class);
        Call<FirstResponse> call = service.searchAttractions(String.valueOf((lng - 0.70)), String.valueOf((lat-1.10)), String.valueOf((lng + 0.70)), String.valueOf((lat+1.10)), API_KEY);

        call.enqueue(new Callback<FirstResponse>() {
            @Override
            public void onResponse(@NonNull Call<FirstResponse> call, @NonNull Response<FirstResponse> response) {
                if (response.isSuccessful()) {
                    FirstResponse apiResponse = response.body();
                    if (apiResponse != null) {
                        List<FeatureClass> attractionItems = apiResponse.getFeatures();
                        featureClassList.clear();
                        featureClassList.addAll(attractionItems);
                        attractionsAdapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, "success", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("TAG", "API Error: " + response.message());
                    Toast.makeText(MainActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<FirstResponse> call, @NonNull Throwable t) {
                Log.d("TAG", "API Error: " + t.getMessage());
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void makeBooking(int pos)
    {
        dbHelper.insert(featureClassList.get(pos));
        Toast.makeText(MainActivity.this, getString(R.string.success), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy()
    {
        dbHelper.close();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId() == R.id.booking_option)
        {
            Intent intent = new Intent(MainActivity.this, BookingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }





}