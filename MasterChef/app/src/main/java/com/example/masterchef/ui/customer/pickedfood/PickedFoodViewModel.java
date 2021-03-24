package com.example.masterchef.ui.customer.pickedfood;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PickedFoodViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PickedFoodViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}