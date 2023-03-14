package com.example.snailscurlup.ui.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.snailscurlup.R;

import java.util.ArrayList;

public class UserAdapter extends ArrayAdapter<MockUser> {

    private ArrayList<MockUser> userList;
    private Context context;
    public UserAdapter(Context context, ArrayList<MockUser> users){
        super(context,0, users);
        this.context = context;
        this.userList = users;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.search_user_bar, parent,false);
        }

        MockUser user = userList.get(position);

        TextView userName = view.findViewById((R.id.username));
        TextView totalScore = view.findViewById(R.id.totalScore);
        TextView totalQR = view.findViewById(R.id.totalQR);

        userName.setText(user.getName());
        totalScore.setText(user.getTotalScore());



        return view;

    }
}
