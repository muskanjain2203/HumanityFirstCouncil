package com.example.humanityfirstcouncil.Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.humanityfirstcouncil.MainActivity;
import com.example.humanityfirstcouncil.R;

import com.example.humanityfirstcouncil.model.RegistrationRequest;
import com.example.humanityfirstcouncil.model.RegistrationResponse;
import com.example.humanityfirstcouncil.retrofit.API;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class SignUpFragment extends Fragment {
    public View view;
    void updateOnUiThread(Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }

    String bloodGroup,country;
    FragmentManager manager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        yourName =view.findViewById(R.id.nameEditText);
        yourEmail = view.findViewById(R.id.emailEditText);
        yourPassword = view.findViewById(R.id.passwordEditText);
        yourMobile = view.findViewById(R.id.mobileEditText);



        countryS = (Spinner) view.findViewById(R.id.countrySpinner);
        setupSpinner();

        bloodS = (Spinner) view.findViewById(R.id.bloodGroupSpinner);
        setBloodSpinner();


        Button submit = view.findViewById(R.id.submitButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               doTheServerSignUpCall();
            }

        });


        return view;
    }

    public Spinner countryS;

    private void setupSpinner() {
        ArrayAdapter countrySpinnerAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.countryArray, android.R.layout.simple_spinner_item);
        countrySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countryS.setAdapter(countrySpinnerAdapter);
        countryS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position).toString();
                country = selection;
                Toast.makeText(parent.getContext(), selection, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public Spinner bloodS;

    private void setBloodSpinner() {
        ArrayAdapter bloodGroupSpinnerAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.bloodArray, android.R.layout.simple_spinner_item);
        bloodGroupSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodS.setAdapter(bloodGroupSpinnerAdapter);
        bloodS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String bloodSelection = (String) parent.getItemAtPosition(position).toString();
                bloodGroup = bloodSelection;
                Toast.makeText(parent.getContext(), bloodSelection, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    EditText yourName;
    EditText yourEmail;
    EditText yourPassword;
    EditText yourMobile;

    MainActivity mainActivity;
    private void doTheServerSignUpCall() {
        new Thread(new Runnable() {
            @Override
            public void run() {



                try {

                    String userName = yourName.getText().toString();
                    String userPassword = yourPassword.getText().toString();
                    String userEmail = yourEmail.getText().toString();
                    String userMobile = yourMobile.getText().toString();


                    RegistrationRequest request = new RegistrationRequest();
                    request.setEmail(userEmail);
                    request.setName(userName);
                    request.setPassword(userPassword);
                    request.setMobileNumber(userMobile);
                    request.setCountry(country);
                    request.setBloodGroup(bloodGroup);

                    Call<RegistrationResponse> call = API.getApiServices().registration(request);
                    final Response<RegistrationResponse> response = call.execute();
                    updateOnUiThread(() -> handleResponse(response));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            private void handleResponse(Response<RegistrationResponse> response) {
                if (response.isSuccessful()) {
                    RegistrationResponse registrationResponse = response.body();
                    Log.e("registration -- status",registrationResponse.getErrorCode()+" "+registrationResponse.getErrorMessage());
                    if (registrationResponse != null) {
                        if (registrationResponse.getErrorCode().equalsIgnoreCase("0000")) {
                            manager = getActivity().getSupportFragmentManager();
                            while(manager.getBackStackEntryCount() >=0){
                                manager.popBackStack();
                            }
                            launchFragment(new LoginScreenFragment(),false);
                            Log.e("status",registrationResponse.getErrorCode()+" "+registrationResponse.getErrorMessage());
                            Toast.makeText(getActivity(),registrationResponse.getErrorMessage(),Toast.LENGTH_SHORT);
                        }
                        Toast.makeText(getActivity(),registrationResponse.getErrorMessage(),Toast.LENGTH_SHORT);
                    }
                }else {
                    Log.e("register -- status","response unsucessful");
                }
            }
        });

    }
    MainActivity mActivity;


    void launchFragment(Fragment fragment, boolean addBackStack) {
        mActivity.launchFragment(fragment, addBackStack);
    }
}
