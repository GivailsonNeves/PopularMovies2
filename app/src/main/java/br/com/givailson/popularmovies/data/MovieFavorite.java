package br.com.givailson.popularmovies.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import br.com.givailson.popularmovies.model.Movie;

@Entity(tableName = MovieFavoriteContract.MovieFavoriteEntry.TABLE_NAME)
public class MovieFavorite {

    @ColumnInfo(name = MovieFavoriteContract.MovieFavoriteEntry.ID)
    @PrimaryKey
    public long id;

    @ColumnInfo(name = MovieFavoriteContract.MovieFavoriteEntry.TITLE)
    private String title;

    @ColumnInfo(name = MovieFavoriteContract.MovieFavoriteEntry.URI_PHOTO)
    private String uriPhoto;

    @ColumnInfo(name = MovieFavoriteContract.MovieFavoriteEntry.OVERVIEW)
    private String overview;

    @ColumnInfo(name = MovieFavoriteContract.MovieFavoriteEntry.RATE)
    private double rate;

    @ColumnInfo(name = MovieFavoriteContract.MovieFavoriteEntry.RELEASE_DATE)
    private String releaseDate;

    public MovieFavorite(long id, String title, String uriPhoto, String overview, double rate, String releaseDate) {
        this.id = id;
        this.title = title;
        this.uriPhoto = uriPhoto;
        this.overview = overview;
        this.rate = rate;
        this.releaseDate = releaseDate;
    }

    public static MovieFavorite fromContentValues(ContentValues values) {

        return new MovieFavorite(
                values.getAsLong(MovieFavoriteContract.MovieFavoriteEntry.ID),
                values.getAsString(MovieFavoriteContract.MovieFavoriteEntry.TITLE),
                values.getAsString(MovieFavoriteContract.MovieFavoriteEntry.URI_PHOTO),
                values.getAsString(MovieFavoriteContract.MovieFavoriteEntry.OVERVIEW),
                values.getAsDouble(MovieFavoriteContract.MovieFavoriteEntry.RATE),
                values.getAsString(MovieFavoriteContract.MovieFavoriteEntry.RELEASE_DATE)
        );
    }

    public static List<Movie> listMovieFromCursor(Cursor cursor) {
        List<Movie> listMoviews = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                listMoviews.add(
                    new Movie(
                        cursor.getLong(cursor.getColumnIndex(MovieFavoriteContract.MovieFavoriteEntry.ID)),
                        cursor.getString(cursor.getColumnIndex(MovieFavoriteContract.MovieFavoriteEntry.TITLE)),
                        cursor.getString(cursor.getColumnIndex(MovieFavoriteContract.MovieFavoriteEntry.URI_PHOTO)),
                        cursor.getString(cursor.getColumnIndex(MovieFavoriteContract.MovieFavoriteEntry.OVERVIEW)),
                        cursor.getDouble(cursor.getColumnIndex(MovieFavoriteContract.MovieFavoriteEntry.RATE)),
                        cursor.getString(cursor.getColumnIndex(MovieFavoriteContract.MovieFavoriteEntry.RELEASE_DATE))
                    )
                );
            }
        }
        return listMoviews;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUriPhoto() {
        return uriPhoto;
    }

    public void setUriPhoto(String uriPhoto) {
        this.uriPhoto = uriPhoto;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
