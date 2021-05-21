package com.videostream.app.Responses;

import org.springframework.data.mongodb.core.mapping.Document;

/*
Failed Upload ->
Upload Status = Failed
Message = File Not Uploaded
Reason = Size/Time Exceeded.
Video Name = Null

Upload Status Done ->
status = Passed
Message = File Uploaded
Reason = Null
Video Name = Renamed Version Of The Video
*/

@Document
public class UploadResponseClass {
    private String uploadStatus;
    private String uploadMessage;
    private String uploadReason;
    private String fileName;
    private long videoLength;
    private String UploadTime;

    public UploadResponseClass(){
    }
    public UploadResponseClass(String uploadStatus, String uploadMessage, String uploadReason,
                               String fileName, long videoLength, String uploadTime) {
        this.uploadStatus = uploadStatus;
        this.uploadMessage = uploadMessage;
        this.uploadReason = uploadReason;
        this.fileName = fileName;
        this.videoLength = videoLength;
        this.UploadTime = uploadTime;
    }
    public UploadResponseClass(String uploadStatus, String uploadMessage, String uploadReason, String fileName) {
        this.uploadStatus = uploadStatus;
        this.uploadMessage = uploadMessage;
        this.uploadReason = uploadReason;
        this.fileName = fileName;
    }

    public long getVideoLength() {
        return videoLength;
    }

    public void setVideoLength(long videoLength) {
        this.videoLength = videoLength;
    }

    public String getUploadTime() {
        return UploadTime;
    }

    public void setUploadTime(String uploadTime) {
        UploadTime = uploadTime;
    }

    public String getVideoName() {
        return fileName;
    }

    public void setVideoName(String videoName) {
        this.fileName = videoName;
    }

    public String getUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(String uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public String getUploadMessage() {
        return uploadMessage;
    }

    public void setUploadMessage(String uploadMessage) {
        this.uploadMessage = uploadMessage;
    }

    public String getUploadReason() {
        return uploadReason;
    }

    public void setUploadReason(String uploadReason) {
        this.uploadReason = uploadReason;
    }

    @Override
    public String toString() {
        return "ResponseEntity{" +
                "uploadStatus='" + uploadStatus + '\'' +
                ", uploadMessage='" + uploadMessage + '\'' +
                ", uploadReason='" + uploadReason + '\'' +
                ", fileName='" + fileName + '\'' +
                ", videoLength=" + videoLength +
                ", UploadTime='" + UploadTime + '\'' +
                '}';
    }
}
