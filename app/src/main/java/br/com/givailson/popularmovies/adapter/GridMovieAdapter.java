package br.com.givailson.popularmovies.adapter;

import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import br.com.givailson.popularmovies.R;
import br.com.givailson.popularmovies.model.Movie;

public class GridMovieAdapter extends RecyclerView.Adapter<GridMovieAdapter.ViewHolderMovie> {

    private List<Movie> movies;
    private OnItemClick itemClickListener;

    public class ViewHolderMovie extends RecyclerView.ViewHolder {
        private View view;
        private TextView tvTitle;
        private final String imageBasePath;
        public int position = 0;
        private ImageView ivPoster;

        private static final int IMAGE_DEFAULT_HEIGHT = 750;
        private static final int IMAGE_DEFAULT_WIDTH = 500;

        public ViewHolderMovie(View view) {
            super(view);
            imageBasePath = view.getContext().getString(R.string.grid_poster_url);
            this.view = view;
            this.tvTitle = this.view.findViewById(R.id.tvTitle);
            this.ivPoster = this.view.findViewById(R.id.ivPoster);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if( itemClickListener != null)
                        itemClickListener.handleClick(movies.get(position));
                }
            });

            int windowSize = view.getResources().getDisplayMetrics().widthPixels;
            int totalRows = view.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 2 : 3;
            final int imageWidthSize = windowSize / totalRows;
            this.ivPoster.getLayoutParams().width = imageWidthSize;
            this.ivPoster.getLayoutParams().height = (int) ( IMAGE_DEFAULT_HEIGHT * ((float) imageWidthSize / IMAGE_DEFAULT_WIDTH) );
        }

        public void prepareData(Movie movie) {
            this.tvTitle.setText(movie.getTitle());
            String urlPoster = imageBasePath +
                    movie.getUriPhoto();
            Picasso.with(view.getContext()).load(urlPoster).into(this.ivPoster);
        }
    }

    public GridMovieAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolderMovie onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.grid_item_movie, viewGroup, false);
        ViewHolderMovie vh = new ViewHolderMovie(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMovie viewHolderMovie, int i) {
        viewHolderMovie.position = i;
        viewHolderMovie.prepareData(movies.get(i));
    }

    @Override
    public int getItemCount() {
        return this.movies.size();
    }


    public void setOnItemClick(OnItemClick handler) {
        this.itemClickListener = handler;
    }


    public interface OnItemClick{
        void handleClick(Movie movie);
    }

}
