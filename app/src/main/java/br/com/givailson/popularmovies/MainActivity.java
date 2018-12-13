package br.com.givailson.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import br.com.givailson.popularmovies.fragment.GridMovieFragment;

public class MainActivity extends AppCompatActivity {

    private GridMovieFragment gmf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.gmf = (GridMovieFragment) getSupportFragmentManager().findFragmentById(R.id.movies_fragment);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        this.gmf.orderBy(item.getItemId());
        return false;
    }
}
