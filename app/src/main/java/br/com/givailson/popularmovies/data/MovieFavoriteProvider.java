package br.com.givailson.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class MovieFavoriteProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static final int MOVIE = 100;
    private static final int MOVIE_WITH_ID = 200;
    private MovieFavoriteDao movieFavoriteDao;

    public  static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieFavoriteContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, MovieFavoriteContract.MovieFavoriteEntry.TABLE_NAME, MOVIE);
        matcher.addURI(authority, MovieFavoriteContract.MovieFavoriteEntry.TABLE_NAME + "/#", MOVIE_WITH_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        AppDatabase db = AppDatabase.getDatabase(getContext());
        movieFavoriteDao = db.movieFavoriteDao();

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                return movieFavoriteDao.listAll();
            case MOVIE_WITH_ID:
                return movieFavoriteDao.getById(ContentUris.parseId(uri));
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

        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                long _id = movieFavoriteDao.insert(MovieFavorite.fromContentValues(values));
                if (_id > 0) {
                    return MovieFavoriteContract.MovieFavoriteEntry.buildFlavorsUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into: " + uri);
                }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                return movieFavoriteDao.deleteById(ContentUris.parseId(uri));
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }
}
