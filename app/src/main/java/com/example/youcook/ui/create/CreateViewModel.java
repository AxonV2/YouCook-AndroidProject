package com.example.youcook.ui.create;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CreateViewModel extends ViewModel {

    private MutableLiveData<String> mText = new MutableLiveData<>("fuck");

    public CreateViewModel()
    {
        mText.setValue("This is the create fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}