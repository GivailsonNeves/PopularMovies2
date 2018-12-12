package br.com.givailson.popularmovies.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.givailson.popularmovies.R;
import br.com.givailson.popularmovies.adapter.GridMovieAdapter;
import br.com.givailson.popularmovies.model.Movie;
import br.com.givailson.popularmovies.model.MovieApiResult;
import br.com.givailson.popularmovies.retrofit.MovieApiService;
import br.com.givailson.popularmovies.retrofit.RetrofitMovieDB;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GridMovieFragment extends Fragment implements Callback<MovieApiResult> {

    @BindView(R.id.gv_movies_list) RecyclerView gridMovieList;
    @BindView(R.id.v_nodata) View vNoData;
    @BindView(R.id.v_loading) View vLoading;
  //  @BindView(R.id.btn_reload) Button btnReload;

    private int selectedFilter;

    public GridMovieFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = prepareUI(inflater, container);
        this.orderBy(R.id.mn_popularity);
        return rootView;
    }

    private void orderBy(int itemId) {
        selectedFilter = itemId;
        this.loadMovies();
    }

    private void loadMovies() {
        MovieApiService movieApiService = new RetrofitMovieDB(this.getContext())
                .movieApiService();

        Call<MovieApiResult> listMovie;
        if(selectedFilter == R.id.mn_rate)
            listMovie = movieApiService.listTopRating();
        else
            listMovie = movieApiService.listPopular();

        listMovie.enqueue(this);


    }

    private View prepareUI(LayoutInflater inflater, ViewGroup container) {
        View rootView = inflater.inflate(R.layout.fragment_grid_movie, container, false);
        ButterKnife.bind(this, rootView);
        this.gridMovieList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        return rootView;
    }

    @Override
    public void onResponse(Call<MovieApiResult> call, Response<MovieApiResult> response) {
        if(response.code() == 200) {
            GridMovieAdapter gridMovieAdapter = new GridMovieAdapter(response.body().results);
            gridMovieList.setAdapter(gridMovieAdapter);
        }
    }

    @Override
    public void onFailure(Call<MovieApiResult> call, Throwable t) {
        Log.i(GridMovieFragment.class.getName(), t.getMessage());
    }

}
