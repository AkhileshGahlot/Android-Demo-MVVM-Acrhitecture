package com.example.myapplicationstructure.view.BaseView;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationstructure.utils.Util;


/**
 * Created by nitesh on 4/26/2017.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Env.currentActivity = this;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Env.currentActivity = this;
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Util.endAct(Env.currentActivity);
    }
}
