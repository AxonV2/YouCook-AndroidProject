package com.example.youcook.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    //SET MUTABLES FOR LOGIN

    private static MutableLiveData<String> mText = new MutableLiveData<String>()
    {{
        setValue("Bom dia dickhead, ta na hora de comer né bola de merda?");
    }};

    public HomeViewModel()
    {
        //Everytime we navigate here this will be set
        //mText.setValue("Bom dia dickhead, ta na hora de comer né bola de merda?");
    }

    public LiveData<String> getText() {
        return mText;
    }
    public void setText(String val) { mText.setValue(val); }
}