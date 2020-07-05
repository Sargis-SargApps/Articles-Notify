package com.sargis.prueba1.model;


import com.google.firebase.firestore.Exclude;

public class Article {

    private String title;
    private String description;
    private String ownerID;
    private String ownerName;
    @Exclude
    private String idArticle;
    //TODO evitar que se guarde el id del articulo en su propio documento es redudante he inecesario.

    public Article(String title, String description, String ownerID,String ownerName,String idArticle) {
        this.title = title;
        this.description = description;
        this.ownerID=ownerID;
        this.ownerName=ownerName;
        this.idArticle=idArticle;
    }


    public Article(String title, String description, String ownerID,String ownerName) {
        this.title = title;
        this.description = description;
        this.ownerID=ownerID;
        this.ownerName=ownerName;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }


    public String getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(String idArticle) {
        this.idArticle = idArticle;
    }
}
