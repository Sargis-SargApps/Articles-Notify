package com.sargis.prueba1.model;

public class ArticleFav {
    private String ownerArticle;
    private String titleArticleFav;
    // yo no lo añado porque no tengo este dato
    //private String ownerName;
    //...


    public ArticleFav(String ownerArticle, String titleArticleFav) {
        this.ownerArticle = ownerArticle;
        this.titleArticleFav=titleArticleFav;

    }

    public String getOwnerArticle() {
        return ownerArticle;
    }

    public void setOwnerArticle(String ownerArticle) {
        this.ownerArticle = ownerArticle;
    }

    public String getTitleArticleFav() {
        return titleArticleFav;
    }

    public void setTitleArticleFav(String titleArticleFav) {
        this.titleArticleFav = titleArticleFav;
    }
}
