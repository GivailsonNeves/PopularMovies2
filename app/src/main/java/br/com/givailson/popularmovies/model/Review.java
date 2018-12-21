package br.com.givailson.popularmovies.model;

import com.google.gson.annotations.SerializedName;

public class Review {

    @SerializedName("id")
    public long id;

    @SerializedName("author")
    public String author;

    @SerializedName("content")
    public String content;

    @SerializedName("url")
    public String url;
}
