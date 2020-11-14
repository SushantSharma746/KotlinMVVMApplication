package com.example.samplekotlinapplication.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Docs (

    @SerializedName("id")
    @Expose
    var id: String,

    @SerializedName("publication_date")
    @Expose
    var publication_date: String,

    @SerializedName("article_type")
    @Expose
    var article_type: String,

    @SerializedName("abstract")
    @Expose
    var _abstract: List<String>


){

    override fun toString(): String {
        return "Docs(id='$id', publication_date='$publication_date', article_type='$article_type', _abstract=$_abstract)"
    }
}