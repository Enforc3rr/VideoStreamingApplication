package com.videostream.app.controller;

import com.coremedia.iso.IsoFile;
import com.videostream.app.entities.FileEntity;
import com.videostream.app.entities.ResponseEntity;
import com.videostream.app.repository.FileRepo;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
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
    //video/upload

    public VideoUploadController(FileRepo fileRepo) {
        this.fileRepo = fileRepo;
    }

    @PostMapping(value = "/upload")
    public ResponseEntity videoUpload(@RequestParam("video")MultipartFile video) throws IOException{

       String videoStorage = "D:\\Programs\\VideoStreamingApplication\\Backend\\VideoUploads\\Temp";
       Date date = new Date();
       String fileName = date.getTime() + "-" +video.getOriginalFilename();

       video.transferTo(new File(videoStorage+fileName));

       IsoFile videoFile = new IsoFile(videoStorage+fileName);

       long lengthOfVideo = videoFile.getMovieBox().getMovieHeaderBox().getDuration()/videoFile
                .getMovieBox().getMovieHeaderBox().getTimescale();
       videoFile.close();

       if(lengthOfVideo >= 60){
           Files.deleteIfExists(Paths.get(videoStorage+video.getOriginalFilename()));
           return new ResponseEntity("Failed","File Not Uploaded"
                   ,"Enter A Video Less Than 60 Seconds",null,0,null);
       }else{
           return new ResponseEntity("Passed","File Uploaded",null
                   ,fileName,lengthOfVideo,date.toString());
       }
    }
    @PostMapping(value = "/details", consumes = "application/json")
    public void videoDetails(@RequestBody FileEntity fileEntity) throws IOException {

        this.fileRepo.save(new FileEntity(fileEntity.getFileName(),fileEntity.getVideoLength(),fileEntity.getVideoUploadedBy()
         , fileEntity.getCaptionOfVideo() , fileEntity.getUploadTime()));

        Thread conversionTo480p = new Thread(() -> {
            try {
                conversionTo480p(fileEntity.getFileName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Thread conversionTo1080p = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    conversionTo1080p(fileEntity.getFileName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread conversionTo240p = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    conversionTo240p(fileEntity.getFileName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        conversionTo240p.start();
        conversionTo480p.start();
        conversionTo1080p.start();
    }

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
}
/*
File Name
Video Length
Uploaded By
Caption Associated With It
Upload Time
*/


