package com.example.scmxpert.views.updateEvent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import com.example.scmxpert.model.UpdateSendRequest;
import com.example.scmxpert.model.updateEventModel.UpdateApiResponse;

public class UpdateEventViewModel extends ViewModel {
    private MediatorLiveData<UpdateApiResponse>  mediatorLiveData;
    private UpdateEventRepostiory eventRepostiory ;

    public UpdateEventViewModel(){
        mediatorLiveData = new MediatorLiveData<>();
        eventRepostiory = new UpdateEventRepostiory();
    }

    public LiveData<UpdateApiResponse> updateEvent(UpdateSendRequest updateSendRequest){
        mediatorLiveData.addSource(eventRepostiory.updateEvent(updateSendRequest), updateApiResponse ->
                mediatorLiveData.setValue(updateApiResponse));
        return mediatorLiveData;
    }
}
