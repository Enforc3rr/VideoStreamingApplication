package com.videostream.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.videostream.app.Responses.UploadResponseClass;
import com.videostream.app.entities.ThumbnailEntity;
import com.videostream.app.dao.ThumbnailRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/video")
public class ThumbnailUploadController {
    final
    ThumbnailRepo thumbnailRepo;

    public ThumbnailUploadController(ThumbnailRepo thumbnailRepo) {
        this.thumbnailRepo = thumbnailRepo;
    }

    @PostMapping(value = "/thumbnailUpload")
    public ResponseEntity<?> thumbnailUpload(@RequestParam("json") String thumbnailString, @RequestParam("thumbnail") MultipartFile thumbnail) throws IOException {
        //De-serialize The JSON data which is received .
        ThumbnailEntity thumbnailEntity = new ObjectMapper().readValue(thumbnailString, ThumbnailEntity.class);
        String thumbnailStorage = "D:\\Programs\\VideoStreamingApplication\\Backend\\ThumbnailUploads\\";
        String thumbnailExtension = thumbnail.getOriginalFilename().split("\\.")[1];
        String thumbnailName = thumbnailEntity.getThumbnailName() + "." + thumbnailExtension;
        String tempThumbnailStorage = "D:\\Programs\\VideoStreamingApplication\\Backend\\ThumbnailUploads\\Temp\\" + thumbnailName;

        File thumbFile = new File(tempThumbnailStorage);
        thumbnail.transferTo(thumbFile);

        int width = 200;    //width of the image
        int height = 200;   //height of the image

        // For storing image in RAM
        BufferedImage bufferedImage = null;
        // READ IMAGE
        File unModifiedThumbnail = new File(tempThumbnailStorage); //image file path
        /* create an object of BufferedImage type and pass as parameter the width,  height and image int
           type.TYPE_INT_ARGB means that we are representing the Alpha, Red, Green and Blue component of the
           image pixel using 8 bit integer value.
        */
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        // Reading input file
        bufferedImage = ImageIO.read(unModifiedThumbnail);
        // WRITE IMAGE
        // Output file path
        File modifiedThumbnail = new File(thumbnailStorage+thumbnailName);
        // Writing to file taking type and path as
        ImageIO.write(bufferedImage, thumbnailExtension, modifiedThumbnail);

        this.thumbnailRepo.save(new ThumbnailEntity(thumbnailEntity.getThumbnailName(),
                thumbnailEntity.getVideoID(),thumbnailExtension));

        thumbFile.delete();

        return new ResponseEntity<>(new UploadResponseClass("Passed", "Thumbnail Uploaded", null, thumbnailEntity.getThumbnailName())
                , HttpStatus.CREATED);
    }
}
