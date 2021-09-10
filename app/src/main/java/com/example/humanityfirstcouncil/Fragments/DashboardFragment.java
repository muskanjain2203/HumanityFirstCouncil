package com.example.humanityfirstcouncil.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.humanityfirstcouncil.Adapter.RecyclerViewSustainableAdapter;
import com.example.humanityfirstcouncil.Card.SustainableItemCard;
import com.example.humanityfirstcouncil.R;
import com.example.humanityfirstcouncil.model.GoalsRequest;
import com.example.humanityfirstcouncil.model.GoalsResponse;

import com.example.humanityfirstcouncil.retrofit.API;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

public class DashboardFragment extends Fragment {
    public View view;
    void updateOnUiThread(Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }

    RecyclerViewSustainableAdapter recyclerViewSustainableAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        text = view.findViewById(R.id.foodForAllTextView);
        image = view.findViewById(R.id.foodImageView);

        ArrayList<SustainableItemCard> sustainableItemCards  = new ArrayList<>();
        for (int i=0;i<17;i++) {
            sustainableItemCards.add(new SustainableItemCard(R.drawable.food_image, "Food For All"));
        }

        Log.e("manan","calling goalsCall");



        RecyclerView sustainableItemRecyclerView = view.findViewById(R.id.sustainableDevelopmentRecyclerView);
        GridLayoutManager layoutManagerCause = new GridLayoutManager(getActivity(), 2);
        sustainableItemRecyclerView.setHasFixedSize(true);
        sustainableItemRecyclerView.setLayoutManager(layoutManagerCause);

        RecyclerViewSustainableAdapter recyclerViewSustainableAdapter = new RecyclerViewSustainableAdapter(getActivity());
        recyclerViewSustainableAdapter.onLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                DescriptionFragment descriptionFragment = new DescriptionFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.mainLayout,descriptionFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                return true;
            }
        });
        sustainableItemRecyclerView.setAdapter(recyclerViewSustainableAdapter);

        doTheServerGoalsCall();

        return view;
    }
    TextView text;
    ImageView image;
    private void doTheServerGoalsCall() {
        Log.e("manan","running  goalsCall");

        new Thread(new Runnable() {
            @SuppressLint("LongLogTag")
            @Override
            public void run() {
                try {

                    GoalsRequest request = new GoalsRequest();
                    SharedPreferences isLogin = getActivity().getSharedPreferences("userID",Context.MODE_PRIVATE);
                    request.setUserId(isLogin.getInt("userID",0));
                    Call<GoalsResponse> call = API.getApiServices().goals(request);
                    final Response<GoalsResponse> response = call.execute();
                    updateOnUiThread(() -> handleResponse(response));
                } catch (IOException e) {
                    Log.e("dashborad status --  fragment ","dashboard server unsucessful");
                }

            }

            @SuppressLint("LongLogTag")
            private void handleResponse(Response<GoalsResponse> response) {
                if (response.isSuccessful()) {
                    GoalsResponse goalsResponse = response.body();
                    if (goalsResponse != null) {
                        ArrayList<GoalsResponse.GoalList> goalLists = goalsResponse.getList();
                        if(goalLists != null && !goalLists.isEmpty()){
                            recyclerViewSustainableAdapter.setCategoryItemList(goalLists);
                            recyclerViewSustainableAdapter.notifyDataSetChanged();
                        }
                    }
                }else {
                    Log.e("dashborad --  fragment ","dashboard server unsucessful");
                }
            }
        });

    }
}
