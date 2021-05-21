package com.videostream.app.Responses;

public class VideoAvailableResponseClass {
    private String uploadStatus;
    private String availabilityStatus;
    private String message;

    public VideoAvailableResponseClass() {}

    public VideoAvailableResponseClass(String uploadStatus, String availabilityStatus, String message) {
        this.uploadStatus = uploadStatus;
        this.availabilityStatus = availabilityStatus;
        this.message = message;
    }

    @Override
    public String toString() {
        return "VideoAvailableResponseClass{" +
                "uploadStatus='" + uploadStatus + '\'' +
                ", availabilityStatus='" + availabilityStatus + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public String getUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(String uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
