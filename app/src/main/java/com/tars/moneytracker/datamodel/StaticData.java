package com.tars.moneytracker.datamodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.tars.moneytracker.R;

import java.util.HashMap;

public class StaticData {
    public static String LoggedInUserName="";
    public static String LoggedInUserEmail="";
    public static String LoggedInUserPass="";



    public static MutableLiveData<String> update=new MutableLiveData<>();


    public static LiveData<String> getUpdate() {
        return update;
    }

    public static void setUpdate(String up) {
        update.setValue(up);
    }
}
