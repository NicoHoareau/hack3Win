package fr.nicolashoareau_toulousewcs.hack3win;

import android.widget.VideoView;

public class ContributeurModel {

    String video;
    String namevideo;
    String descriptionvideo;

    public ContributeurModel(String video, String namevideo, String descriptionvideo) {
        this.video = video;
        this.namevideo = namevideo;
        this.descriptionvideo = descriptionvideo;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getNamevideo() {
        return namevideo;
    }

    public void setNamevideo(String namevideo) {
        this.namevideo = namevideo;
    }

    public String getDescriptionvideo() {
        return descriptionvideo;
    }

    public void setDescriptionvideo(String descriptionvideo) {
        this.descriptionvideo = descriptionvideo;
    }
}
