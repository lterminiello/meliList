package com.lmterminiello.melilist.model;

import com.google.gson.annotations.SerializedName;

public class Description {

    @SerializedName("id")
    private String id;
    @SerializedName("text")
    private String text;
    @SerializedName("plain_text")
    private String plainText;
    @SerializedName("last_updated")
    private String lastUpdated;
    @SerializedName("date_created")
    private String dateCreated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPlainText() {
        return plainText;
    }

    public void setPlainText(String plainText) {
        this.plainText = plainText;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

}
