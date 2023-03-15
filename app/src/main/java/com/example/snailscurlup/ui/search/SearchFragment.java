package com.example.snailscurlup.ui.search;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;


import com.example.snailscurlup.R;
import com.example.snailscurlup.ui.scan.QrCode;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.example.snailscurlup.model.User;

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

    private ArrayList<User> userList;
    private ArrayAdapter<User> userAdapter;

    private ListView listView;

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
        userList = new ArrayList<User>();
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
                        CollectionReference collection = db.collection("Users").document(doc.getId()).collection("QRList");
                        User user1 = new User(name,email,phone,totalScore);
                        collection.addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                for (QueryDocumentSnapshot dc: value){
                                    String qr = dc.getId();
                                    QrCode code = new QrCode(qr);
                                    user1.addScannedQrCodes(code);
                                }
                            }
                        });
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