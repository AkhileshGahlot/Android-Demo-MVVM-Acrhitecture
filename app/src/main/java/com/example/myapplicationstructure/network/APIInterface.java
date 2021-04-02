package com.example.myapplicationstructure.network;



import com.example.myapplicationstructure.data_modal.GenericData;
import com.example.myapplicationstructure.data_modal.ResponceClass;
import com.google.gson.JsonElement;

import java.util.HashMap;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;


public interface APIInterface {

    String BASE_URL = "";  //Live URl
    //  String BASE_URL = "";  //Change domain URl
    //String BASE_URL = "";   //Staging URL

    // **************************************************************************************************
//LOGIN API

    @FormUrlEncoded
    @POST("auth2/login")
    Call<GenericData> userCheck(@Field("phone_number") String phone_number,
                                @Field("type") String type,
                                @Field("method") String method,
                                @Field("institute_code") String institute_code);



    // **************************************************************************************************
    //PROFILE API


    @GET("user/profile")
    Call<GenericData> userProfileResponse(@Header("authorization") String authorization);


    @FormUrlEncoded
    @POST("user/update")
    Call<ResponceClass> updateUser(@Header("authorization") String authorization,
                                   @FieldMap HashMap<String, Object> hashFields);



    @Multipart
    @POST("material/v2_add_m")
    Call<JsonElement> studymaterialuploadSubs(@Header("authorization") String authorization,
                                              @Part MultipartBody.Part file,
                                              @Part("type") RequestBody type,
                                              @Part("userID") RequestBody batch_id,
                                              @Part("name") RequestBody name,
                                              @Part("free") RequestBody free,
                                              @Part("month") RequestBody month,
                                              @Part("year") RequestBody year);

}


