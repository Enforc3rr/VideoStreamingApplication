package com.videostream.app.entities;

import org.springframework.data.mongodb.core.mapping.Document;
/*
ID
File Name
Video Length
Uploaded By
Title of the video
Caption Associated With It
Genre Of the video
Upload Time
Likes on the Video
Dislikes on the Video
*/

@Document
public class FileEntity {
    private String _id;
    private String fileName;
    private long videoLength;
    private String videoUploadedBy;
    private String titleOfVideo;
    private String captionOfVideo;
    private String uploadTime;
    private String genreOfVideo;
    private long likesOnVideo;
    private long dislikesOnVideo;


    public FileEntity(){ }

    public FileEntity(String fileName, long videoLength, String videoUploadedBy, String titleOfVideo, String captionOfVideo,
                      String uploadTime, String genreOfVideo) {
        this.fileName = fileName;
        this.videoLength = videoLength;
        this.videoUploadedBy = videoUploadedBy;
        this.titleOfVideo = titleOfVideo;
        this.captionOfVideo = captionOfVideo;
        this.uploadTime = uploadTime;
        this.genreOfVideo = genreOfVideo;
    }

    public String getTitleOfVideo() {
        return titleOfVideo;
    }

    public void setTitleOfVideo(String titleOfVideo) {
        this.titleOfVideo = titleOfVideo;
    }

    public String getGenreOfVideo() {
        return genreOfVideo;
    }

    public void setGenreOfVideo(String genreOfVideo) {
        this.genreOfVideo = genreOfVideo;
    }

    public long getLikesOnVideo() {
        return likesOnVideo;
    }

    public void setLikesOnVideo(long likesOnVideo) {
        this.likesOnVideo = likesOnVideo;
    }

    public long getDislikesOnVideo() {
        return dislikesOnVideo;
    }

    public void setDislikesOnVideo(long dislikesOnVideo) {
        this.dislikesOnVideo = dislikesOnVideo;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getVideoLength() {
        return videoLength;
    }

    public void setVideoLength(long videoLength) {
        this.videoLength = videoLength;
    }

    public String getVideoUploadedBy() {
        return videoUploadedBy;
    }

    public void setVideoUploadedBy(String videoUploadedBy) {
        this.videoUploadedBy = videoUploadedBy;
    }

    public String getCaptionOfVideo() {
        return captionOfVideo;
    }

    public void setCaptionOfVideo(String captionOfVideo) {
        this.captionOfVideo = captionOfVideo;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    @Override
    public String toString() {
        return "FileEntity{" +
                "_id='" + _id + '\'' +
                ", fileName='" + fileName + '\'' +
                ", videoLength=" + videoLength +
                ", videoUploadedBy='" + videoUploadedBy + '\'' +
                ", titleOfVideo='" + titleOfVideo + '\'' +
                ", captionOfVideo='" + captionOfVideo + '\'' +
                ", uploadTime='" + uploadTime + '\'' +
                ", genreOfVideo='" + genreOfVideo + '\'' +
                ", likesOnVideo=" + likesOnVideo +
                ", dislikesOnVideo=" + dislikesOnVideo +
                '}';
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
