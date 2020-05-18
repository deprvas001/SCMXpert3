package com.example.scmxpert.ViewModel;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.scmxpert.model.UpdateEventPost;

public class ShipmentStatusFactory implements ViewModelProvider.Factory {
    private Application mApplication;
    private String mParam;
    private UpdateEventPost eventPost;


    public ShipmentStatusFactory(Application application, String param) {
        mApplication = application;
        mParam = param;
    }

    public ShipmentStatusFactory(Application application) {
        mApplication = application;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new ShipmentStatus(mApplication, mParam);
    }
}
