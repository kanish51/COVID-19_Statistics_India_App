package com.example.covid_19statistics;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StateDataActivity extends AppCompatActivity implements StateListItemAdapter.ItemClicked
{
    RecyclerView recyclerView;
    RecyclerView.Adapter myadapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<StateListItem> list;
    ActionBar actionbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionbar=getSupportActionBar();
        actionbar.setTitle("Detailed Info");
        actionbar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_state_data);
        recyclerView=findViewById(R.id.rvStateList);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        list=new ArrayList<StateListItem>();
        try {
            JSONArray jsonArr = new JSONArray(getIntent().getStringExtra("StatewiseData"));
            JSONObject temp=new JSONObject();
            for(int i=1;i<jsonArr.length();i++)
            {
                temp=jsonArr.getJSONObject(i);
                list.add(new StateListItem(temp.getString("state"),temp.getString("confirmed"),temp.getString("active"),temp.getString("deaths")));
            }
            temp=jsonArr.getJSONObject(1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        myadapter=new StateListItemAdapter(this,list);
        recyclerView.setAdapter(myadapter);

    }

    @Override
    public void OnItemClicked(String stateName)
    {
        Intent intent=new Intent(this,com.example.covid_19statistics.DistrictDataActivity.class);
        intent.putExtra("stateclicked",stateName);
        startActivity(intent);
    }
}
