package com.videostream.app.entities;

import org.springframework.data.mongodb.core.mapping.Document;
/*
ID
File Name
Video Length
Uploaded By
Caption Associated With It
Upload Time
*/

@Document
public class FileEntity {
    private String fileName;
    private long videoLength;
    private String videoUploadedBy;
    private String captionOfVideo;
    private String uploadTime;

    public FileEntity(){ }

    public FileEntity(String fileName, long videoLength, String videoUploadedBy, String captionOfVideo, String uploadTime) {
        this.fileName = fileName;
        this.videoLength = videoLength;
        this.videoUploadedBy = videoUploadedBy;
        this.captionOfVideo = captionOfVideo;
        this.uploadTime = uploadTime;
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
                "fileName='" + fileName + '\'' +
                ", videoLength=" + videoLength +
                ", videoUploadedBy='" + videoUploadedBy + '\'' +
                ", captionOfVideo='" + captionOfVideo + '\'' +
                ", uploadTime='" + uploadTime + '\'' +
                '}';
    }
}
