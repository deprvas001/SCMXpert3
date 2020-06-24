package com.development.scmxpert.views.login;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import com.development.scmxpert.model.loginModel.LoginApiResponse;

public class LoginViewModel extends ViewModel {
    private MediatorLiveData<LoginApiResponse> mutableLiveData;
    private LoginRepository loginRepository;

    public  LoginViewModel(){
        mutableLiveData = new MediatorLiveData<>();
        loginRepository = new LoginRepository();
    }

    public LiveData<LoginApiResponse> getLoginUser(Context context,String username,String password){
        mutableLiveData.addSource(loginRepository.loginUser(context,username,password), new Observer<LoginApiResponse>() {
            @Override
            public void onChanged(LoginApiResponse loginApiResponse) {
                mutableLiveData.setValue(loginApiResponse);
            }
        });
        return mutableLiveData;
    }
}
