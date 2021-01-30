package com.tars.moneytracker.ui.home;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.tars.moneytracker.R;
import com.tars.moneytracker.RecyclerItemClickInterface;
import com.tars.moneytracker.api.RestClient;
import com.tars.moneytracker.api.RetroInterface;
import com.tars.moneytracker.datamodel.GoalDataModel;
import com.tars.moneytracker.datamodel.HomeDataModel;
import com.tars.moneytracker.datamodel.OverviewDataModel;
import com.tars.moneytracker.datamodel.StaticData;
import com.tars.moneytracker.datamodel.WalletDataModel;
import com.tars.moneytracker.ui.home.adapters.GoalsAdapter;
import com.tars.moneytracker.ui.wallet.WalletViewModel;
import com.tars.moneytracker.ui.wallet.adapters.WalletAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment  {

    private HomeViewModel homeViewModel;
    private WalletViewModel walletViewModel;
    private OverviewDataModel overviewDataModel;
    private RecyclerView walletRecyclerView,goalsRecyclerView;
    private TextView spentText, remainingText, limitText;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        walletViewModel=new ViewModelProvider(this).get(WalletViewModel.class);


        View root = inflater.inflate(R.layout.fragment_home, container, false);

        walletRecyclerView = root.findViewById(R.id.overViewRecycler);
        goalsRecyclerView=root.findViewById(R.id.home_goals_recyclerView);
        spentText = root.findViewById(R.id.home_overview_spent_amount);
        remainingText = root.findViewById(R.id.home_overview_remaining_amount);
        limitText = root.findViewById(R.id.home_overview_limit_amount);


        walletRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        goalsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));

        fetchOverviewData();
        fetchGoalData();
        fetchWalletData();


//        RestClient.getWallets(getContext(),walletRecyclerView);
//        RestClient.getGoals(getContext(),goalsRecyclerView);
        homeViewModel.getHomeData().observe(getViewLifecycleOwner(), new Observer<HomeDataModel>() {
            @Override
            public void onChanged(HomeDataModel homeDataModel) {

            }
        });

        homeViewModel.getOverviewLiveData().observe(getViewLifecycleOwner(),overviewDataModel -> {
            spentText.setText(overviewDataModel.getSpent()+" BDT");
            remainingText.setText(overviewDataModel.getRemaining()+" BDT");
            limitText.setText(overviewDataModel.getLimit()+" BDT");
        });
        
        walletViewModel.getWallets().observe(getViewLifecycleOwner(), new Observer<ArrayList<WalletDataModel>>() {
            @Override
            public void onChanged(ArrayList<WalletDataModel> walletDataModels) {
                walletRecyclerView.setAdapter(new WalletAdapter(getContext(),walletDataModels));
            }
        });
        walletViewModel.getGoalLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<GoalDataModel>>() {
            @Override
            public void onChanged(ArrayList<GoalDataModel> goalDataModels) {
                goalsRecyclerView.setAdapter(new GoalsAdapter(getContext(),goalDataModels));
            }
        });
        StaticData.getUpdate().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.equals("yes")) {
                    fetchWalletData();
                    fetchGoalData();
                   
                    StaticData.setUpdate("no");
                }

            }
        });

        return root;
    }




    public void fetchOverviewData(){
        RetroInterface retroInterface = RestClient.createRestClient();
        OverviewDataModel overviewDataModel = new OverviewDataModel(StaticData.LoggedInUserEmail);
        Call<OverviewDataModel> call = retroInterface.getOverviewData(overviewDataModel);

        call.enqueue(new Callback<OverviewDataModel>() {
            @Override
            public void onResponse(Call<OverviewDataModel> call, Response<OverviewDataModel> response) {
                if(response.isSuccessful()){
                    homeViewModel.setOverviewLiveData(response.body());
                }
                else{
                    Toast.makeText(getActivity(), "response failed", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<OverviewDataModel> call, Throwable t) {
                Toast.makeText(getActivity(), "Connection failed", Toast.LENGTH_SHORT).show();

            }
        });
    }


    public void fetchWalletData(){
        RetroInterface retroInterface = RestClient.createRestClient();
        Call<ArrayList<WalletDataModel>> call = retroInterface.getWalletData(new WalletDataModel(StaticData.LoggedInUserEmail));

        call.enqueue(new Callback<ArrayList<WalletDataModel>>() {
            @Override
            public void onResponse(Call<ArrayList<WalletDataModel>> call, Response<ArrayList<WalletDataModel>> response) {
                if(response.isSuccessful()) {
                    walletViewModel.setWalletLiveData(response.body());
                }
                else {
                    Toast.makeText(getActivity(), "response failed", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<WalletDataModel>> call, Throwable t) {
                Toast.makeText(getActivity(), "Connection failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void fetchGoalData(){

        RetroInterface retroInterface = RestClient.createRestClient();
        Call<ArrayList<GoalDataModel>> call = retroInterface.getGoalData(new GoalDataModel(StaticData.LoggedInUserEmail));
        call.enqueue(new Callback<ArrayList<GoalDataModel>>() {
            @Override
            public void onResponse(Call<ArrayList<GoalDataModel>> call, Response<ArrayList<GoalDataModel>> response) {

                if(response.isSuccessful()) {
                    walletViewModel.setGoalLiveData(response.body());
                }
                else {
                    Toast.makeText(getActivity(), "response failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GoalDataModel>> call, Throwable t) {
//              Toast.makeText(getContext(), "Connection failed", Toast.LENGTH_SHORT).show();
            }
        });



    }
}