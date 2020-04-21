package com.example.covid_19statistics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainInfoContentCardsAdapter.CardClicked{

    private String TAG = MainActivity.class.getSimpleName();
    //TextView tvChartTitle;
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    ProgressBar progressBar;
    CardView cvMoreInfo,cvChartCard;
    LineChart mpLineChart;
    LineDataSet lineDataSet1;
    ArrayList<ILineDataSet> dataSets;
    LineData data;
    String rawStateData;
    ActionBar actionBar;
    RetrieveCovidData async;
    IMarker marker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionBar=getSupportActionBar();
        actionBar.setTitle("COVID-19 India");
        progressBar=findViewById(R.id.progressBar);
        cvChartCard=findViewById(R.id.cvChartCard);
        cvMoreInfo=findViewById(R.id.cvMoreInfo);
        mpLineChart=findViewById(R.id.gvMain);
        recyclerView=findViewById(R.id.myrecyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager=new GridLayoutManager(MainActivity.this,2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        marker = new MyGraphMarker(MainActivity.this,R.layout.marker_view);
        RefreshFunc();

    }
    public void RefreshFunc()
    {
        recyclerView.setVisibility(View.GONE);
        cvChartCard.setVisibility(View.GONE);
        cvMoreInfo.setVisibility(View.GONE);
        async=new RetrieveCovidData();
        async.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.refresh:
                RefreshFunc();
                break;
            case R.id.about:
                Toast.makeText(MainActivity.this,"Developed by Kanish",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void startStateDataActivity(View view)
    {
        Intent intent=new Intent(MainActivity.this,com.example.covid_19statistics.StateDataActivity.class);
        intent.putExtra("StatewiseData",rawStateData);
        startActivity(intent);
    }

    @Override
    public void totalCasesCardClicked()
    {
        Intent intent1=new Intent(MainActivity.this,com.example.covid_19statistics.StateDataActivity.class);
        intent1.putExtra("StatewiseData",rawStateData);
        startActivity(intent1);
    }

    private class RetrieveCovidData extends AsyncTask<Void, Void,ArrayList<MainInfoContentCards>>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected ArrayList<MainInfoContentCards> doInBackground(Void... arg0)
        {
            HttpHandler sh = new HttpHandler();
            String url = "https://api.covid19india.org/data.json";
            String jsonStr = sh.makeServiceCall(url);
            if (jsonStr != null)
            {
                try
                {
                    ArrayList<MainInfoContentCards> mainList;
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray statewisedataArray = jsonObj.getJSONArray("statewise");
                    rawStateData=jsonObj.getString("statewise");
                    JSONArray casesdataArray = jsonObj.getJSONArray("cases_time_series");
                    JSONObject Totalobj=statewisedataArray.getJSONObject(0);
                    mainList=new ArrayList<MainInfoContentCards>();
                    mainList.add(new MainInfoContentCards(Totalobj.getString("confirmed")));
                    mainList.add(new MainInfoContentCards(Totalobj.getString("deaths")));
                    mainList.add(new MainInfoContentCards(Totalobj.getString("recovered")));
                    String str=Totalobj.getString("lastupdatedtime");
                    String[] splitStr = str.split("\\s+");
                    mainList.add(new MainInfoContentCards(splitStr[1]));
                    int noOfPoints=casesdataArray.length();
                    JSONObject holdsPointData;
                    String temp;
                    ArrayList<Entry> dataVals=new ArrayList<Entry>();
                    for(int i=0;i<noOfPoints;i++)
                    {
                        holdsPointData=casesdataArray.getJSONObject(i);
                        temp=holdsPointData.getString("totalconfirmed");
                        dataVals.add(new Entry(i+1,Float.parseFloat(temp)));
                    }
                    lineDataSet1=new LineDataSet(dataVals,"No of deaths");
                    dataSets=new ArrayList<ILineDataSet>();
                    dataSets.add(lineDataSet1);
                    data=new LineData(dataSets);
                    return mainList;

                } catch (final JSONException e)
                {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    Toast.makeText(MainActivity.this,"Json parsing error!",Toast.LENGTH_LONG).show();
                    ArrayList<MainInfoContentCards> templist=new ArrayList<MainInfoContentCards>();
                    templist.clear();
                    return templist;
                }

            }
            ArrayList<MainInfoContentCards> templist=new ArrayList<MainInfoContentCards>();
            templist.clear();
            return templist;
        }

        @Override
        protected void onPostExecute(ArrayList<MainInfoContentCards> result) {
            super.onPostExecute(result);

            if(result.isEmpty())
            {
                Log.i("Got IT!","No internet");
                AlertDialog.Builder alert=new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Alert!");
                alert.setMessage("Something went wrong! Try again later...");
                alert.setCancelable(false);
                alert.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        MainActivity.this.finish();
                        System.exit(0);
                    }
                });
                alert.create().show();
            }
            else
            {
                myAdapter=new MainInfoContentCardsAdapter(MainActivity.this,result);
                recyclerView.setAdapter(myAdapter);
                mpLineChart.setData(data);
                mpLineChart.fitScreen();
                mpLineChart.invalidate();
                mpLineChart.getAxisRight().setEnabled(false);
                mpLineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                mpLineChart.getDescription().setEnabled(false);
                mpLineChart.getLegend().setEnabled(false);
                lineDataSet1.setDrawCircles(false);
                lineDataSet1.setLineWidth(5f);
                lineDataSet1.setDrawValues(false);
                mpLineChart.setTouchEnabled(true);
                mpLineChart.setDragEnabled(true);
                mpLineChart.setDrawGridBackground(false);
                mpLineChart.setPinchZoom(true);
                mpLineChart.setMarker(marker);
                recyclerView.setVisibility(View.VISIBLE);
                cvMoreInfo.setVisibility(View.VISIBLE);
                cvChartCard.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }

        }
    }
}
