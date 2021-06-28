package com.dds.cotracker;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dds.cotracker.api.ContryData;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ContryAdapter extends RecyclerView.Adapter<ContryAdapter.ConrtyViewHolder> {
    private Context context;
    private List<ContryData> list;

    public ContryAdapter(Context context, List<ContryData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ConrtyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contryitemcayout,parent,false);
        return new ConrtyViewHolder(view);

    }
    void filterlist(List<ContryData> filterList){
        list = filterList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ContryAdapter.ConrtyViewHolder holder, int position) {
ContryData data = list.get(position);
holder.contrycase.setText(NumberFormat.getInstance().format(Integer.parseInt(data.getCases())));
holder.contryname.setText(data.getCountry());
holder.sno.setText(String.valueOf(position+1));



Map<String,String> img = data.getCountryInfo();
        Glide.with(context).load(img.get("flag")).into(holder.imageView);

        holder.itemView.setOnClickListener(v ->{
            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("country",data.getCountry());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ConrtyViewHolder extends RecyclerView.ViewHolder {

        private TextView sno,contryname,contrycase;
        private ImageView imageView;

        public ConrtyViewHolder(@NonNull View itemView) {
            super(itemView);

            sno = itemView.findViewById(R.id.sno);
            contryname = itemView.findViewById(R.id.coname);
            contrycase = itemView.findViewById(R.id.ContryCase);
            imageView = itemView.findViewById(R.id.contryflag);

        }
    }
}
