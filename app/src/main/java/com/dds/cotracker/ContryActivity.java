package com.dds.cotracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.Toast;

import com.dds.cotracker.api.ApiUtility;
import com.dds.cotracker.api.ContryData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<ContryData> list;
    private ProgressDialog pd;
    private ContryAdapter adapter;
    private EditText Serchbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contry);

        recyclerView = findViewById(R.id.contryrecy);
        Serchbar =  findViewById(R.id.serchContry);
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

         adapter = new ContryAdapter(this,list);
        recyclerView.setAdapter(adapter);


        pd = new ProgressDialog(this);
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();

        ApiUtility.getApiInterface().getcountryData().enqueue(new Callback<List<ContryData>>() {
            @Override
            public void onResponse(Call<List<ContryData>> call, Response<List<ContryData>> response) {
                list.addAll(response.body());
                adapter.notifyDataSetChanged();
                pd.dismiss();
            }

            @Override
            public void onFailure(Call<List<ContryData>> call, Throwable t) {
                Toast.makeText(ContryActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        Serchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    private void filter(String toString) {
        List<ContryData> filterlist = new ArrayList<>();
        for(ContryData items : list){
            if(items.getCountry().toLowerCase().contains(toString.toLowerCase())){
                filterlist.add(items);


            }
        }
        adapter.filterlist(filterlist);
    }
}