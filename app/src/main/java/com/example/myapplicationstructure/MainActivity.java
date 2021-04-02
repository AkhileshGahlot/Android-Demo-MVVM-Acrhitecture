package com.example.myapplicationstructure;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplicationstructure.network.APIInterface;
import com.example.myapplicationstructure.utils.FileUploader;
import com.example.myapplicationstructure.utils.Util;
import com.example.myapplicationstructure.view.base_view.BaseActivity;
import com.example.myapplicationstructure.view.base_view.Env;
import com.example.myapplicationstructure.view_model.UserModel;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;


/* A splash screen activity
* call the home activity from here
* */


public class MainActivity extends BaseActivity {
    private ProgressDialog pDialog;

    private ArrayList<String> files = new ArrayList<>();
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }


    private void init() {
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        pDialog = new ProgressDialog(Env.currentActivity);

        Button backPressBtn = findViewById(R.id.backPressBtn);
      backPressBtn.setOnClickListener(this);

    }

    //get request
    private void getUserInfo() {
            try {
                if (Util.hasInternet(Env.currentActivity)) {
                    UserModel doubtViewModel = ViewModelProviders.of(this).get(UserModel.class);
                    doubtViewModel.init();
                    doubtViewModel.getUserInfo().observe(this, newsResponse -> {
                        Util.dismissProDialog();
                        if (newsResponse != null && newsResponse.getCode() == 200) {
                         //success
                            Util.showSuccessSnackBar(coordinatorLayout, getResources().getString(R.string.Uploading_successful), Env.currentActivity);

                        } else {
                            //fail
                            Util.showErrorSnackBar(coordinatorLayout, getResources().getString(R.string.server_error_try), Env.currentActivity);
                        }

                    });

                } else {
                    Util.dismissProDialog();
                    Util.showNoInternetToast();
                }
            } catch (Exception e) {
                Util.dismissProDialog();
                e.printStackTrace();
            }
        }

   //post request
    private void updateUser(HashMap<String, Object> data) {

        try {

            if (Util.hasInternet(Env.currentActivity)) {
                Util.showProDialog(Env.currentActivity);

                UserModel userModel = ViewModelProviders.of(this).get(UserModel.class);
                userModel.updateUser(data).observe(this, newsResponse -> {

                    try {
                        if (newsResponse == null) {

                            Util.showErrorSnackBar(coordinatorLayout, getResources().getString(R.string.server_error_try), Env.currentActivity);
                            return;
                        }
                        if (newsResponse.getCode() == userModel.getErrorCode()) {

                            Util.showErrorSnackBar(coordinatorLayout, newsResponse.getStatus(), Env.currentActivity);
                            return;
                        }

                        Util.showSuccessSnackBar(coordinatorLayout, getResources().getString(R.string.Uploading_successful), Env.currentActivity);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

            } else {
                Util.showErrorSnackBar(coordinatorLayout, getResources().getString(R.string.no_internet), Env.currentActivity);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Util.showErrorSnackBar(coordinatorLayout, getResources().getString(R.string.server_error_try), Env.currentActivity);
        }
    }


    //Post with media - Before calling this function, fetch and initialize @files
    public void uploadFiles( int type, String UserID, String name) {
        File[] filesToUpload = new File[files.size()];
        for (int i = 0; i < files.size(); i++) {
            filesToUpload[i] = new File(files.get(i));
        }
        showProgress(getResources().getString(R.string.uploading));
        FileUploader fileUploader = new FileUploader();
        fileUploader.uploadFiles(true, APIInterface.BASE_URL,  type, UserID, name, "file","month","year", filesToUpload, new FileUploader.FileUploaderCallback() {
            @Override
            public void onError() {
                hideProgress();
                Util.dismissProDialog();
                Util.showErrorSnackBar(coordinatorLayout, getResources().getString(R.string.Uploading_fail), Env.currentActivity);
            }

            @Override
            public void onFinish(String[] responses) {
                hideProgress();
                Util.dismissProDialog();
                Util.showSuccessSnackBar(coordinatorLayout, getResources().getString(R.string.Uploading_successful), Env.currentActivity);
                for (int i = 0; i < responses.length; i++) {
                    String str = responses[i];
                    Log.e("RESPONSE " + i, responses[i]);
                }
                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
                Util.endAct(Env.currentActivity);

            }

            @Override
            public void onProgressUpdate(int currentpercent, int totalpercent, int filenumber) {
                updateProgress(totalpercent, getResources().getString(R.string.uploading) + " " + filenumber, "");
                Log.e("Progress Status", currentpercent + " " + totalpercent + " " + filenumber);
            }
        });
    }

    public void updateProgress(int val, String title, String msg) {
        pDialog.setTitle(title);
        pDialog.setMessage(msg);
        pDialog.setProgress(val);
    }

    public void showProgress(String str) {
        try {
            pDialog.setCancelable(false);
            pDialog.setTitle(getResources().getString(R.string.Please_wait));
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.setMax(100); // Progress Dialog Max Value
            pDialog.setMessage(str);
            if (pDialog.isShowing())
                pDialog.dismiss();
            pDialog.show();
        } catch (Exception e) {

        }
    }

    public void hideProgress() {
        try {
            if (pDialog.isShowing())
                pDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backPressBtn: {
//                getUserInfo();
//                uploadFiles();
//                updateUser
//                startActivity(new Intent(this, HomeActivity.class));
            }
            break;
        }
    }
}