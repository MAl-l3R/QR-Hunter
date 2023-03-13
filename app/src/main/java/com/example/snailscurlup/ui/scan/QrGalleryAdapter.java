package com.example.snailscurlup.ui.scan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snailscurlup.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;




public class QrGalleryAdapter extends RecyclerView.Adapter<QrGalleryAdapter.Viewholder>{

    private final Context context;
    private final ArrayList<QrCode> QRCodeArrayList;

    // Constructor
    public QrGalleryAdapter(Context context, ArrayList<QrCode> QRCodeArrayList) {
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
        QrCode singleqrcode = QRCodeArrayList.get(position);
        holder.QRCodeName.setText(singleqrcode.getName());
        holder.QRCodeScore.setText(String.valueOf(singleqrcode.getPointsInt()));
        Picasso.get().load(singleqrcode.getURL()).into(holder.QrCodeVisual);
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

        public Viewholder(@NonNull View itemView){
            super(itemView);
            QrCodeVisual= itemView.findViewById(R.id.qrcode_visual);
            QRCodeName= itemView.findViewById(R.id.qrcode_namefield);
            QRCodeScore= itemView.findViewById(R.id.qrcode_scorefield);
        }
    }
}
