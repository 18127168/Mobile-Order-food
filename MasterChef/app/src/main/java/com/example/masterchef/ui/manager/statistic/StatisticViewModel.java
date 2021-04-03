package com.example.masterchef.ui.manager.statistic;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StatisticViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public StatisticViewModel() {
        mText = new MutableLiveData<>();
    }

}