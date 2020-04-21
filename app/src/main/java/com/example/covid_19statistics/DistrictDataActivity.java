package com.example.covid_19statistics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DistrictDataActivity extends AppCompatActivity {

    private String TAG = DistrictDataActivity.class.getSimpleName();
    ActionBar actionBar;
    RecyclerView recyclerView;
    RecyclerView.Adapter myadapter;
    RecyclerView.LayoutManager layoutManager;
    CardView cvColumnHeadingDistrict;
    ProgressBar progressBar;
    ArrayList<DistrictListItem> record;
    Boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district_data);
        recyclerView=findViewById(R.id.rvDistrictList);
        progressBar=findViewById(R.id.pgBar);
        actionBar=getSupportActionBar();
        actionBar.setTitle("Detailed Info");
        actionBar.setDisplayHomeAsUpEnabled(true);
        recyclerView.setVisibility(View.GONE);
        cvColumnHeadingDistrict=findViewById(R.id.cvColumnHeadingDistrict);
        cvColumnHeadingDistrict.setVisibility(View.GONE);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        RetrieveDistrictCovidData async=new RetrieveDistrictCovidData();
        async.execute();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if(item.getItemId()==android.R.id.home)
        {
            onBackPressed();
            return true;
        }
        return false;
    }

    public class RetrieveDistrictCovidData extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            flag=false;
            HttpHandler sh1 = new HttpHandler();
            String url1 = "https://api.covid19india.org/v2/state_district_wise.json";
            String jsonstr = sh1.makeServiceCall(url1);
            if (jsonstr != null)
            {
                try
                {
                    record=new ArrayList<DistrictListItem>();
                    JSONArray jsonArray=new JSONArray(jsonstr);
                    JSONObject jsonObject=new JSONObject();
                    String stateClicked=getIntent().getStringExtra("stateclicked");
                    for (int i=0;i<jsonArray.length();i++)
                    {
                        jsonObject=jsonArray.getJSONObject(i);
                        String temp=jsonObject.getString("state");
                        if(stateClicked.equals(temp))
                        {
                            JSONArray arr=new JSONArray();
                            arr=jsonObject.getJSONArray("districtData");
                            JSONObject tempjsonobj=new JSONObject();
                            for(int j=0;j<arr.length();j++)
                            {
                                tempjsonobj=arr.getJSONObject(j);
                                record.add(new DistrictListItem(tempjsonobj.getString("district"),tempjsonobj.getString("confirmed")));
                            }
                            break;
                        }
                    }
                    flag=true;

                } catch (JSONException e)
                {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    flag=false;
                }
                return null;

            }
            else
            {
                flag=false;
                return null;
            }
        }
        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            if(flag)
            {
                myadapter=new DistrictListItemAdapter(DistrictDataActivity.this,record);
                recyclerView.setAdapter(myadapter);
                recyclerView.setVisibility(View.VISIBLE);
                cvColumnHeadingDistrict.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
            else {

                Toast.makeText(DistrictDataActivity.this,"Something went wrong!",Toast.LENGTH_SHORT).show();
                finish();
            }

        }
    }

}
