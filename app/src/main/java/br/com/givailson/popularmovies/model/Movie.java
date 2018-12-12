package br.com.givailson.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Movie implements Parcelable {

    @SerializedName("id")
    public long id;

    @SerializedName("title")
    private String title;

    @SerializedName("poster_path")
    private String uriPhoto;

    @SerializedName("overview")
    private String overview;

    @SerializedName("vote_average")
    private double rate;

    @SerializedName("release_date")
    private String releaseDate;

    @Override
    public int describeContents() {
        return 0;
    }

    public Movie () {}

    public Movie(long id, String title, String uriPhoto, String overview, double rate, String releaseDate) {
        this.id = id;
        this.title = title;
        this.uriPhoto = uriPhoto;
        this.overview = overview;
        this.rate = rate;
        this.releaseDate = releaseDate;
    }

    public Movie(Parcel in) {
        id = in.readLong();
        title = in.readString();
        uriPhoto = in.readString();
        overview = in.readString();
        rate = in.readDouble();
        releaseDate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(uriPhoto);
        dest.writeString(overview);
        dest.writeDouble(rate);
        dest.writeString(releaseDate);
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

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

    public void setOverview(String sinopse) {
        this.overview = sinopse;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getReleaseDate() { return releaseDate; }

    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }

}
