package com.example.snailscurlup.ui.leaderboard;

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

public class LeaderList extends ArrayAdapter<Leader> {

    private ArrayList<Leader> userList;
    private Context context;
    public LeaderList(Context context, ArrayList<Leader> users){
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
            view = LayoutInflater.from(context).inflate(R.layout.fragment_list_content, parent,false);
        }

        Leader user = userList.get(position);


        //ImageView image = view.findViewById(R.id.leader_image);
        TextView Name = view.findViewById(R.id.leader_name);
        TextView score = view.findViewById(R.id.leader_score);
        TextView leaderIndex = view.findViewById(R.id.leader_index);


        //image.setImageIcon(user.getUsername());
        Name.setText(user.getUsername());
        score.setText("Score: "+user.getEmail());
        leaderIndex.setText(Integer.toString(position+1));
        return view;

    }
}