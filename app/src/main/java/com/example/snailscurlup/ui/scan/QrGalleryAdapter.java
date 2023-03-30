package com.example.snailscurlup.ui.scan;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static java.text.DateFormat.getDateTimeInstance;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snailscurlup.R;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;




public class QrGalleryAdapter extends RecyclerView.Adapter<QrGalleryAdapter.Viewholder>{

    private final Context context;
   /*** commented out for new Abstract
    * private final ArrayList<QrCode> QRCodeArrayList; **/

   private final ArrayList<QRCodeInstanceNew> QRCodeArrayList;


    // Constructor
    public QrGalleryAdapter(Context context, ArrayList<QRCodeInstanceNew> QRCodeArrayList) {
        this.context = context;
        this.QRCodeArrayList= QRCodeArrayList;
    }

    @NonNull
    @Override
    public QrGalleryAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.qrcode_gallery_card, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QrGalleryAdapter.Viewholder holder, int position) {
        // to set data to textview and imageview of each card layout
        QRCodeInstanceNew singleqrcode = QRCodeArrayList.get(holder.getAdapterPosition());
        holder.QRCodeName.setText(singleqrcode.getName());
        holder.QRCodeScore.setText(String.valueOf(singleqrcode.getPointsInt()));

        // format timestamp with simple Date format
        String pattern = "E, dd MMM yyyy HH:mm:ss z";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String formattedTimestamp =  simpleDateFormat .format(singleqrcode.getScanQRLogTimeStamp());
        holder.QRCodeTimeStamp.setText(formattedTimestamp);
        Picasso.get().load(singleqrcode.getURL()).into(holder.QrCodeVisual);


        //to send item click on to view page
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                QRCodeInstanceNew clickedQrCodeItem = QRCodeArrayList.get(holder.getAdapterPosition());

                Intent intent = new Intent(context, QRCodeInfoFragment.class);
                intent.putExtra("hash", clickedQrCodeItem.getAbstractQR().getHash());
                intent.putExtra("url", clickedQrCodeItem.getAbstractQR().getURL());
                intent.putExtra("name", clickedQrCodeItem.getAbstractQR().getName());
                intent.putExtra("points", clickedQrCodeItem.getAbstractQR().getPointsInt());
                context.startActivity(intent);



            }
        });



    }

    @Override
    public int getItemCount() {
        // this method is used for showing number of card items in recycler view
        if (QRCodeArrayList == null) {
            return 0;
        } else {
            return QRCodeArrayList.size();
        }
    }

    // View holder class for initializing of your views such as TextView and Imageview
    public static class Viewholder extends RecyclerView.ViewHolder{
        private final ImageView QrCodeVisual;
        private final TextView QRCodeName;
        private final TextView QRCodeScore;

        private final TextView QRCodeTimeStamp;

        public Viewholder(@NonNull View itemView){
            super(itemView);
            QrCodeVisual= itemView.findViewById(R.id.qrcode_visual);
            QRCodeName= itemView.findViewById(R.id.qrcode_namefield);
            QRCodeScore= itemView.findViewById(R.id.qrcode_scorefield);
            QRCodeTimeStamp= itemView.findViewById(R.id.qrcode_timestampfield);
        }
    }
}
