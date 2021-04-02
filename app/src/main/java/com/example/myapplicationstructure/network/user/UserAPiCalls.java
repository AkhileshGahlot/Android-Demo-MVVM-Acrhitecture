package com.example.myapplicationstructure.network.user;

import androidx.lifecycle.MutableLiveData;

import com.example.myapplicationstructure.config.Config;
import com.example.myapplicationstructure.data_modal.GenericData;
import com.example.myapplicationstructure.data_modal.ResponceClass;
import com.example.myapplicationstructure.network.APIClient;
import com.example.myapplicationstructure.network.APIInterface;
import com.example.myapplicationstructure.utils.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAPiCalls {

    private APIInterface apiInterface;

    private static UserAPiCalls userAPiCalls;

    public UserAPiCalls() {
        apiInterface = APIClient.createService(APIInterface.class);
    }

    public static UserAPiCalls getInstance() {
        if (userAPiCalls == null) {
            userAPiCalls = new UserAPiCalls();
        }
        return userAPiCalls;
    }

    public MutableLiveData<GenericData> getClassList() {
      final  MutableLiveData<GenericData> newData = new MutableLiveData<>();
        try {
            apiInterface.userProfileResponse(Config.getJwtToken())
                    .enqueue(new Callback<GenericData>() {
                        @Override
                        public void onResponse(Call<GenericData> call, Response<GenericData> response) {
                            Util.dismissProDialog();
                            if (response.isSuccessful() && response.body() != null) {
                                GenericData feeListResponse = response.body();
                                if (feeListResponse != null) {
                                    if (feeListResponse.getCode() == 200) {
//                                        if (feeListResponse.getBatches().size() > 0) {
                                            newData.setValue(feeListResponse);
//                                        } else {
//                                            newData.setValue(null);
//                                        }
                                    }
                                } else {
                                    newData.setValue(null);

                                }
                            } else {
                                newData.setValue(null);
                            }

                        }

                        @Override
                        public void onFailure(Call<GenericData> call, Throwable t) {
                            System.out.println(t.getCause());
                            newData.setValue(null);
                            Util.dismissProDialog();
                        }
                    });
            return newData;
        } catch (Exception e) {
            e.printStackTrace();
            newData.setValue(null);
        }
        return newData;

    }

    public MutableLiveData<ResponceClass> updateUser(HashMap<String, Object> updatedData) {
        MutableLiveData<ResponceClass> newsData = new MutableLiveData<>();
        try {
            apiInterface.updateUser(Config.getJwtToken(), updatedData)
                    .enqueue(new Callback<ResponceClass>() {
                        @Override
                        public void onResponse(Call<ResponceClass> call, Response<ResponceClass> response) {
                            Util.dismissProDialog();

                            try {
                                ResponceClass receivedResponse = response.body();
                                if (receivedResponse != null && receivedResponse.getCode() == 200) {

                                    newsData.setValue(receivedResponse);

                                } else {

                                    try {
                                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                                        receivedResponse = new ResponceClass();
                                        receivedResponse.setStatus(jObjError.getString("error"));
                                        receivedResponse.setCode(-110);

                                        newsData.setValue(receivedResponse);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        newsData.setValue(null);
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                newsData.setValue(null);
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponceClass> call, Throwable t) {
                            Util.dismissProDialog();
                            newsData.setValue(null);

                        }
                    });

            return newsData;
        } catch (Exception e) {
            e.printStackTrace();
            Util.dismissProDialog();
        }
        newsData.setValue(null);
        return newsData;
    }


}
