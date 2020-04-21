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

public class MainInfoContentCardsAdapter extends RecyclerView.Adapter<MainInfoContentCardsAdapter.ViewHolder>
{
    ArrayList<MainInfoContentCards> records;
    CardClicked activity1;
    public interface CardClicked
    {
        void totalCasesCardClicked();
    }

    public MainInfoContentCardsAdapter(Context context,ArrayList<MainInfoContentCards> records)
    {
        this.records = records;
        activity1=(CardClicked) context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvFirst,tvSecond;
        ImageView ivLogo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFirst=itemView.findViewById(R.id.tvFirst);
            tvSecond=itemView.findViewById(R.id.tvSecond);
            ivLogo=itemView.findViewById(R.id.ivLogo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    if(records.indexOf((MainInfoContentCards)view.getTag())==0)
                    {
                        activity1.totalCasesCardClicked();
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public MainInfoContentCardsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.initial_cards_view,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MainInfoContentCardsAdapter.ViewHolder holder, int position)
    {
        holder.itemView.setTag(records.get(position));
        if(position==0)
        {
            holder.tvFirst.setText("Total Cases");
            holder.tvSecond.setText(records.get(position).getLatestValue());
            holder.ivLogo.setImageResource(R.drawable.coronavirus);
        }
        else if(position==1)
        {
            holder.tvFirst.setText("Total Deaths");
            holder.tvSecond.setText(records.get(position).getLatestValue());
            holder.ivLogo.setImageResource(R.drawable.death);
        }
        else if(position==2)
        {
            holder.tvFirst.setText(" Recovered");
            holder.tvSecond.setText(records.get(position).getLatestValue());
            holder.ivLogo.setImageResource(R.drawable.recoveries);
        }
        else
        {
            holder.tvFirst.setText("Last Updated");
            holder.tvSecond.setText(records.get(position).getLatestValue());
            holder.ivLogo.setImageResource(R.drawable.newcases);
        }


    }

    @Override
    public int getItemCount() {
        return records.size();
    }
}
