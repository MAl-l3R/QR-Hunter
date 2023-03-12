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
import com.example.snailscurlup.model.User;

import java.util.ArrayList;

public class UserAdapter extends ArrayAdapter<User> {

    private ArrayList<User> userList;
    private Context context;
    public UserAdapter(Context context, ArrayList<User> users){
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

        User user = userList.get(position);

//        TextView cityName = view.findViewById(R.id.city_text);
//        TextView provinceName = view.findViewById(R.id.province_text);
//
//        cityName.setText(city.getCityName());
//        provinceName.setText(city.getProvinceName());


        return view;

    }
}
