package com.example.myapplicationstructure.utils;

/// Add all global function

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;

import com.example.myapplicationstructure.R;
import com.example.myapplicationstructure.view.BaseView.Env;
import com.google.android.material.snackbar.Snackbar;

public final class Util {

    private static ProgressDialog progressDialog = null;


//    public static String getDateFromMiliSec(long timeStamp, String format) {
//    }

//    public static String parseDateFormat(String time, String input, String output) {
//    }
    /**
     * animation on finishing activity
     *
     * @param context
     */
    public static void endAct(Context context) {
//        try {
//            ((Activity) context).overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
    public static Dialog dismissProDialog() {
        try {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            progressDialog = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return progressDialog;
    }

    //this function only work with coordinate layout
    public static void showErrorSnackBar(CoordinatorLayout coordinatorLayout, String msg, Context context) {
        try {
            Snackbar snackbar = Snackbar.make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);
            View sbView = snackbar.getView();
//            sbView.setBackgroundColor(ContextCompat.getColor(context, R.color.material_red600));
            TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showSuccessSnackBar(CoordinatorLayout coordinatorLayout, String msg, Context context) {
        try {
            Snackbar snackbar = Snackbar.make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);
            View sbView = snackbar.getView();
//            sbView.setBackgroundColor(ContextCompat.getColor(context, R.color.material_green500));
            TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
            textView.setTextColor(Color.WHITE);
            snackbar.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean hasInternet(Context context) {
        try {
            ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnectedOrConnecting();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static void showNoInternetToast() {
        try {
            Toast.makeText(Env.currentActivity, Env.currentActivity.getResources().getString(R.string.no_internet), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Dialog showProDialog(Context context) {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            progressDialog = new ProgressDialog(context, R.style.NewDialog);
            progressDialog.setCancelable(false);
//            progressDialog.setIndeterminateDrawable(context.getResources().getDrawable(R.drawable.progress));
            progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Large);
            progressDialog.show();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return progressDialog;
    }

}
