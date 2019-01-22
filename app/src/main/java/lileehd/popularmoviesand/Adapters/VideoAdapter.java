package lileehd.popularmoviesand.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import lileehd.popularmoviesand.Models.Video;
import lileehd.popularmoviesand.R;
import lileehd.popularmoviesand.Utils.OnItemClickListener;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {
    private Context mContext;
    private ArrayList<Video> mVideoList;
    private OnItemClickListener mListener;

    public VideoAdapter(Context context) {
        this.mContext = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void setmVideoList(ArrayList<Video> videos) {
        mVideoList = videos;
    }

    public Video getVideoFrom(int position) {
        return this.mVideoList.get(position);
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View videoView = LayoutInflater.from(mContext).inflate(R.layout.trailer_item, parent, false);
        return new VideoViewHolder(videoView);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        Video currentVideo = mVideoList.get(position);
        holder.mVideoView.setText(currentVideo.getName());
    }

    @Override
    public int getItemCount() {
        return mVideoList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        public TextView mVideoView;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            mVideoView = itemView.findViewById(R.id.videoTitleView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onVideoClick(position);
                        }
                    }
                }
            });
        }
    }

}
