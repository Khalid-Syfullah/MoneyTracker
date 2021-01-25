package com.tars.moneytracker.ui.notification;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tars.moneytracker.datamodel.NotificationsDataModel;

import java.util.ArrayList;

public class NotificationViewModel extends ViewModel {


    private MutableLiveData<ArrayList<NotificationsDataModel>> notificationLiveData;

    public NotificationViewModel() {
        notificationLiveData=new MutableLiveData<>();
    }

    public LiveData<ArrayList<NotificationsDataModel>> getNotificationLiveData() {
        return notificationLiveData;
    }

    public void setNotificationLiveData(ArrayList<NotificationsDataModel> notificationsData) {
        notificationLiveData.setValue(notificationsData);
    }
// TODO: Implement the ViewModel
}