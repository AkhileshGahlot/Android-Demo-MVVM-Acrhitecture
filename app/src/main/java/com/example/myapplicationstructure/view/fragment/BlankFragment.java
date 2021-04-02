package com.example.myapplicationstructure.view.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplicationstructure.R;
import com.example.myapplicationstructure.view.base_view.BaseFragment;

public class BlankFragment extends BaseFragment {


    public static BlankFragment newInstance() {
        return new BlankFragment();
    }

//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.blank_fragment, container, false);
//    }

    @Override
    public View onCreateViewPost(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.blank_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

    @Override
    public void onClick(View view) {

    }
}