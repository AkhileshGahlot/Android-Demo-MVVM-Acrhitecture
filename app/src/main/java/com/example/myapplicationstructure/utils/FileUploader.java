package com.example.myapplicationstructure.utils;

import android.os.Handler;
import android.os.Looper;


import com.example.myapplicationstructure.config.Config;
import com.example.myapplicationstructure.network.APIClient;
import com.example.myapplicationstructure.network.APIInterface;

import com.google.gson.JsonElement;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okio.BufferedSink;
import retrofit2.Call;
import retrofit2.Callback;

public class FileUploader {

    public FileUploaderCallback fileUploaderCallback;
    public int uploadIndex = -1;
    private File[] files;
    private String uploadURL = "";
    private long totalFileLength = 0;
    private long totalFileUploaded = 0;
    private String filekey = "";
    private APIInterface uploadInterface;
    private String auth_token = "";
    private String[] responses;
    private int mediaType = 0;
    private boolean free;
    private String month = "", year = "";


    public FileUploader() {
        uploadInterface = APIClient.getClient().create(APIInterface.class);
    }

    public void uploadFiles(boolean checked, String baseUrl,  int type, String userID, String name, String filekey, String month, String year, File[] files, FileUploaderCallback fileUploaderCallback) {
        uploadFiles(checked, baseUrl,  type, userID, name, filekey, month, year, files, fileUploaderCallback, "");
    }

    public void uploadFiles(boolean checked, String baseUrl,  int type, String userID, String name, String filekey, String month, String year, File[] files, FileUploaderCallback fileUploaderCallback, String auth_token) {
        this.fileUploaderCallback = fileUploaderCallback;
        this.files = files;
        this.free = checked;
        this.uploadIndex = -1;
        this.uploadURL = baseUrl;
        this.filekey = filekey;
        this.auth_token = auth_token;
        totalFileUploaded = 0;
        totalFileLength = 0;
        uploadIndex = -1;
        mediaType = type;
        this.month = month;
        this.year = year;
        responses = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            totalFileLength = totalFileLength + files[i].length();
        }
        uploadNext(free, String.valueOf(type), userID, name, month, year);
    }

    private void uploadNext(boolean free,  String type, String userID, String name, String month, String year) {
        if (files.length > 0) {
            if (uploadIndex != -1)
                totalFileUploaded = totalFileUploaded + files[uploadIndex].length();
            uploadIndex++;
            if (uploadIndex < files.length) {
                uploadSingleFile(free, uploadIndex,  type, userID, name, month, year);
            } else {
                fileUploaderCallback.onFinish(responses);
            }
        } else {
            fileUploaderCallback.onFinish(responses);
        }
    }

    private void uploadSingleFile(boolean free, final int index,  final String type, final String userID, final String name, String month, String year) {
        PRRequestBody fileBody = new PRRequestBody(files[index]);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData(filekey, files[index].getName(), fileBody);
        RequestBody typePart = RequestBody.create(MediaType.parse("text/plain"), type);
        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), userID);
        RequestBody freeReq = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(free));
        RequestBody nme = RequestBody.create(MediaType.parse("text/plain"), name);
        Call<JsonElement> call;

            RequestBody monthBody = RequestBody.create(MediaType.parse("text/plain"), month);
            RequestBody yearBody = RequestBody.create(MediaType.parse("text/plain"), year);
            call = uploadInterface.studymaterialuploadSubs(Config.getJwtToken(), filePart,  typePart, userId, nme, freeReq, monthBody, yearBody);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, retrofit2.Response<JsonElement> response) {
                if (response.isSuccessful()) {
                    JsonElement jsonElement = response.body();
                    responses[index] = jsonElement.toString();
                } else {
                    responses[index] = "";
                }
                uploadNext(FileUploader.this.free,  type, userID, name, FileUploader.this.month, FileUploader.this.year);
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                fileUploaderCallback.onError();
            }
        });
    }


    public interface FileUploaderCallback {
        void onError();

        void onFinish(String[] responses);

        void onProgressUpdate(int currentpercent, int totalpercent, int filenumber);
    }

    public class PRRequestBody extends RequestBody {
        private static final int DEFAULT_BUFFER_SIZE = 2048;
        private File mFile;

        public PRRequestBody(final File file) {
            mFile = file;

        }

        @Override
        public MediaType contentType() {
            // i want to upload only images
            if (mediaType == 4) {
                return MediaType.parse("audio/*");
            } else {
                return MediaType.parse("image/*");
            }

        }

        @Override
        public long contentLength() throws IOException {
            return mFile.length();
        }

        @Override
        public void writeTo(BufferedSink sink) throws IOException {
            long fileLength = mFile.length();
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            FileInputStream in = new FileInputStream(mFile);
            long uploaded = 0;

            try {
                int read;
                Handler handler = new Handler(Looper.getMainLooper());
                while ((read = in.read(buffer)) != -1) {

                    // update progress on UI thread
                    handler.post(new ProgressUpdater(uploaded, fileLength));
                    uploaded += read;
                    sink.write(buffer, 0, read);
                }
            } finally {
                in.close();
            }
        }
    }

    private class ProgressUpdater implements Runnable {
        private long mUploaded;
        private long mTotal;

        public ProgressUpdater(long uploaded, long total) {
            mUploaded = uploaded;
            mTotal = total;
        }

        @Override
        public void run() {
            int current_percent = (int) (100 * mUploaded / mTotal);
            int total_percent = (int) (100 * (totalFileUploaded + mUploaded) / totalFileLength);
            fileUploaderCallback.onProgressUpdate(current_percent, total_percent, uploadIndex + 1);
        }
    }
}