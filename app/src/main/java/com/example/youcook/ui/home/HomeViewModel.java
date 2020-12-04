package com.example.youcook.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    //SET MUTABLES FOR LOGIN

    private static MutableLiveData<String> mText = new MutableLiveData<String>()
    {{
        setValue("Bom dia, ta na hora de comer");
    }};

    public HomeViewModel()
    {
    }

    public LiveData<String> getText() {
        return mText;
    }
    public void setText(String val) { mText.setValue(val); }
}