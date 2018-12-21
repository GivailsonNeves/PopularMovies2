package br.com.givailson.popularmovies.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.givailson.popularmovies.R;
import br.com.givailson.popularmovies.model.Video;
import br.com.givailson.popularmovies.model.VideoResult;
import retrofit2.Callback;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private List<Video> videos;
    private OnItemClick itemClickListener;

    public TrailerAdapter(List<Video> videos) {
        this.videos = videos;
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitle;
        public int position = 0;

        public TrailerViewHolder(View view)
        {
            super(view);
            this.tvTitle = view.findViewById(R.id.tvTitle);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if( itemClickListener != null) {
                        itemClickListener.handleClick(videos.get(position));
                    }
                }
            });

        }

        public void prepare(Video video) {
            this.tvTitle.setText(video.name);
        }
    }

    @NonNull
    @Override
    public TrailerAdapter.TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.trailer_item, viewGroup, false);
        TrailerAdapter.TrailerViewHolder vh = new TrailerAdapter.TrailerViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder trailerViewHolder, int i) {
        trailerViewHolder.position = i;
        trailerViewHolder.prepare(this.videos.get(i));
    }

    @Override
    public int getItemCount() {
        return this.videos.size();
    }

    public void setOnItemClick(OnItemClick handler) {
        this.itemClickListener = handler;
    }


    public interface OnItemClick{
        void handleClick(Video video);
    }

}
