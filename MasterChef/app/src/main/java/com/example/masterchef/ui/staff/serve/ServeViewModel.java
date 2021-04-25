package com.example.masterchef.ui.staff.serve;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ServeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ServeViewModel() {
        mText = new MutableLiveData<>();
    }

}