package com.example.humanityfirstcouncil.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.humanityfirstcouncil.MainActivity;
import com.example.humanityfirstcouncil.R;
import com.example.humanityfirstcouncil.model.LoginRequest;
import com.example.humanityfirstcouncil.model.LoginResponse;
import com.example.humanityfirstcouncil.retrofit.API;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;


public class LoginScreenFragment extends Fragment {
    public View view;

    private Handler mHandler = new Handler(Looper.getMainLooper());

    void updateOnUiThread(Runnable runnable) {
        new Handler(Looper.getMainLooper()).post(runnable);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       view = inflater.inflate(R.layout.fragment_login_screen, container, false);

       email = view.findViewById(R.id.emailEditText);
       password = view.findViewById(R.id.passwordEditText);

        Button signIn = view.findViewById(R.id.signInButton);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doTheServerCall();
            }
        });

        TextView register = view.findViewById(R.id.registerTextView);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUpFragment signUpFragment = new SignUpFragment();
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.mainLayout, signUpFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

       return view;
    }

    EditText email;
    EditText password;


    private void doTheServerCall() {
        new Thread(new Runnable() {
            @Override
            public void run() {



                try {

                    String userName = email.getText().toString();
                    String userPassword = password.getText().toString();


                    LoginRequest request = new LoginRequest();
                    request.setEmail(userName);
                    request.setPassword(userPassword);
                    Call<LoginResponse> call = API.getApiServices().login(request);
                    final Response<LoginResponse> response = call.execute();
                    updateOnUiThread(() -> handleResponse(response));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            private void handleResponse(Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    Log.e("login -- status",loginResponse.getErrorCode()+" "+loginResponse.getErrorMessage());
                    if (loginResponse != null) {
                        if (loginResponse.getErrorCode().equalsIgnoreCase("0000")) {

                            SharedPreferences isLogin = getActivity().getSharedPreferences("userID", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = isLogin.edit();
                            editor.putInt("userID",loginResponse.getUserId());
                            editor.commit();
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    FragmentManager manager = getActivity().getSupportFragmentManager();
                                    Fragment das = new DashboardFragment();
                                    FragmentTransaction transaction = manager.beginTransaction();
                                    transaction.replace(R.id.mainLayout,das);
                                    transaction.commit();
                                }
                            });



                        }
                            Toast.makeText(getActivity(),loginResponse.getErrorMessage(),Toast.LENGTH_SHORT);
                            Log.e("status",loginResponse.getErrorCode()+" "+loginResponse.getErrorMessage());
                       }
                        Toast.makeText(getActivity(),loginResponse.getErrorMessage(),Toast.LENGTH_SHORT);
                    }
                else {
                    Log.e("login -- status","response unsucessful");
                }
            }

        }).start();

    }
    protected MainActivity mActivity;
    void launchFragment(Fragment fragment, boolean addBackStack) {
        mActivity.launchFragment(fragment, addBackStack);
    }
}