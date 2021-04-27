package com.videostream.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.videostream.app.entities.ResponseEntity;
import com.videostream.app.entities.ThumbnailEntity;
import com.videostream.app.repository.ThumbnailRepo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@RestController
@RequestMapping("/video")
public class ThumbnailUploadController {
    final
    ThumbnailRepo thumbnailRepo;

    public ThumbnailUploadController(ThumbnailRepo thumbnailRepo) {
        this.thumbnailRepo = thumbnailRepo;
    }
    @PostMapping(value = "/thumbnailUpload")
    public ResponseEntity thumbnailUpload(@RequestParam("json") String thumbnailString,@RequestParam("thumbnail") MultipartFile thumbnail) throws IOException {
        //De-serialize The JSON data which is received .
        ThumbnailEntity thumbnailEntity = new ObjectMapper().readValue(thumbnailString,ThumbnailEntity.class);
        String tempThumbnailStorage = "D:\\Programs\\VideoStreamingApplication\\Backend\\ThumbnailUploads\\Temp\\";
        String thumbnailStorage = "D:\\Programs\\VideoStreamingApplication\\Backend\\ThumbnailUploads\\";
        String thumbnailExtension = thumbnail.getOriginalFilename().split("\\.")[1];
        String thumbnailName = thumbnailEntity.getThumbnailName()+"."+thumbnailExtension;

        File thumbFile = new File(tempThumbnailStorage+thumbnailName);

        thumbnail.transferTo(thumbFile);

        System.out.println(thumbnailEntity);
        Path sourceOfTempThumbnail = Paths.get(tempThumbnailStorage+thumbnailName);
        Path targetOfNewResolutionThumbnail = Paths.get(thumbnailStorage+thumbnailName);

        InputStream inputStream = new FileInputStream(sourceOfTempThumbnail.toFile());
        resize(inputStream,targetOfNewResolutionThumbnail,854,480,"."+thumbnailExtension);

        inputStream.close();


//        this.thumbnailRepo.save(new ThumbnailEntity(thumbnailEntity.getThumbnailName() , thumbnailEntity.getVideoID()));

        return new ResponseEntity("Passed","Thumbnail Uploaded",null,thumbnailEntity.getThumbnailName());

    }
    private static void resize(InputStream input, Path target,
                               int width, int height,String fileExtension) throws IOException {
        BufferedImage originalImage = ImageIO.read(input);
        Image newResizedImage = originalImage
                .getScaledInstance(width, height, Image.SCALE_SMOOTH);

        // we want image in png format
        ImageIO.write(convertToBufferedImage(newResizedImage),
                fileExtension, target.toFile());
    }
    public static BufferedImage convertToBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }
        // Create a buffered image with transparency
        BufferedImage bufferedImage = new BufferedImage(
                img.getWidth(null), img.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.drawImage(img, 0, 0, null);
        graphics2D.dispose();
        return bufferedImage;
    }
}
