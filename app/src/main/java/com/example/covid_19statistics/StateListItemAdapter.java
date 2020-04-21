package com.example.covid_19statistics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class StateListItemAdapter extends RecyclerView.Adapter<StateListItemAdapter.ViewHolder>
{
    ArrayList<StateListItem> records;
    ItemClicked activity;
    public interface ItemClicked
    {
        void OnItemClicked(String stateName);
    }

    public StateListItemAdapter(Context context,ArrayList<StateListItem> records)
    {
        this.records = records;
        activity=(ItemClicked)context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView tvStateName,tvConfirmed,tvActive,tvDeaths;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStateName=itemView.findViewById(R.id.tvStateName);
            tvConfirmed=itemView.findViewById(R.id.tvConfirmed);
            tvActive=itemView.findViewById(R.id.tvActive);
            tvDeaths=itemView.findViewById(R.id.tvDeaths);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    activity.OnItemClicked(((StateListItem)view.getTag()).getStateName());
                }
            });
        }
    }

    @NonNull
    @Override
    public StateListItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.statelist_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StateListItemAdapter.ViewHolder holder, int position)
    {
        holder.itemView.setTag(records.get(position));
        holder.tvStateName.setText(records.get(position).getStateName());
        holder.tvConfirmed.setText(records.get(position).getConfirmedCases());
        holder.tvActive.setText(records.get(position).getActiveCases());
        holder.tvDeaths.setText(records.get(position).getDeathCases());

    }

    @Override
    public int getItemCount() {
        return records.size();
    }
}
