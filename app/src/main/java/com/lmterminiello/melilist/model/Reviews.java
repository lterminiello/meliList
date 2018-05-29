
package com.lmterminiello.melilist.model;

import com.google.gson.annotations.SerializedName;


public class Reviews {

    @SerializedName("total")
    private Integer total;
    @SerializedName("rating_average")
    private Double ratingAverage;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Double getRatingAverage() {
        return ratingAverage;
    }

    public void setRatingAverage(Double ratingAverage) {
        this.ratingAverage = ratingAverage;
    }
}
