package com.example.ajay.a_step;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.net.Uri;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.ArrayList;

/**
 * Created by Ajay on 6/9/2017.
 */

public class videoAdapter extends RecyclerView.Adapter<videoAdapter.ViewHolder>  {
    Context context;

    ProgressDialog pDialog;

    ArrayList<videoModel> flist=new ArrayList<>();
    public videoAdapter(Context context,ArrayList<videoModel>flist){
        this.context=context;
        this.flist=flist;
        pDialog = new ProgressDialog(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        flist.get(position);
        holder.title.setText(flist.get(position).getTitle());
        // holder.link.setText(flist.get(position).getLink());
        holder.VideoURL=flist.get(position).getLink();
        holder.playVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog.setTitle("Video Streaming");
// Set progressbar message
                pDialog.setMessage("Buffering...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(false);
// Show progressbar
                pDialog.show();
                try {
// Start the MediaController
                    MediaController mediacontroller = new MediaController(context);
                    mediacontroller.setAnchorView(holder.videoView);
// Get the URL from String VideoURL
                    Uri video = Uri.parse(holder.VideoURL);
                    holder.videoView.setMediaController(mediacontroller);
                    holder.videoView.setVideoURI(video);

                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
                holder.videoView.requestFocus();
                holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    // Close the progress bar and play the video
                    public void onPrepared(MediaPlayer mp) {

                        pDialog.dismiss();
                        holder.videoView.start();
                    }
                });
            }
        });


    }

    @Override
    public int getItemCount()
    {
        return flist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView title,link;
        String VideoURL;
        VideoView videoView;
        CardView cardView;
        ImageView playVideo;
        public ViewHolder(View itemView)
        {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.title);

            videoView= (VideoView) itemView.findViewById(R.id.videoview);
            cardView= (CardView) itemView.findViewById(R.id.cview);
            playVideo= (ImageView) itemView.findViewById(R.id.plaVideo);
        }
    }

}

