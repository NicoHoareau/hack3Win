package fr.nicolashoareau_toulousewcs.hack3win;

import android.widget.VideoView;

public class ContributeurModel {

    String video;
    String title;
    String resume;

    public ContributeurModel(String video, String title, String resume) {
        this.video = video;
        this.title = title;
        this.resume = resume;
    }

    public ContributeurModel(){

    }
    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
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
}
