package com.example.myapplicationstructure.application;

import android.content.Context;
import androidx.lifecycle.LifecycleObserver;
import androidx.multidex.MultiDexApplication;



/* Enable multidex for apps with over 64K methods
//        When your app and the libraries it references exceed 65,536 methods, you encounter a build error that indicates your app has reached the limit of the Android build architecture:
//
//
//        trouble writing output:
//        Too many field references: 131000; max is 65536.
//       You may try using --multi-dex option.
//        Older versions of the build system report a different error, which is an indication of the same problem:
//
//
//        Conversion to Dalvik format failed:
//        Unable to execute dex: method ID not in [0, 0xffff]: 65536
//        Both these error conditions display a common number: 65536. This number represents the total number of references that can be invoked by the code within a single Dalvik Executable (DEX) bytecode file. This page explains how to move past this limitation by enabling an app configuration known as multidex, which allows your app to build and read multiple DEX files.
//
//        Get more Info on - https://developer.android.com/studio/build/multidex
*/

public class YourApplication extends MultiDexApplication implements LifecycleObserver {
    public static Context APP_CONTEXT;
    private static YourApplication mInstance;

//    public static void initImageLoader(Context context) {
//        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
//        config.threadPriority(Thread.NORM_PRIORITY - 2);
//        config.denyCacheImageMultipleSizesInMemory();
//        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
//        config.diskCacheSize(150 * 1024 * 1024); // 150 MiB
//        config.tasksProcessingOrder(QueueProcessingType.LIFO);
//        config.memoryCacheExtraOptions(480, 800);// default = device screen dimensions
//        config.diskCacheExtraOptions(480, 800, null);
//        config.diskCacheSize(150 * 1024 * 1024);
//        config.diskCacheFileCount(150);
//        config.diskCacheFileNameGenerator(new HashCodeFileNameGenerator());
//        ImageLoader.getInstance().init(config.build());
//
//
//    }

    public static synchronized YourApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        APP_CONTEXT = getApplicationContext();
//        mInstance = this;
//        Env.init(this);
//        Config.init(APP_CONTEXT);
//        initImageLoader(getApplicationContext());
//        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
//        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
//            @Override
//            public void uncaughtException(Thread thread, Throwable throwable) {
//                throwable.printStackTrace();
//                System.exit(1);
//            }
//        });

    }

//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//        MultiDex.install(this);
//    }

//    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
//        ConnectivityReceiver.connectivityReceiverListener = listener;
//    }

//    @Override
//    public void onTerminate() {
//        super.onTerminate();
//    }

//    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
//    private void onAppBackgrounded() {
//        Log.d("MyApp", "App in background");
//        if (Util.hasInternet(getApplicationContext())) {
//            long now = System.currentTimeMillis() - Config.getAppOpenTime();
//            now = now / 1000;
//            Util.updateReport(Config.getUserType(), 1, now, "");
//        }
//    }

//    @OnLifecycleEvent(Lifecycle.Event.ON_START)
//    private void onAppForegrounded() {
//        Log.d("MyApp", "App in foreground");
//        if (Util.hasInternet(getApplicationContext())) {
//            Util.updateReport(Config.getUserType(), 0, 0, "");
//            Config.setAppOpenTime(System.currentTimeMillis());
//        }
//    }

}

