package com.example.touristattractions;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.touristattractions.api.SecondApiCall;
import com.example.touristattractions.classes.AttractionClass;
import com.example.touristattractions.db.BookingDbHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailsActivity extends AppCompatActivity
{

    private ProgressDialog progressDialog;
    String API_KEY = "5ae2e3f221c38a28845f05b6ae86057440a1ca4b6bffca68c6b7dd1a";
    BookingDbHelper dbHelper;
    String xid;
    TextView nameTv, referenceTv, addressTv, descriptionTv;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        nameTv = findViewById(R.id.nameTv);
        referenceTv = findViewById(R.id.referenceTv);
        addressTv = findViewById(R.id.addressTv);
        descriptionTv = findViewById(R.id.descrptionTv);
        imageView = findViewById(R.id.imageView);

        dbHelper = new BookingDbHelper(this);
        dbHelper.open();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.loading));

        xid = getIntent().getStringExtra("xid");

        progressDialog.show();
        searchAttraction();
    }

    private void searchAttraction() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.opentripmap.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SecondApiCall service = retrofit.create(SecondApiCall.class);
        Call<AttractionClass> call = service.searchAttraction(xid, API_KEY);

        call.enqueue(new Callback<AttractionClass>() {
            @Override
            public void onResponse(@NonNull Call<AttractionClass> call, @NonNull Response<AttractionClass> response) {
                if (response.isSuccessful()) {
                    AttractionClass apiResponse = response.body();
                    if (apiResponse != null) {
                        nameTv.setText("Name" + apiResponse.getName());
                        referenceTv.setText("Reference: " +  apiResponse.getWikidata());
                        AttractionClass.Address address = apiResponse.getAddress();
                        addressTv.setText("Address: "+address.getPostcode()+", "
                                +address.getVillage()+", "
                                +address.getSuburb()+", "
                                +address.getState()+", "
                                +address.getCountry()
                                         );
                        descriptionTv.setText(apiResponse.getWikipedia_extracts().getText());

                        Glide.with(DetailsActivity.this).load(apiResponse.getPreview().getSource()).into(imageView);

                        Toast.makeText(DetailsActivity.this, "success", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("TAG", "API Error: " + response.message());
                    Toast.makeText(DetailsActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(@NonNull Call<AttractionClass> call, @NonNull Throwable t) {
                Log.d("TAG", "API Error: " + t.getMessage());
                Toast.makeText(DetailsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    @Override
    protected void onDestroy()
    {
        dbHelper.close();
        super.onDestroy();
    }

}