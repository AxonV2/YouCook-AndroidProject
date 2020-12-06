package com.example.youcook.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class HomeViewModel extends ViewModel {

    private static MutableLiveData<String> mText = new MutableLiveData<String>()
    {{
        //setValue("Bem vindo (user.name) á nossa aplicação de receitas.");
    }};

    public HomeViewModel()
    {
    }

    public LiveData<String> getText() {
        return mText;
    }
    public void setText(String val) { mText.setValue(val); }
}