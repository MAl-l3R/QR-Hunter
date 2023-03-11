package com.example.snailscurlup.ui.Startup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.snailscurlup.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link com.example.snailscurlup.ui.Startup.StartUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StartUpFragment extends Fragment {

        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private static final String ARG_PARAM1 = "param1";
        private static final String ARG_PARAM2 = "param2";

        // TODO: Rename and change types of parameters
        private String mParam1;
        private String mParam2;

        private Button createAccountButton;
        private Button loginAccountButton;

        View view;

        public StartUpFragment() {
                // Required empty public constructor
        }

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MapFragment.
         */
        // TODO: Rename and change types and number of parameters
        public static StartUpFragment newInstance(String param1, String param2) {
                StartUpFragment fragment = new  StartUpFragment();
                Bundle args = new Bundle();
                args.putString(ARG_PARAM1, param1);
                args.putString(ARG_PARAM2, param2);
                fragment.setArguments(args);
                return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {

                super.onCreate(savedInstanceState);

                if (getArguments() != null) {
                        mParam1 = getArguments().getString(ARG_PARAM1);
                        mParam2 = getArguments().getString(ARG_PARAM2);
                }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

                view = inflater.inflate(R.layout.fragment_startup, container, false);
                createAccountButton = view.findViewById(R.id.create_account_button);
                loginAccountButton = view.findViewById(R.id.login_account_button);

                createAccountButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                // Handle login button click here
                                // Go back to scan fragment
                                Fragment fragment = new CreateAccountFragment();
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frameLayout, fragment);
                                fragmentTransaction.addToBackStack("StartupFragment");
                                fragmentTransaction.commit();

                        }
                });

                loginAccountButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                // Handle login button click here
                                // Go back to scan fragment
                                Fragment fragment = new LoginFragment();
                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.frameLayout, fragment);
                                fragmentTransaction.addToBackStack(null); // add the fragment to the back stack
                                fragmentTransaction.commit();

                        }
                });

                // Inflate the layout for this fragment
                return view;
        }
}