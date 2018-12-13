package br.com.givailson.popularmovies.model;

import com.google.gson.annotations.SerializedName;

public class Video {

    @SerializedName("id")
    public String id;

    @SerializedName("key")
    public String key;

    @SerializedName("name")
    public String name;
}
