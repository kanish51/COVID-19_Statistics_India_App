package com.example.covid_19statistics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DistrictListItemAdapter extends RecyclerView.Adapter<DistrictListItemAdapter.ViewHolder>
{
    ArrayList<DistrictListItem> records;


    public DistrictListItemAdapter(Context context, ArrayList<DistrictListItem> records)
    {
        this.records = records;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView tvDistrictName,tvDistrictCases;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDistrictName=itemView.findViewById(R.id.tvDistrictName);
            tvDistrictCases=itemView.findViewById(R.id.tvDistrictCases);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {

                }
            });
        }
    }

    @NonNull
    @Override
    public DistrictListItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.districtlist_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DistrictListItemAdapter.ViewHolder holder, int position)
    {
        //holder.itemView.setTag(records.get(position));
        holder.tvDistrictName.setText(records.get(position).getDistrictName());
        holder.tvDistrictCases.setText(records.get(position).getNoOfCases());

    }

    @Override
    public int getItemCount() {
        return records.size();
    }
}

