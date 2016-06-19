package android.com.mmassignment.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Ajit on 6/14/2016.
 */

public class Author implements Serializable{

    @SerializedName("original")
    public String author;
}
