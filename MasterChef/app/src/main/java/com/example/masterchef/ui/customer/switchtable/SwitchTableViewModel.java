package com.example.masterchef.ui.customer.switchtable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SwitchTableViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SwitchTableViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}