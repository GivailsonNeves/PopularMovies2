package br.com.givailson.popularmovies.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.givailson.popularmovies.R;
import br.com.givailson.popularmovies.adapter.GridMovieAdapter;
import br.com.givailson.popularmovies.data.MovieFavoriteContract;
import br.com.givailson.popularmovies.model.Movie;
import br.com.givailson.popularmovies.model.MovieApiResult;
import br.com.givailson.popularmovies.retrofit.MovieApiService;
import br.com.givailson.popularmovies.retrofit.RetrofitMovieDB;
import br.com.givailson.popularmovies.views.MovieActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GridMovieFragment extends Fragment implements Callback<MovieApiResult>,GridMovieAdapter.OnItemClick, LoaderManager.LoaderCallbacks<Cursor> {

    private static final String SAVED_LAYOUT_MANAGER = "SAVED_LAYOUT_MANAGER";
    private static final String SELECTED_FILTER = "SELECTED_FILTER";

    @BindView(R.id.gv_movies_list) RecyclerView gridMovieList;
    @BindView(R.id.v_nodata) View vNoData;
    @BindView(R.id.v_loading) View vLoading;
    @BindView(R.id.btn_reload) Button btReload;

    private int selectedFilter;
    private GridMovieAdapter gridMovieAdapter;
    private List<Movie> movies;
    private final int CURSOR_LOADER_ID = 0;


    public GridMovieFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = prepareUI(inflater, container);
        if(!restoreState(savedInstanceState))
            this.orderBy(R.id.mn_popularity);

        return rootView;
    }

    public void orderBy(int itemId) {
        selectedFilter = itemId;
        this.loadMovies();
    }

    private void loadMovies() {


        if(selectedFilter == R.id.mn_favorites || selectedFilter == R.id.mn_popularity || selectedFilter == R.id.mn_rate) {
            if (isConnected()) {
                showLoading();

                Call<MovieApiResult> listMovie;

                if (selectedFilter == R.id.mn_rate) {
                    MovieApiService movieApiService = new RetrofitMovieDB(this.getContext())
                            .movieApiService();
                    listMovie = movieApiService.listTopRating();
                    listMovie.enqueue(this);

                } else if(selectedFilter == R.id.mn_popularity) {
                    MovieApiService movieApiService = new RetrofitMovieDB(this.getContext())
                            .movieApiService();
                    listMovie = movieApiService.listPopular();
                    listMovie.enqueue(this);

                } else {
                    getLoaderManager().restartLoader(CURSOR_LOADER_ID, null, this);

                }

            } else {
                this.showErrorMessage(getString(R.string.connection_fail));
            }
        }

    }

    @Override
    public void onResume() {
        if(selectedFilter == R.id.mn_favorites)
            getLoaderManager().restartLoader(CURSOR_LOADER_ID, null, this);

        super.onResume();
    }

    private View prepareUI(LayoutInflater inflater, ViewGroup container) {
        View rootView = inflater.inflate(R.layout.fragment_grid_movie, container, false);
        ButterKnife.bind(this, rootView);
        this.btReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderBy(selectedFilter);
            }
        });
        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        savedInstanceState.putParcelableArrayList(getString(R.string.movie_list_parceble_name), (ArrayList<Movie>) this.movies);
        savedInstanceState.putParcelable(SAVED_LAYOUT_MANAGER, gridMovieList.getLayoutManager().onSaveInstanceState());
        savedInstanceState.putInt(SELECTED_FILTER, selectedFilter);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        restoreState(savedInstanceState);
    }

    private boolean restoreState(@NonNull Bundle savedInstanceState) {
        if(savedInstanceState != null) {
            this.movies = savedInstanceState.getParcelableArrayList(getString(R.string.movie_list_parceble_name));
            mountList();

            Parcelable layoutStateSaved = savedInstanceState.getParcelable(SAVED_LAYOUT_MANAGER);

            gridMovieList.getLayoutManager().onRestoreInstanceState(layoutStateSaved);
            selectedFilter = savedInstanceState.getInt(SELECTED_FILTER);
            return true;
        }
        return false;
    }

    @Override
    public void onResponse(Call<MovieApiResult> call, Response<MovieApiResult> response) {
        if(response.code() == 200) {
            this.movies = response.body().results;
            this.hideLoading();
            mountList();
        } else {
          this.showErrorMessage(getString(R.string.error_loading_code) + response.code());
        }
    }

    private void mountList() {
        int totalRows = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 2 : 3;
        this.gridMovieList.setLayoutManager(new GridLayoutManager(getContext(), totalRows));

        gridMovieAdapter = new GridMovieAdapter(this.movies);
        gridMovieAdapter.setOnItemClick(this);
        gridMovieList.setAdapter(gridMovieAdapter);
    }

    @Override
    public void onFailure(Call<MovieApiResult> call, Throwable t) {
        this.showErrorMessage(getString(R.string.error_loading));
    }

    private void showLoading() {
        vLoading.setVisibility(View.VISIBLE);
        vNoData.setVisibility(View.GONE);
        gridMovieList.setVisibility(View.GONE);
    }

    private void hideLoading() {
        vLoading.setVisibility(View.GONE);
        gridMovieList.setVisibility(View.VISIBLE);
    }


    private void showErrorMessage(String message) {
        gridMovieList.setVisibility(View.GONE);
        vLoading.setVisibility(View.GONE);
        vNoData.setVisibility(View.VISIBLE);
        Toast.makeText(this.getContext(),message, Toast.LENGTH_LONG)
                .show();
    }

    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) this.getContext()
                .getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnected();
    }

    @Override
    public void handleClick(Movie movie) {
        Intent intentMovie = new Intent(this.getActivity(), MovieActivity.class);
        intentMovie.putExtra(getString(R.string.movie_parceble_name), movie);
        startActivity(intentMovie);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        CursorLoader cursorLoader = new CursorLoader(getActivity(),
                MovieFavoriteContract.MovieFavoriteEntry.CONTENT_URI,
                null,
                null,
                null,
                null);

        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {

        if(selectedFilter == R.id.mn_favorites) {
            this.movies = MovieFavoriteContract.listMovieFromCursor(cursor);
            if(this.movies.size() > 0) {
                this.hideLoading();
            } else {
                this.showErrorMessage(getString(R.string.no_favorites));
            }
            mountList();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        return;
    }
}
