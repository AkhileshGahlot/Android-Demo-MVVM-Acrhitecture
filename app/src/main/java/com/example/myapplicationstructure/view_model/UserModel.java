package com.example.myapplicationstructure.view_model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplicationstructure.data_modal.GenericData;
import com.example.myapplicationstructure.data_modal.ResponceClass;
import com.example.myapplicationstructure.network.user.UserAPiCalls;

import java.util.HashMap;

public class UserModel  extends ViewModel {

    private MutableLiveData<GenericData> classListDataMutableLiveData;
    private UserAPiCalls userAPiCalls;
    private MutableLiveData<ResponceClass> updatedDataResponse;
    public int errorCode = -110;

    public void init() {
        if (userAPiCalls == null) {
            userAPiCalls = UserAPiCalls.getInstance();
        }
    }
    public int getErrorCode() {
        return errorCode;
    }


    public LiveData<GenericData> getUserInfo() {
        init();
        classListDataMutableLiveData = userAPiCalls.getClassList();
        return classListDataMutableLiveData;
    }

    public LiveData<ResponceClass> updateUser(HashMap<String, Object> updatedData) {
        try {
            init();
            updatedDataResponse = userAPiCalls.updateUser(updatedData);
            return updatedDataResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
