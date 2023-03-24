package com.example.snailscurlup.ui.leaderboard;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.snailscurlup.R;
import com.example.snailscurlup.model.User;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LeaderboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LeaderboardFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    ListView leaderboardList;
    ArrayAdapter<Leader> leaderAdapter;
    ArrayList<Leader> leaderDataList;
    Button cumulativeButton;
    Button TopQrButton;
    FirebaseFirestore db;

    public LeaderboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LeaderboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LeaderboardFragment newInstance(String param1, String param2) {
        LeaderboardFragment fragment = new LeaderboardFragment();
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
        View view = inflater.inflate(R.layout.fragment_leaderboard,container, false);
        cumulativeButton = view.findViewById(R.id.buttonUserList);   //button
        TopQrButton = view.findViewById(R.id.buttonQRList);

        leaderboardList = view.findViewById(R.id.leader_list);
        leaderDataList = new ArrayList<>();
        leaderAdapter = new com.example.snailscurlup.ui.leaderboard.LeaderList(getActivity(), leaderDataList);
        leaderboardList.setAdapter(leaderAdapter);

        db = FirebaseFirestore.getInstance();

        cumulativeButton.setBackgroundColor(Color.YELLOW);
        TopQrButton.setBackgroundColor(Color.CYAN);


        Query User_leaderboard = db.collection("Users").orderBy("Total Score", Query.Direction.DESCENDING);
        Query QR_leaderboard = db.collection("QR").orderBy("score", Query.Direction.DESCENDING);

        User_leaderboard.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                leaderDataList.clear();
                for (QueryDocumentSnapshot doc : value) {
                    String userName = doc.getId();
                    String Score = (String) doc.getData().get("Total Score");
                    String phone = (String) doc.getData().get("PhoneNumber");
                    leaderDataList.add(new Leader(userName, Score, phone));
                }
                leaderAdapter.notifyDataSetChanged();
            }
        });

        cumulativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cumulativeButton.setBackgroundColor(Color.YELLOW);
                TopQrButton.setBackgroundColor(Color.CYAN);
                User_leaderboard.addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        leaderDataList.clear();
                        for (QueryDocumentSnapshot doc : value) {
                            String userName = doc.getId();
                            String Score = (String) doc.getData().get("Total Score");
                            String phone = (String) doc.getData().get("PhoneNumber");
                            leaderDataList.add(new Leader(userName, Score, phone));
                        }
                        leaderAdapter.notifyDataSetChanged();
                    }
                });
            }
        });


        TopQrButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               TopQrButton.setBackgroundColor(Color.YELLOW);
               cumulativeButton.setBackgroundColor(Color.CYAN);
               QR_leaderboard.addSnapshotListener(new EventListener<QuerySnapshot>() {
                   @Override
                   public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                       leaderDataList.clear();
                       for (QueryDocumentSnapshot doc : value) {
                           String city = doc.getId();
                           String province = (String) doc.getData().get("score");
                           String phone = (String) doc.getData().get("PhoneNumber");
                           leaderDataList.add(new Leader(city, province, phone));
                       }
                       leaderAdapter.notifyDataSetChanged();
                   }
               });
           }
       }
        );

        // Inflate the layout for this fragment
        return view;
    }
}