package com.example.masterchef.ui.staff.tables;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TablesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TablesViewModel() {
        mText = new MutableLiveData<>();
    }

}