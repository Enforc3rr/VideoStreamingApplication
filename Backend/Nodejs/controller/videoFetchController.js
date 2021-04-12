/*
Title of the video
Video
*/
const mongodb = require("../database/videoModel");
const fs = require("fs");

exports.getVideos = async (req, res, next) =>{
    const videoData = await mongodb.find();
    const videoDetail = [];

    videoData.forEach(data =>{
        const {videoUploadedBy,captionOfVideo,uploadTime} = data;
        const video = {
            caption : captionOfVideo,
            uploadedBy : videoUploadedBy,
            uploadTime : uploadTime
        };
        videoDetail.push(video);
    });
    res.status(200).json(videoDetail);
    next();
};
/*
Header Contains -
ID of Video To be Played
Playback Resolution which will decide and request for the resolution of file .
*/
exports.playVideo = (req,res,next)=>{
    if(req.headers.playbackresolution==='480p'){
        playbackOptions(req,res);
    }else if(req.headers.playbackresolution==='1080p'){

    }else{

    }
};
const findFileName = async (_id)=>{
    const file = await mongodb.findById(_id);
    let anotherFile = {};
    anotherFile = file;
    log(anotherFile.)
}

const playbackOptions = (req,res)=>{
    const resolution = req.headers.playbackresolution;
    const range = req.headers.range;
    const fileName = findFileName(req.headers.idofvideo);
    // const videoPath = `D:\\Programs\\VideoStreamingApplication\\Backend\\VideoUploads
    // \\${resolution}\\${resolution}1618224406803-VID_20210411_160457.mp4`;
    const videoPath = "D:\\Programs\\VideoStreamingApplication\\Backend\\VideoUploads" +
        "\\480p\\480p1618227327075-VID_20210411_161628.mp4";
    const fileSize = fs.statSync(videoPath).size;
    let chunkSize = 0;
    if(resolution==='480p'){
        chunkSize = 10001;
    }else if(resolution === '1080p'){
        chunkSize = 100001;
    }else{
        chunkSize = 1001;
    }
    const start = Number(range.replace(/\D/g,""));
    const end = Math.min(chunkSize+start,fileSize-1);

    const contentLength = end - start + 1 ;
    const headers = {
        "Content-Range":`bytes ${start}-${end}/${fileSize}`,
        "Accept-Range":"bytes",
        "Content-Length":contentLength,
        "Content-Type":"video/mp4",
    };
    res.writeHead(206,headers);
    const videoStream = fs.createReadStream(videoPath,{"start":start ,"end":end});
    videoStream.pipe(res);
}