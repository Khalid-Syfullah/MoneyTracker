package com.tars.moneytracker.ui.graphs;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.tars.moneytracker.R;
import com.tars.moneytracker.api.RestClient;
import com.tars.moneytracker.api.RetroInterface;
import com.tars.moneytracker.datamodel.GraphDataModel;
import com.tars.moneytracker.datamodel.StaticData;


import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.StackedBarChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.StackedBarModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GraphsFragment extends Fragment {


    private GraphsViewModel graphsViewModel;
    private GraphDataModel graphDataModel;
    private String overviewTimeline, categoryTimeline, creditsTimeline;
    private String timeline="daily", type="overview";
    ArrayList<String> dailyList,weeklyList,monthlyList;
    ArrayList<Float> amountList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        graphsViewModel =
                new ViewModelProvider(this).get(GraphsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_graphs, container, false);
        graphsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        setupBarChart(root);
        setupPieChart(root);

        timeline="daily";
        type="overview";



        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        calendar.set(year, month, day);

        dailyList = new ArrayList<>();
        calendar.add(Calendar.DATE, -4);
        dailyList.add(sdf.format(calendar.getTime().getTime()));
        calendar.add(Calendar.DATE, 1);
        dailyList.add(sdf.format(calendar.getTime().getTime()));
        calendar.add(Calendar.DATE, 1);
        dailyList.add(sdf.format(calendar.getTime().getTime()));
        calendar.add(Calendar.DATE, 1);
        dailyList.add(sdf.format(calendar.getTime().getTime()));
        calendar.add(Calendar.DATE, 1);
        dailyList.add(sdf.format(calendar.getTime().getTime()));
        calendar.add(Calendar.DATE, 1);
        dailyList.add(sdf.format(calendar.getTime().getTime()));


        calendar.set(year, month, day);

        weeklyList = new ArrayList<>();
        calendar.add(Calendar.DATE, -13);
        weeklyList.add(sdf.format(calendar.getTime().getTime()));

        for(int i=1;i<=14;i++){
            calendar.add(Calendar.DATE, 1);
            weeklyList.add(sdf.format(calendar.getTime().getTime()));
            calendar.add(Calendar.DATE, 1);
            weeklyList.add(sdf.format(calendar.getTime().getTime()));
        }


        calendar.set(year, month, day);

        monthlyList = new ArrayList<>();
        calendar.add(Calendar.DATE, -60);
        monthlyList.add(sdf.format(calendar.getTime().getTime()));

        for(int i=1;i<=61;i++){
            calendar.add(Calendar.DATE, 1);
            monthlyList.add(sdf.format(calendar.getTime().getTime()));
            calendar.add(Calendar.DATE, 1);
            monthlyList.add(sdf.format(calendar.getTime().getTime()));
        }
        //setupStackedBarChart(root);

        return root;
    }



    private void setupBarChart(View view)
    {

        BarChart mBarChart = view.findViewById(R.id.graphs_overview_barchart);
        Spinner timelineSpinner=view.findViewById(R.id.graphs_overview_spinner);
        overviewTimeline = timelineSpinner.getSelectedItem().toString();

        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        if(overviewTimeline.equals("Daily")){
            timeline = "daily";
        }
        else if(overviewTimeline.equals("Weekly")){
            timeline = "weekly";
        }
        else if(overviewTimeline.equals("Monthly")){
            timeline = "monthly";
        }

        type = "overview";

        timelineSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                overviewTimeline = timelineSpinner.getSelectedItem().toString();
                if(overviewTimeline.equals("Daily")){
                    timeline = "daily";
                }
                else if(overviewTimeline.equals("Weekly")){
                    timeline = "weekly";
                }
                else if(overviewTimeline.equals("Monthly")){
                    timeline = "monthly";
                }

                if(overviewTimeline.equals("Daily")){

                    mBarChart.clearChart();

                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM");
                    calendar.set(year, month, day);

                    RetroInterface retroInterface = RestClient.createRestClient();
                    Call<GraphDataModel> call = retroInterface.getGraphOverviewData(new GraphDataModel(timeline,type, StaticData.LoggedInUserEmail, dailyList, weeklyList, monthlyList));
                    call.enqueue(new Callback<GraphDataModel>() {
                        @Override
                        public void onResponse(Call<GraphDataModel> call, Response<GraphDataModel> response) {
                            if(response.isSuccessful()){
                                graphDataModel = response.body();

                                amountList = new ArrayList<>();
                                amountList = graphDataModel.getDailyOverviewSpendingAmount();

                                calendar.add(Calendar.DATE, -4);
                                mBarChart.addBar(new BarModel(sdf.format(calendar.getTime().getTime()),amountList.get(0), 0xDD123456));
                                calendar.add(Calendar.DATE, 1);
                                mBarChart.addBar(new BarModel(sdf.format(calendar.getTime().getTime()),amountList.get(1), 0x3356B7F1));
                                calendar.add(Calendar.DATE, 1);
                                mBarChart.addBar(new BarModel(sdf.format(calendar.getTime().getTime()),amountList.get(2), 0xBC167456));
                                calendar.add(Calendar.DATE, 1);
                                mBarChart.addBar(new BarModel(sdf.format(calendar.getTime().getTime()),amountList.get(3), 0xDDDDDDDD));
                                calendar.add(Calendar.DATE, 1);
                                mBarChart.addBar(new BarModel(sdf.format(calendar.getTime().getTime()),amountList.get(4), 0xFF56B7F1));
                                calendar.add(Calendar.DATE, 1);
                                mBarChart.addBar(new BarModel("Today",amountList.get(5), 0xFFCDA67F));

                                mBarChart.startAnimation();


                            }
                            else{
                                Toast.makeText(getContext(),"No response from server!",Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<GraphDataModel> call, Throwable t) {
                            Toast.makeText(getContext(),"No Retrofit Connection!",Toast.LENGTH_SHORT).show();

                        }
                    });



                }
                else if(overviewTimeline.equals("Weekly")){

                    RetroInterface retroInterface = RestClient.createRestClient();
                    Call<GraphDataModel> call = retroInterface.getGraphOverviewData(new GraphDataModel(timeline,type, StaticData.LoggedInUserEmail, dailyList, weeklyList, monthlyList));
                    call.enqueue(new Callback<GraphDataModel>() {
                        @Override
                        public void onResponse(Call<GraphDataModel> call, Response<GraphDataModel> response) {
                            if(response.isSuccessful()){
                                graphDataModel = response.body();

                                amountList = new ArrayList<>();
                                amountList = graphDataModel.getWeeklyOverviewSpendingAmount();

                                mBarChart.clearChart();

                                float lastWeek=0, thisWeek=0;

                                for(int i=0;i<7;i++){
                                    lastWeek += amountList.get(i);
                                }
                                for(int i=7;i<14;i++){
                                    thisWeek += amountList.get(i);
                                }

                                mBarChart.addBar(new BarModel("Last Week",lastWeek, 0xFF56B7F1));
                                mBarChart.addBar(new BarModel("This Week",thisWeek, 0xEE1FF4AC));

                                mBarChart.startAnimation();


                            }
                            else{
                                Toast.makeText(getContext(),"No response from server!",Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<GraphDataModel> call, Throwable t) {
                            Toast.makeText(getContext(),"No Retrofit Connection!",Toast.LENGTH_SHORT).show();

                        }
                    });



                }
                else if(overviewTimeline.equals("Monthly")){

                    RetroInterface retroInterface = RestClient.createRestClient();
                    Call<GraphDataModel> call = retroInterface.getGraphOverviewData(new GraphDataModel(timeline,type, StaticData.LoggedInUserEmail, dailyList, weeklyList, monthlyList));
                    call.enqueue(new Callback<GraphDataModel>() {
                        @Override
                        public void onResponse(Call<GraphDataModel> call, Response<GraphDataModel> response) {
                            if(response.isSuccessful()){
                                graphDataModel = response.body();

                                amountList = new ArrayList<>();
                                amountList = graphDataModel.getMonthlyOverviewSpendingAmount();

                                mBarChart.clearChart();

                                SimpleDateFormat sdf = new SimpleDateFormat("MMM");
                                calendar.set(year, month, day);

                                float thisMonth=0,lastMonth=0;

                                for(int i=0;i<=31;i++){
                                    thisMonth += amountList.get(i);
                                }

                                for(int i=31;i<=62;i++){
                                    lastMonth += amountList.get(i);
                                }
                                calendar.add(Calendar.MONTH, -1);
                                mBarChart.addBar(new BarModel(sdf.format(calendar.getTime().getTime()),thisMonth, 0xBC167456));
                                calendar.add(Calendar.MONTH, 1);
                                mBarChart.addBar(new BarModel(sdf.format(calendar.getTime().getTime()),lastMonth, 0x325377DD));

                                mBarChart.startAnimation();




                            }
                            else{
                                Toast.makeText(getContext(),"No response from server!",Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<GraphDataModel> call, Throwable t) {
                            Toast.makeText(getContext(),"No Retrofit Connection!",Toast.LENGTH_SHORT).show();

                        }
                    });



                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }


    private void setupPieChart(View v){

        PieChart pieChart = v.findViewById(R.id.graphs_category_piechart);
        Spinner categorySpinner = v.findViewById(R.id.graphs_category_spinner);
        categoryTimeline = categorySpinner.getSelectedItem().toString();


        float time[] = {55, 95, 30 , 360 - (55+95+30)};
        String activity[] ={"Jan","Feb","March",""};

        //pupulating list of PieEntires
        List<PieEntry> pieEntires = new ArrayList<>();
        for( int i = 0 ; i<time.length;i++){
            pieEntires.add(new PieEntry(time[i],activity[i]));
        }
        PieDataSet dataSet = new PieDataSet(pieEntires,"");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        PieData data = new PieData(dataSet);
        //Get the chart
        pieChart.setData(data);
        pieChart.invalidate();
        pieChart.setCenterText("50% \n ");
        pieChart.setDrawEntryLabels(false);
        pieChart.setContentDescription("");
        //pieChart.setDrawMarkers(true);
        //pieChart.setMaxHighlightDistance(34);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setHoleRadius(75);

        //legend attributes
        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setTextSize(12);
        legend.setFormSize(20);
        legend.setFormToTextSpace(2);

        pieChart.animateXY(1000,1000);

    }
/*
    private void setupStackedBarChart(View view){
        StackedBarChart mStackedBarChart = (StackedBarChart) view.findViewById(R.id.graphs_monthly_stackedbarchart);


        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        calendar.set(year, month, day);
        String date = sdf.format(calendar.getTime());

        StackedBarModel s1 = new StackedBarModel("12.4");

        s1.addBar(new BarModel(2.3f, 0xFF63CBB0));
        s1.addBar(new BarModel(2.3f, 0xFF56B7F1));
        s1.addBar(new BarModel(2.3f, 0xFFCDA67F));

        StackedBarModel s2 = new StackedBarModel("13.4");
        s2.addBar(new BarModel(1.1f, 0xFF63CBB0));
        s2.addBar(new BarModel(2.7f, 0xFF56B7F1));
        s2.addBar(new BarModel(0.7f, 0xFFCDA67F));

        StackedBarModel s3 = new StackedBarModel("14.4");

        s3.addBar(new BarModel(2.3f, 0xFF63CBB0));
        s3.addBar(new BarModel(2.f, 0xFF56B7F1));
        s3.addBar(new BarModel(3.3f, 0xFFCDA67F));

        StackedBarModel s4 = new StackedBarModel("15.4");
        s4.addBar(new BarModel(1.f, 0xFF63CBB0));
        s4.addBar(new BarModel(4.2f, 0xFF56B7F1));
        s4.addBar(new BarModel(2.1f, 0xFFCDA67F));

        mStackedBarChart.addBar(s1);
        mStackedBarChart.addBar(s2);
        mStackedBarChart.addBar(s3);
        mStackedBarChart.addBar(s4);


        mStackedBarChart.startAnimation();
    }
*/
    /*
    private void setupPieChart(View view){

        org.eazegraph.lib.charts.PieChart mPieChart = (org.eazegraph.lib.charts.PieChart) view.findViewById(R.id.graphs_category_piechart2);

        mPieChart.addPieSlice(new PieModel("Freetime", 15, Color.parseColor("#FE6DA8")));
        mPieChart.addPieSlice(new PieModel("Sleep", 25, Color.parseColor("#56B7F1")));
        mPieChart.addPieSlice(new PieModel("Work", 35, Color.parseColor("#CDA67F")));
        mPieChart.addPieSlice(new PieModel("Eating", 9, Color.parseColor("#FED70E")));

        mPieChart.startAnimation();
    }
*/
    /*

    public void addBurned(View v) {
        // Get the new value from a user input:
        EditText burnedEditText = v.findViewById(R.id.burned);
        int newBurnedCals = Integer.parseInt(burnedEditText.getText().toString());

        // Update the old value:
        calsBurned = newBurnedCals;
        updateChart(v);
    }

    public void addConsumed(View v) {
        // Get the new value from a user input:
        EditText consumedEditText = v.findViewById(R.id.consumed);
        int newConsumedCals = Integer.parseInt(consumedEditText.getText().toString());

        // Update the old value:
        calsConsumed = newConsumedCals;
        updateChart(v);
    }

    public void updateChart(View v){
        // Update the text in a center of the chart:
        TextView numberOfCals = v.findViewById(R.id.number_of_calories);
        numberOfCals.setText(String.valueOf(calsBurned) + " / " + calsConsumed);

        // Calculate the slice size and update the pie chart:
        ProgressBar pieChart = v.findViewById(R.id.stats_progressbar);
        double d = (double) calsBurned / (double) calsConsumed;
        int progress = (int) (d * 100);
        pieChart.setProgress(progress);
    }

*/




    private void chooseDate(Context context, TextView dateText) {
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);



        DatePickerDialog datePicker =
                new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(final DatePicker view, final int year, final int month,
                                          final int dayOfMonth) {

                        @SuppressLint("SimpleDateFormat")
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
                        calendar.set(year, month, dayOfMonth);
                        String date = sdf.format(calendar.getTime());
                        dateText.setText(date);
                    }
                }, year, month, day); // set date picker to current date
        datePicker.getDatePicker().setMinDate(calendar.getTime().getTime());
        calendar.add(Calendar.DATE, 30);
        datePicker.getDatePicker().setMaxDate(calendar.getTime().getTime());
        datePicker.show();

        datePicker.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(final DialogInterface dialog) {
                dialog.dismiss();
            }
        });
    }


}