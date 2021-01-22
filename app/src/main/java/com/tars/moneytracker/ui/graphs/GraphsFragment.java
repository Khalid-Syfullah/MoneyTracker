package com.tars.moneytracker.ui.graphs;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.StackedBarChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.PieModel;
import org.eazegraph.lib.models.StackedBarModel;

import java.util.ArrayList;
import java.util.List;

public class GraphsFragment extends Fragment {


    private GraphsViewModel graphsViewModel;

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

        setupPieChart(root);
        setData(root);


        return root;
    }


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
    private void setData(View view)
    {

        BarChart mBarChart = (BarChart) view.findViewById(R.id.graphs_overview_barchart);
        //org.eazegraph.lib.charts.PieChart mPieChart = (org.eazegraph.lib.charts.PieChart) view.findViewById(R.id.graphs_category_piechart2);
        StackedBarChart mStackedBarChart = (StackedBarChart) view.findViewById(R.id.graphs_monthly_stackedbarchart);

        mBarChart.addBar(new BarModel("Sat",2.3f, 0xFF123456));
        mBarChart.addBar(new BarModel("Sun",2.f,  0xFF343456));
        mBarChart.addBar(new BarModel("Mon",3.3f, 0xFF563456));
        mBarChart.addBar(new BarModel("Tue",1.1f, 0xFF873F56));
        mBarChart.addBar(new BarModel("Wed",2.7f, 0xFF56B7F1));
        mBarChart.addBar(new BarModel("Thu",2.f,  0xFF343456));
        mBarChart.addBar(new BarModel("Fri",0.4f, 0xFF1FF4AC));
/*

        mPieChart.addPieSlice(new PieModel("Freetime", 15, Color.parseColor("#FE6DA8")));
        mPieChart.addPieSlice(new PieModel("Sleep", 25, Color.parseColor("#56B7F1")));
        mPieChart.addPieSlice(new PieModel("Work", 35, Color.parseColor("#CDA67F")));
        mPieChart.addPieSlice(new PieModel("Eating", 9, Color.parseColor("#FED70E")));
*/
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


        mBarChart.startAnimation();

      //  mPieChart.startAnimation();
        mStackedBarChart.startAnimation();

    }

    private void setupPieChart(View v){

        PieChart pieChart = v.findViewById(R.id.graphs_category_piechart);

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


}