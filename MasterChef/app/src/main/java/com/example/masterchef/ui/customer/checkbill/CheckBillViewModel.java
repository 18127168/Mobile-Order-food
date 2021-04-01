package com.example.masterchef.ui.customer.checkbill;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CheckBillViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CheckBillViewModel() {
        mText = new MutableLiveData<>();
    }

}