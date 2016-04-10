
package com.example.ankursingh.shaeredelementdemo.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Aspect {

    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("type")
    @Expose
    private String type;

    /**
     * 
     * @return
     *     The rating
     */
    public Integer getRating() {
        return rating;
    }

    /**
     * 
     * @param rating
     *     The rating
     */
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

}
