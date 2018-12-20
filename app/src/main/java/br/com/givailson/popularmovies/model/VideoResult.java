package br.com.givailson.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoResult {

    @SerializedName("id")
    public int id;

    @SerializedName("results")
    public List<Video> videos;
}
