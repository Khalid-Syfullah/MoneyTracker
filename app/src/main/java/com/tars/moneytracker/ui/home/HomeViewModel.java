package com.tars.moneytracker.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tars.moneytracker.datamodel.HomeDataModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<HomeDataModel> homeLiveData;

    public HomeViewModel() {
        homeLiveData = new MutableLiveData<>();

    }

    public void setHomeLiveData(HomeDataModel homeData) {
        homeLiveData.setValue(homeData);
    }

    public LiveData<HomeDataModel> getHomeData() {
        return homeLiveData;
    }
}