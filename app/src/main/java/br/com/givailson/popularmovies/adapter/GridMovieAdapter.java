package br.com.givailson.popularmovies.adapter;

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
import butterknife.ButterKnife;

public class GridMovieAdapter extends RecyclerView.Adapter<GridMovieAdapter.ViewHolderMovie> {

    private List<Movie> movies;

    public static class ViewHolderMovie extends RecyclerView.ViewHolder {
        private View view;
        private TextView tvTitle;
        private final String imageBasePath;
        private ImageView ivPoster;

        public ViewHolderMovie(View view) {
            super(view);
            imageBasePath = view.getContext().getString(R.string.grid_poster_url);
            this.view = view;
            this.tvTitle = this.view.findViewById(R.id.tvTitle);
            this.ivPoster = this.view.findViewById(R.id.ivPoster);
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
        viewHolderMovie.prepareData(movies.get(i));
    }

    @Override
    public int getItemCount() {
        return this.movies.size();
    }

}
