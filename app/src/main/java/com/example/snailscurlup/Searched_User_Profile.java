package com.example.snailscurlup;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.snailscurlup.ui.scan.QRGalleryAdapter;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.snailscurlup.R;
import com.example.snailscurlup.model.User;

import java.sql.Timestamp;

public class Searched_User_Profile extends AppCompatActivity {

    private Intent intent;
    private ImageButton backPageButton;
    private final int key = -1;
    private final String userKey = "User Object";
    private final String localID = "local Host's id";
    private final String timeStamp = "timeStamp";
    private String activeUserID;
    private User selectedUser;
    private TextView username_fieldprof;
    private TextView totalscore_fieldprof;
    private TextView codesscanned_fieldprof;
    private RecyclerView QrRecylerView;
    private QRGalleryAdapter qrGalleryAdapter;
    private String[] timeStampArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_user_profile);
        backPageButton = findViewById(R.id.backPage);
        username_fieldprof = findViewById(R.id.username_fieldprof);
        totalscore_fieldprof = findViewById(R.id.totalscore_fieldprof);
        codesscanned_fieldprof = findViewById(R.id.codesscanned_fieldprof);
        QrRecylerView = findViewById(R.id.QRGalleryRecyclerView);


        intent = getIntent();
        timeStampArray = intent.getStringArrayExtra(timeStamp);
        activeUserID = intent.getStringExtra(localID);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            selectedUser = intent.getSerializableExtra(userKey,User.class);
        }
        //int size = intent.getIntExtra("size",0);

        username_fieldprof.setText(selectedUser.getUsername());
        totalscore_fieldprof.setText(selectedUser.getTotalScore());
        String size = String.valueOf(selectedUser.getScannedInstanceQrCodes().size());
        codesscanned_fieldprof.setText(size);

        int a = selectedUser.getScannedQrCodes().size();

        for (int i=0;i<selectedUser.getScannedInstanceQrCodes().size();i++){
            selectedUser.getScannedInstanceQrCodes().get(i).setScanQRLogTimeStamp(Timestamp.valueOf(timeStampArray[i]));
        }

        QrRecylerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        qrGalleryAdapter = new QRGalleryAdapter(this, selectedUser.getScannedQrCodes());
        QrRecylerView.setAdapter(qrGalleryAdapter);







        backPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}