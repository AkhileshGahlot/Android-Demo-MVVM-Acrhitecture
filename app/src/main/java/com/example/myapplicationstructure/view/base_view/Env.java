package com.example.myapplicationstructure.view.base_view;

import android.content.Context;

/**
 * Created by Nitesh on 8/10/2017.
 */

public class Env {

    public static Context appContext;
    public static Context currentActivity;

    public static void init(Context appContext) {
        Env.appContext = appContext;
    }
}
