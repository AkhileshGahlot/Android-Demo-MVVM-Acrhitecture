package com.example.myapplicationstructure.network;

import android.os.Build;

import com.example.myapplicationstructure.utils.TLSSocketFactory;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static Retrofit retrofit;

    public static Retrofit getClient() {
        OkHttpClient client = null;
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            client = new OkHttpClient.Builder()
                    .readTimeout(20, TimeUnit.SECONDS)
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(20, TimeUnit.SECONDS)
                  //  .addInterceptor(interceptor)
                    .build();
        } else {
            try {
                TLSSocketFactory tlsSocketFactory = new TLSSocketFactory();
                if (tlsSocketFactory.getTrustManager() != null) {
                    client = new OkHttpClient.Builder()
                            .readTimeout(20, TimeUnit.SECONDS)
                            .connectTimeout(20, TimeUnit.SECONDS)
                            .writeTimeout(20, TimeUnit.SECONDS)
                            .sslSocketFactory(tlsSocketFactory, tlsSocketFactory.getTrustManager())
                            .build();
                }
            } catch (KeyManagementException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyStoreException e) {
                e.printStackTrace();
            }
        }
        retrofit = new Retrofit.Builder()
                .baseUrl(APIInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}


