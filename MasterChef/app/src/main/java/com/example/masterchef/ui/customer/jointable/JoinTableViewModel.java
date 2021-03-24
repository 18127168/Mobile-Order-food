package com.example.masterchef.ui.customer.jointable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class JoinTableViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public JoinTableViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is JoinTable fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}