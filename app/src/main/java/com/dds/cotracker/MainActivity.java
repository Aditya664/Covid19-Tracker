package com.dds.cotracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dds.cotracker.api.ApiUtility;
import com.dds.cotracker.api.ContryData;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private TextView totalConfirem, totalDeath, totalActive, totalTests, totalRecoverd;
    private TextView todayConfim, todayRecoverd, todayDeath, date;
    private PieChart pieChart;

    private List<ContryData> list;
    String country = "India";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = new ArrayList<>();

        if (getIntent().getStringExtra("country") != null)
            country = getIntent().getStringExtra("country");

        init();
        TextView cname = findViewById(R.id.seenName);
        cname.setText(country);
        cname.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ContryActivity.class)));

        ApiUtility.getApiInterface().getcountryData()
                .enqueue(new Callback<List<ContryData>>() {
                    @Override
                    public void onResponse(Call<List<ContryData>> call, Response<List<ContryData>> response) {
                        list.addAll(response.body());

                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getCountry().equals((country))) {
                                int confirm = Integer.parseInt(list.get(i).getCases());
                                int active = Integer.parseInt(list.get(i).getActive());
                                int recovered = Integer.parseInt(list.get(i).getRecovered());
                                int death = Integer.parseInt(list.get(i).getDeaths());

                                totalActive.setText(NumberFormat.getInstance().format(confirm));
                                todayConfim.setText(NumberFormat.getInstance().format(active));
                                totalRecoverd.setText(NumberFormat.getInstance().format(recovered));
                                totalDeath.setText(NumberFormat.getInstance().format(death));

                                todayDeath.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayDeaths())));
                                todayConfim.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayCases())));
                                todayRecoverd.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTodayRecovered())));
                                totalTests.setText(NumberFormat.getInstance().format(Integer.parseInt(list.get(i).getTests())));

                                setText(list.get(i).getUpdated());

                                pieChart.addPieSlice(new PieModel("Confirm", confirm, getResources().getColor(R.color.yellow)));
                                pieChart.addPieSlice(new PieModel("Active", active, getResources().getColor(R.color.blue_pie)));
                                pieChart.addPieSlice(new PieModel("Recovered", recovered, getResources().getColor(R.color.green_pie)));
                                pieChart.addPieSlice(new PieModel("Death", death, getResources().getColor(R.color.red_pie)));


                                pieChart.startAnimation();

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ContryData>> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Error :" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setText(String updated) {
        DateFormat format = new SimpleDateFormat("MMM dd, yyyy");


        long milisecond = Long.parseLong(updated);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milisecond);
        date.setText("Updated at  " + format.format(calendar.getTime()));

    }

    private void init() {
        totalConfirem = findViewById(R.id.TotalConfirm);
        totalActive = findViewById(R.id.TotalActive);
        totalDeath = findViewById(R.id.TotalDeath);
        totalTests = findViewById(R.id.Totaltest);
        totalRecoverd = findViewById(R.id.Totalrecoverd);
        todayConfim = findViewById(R.id.TodayConfir);
        todayRecoverd = findViewById(R.id.Toadyrecovr);
        todayDeath = findViewById(R.id.TodayDeath);
        pieChart = findViewById(R.id.piechart);
        date = findViewById(R.id.date);

    }
}