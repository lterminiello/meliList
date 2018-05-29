
package com.lmterminiello.melilist.model; ;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Seller {

    @SerializedName("id")
    private Integer id;
    @SerializedName("power_seller_status")
    private Object powerSellerStatus;
    @SerializedName("car_dealer")
    private Boolean carDealer;
    @SerializedName("real_estate_agency")
    private Boolean realEstateAgency;
    @SerializedName("tags")
    private List<Object> tags = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getPowerSellerStatus() {
        return powerSellerStatus;
    }

    public void setPowerSellerStatus(Object powerSellerStatus) {
        this.powerSellerStatus = powerSellerStatus;
    }

    public Boolean getCarDealer() {
        return carDealer;
    }

    public void setCarDealer(Boolean carDealer) {
        this.carDealer = carDealer;
    }

    public Boolean getRealEstateAgency() {
        return realEstateAgency;
    }

    public void setRealEstateAgency(Boolean realEstateAgency) {
        this.realEstateAgency = realEstateAgency;
    }

    public List<Object> getTags() {
        return tags;
    }

    public void setTags(List<Object> tags) {
        this.tags = tags;
    }

}
