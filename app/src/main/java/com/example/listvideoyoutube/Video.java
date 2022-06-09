package com.example.listvideoyoutube;

public class Video {
    private String title, idVideo, thumbnail;

    public Video(String title, String idVideo, String thumbnail) {
        this.title = title;
        this.idVideo = idVideo;
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIdVideo() {
        return idVideo;
    }

    public void setIdVideo(String idVideo) {
        this.idVideo = idVideo;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
