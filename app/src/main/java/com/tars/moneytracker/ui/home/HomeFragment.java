package com.tars.moneytracker.ui.home;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
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
import com.tars.moneytracker.datamodel.WalletDataModel;
import com.tars.moneytracker.ui.home.adapters.GoalsAdapter;
import com.tars.moneytracker.ui.wallet.WalletViewModel;
import com.tars.moneytracker.ui.wallet.adapters.WalletAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements RecyclerItemClickInterface {

    private HomeViewModel homeViewModel;
    private WalletViewModel walletViewModel;
    private RecyclerView walletRecyclerView,goalsRecyclerView;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        walletViewModel=new ViewModelProvider(this).get(WalletViewModel.class);


        View root = inflater.inflate(R.layout.fragment_home, container, false);

        walletRecyclerView = root.findViewById(R.id.overViewRecycler);
        goalsRecyclerView=root.findViewById(R.id.home_goals_recyclerView);

        walletRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        goalsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));

        fetchGoalData();
        fetchWalletData();


//        RestClient.getWallets(getContext(),walletRecyclerView);
//        RestClient.getGoals(getContext(),goalsRecyclerView);
        homeViewModel.getHomeData().observe(getViewLifecycleOwner(),homeDataModel -> {

        });
        
        walletViewModel.getWallets().observe(getViewLifecycleOwner(),walletDataModels -> {
            walletRecyclerView.setAdapter(new WalletAdapter(getContext(),walletDataModels));
        });
        walletViewModel.getGoalLiveData().observe(getViewLifecycleOwner(),goalDataModels -> {
            goalsRecyclerView.setAdapter(new GoalsAdapter(getContext(),goalDataModels));
        });


        return root;
    }


    @Override
    public void onItemClick(int position) {
        Toast.makeText(getActivity(), Integer.toString(position), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onItemClick(Drawable position, String name) {

    }

    @Override
    public void onItemClick(String name) {

    }


    public void fetchWalletData(){
        RetroInterface retroInterface = RestClient.createRestClient();
        Call<ArrayList<WalletDataModel>> call = retroInterface.getWalletData();

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
        Call<ArrayList<GoalDataModel>> call = retroInterface.getGoalData();
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
                Toast.makeText(getActivity(), "Connection failed", Toast.LENGTH_SHORT).show();
            }
        });



    }
}