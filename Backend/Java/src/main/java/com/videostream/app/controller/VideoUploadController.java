package com.videostream.app.controller;

import com.coremedia.iso.IsoFile;
import com.videostream.app.entities.FileEntity;
import com.videostream.app.entities.ThumbnailEntity;
import com.videostream.app.repository.ThumbnailRepo;
import com.videostream.app.repository.UserRepo;
import com.videostream.app.service.ResponseClass;
import com.videostream.app.repository.FileRepo;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

@RestController
@RequestMapping("/video")
public class VideoUploadController {

    final
    FileRepo fileRepo;
    final
    ThumbnailRepo thumbnailRepo;
    final
    UserRepo userRepo;

    public VideoUploadController(FileRepo fileRepo, UserRepo userRepo, ThumbnailRepo thumbnailRepo) {
        this.fileRepo = fileRepo;
        this.userRepo = userRepo;
        this.thumbnailRepo = thumbnailRepo;
    }

    /*
    @desc  To Upload Videos
    @route POST /video/upload
    @Response
    */
    @PostMapping(value = "/upload")
    public ResponseEntity<?> videoUpload(@RequestParam("video")MultipartFile video) throws IOException{

       String videoStorage = "D:\\Programs\\VideoStreamingApplication\\Backend\\VideoUploads\\Temp";
       Date date = new Date();
       String fileName = date.getTime() + "-" +video.getOriginalFilename();

       video.transferTo(new File(videoStorage+fileName));

       IsoFile videoFile = new IsoFile(videoStorage+fileName);

       long lengthOfVideo = videoFile.getMovieBox().getMovieHeaderBox().getDuration()/videoFile
                .getMovieBox().getMovieHeaderBox().getTimescale();
       videoFile.close();

       if(lengthOfVideo >= 120){
           Files.deleteIfExists(Paths.get(videoStorage+video.getOriginalFilename()));
           /*
           @StatusCode 400 ( Bad_Request )
           @Response If Upload Failed ( actual variable names are being used here){
           uploadStatus : Failed
           uploadMessage : File Not Uploaded
           uploadReason : Enter A Video Of Less Than 120 Seconds
           fileName : null
           videoLength : 0
           uploadTime : null
           }
            */
           return new ResponseEntity<>(new ResponseClass("Failed","File Not Uploaded"
                   ,"Enter A Video Of Less Than 120 Seconds",null,0,null),
                   HttpStatus.BAD_REQUEST);
       }else{
          /*
          @StatusCode 201 ( Created )
           @Response If Upload Is Successful (actual variable names are being used here)
           uploadStatus = Passed
           uploadMessage = File Uploaded
           uploadReason = null
           fileName = time at which video was uploaded in ms
           videoLength = length of video
           uploadTime = current time
            */
           return new ResponseEntity<>( new ResponseClass("Passed","File Uploaded"
                   ,null,fileName,lengthOfVideo,date.toString()),
                   HttpStatus.CREATED);
       }
    }
    /*
    @desc  If Upload Doesn't fail then call this end point to associate details of video with it.
    @route Post /video/details
    File Name
    Video Length
    Uploaded By
    Caption Associated With It
    Upload Time
    */
    @PostMapping(value = "/details", consumes = "application/json")
    public void videoDetails(@RequestBody FileEntity fileEntity) {

        this.fileRepo.save(new FileEntity(fileEntity.getFileName(),fileEntity.getVideoLength(),fileEntity.getVideoUploadedBy()
         , fileEntity.getCaptionOfVideo() , fileEntity.getUploadTime()));

        Thread conversionTo480p = new Thread(() -> {
            try {
                conversionTo480p(fileEntity.getFileName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Thread conversionTo1080p = new Thread(() -> {
            try {
                conversionTo1080p(fileEntity.getFileName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Thread conversionTo240p = new Thread(() -> {
            try {
                conversionTo240p(fileEntity.getFileName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        /*
        Different Methods have been used for different resolutions because in future i have planned to change
        the encoding and some other details for different resolutions.
         */
        conversionTo240p.start();
        conversionTo480p.start();
        conversionTo1080p.start();
    }
    //Static Method to Convert the Uploaded Video into 480p resolution
    private static void conversionTo480p(String fileName) throws IOException {
        FFmpeg fFmpeg = new FFmpeg("D:\\ffmpeg-4.4-essentials_build\\bin\\ffmpeg.exe");
        FFprobe fFprobe = new FFprobe("D:\\ffmpeg-4.4-essentials_build\\bin\\ffprobe.exe");
        FFmpegBuilder fFmpegBuilder = new FFmpegBuilder()
                .setInput("D:\\Programs\\VideoStreamingApplication\\Backend\\VideoUploads\\Temp"+fileName)
                .overrideOutputFiles(true)
                .addOutput("D:\\Programs\\VideoStreamingApplication\\Backend\\VideoUploads\\480p\\"+"480p"+fileName)
                .setAudioCodec("aac")
                .setVideoCodec("libx265")
                .setVideoFrameRate(30,1)
                .setVideoResolution(640,480)
                .done();
        FFmpegExecutor fFmpegExecutor = new FFmpegExecutor(fFmpeg,fFprobe);
        fFmpegExecutor.createJob(fFmpegBuilder).run();
    }
    //Static Method to Convert the Uploaded Video into 1080p resolution
    private static void conversionTo1080p(String fileName) throws IOException {
        FFmpeg fFmpeg = new FFmpeg("D:\\ffmpeg-4.4-essentials_build\\bin\\ffmpeg.exe");
        FFprobe fFprobe = new FFprobe("D:\\ffmpeg-4.4-essentials_build\\bin\\ffprobe.exe");
        FFmpegBuilder fFmpegBuilder = new FFmpegBuilder()
                .setInput("D:\\Programs\\VideoStreamingApplication\\Backend\\VideoUploads\\Temp"+fileName)
                .overrideOutputFiles(true)
                .addOutput("D:\\Programs\\VideoStreamingApplication\\Backend\\VideoUploads\\1080p\\"+"1080p"+fileName)
                .setAudioCodec("aac")
                .setVideoCodec("libx265")
                .setVideoFrameRate(30,1)
                .setVideoResolution(1920,1080)
                .done();
        FFmpegExecutor fFmpegExecutor = new FFmpegExecutor(fFmpeg,fFprobe);
        fFmpegExecutor.createJob(fFmpegBuilder).run();
    }
    //Static Method to Convert the Uploaded Video into 240p resolution
    private static void conversionTo240p(String fileName) throws IOException {
        FFmpeg fFmpeg = new FFmpeg("D:\\ffmpeg-4.4-essentials_build\\bin\\ffmpeg.exe");
        FFprobe fFprobe = new FFprobe("D:\\ffmpeg-4.4-essentials_build\\bin\\ffprobe.exe");
        FFmpegBuilder fFmpegBuilder = new FFmpegBuilder()
                .setInput("D:\\Programs\\VideoStreamingApplication\\Backend\\VideoUploads\\Temp"+fileName)
                .overrideOutputFiles(true)
                .addOutput("D:\\Programs\\VideoStreamingApplication\\Backend\\VideoUploads\\240p\\"+"240p"+fileName)
                .setAudioCodec("aac")
                .setVideoCodec("libx265")
                .setVideoFrameRate(30,1)
                .setVideoResolution(320,240)
                .done();
        FFmpegExecutor fFmpegExecutor = new FFmpegExecutor(fFmpeg,fFprobe);
        fFmpegExecutor.createJob(fFmpegBuilder).run();
    }
    /*
    @desc To Delete The Video
    @route DELETE /video/delete/id
    */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteVideo(@PathVariable("id") String videoId , Authentication authentication){
        FileEntity fileToDelete = this.fileRepo.findById(videoId).get();
//        ThumbnailEntity thumbnailToDelete = this.thumbnailRepo.findB
        if(fileToDelete.getVideoUploadedBy().equals(authentication.getName())){
            System.out.println("Entered");
            Thread toDelete240p = new Thread(()->{
                deleteVideos("240p",fileToDelete.getFileName());
            });
            Thread toDelete480p = new Thread(()->{
                deleteVideos("480p",fileToDelete.getFileName());
            });
            Thread toDelete1080p = new Thread(()->{
                deleteVideos("1080p",fileToDelete.getFileName());
            });
            //Name Of Thumbnail Matches the videoID.
//            Thread toDeleteThumbnail = new Thread(() -> {
//                deleteThumbnail(videoId);
//            });

            //Starting The Threads.
            toDelete240p.start();
            toDelete480p.start();
            toDelete1080p.start();
//            toDeleteThumbnail.start();
            this.fileRepo.deleteByFileName(fileToDelete.getFileName());
            return new ResponseEntity<>("Deletion Successful",HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>("File was Not Found or Has Already Been Deleted",HttpStatus.BAD_REQUEST);
    }
    private static void deleteVideos(String resolution,String fileName){
        String videoLocation = "D:\\Programs\\VideoStreamingApplication\\Backend\\VideoUploads\\"+resolution+"\\"+resolution+fileName;
        File videoToDelete = new File(videoLocation);
        videoToDelete.delete();
    }
    private static void deleteThumbnail(String thumbnailName){
        String thumbnailLocation = "D:\\Programs\\VideoStreamingApplication\\Backend\\ThumbnailUploads"+thumbnailName;
        File thumbnailToDelete = new File(thumbnailLocation);
        thumbnailToDelete.delete();
    }
}



