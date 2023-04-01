package com.example.snailscurlup.ui.scan;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.snailscurlup.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AbstractQRCommentsGalleryAdapter extends RecyclerView.Adapter<AbstractQRCommentsGalleryAdapter.ViewHolder> {

    private final Context context;
    private final ArrayList<AbstractQRComment> QRComments;

    public AbstractQRCommentsGalleryAdapter(Context context, ArrayList<AbstractQRComment> QRComments) {
        this.context = context;
        this.QRComments = QRComments;
    }

    @NonNull
    @Override
    public AbstractQRCommentsGalleryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_comment_card, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = parent.getWidth();
        view.setLayoutParams(layoutParams);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AbstractQRComment singleComment = QRComments.get(holder.getAdapterPosition());


        // Set username
        holder.commentUsername.setText(singleComment.getUsername());
        holder.commentMessage.setText(singleComment.getMessage());


        // format timestamp with simple Date format
        String pattern = "yyyy-MM-dd HH:mm:ssZ";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String formattedTimestamp = simpleDateFormat.format(singleComment.getTimestamp());
        holder.commentTimestamp.setText(formattedTimestamp);

    }

    @Override
    public int getItemCount() {
        // this method is used for showing number of card items in recycler view
        if (QRComments == null) {
            return 0;
        } else {
            return QRComments.size();
        }
    }


    // update comments and notify adapter when new comment is added
    public void setComments(ArrayList<AbstractQRComment> comments) {
        QRComments.clear();
        QRComments.addAll(comments);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //public final ImageView commentUserImage;
        public final TextView commentUsername;
        public final TextView commentMessage;
        public final TextView commentTimestamp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //commentUserImage = itemView.findViewById(R.id.comment_user_img);
            commentUsername = itemView.findViewById(R.id.comment_username);
            commentMessage = itemView.findViewById(R.id.comment_message);
            commentTimestamp = itemView.findViewById(R.id.comment_timestamp);
        }
    }
}
