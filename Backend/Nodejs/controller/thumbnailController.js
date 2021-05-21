const mongodb  = require("../database/thumbnailModel");

exports.findThumbnail = (req,res,next)=>{
    mongodb.findOne({
        videoID : req.params.id
    })
        .then((data)=>{
            //We are basically now converting the object received into Object form ( Express.Json() is used for conversion of object received via JSON
            data = data.toObject();
            const {thumbnailName,thumbnailExtension} = data;
            res.download("D:\\Programs\\VideoStreamingApplication\\Backend\\ThumbnailUploads\\"+thumbnailName+"."+thumbnailExtension);
        })
        .catch(()=>{
            res.status(400).json({
                message : "couldn't load Thumbnail"
            });
        });
}