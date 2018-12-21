package br.com.givailson.popularmovies.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import br.com.givailson.popularmovies.R;
import br.com.givailson.popularmovies.adapter.ReviewAdapter;
import br.com.givailson.popularmovies.adapter.TrailerAdapter;
import br.com.givailson.popularmovies.model.Movie;
import br.com.givailson.popularmovies.model.ReviewResult;
import br.com.givailson.popularmovies.model.Video;
import br.com.givailson.popularmovies.model.VideoResult;
import br.com.givailson.popularmovies.retrofit.MovieApiService;
import br.com.givailson.popularmovies.retrofit.RetrofitMovieDB;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MovieActivity extends AppCompatActivity {

    @BindView(R.id.ivPoster) ImageView ivPoster;
    @BindView(R.id.tvTitle) TextView tvTitle;
    @BindView(R.id.tvRate) TextView tvRate;
    @BindView(R.id.tvReview) TextView tvReview;
    @BindView(R.id.tvDuration) TextView tvDuration;
    @BindView(R.id.tvYear) TextView tvYear;
    @BindView(R.id.rvTrailers) RecyclerView trailersList;
    @BindView(R.id.tvNoTrailers) View tvNoTrailers;
    @BindView(R.id.tvNoReviews) TextView tvNoReviews;
    @BindView(R.id.tvResumes) TextView tvResumes;
    @BindView(R.id.rvResumes) RecyclerView rvResumes;

    private String basePathImage;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);
        this.basePathImage = getString(R.string.grid_poster_inner_url);
        prepareActionBar();
        prepareMovie();
    }

    private void prepareMovie() {
        this.movie = getIntent().getParcelableExtra(getString(R.string.movie_parceble_name));

        if (movie != null) {
            StringBuilder urlImage = new StringBuilder(basePathImage)
                    .append(movie.getUriPhoto());

            tvYear.setText(movie.getReleaseDate());
            tvTitle.setText(movie.getTitle());
            tvRate.setText(Double.toString(movie.getRate()) + getString(R.string.max_rate));
            tvDuration.setText("120min");
            tvReview.setText(movie.getOverview());

            Picasso.with(this).load(urlImage.toString())
                    .into(ivPoster);

            this.prepareTrailers();
            this.prepareReviews();
        } else {

        }
    }

    private void prepareTrailers() {
        MovieApiService movieApiService = new RetrofitMovieDB(this)
                .movieApiService();

        movieApiService.listTrailers(this.movie.id)
                .enqueue(new Callback<VideoResult>(){

                    @Override
                    public void onResponse(Call<VideoResult> call, Response<VideoResult> response) {
                        if(response.code() == 200 && response.body().videos.size() > 0) {
                            tvNoTrailers.setVisibility(View.GONE);
                            trailersList.setVisibility(View.VISIBLE);
                            TrailerAdapter adapter = new TrailerAdapter(response.body().videos);
                            adapter.setOnItemClick(new TrailerAdapter.OnItemClick() {
                                @Override
                                public void handleClick(Video video) {
                                    openYoutube(video);
                                }
                            });
                            trailersList.setLayoutManager(new LinearLayoutManager(getParent()));
                            trailersList.setAdapter(adapter);
                        } else {
                            showTrailersFail();
                        }
                    }

                    @Override
                    public void onFailure(Call<VideoResult> call, Throwable t) {
                        showTrailersFail();
                    }
                });
    }

    private void prepareReviews() {
        MovieApiService movieApiService = new RetrofitMovieDB(this)
                .movieApiService();

        movieApiService.listReviews(this.movie.id)
                .enqueue(new Callback<ReviewResult>() {
                    @Override
                    public void onResponse(Call<ReviewResult> call, Response<ReviewResult> response) {

                        if(response.code() == 200 && response.body().reviews.size() > 0) {
                            tvNoReviews.setVisibility(View.GONE);
                            rvResumes.setVisibility(View.VISIBLE);

                            ReviewAdapter reviewAdapter = new ReviewAdapter(response.body().reviews);
                            rvResumes.setLayoutManager(new LinearLayoutManager(getParent()));
                            rvResumes.setAdapter(reviewAdapter);

                        } else {
                          showReviewsFail();
                        }

                    }

                    @Override
                    public void onFailure(Call<ReviewResult> call, Throwable t) {
                        showReviewsFail();
                    }
                });
    }

    private void prepareActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    private void showTrailersFail() {
        tvNoTrailers.setVisibility(View.VISIBLE);
        trailersList.setVisibility(View.GONE);
    }

    private void showReviewsFail() {
        tvNoReviews.setVisibility(View.VISIBLE);
        rvResumes.setVisibility(View.GONE);
    }

    private void openYoutube(Video video) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube" + video.key));

        if(intent.resolveActivity(getPackageManager()) == null) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + video.key));
        }

        startActivity(intent);
    }
}
