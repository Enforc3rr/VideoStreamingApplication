package com.videostream.app.entities;

import org.springframework.data.mongodb.core.mapping.Document;
/*
ID
Thumbnail Name (same as name of the Video Generated)
ID of the video With which it is associated.
*/
@Document
public class ThumbnailEntity {
    private String thumbnailName;
    private String videoID ;

    public ThumbnailEntity() {
    }
    public ThumbnailEntity(String thumbnailName, String videoID) {
        this.thumbnailName = thumbnailName;
        this.videoID = videoID;
    }

    public String getThumbnailName() {
        return thumbnailName;
    }

    public void setThumbnailName(String thumbnailName) {
        this.thumbnailName = thumbnailName;
    }

    public String getVideoID() {
        return videoID;
    }

    public void setVideoID(String videoID) {
        this.videoID = videoID;
    }

    @Override
    public String toString() {
        return "ThumbnailEntity{" +
                "thumbnailName='" + thumbnailName + '\'' +
                ", videoID='" + videoID + '\'' +
                '}';
    }
}
