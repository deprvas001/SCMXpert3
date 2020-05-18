package com.example.scmxpert.views.forgotPassword;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import com.example.scmxpert.model.forgotModel.ForgotApiResponse;

public class ForgotViewModel extends ViewModel {
    private MediatorLiveData<ForgotApiResponse>  apiResponseMediatorLiveData;
    private ForgotRepository forgotRepository;

   public  ForgotViewModel(){
        apiResponseMediatorLiveData = new MediatorLiveData<>();
        forgotRepository = new ForgotRepository();
    }

    public LiveData<ForgotApiResponse> forgotPassword(String email){
        apiResponseMediatorLiveData.addSource(forgotRepository.forgotPassword(email), forgotApiResponse ->
                apiResponseMediatorLiveData.setValue(forgotApiResponse));
        return apiResponseMediatorLiveData;
    }
}
