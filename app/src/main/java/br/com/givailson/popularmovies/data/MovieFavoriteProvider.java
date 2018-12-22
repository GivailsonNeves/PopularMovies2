package br.com.givailson.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import br.com.givailson.popularmovies.model.Movie;

public class MovieFavoriteProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static final int MOVIE = 100;
    private static final int MOVIE_WITH_ID = 200;
    private MovieFavoriteDBHelper movieFavoriteDBHelper;

    public  static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieFavoriteContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, MovieFavoriteContract.MovieFavoriteEntry.TABLE_NAME, MOVIE);
        matcher.addURI(authority, MovieFavoriteContract.MovieFavoriteEntry.TABLE_NAME + "/#", MOVIE_WITH_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {

        movieFavoriteDBHelper = new MovieFavoriteDBHelper(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                return movieFavoriteDBHelper.getReadableDatabase().query(
                        MovieFavoriteContract.MovieFavoriteEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
            case MOVIE_WITH_ID:
                Cursor returnCursor = movieFavoriteDBHelper.getReadableDatabase().query(
                    MovieFavoriteContract.MovieFavoriteEntry.TABLE_NAME,
                    projection,
                    MovieFavoriteContract.MovieFavoriteEntry.ID + " = ?",
                    new String[] {String.valueOf(ContentUris.parseId(uri))},
                    null,
                    null,
                    sortOrder
                );
                return returnCursor;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case MOVIE:
                return MovieFavoriteContract.MovieFavoriteEntry.CONTENT_DIR_TYPE;
            case MOVIE_WITH_ID:
                return MovieFavoriteContract.MovieFavoriteEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = movieFavoriteDBHelper.getWritableDatabase();
        Uri returnUri;

        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                long id = db.insert(MovieFavoriteContract.MovieFavoriteEntry.TABLE_NAME, null, values);
                if(id > 0)
                    returnUri = MovieFavoriteContract.MovieFavoriteEntry.buildFlavorsUri(id);
                else
                    throw new android.database.SQLException("Failed to insert row into: " + uri);

                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        int numDeleted;
        final SQLiteDatabase db = movieFavoriteDBHelper.getWritableDatabase();

        switch (sUriMatcher.match(uri)) {
            case MOVIE_WITH_ID:
                numDeleted = db.delete(MovieFavoriteContract.MovieFavoriteEntry.TABLE_NAME,
                        MovieFavoriteContract.MovieFavoriteEntry.ID + " = ? ",
                        new String[] { String.valueOf(ContentUris.parseId(uri)) });
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return numDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }

}
