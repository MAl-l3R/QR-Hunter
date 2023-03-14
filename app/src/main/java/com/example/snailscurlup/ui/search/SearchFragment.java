package com.example.snailscurlup.ui.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;


import com.example.snailscurlup.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<MockUser> userList;
    private ArrayAdapter<MockUser> userAdapter;

    private ListView listView;
    private MockUser mockUser;

    private ImageButton searchButton;

    private FirebaseFirestore db;
    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
        userList = new ArrayList<MockUser>();
        userAdapter = new UserAdapter(getActivity(),userList);

        // load the User file and put it in UserList here
    }
    String value;
    public String createSearchResult(String input, View view){
        db = FirebaseFirestore.getInstance();
        CollectionReference reference =  db.collection("Users");
        Query searchQuery;
        if (input.equals("")){
            searchQuery = reference;
        }else{
            searchQuery = reference.orderBy(FieldPath.documentId()).startAt(input).endAt(input+"\uf8ff");
        }

        searchQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent( QuerySnapshot queryDocumentSnapshots,
                        FirebaseFirestoreException error) {
                    userList.clear();
                    for(QueryDocumentSnapshot doc: queryDocumentSnapshots)
                    {

                        String name = doc.getId();
                        String email = (String) doc.getData().get("Email");
                        String phone = (String)doc.getData().get("PhoneNumber");
                        String totalScore = doc.getData().get("Total Score").toString();
                        MockUser user1 = new MockUser(name,email,phone,totalScore);
                        userList.add(user1);

                    }
                    userAdapter.notifyDataSetChanged();
                }
            });
        return value;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search,container, false);
        listView = view.findViewById(R.id.user_list);
        listView.setAdapter(userAdapter);

        searchButton = view.findViewById(R.id.search_button);




        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                MockUser mu = new MockUser("chen","email","778302",5);
//                userList.add(mu);
//                userAdapter.notifyDataSetChanged();
                EditText inputField = view.findViewById(R.id.search_bar_input_text);
                String input = inputField.getText().toString();


                createSearchResult(input,view);
                //startAt(input).endAt(input+"\uf8ff")
            }
        });

        // Inflate the layout for this fragment
        return view;

    }
}