package com.capstone.newmytripplanner.model.location;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LocationPOJO {
    @SerializedName("meta")
    public Meta meta;
    @SerializedName("documents")
    public List<Documents> documents;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<Documents> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Documents> documents) {
        this.documents = documents;
    }
}
