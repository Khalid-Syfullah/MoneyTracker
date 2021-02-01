package com.tars.moneytracker.ui.notes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.tars.moneytracker.datamodel.HomeDataModel;
import com.tars.moneytracker.datamodel.NoteDataModel;
import com.tars.moneytracker.datamodel.OverviewDataModel;

public class NotesViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private MutableLiveData<NoteDataModel> noteLiveData;
    public NotesViewModel() {
        noteLiveData = new MutableLiveData<>();


    }

    public void setNoteLiveData(NoteDataModel noteData) {
        noteLiveData.setValue(noteData);
    }



    public LiveData<NoteDataModel> getNoteData() {
        return noteLiveData;
    }

}