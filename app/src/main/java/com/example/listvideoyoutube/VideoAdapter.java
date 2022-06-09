package com.example.listvideoyoutube;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listvideoyoutube.my_interface.IClickItemVideoListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private ArrayList<Video> listVideo;
    private IClickItemVideoListener iClickItemVideoListener;
    public VideoAdapter(ArrayList<Video> listVideo, IClickItemVideoListener iClickItemVideoListener) {
        this.listVideo = listVideo;
        this.iClickItemVideoListener = iClickItemVideoListener;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view
                = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        Video video = listVideo.get(position);
        if (video == null) {
            return;
        }

        holder.tvName.setText(video.getTitle());
        Picasso.get().load(video.getThumbnail()).into(holder.ivImage);

        holder.llItemVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iClickItemVideoListener != null) {
                    int pos = holder.getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        iClickItemVideoListener.onClickItemVideo(pos);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listVideo != null) {
            return listVideo.size();
        }
        return 0;
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivImage;
        private TextView tvName;
        private LinearLayout llItemVideo;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_image);
            tvName = itemView.findViewById(R.id.tv_name);
            llItemVideo = itemView.findViewById(R.id.ll_item_video);
        }
    }
}
