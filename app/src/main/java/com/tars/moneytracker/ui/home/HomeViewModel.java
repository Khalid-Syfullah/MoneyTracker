package com.tars.moneytracker.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tars.moneytracker.datamodel.HomeDataModel;
import com.tars.moneytracker.datamodel.OverviewDataModel;

import java.util.ArrayList;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<HomeDataModel> homeLiveData;
    private MutableLiveData<OverviewDataModel> overviewLiveData;

    public HomeViewModel() {
        homeLiveData = new MutableLiveData<>();
        overviewLiveData = new MutableLiveData<>();

    }

    public void setHomeLiveData(HomeDataModel homeData) {
        homeLiveData.setValue(homeData);
    }
    public void setOverviewLiveData(OverviewDataModel overviewData) {
        overviewLiveData.setValue(overviewData);
    }


    public LiveData<HomeDataModel> getHomeData() {
        return homeLiveData;
    }
    public LiveData<OverviewDataModel> getOverviewLiveData() {
        return overviewLiveData;
    }

}