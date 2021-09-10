package com.example.humanityfirstcouncil.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.example.humanityfirstcouncil.R;
import com.example.humanityfirstcouncil.model.GoalsResponse;

import java.util.ArrayList;

public class DescriptionFragment extends Fragment {
    public View view;
    private int position;
    private ArrayList<GoalsResponse.GoalList> list;


    public DescriptionFragment(ArrayList<GoalsResponse.GoalList> list) {
        this.position = position;
        this.list = list;
    }

    FragmentManager manager;

    public DescriptionFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_description, container, false);
        ImageView arrow = view.findViewById(R.id.arrowImageView);
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<3;i++){
                    manager  = getActivity().getSupportFragmentManager();
                    manager.popBackStack();
                }
                DashboardFragment dashboardFragment = new DashboardFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.mainLayout,dashboardFragment);
                transaction.commit();
            }

        });



        return view;
    }
}
