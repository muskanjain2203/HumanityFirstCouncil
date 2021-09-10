package com.example.humanityfirstcouncil.Fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.humanityfirstcouncil.R;

public class SplashScreenFragment extends Fragment {
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_splash_screen,container,false);
        new CountDownTimer(2500 , 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                Fragment loginScreenFragment = new LoginScreenFragment();
                load(loginScreenFragment, false);
            }
        }.start();

        return view;

    }
    public void load(Fragment fragment, boolean bool){
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.mainLayout,fragment);
        if(bool) transaction.addToBackStack(null);
        transaction.commit();
    }
}
