package br.com.givailson.popularmovies.views;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.givailson.popularmovies.R;
import br.com.givailson.popularmovies.model.Movie;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieActivity extends AppCompatActivity {

    @BindView(R.id.ivPoster) ImageView ivPoster;
    @BindView(R.id.tvTitle) TextView tvTitle;
    @BindView(R.id.tvRate) TextView tvRate;
    @BindView(R.id.tvReview) TextView tvReview;
    @BindView(R.id.tvDuration) TextView tvDuration;
    @BindView(R.id.tvYear) TextView tvYear;

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
        } else {

        }
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
}
