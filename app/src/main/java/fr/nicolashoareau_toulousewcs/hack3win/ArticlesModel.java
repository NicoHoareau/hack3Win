package fr.nicolashoareau_toulousewcs.hack3win;

public class ArticlesModel {

    private String nameArticle;
    private String urlVideoArticle;
    private String urlArticles;
    private String resumeArticles;

    public ArticlesModel(String nameArticle, String urlVideoArticle, String urlArticles, String resumeArticles) {
        this.nameArticle = nameArticle;
        this.urlVideoArticle = urlVideoArticle;
        this.urlArticles = urlArticles;
        this.resumeArticles = resumeArticles;
    }

    public ArticlesModel() {

    }

    public String getNameArticle() {
        return nameArticle;
    }

    public void setNameArticle(String nameArticle) {
        this.nameArticle = nameArticle;
    }

    public String getUrlVideoArticle() {
        return urlVideoArticle;
    }

    public void setUrlVideoArticle(String urlVideoArticle) {
        this.urlVideoArticle = urlVideoArticle;
    }

    public String getUrlArticles() {
        return urlArticles;
    }

    public void setUrlArticles(String urlArticles) {
        this.urlArticles = urlArticles;
    }

    public String getResumeArticles() {
        return resumeArticles;
    }

    public void setResumeArticles(String resumeArticles) {
        this.resumeArticles = resumeArticles;
    }
}
