package com.imagine.mohamedtaha.store;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/**
 * Created by ManasatPC on 09/01/18.
 */

public class ShowReportes extends Fragment {
    final static String ARG_POSITION = "position";
    int mCurrentPosition =-1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null){
            mCurrentPosition = savedInstanceState.getInt(ARG_POSITION);
        }
        //return inflater.inflate(R.layout, container, false);
    return null;
    }

}

























