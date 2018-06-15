package fr.nicolashoareau_toulousewcs.hack3win;

import android.graphics.drawable.Drawable;

public class ArticleModel {
    private String title;
    private String resume;
    private String url;
    private int miniature;
    private String site;

    public ArticleModel(String title, String resume, String url, int miniature, String site) {
        this.title = title;
        this.resume = resume;
        this.url = url;
        this.miniature = miniature;
        this.site = site;
    }

    private String coordonate;

    public ArticleModel(String title, String resume, String url, String site, String coordonate) {
        this.title = title;
        this.resume = resume;
        this.url = url;
        this.site = site;
        this.coordonate = coordonate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getMiniature() {
        return miniature;
    }

    public void setMiniature(int miniature) {
        this.miniature = miniature;
    }

    public String getSite() {
        return site;
    }

    public void getSite(String site) {
        this.site = site;
    }
}
