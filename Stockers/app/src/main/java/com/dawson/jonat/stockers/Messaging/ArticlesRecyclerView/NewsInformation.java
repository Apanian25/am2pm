package com.dawson.jonat.stockers.Messaging.ArticlesRecyclerView;

/**
 * This class is used to hold the information of a news article
 */
public class NewsInformation {

    private String title;
    private String description;
    private String urlToArticle;


    /**
     * Contructor that sets the value of the title, description and urlToArticle property.
     *
     * @param title
     * @param description
     * @param urlToArticle
     */
    public NewsInformation(String title, String description, String urlToArticle) {
        this.title = title;
        this.description = description;
        this.urlToArticle = urlToArticle;
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

    public String getUrlToArticle() {
        return urlToArticle;
    }

    public void setUrlToArticle(String urlToArticle) {
        this.urlToArticle = urlToArticle;
    }
}
