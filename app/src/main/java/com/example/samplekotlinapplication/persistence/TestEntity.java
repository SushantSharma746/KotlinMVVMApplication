package com.example.samplekotlinapplication.persistence;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Arrays;

@Entity(tableName = "testTable")
public class TestEntity {

    @PrimaryKey
    Long randomId;

    @ColumnInfo(name = "Id")
    private String Id;

    @ColumnInfo(name = "PublicationDate")
    private String PublicationDate;

    @ColumnInfo(name = "ArticleType")
    private String ArticleType;

    @ColumnInfo(name = "Abstract")
    private String Abstract;


    public TestEntity() {
    }


    public Long getRandomId() {
        return randomId;
    }

    public void setRandomId(Long randomId) {
        this.randomId = randomId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getPublicationDate() {
        return PublicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        PublicationDate = publicationDate;
    }

    public String getArticleType() {
        return ArticleType;
    }

    public void setArticleType(String articleType) {
        ArticleType = articleType;
    }

    public String getAbstract() {
        return Abstract;
    }

    public void setAbstract(String anAbstract) {
        Abstract = anAbstract;
    }
}