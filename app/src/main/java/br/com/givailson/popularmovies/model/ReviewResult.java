package br.com.givailson.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewResult {

    @SerializedName("id")
    public int id;

    @SerializedName("results")
    public List<Review> reviews;

}
