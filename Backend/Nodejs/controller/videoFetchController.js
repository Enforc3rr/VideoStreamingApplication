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
        const {videoUploadedBy,captionOfVideo,uploadTime , _id,fileName} = data;
        const video = {
            id : _id ,
            fName : fileName ,
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
Range :bytes=0-
Playback Resolution which will decide and request for the resolution of file .
*/
exports.playVideo = async (req,res,next)=>{
    const playbackResolution = req.headers.playbackresolution;
    // const playbackResolution = "1080p";
    const range = req.headers.range;
    let flName = await findFileName(req.params.id);
    if(!range){
        res.status(400).json({message:"Range In Header Is Required"});
    }
    if(!playbackResolution){
        res.status(400).json({message:"Playback Resolution In Header Is Required"});
    }
    let chunkSize = 0 ;
    if(playbackResolution==="480p"){
        chunkSize = 10001;
    }else if(playbackResolution==="1080p"){
        chunkSize = 100001;
    }else{
        chunkSize = 10001;
    }
    //Range = bytes=1234-
    const videoPath = `D:\\Programs\\VideoStreamingApplication\\Backend\\VideoUploads\\${playbackResolution}\\${playbackResolution}${flName}`;
    const videoSize = fs.statSync(videoPath).size;


    const start = Number(range.replace(/\D/g,""));
    const end = Math.min(chunkSize+start,videoSize-1);

    const contentLength = end - start + 1 ;

    const headers = {
        "Content-Range":`bytes ${start}-${end}/${videoSize}`,
        "Accept-Range":"bytes",
        "Content-Length":contentLength,
        "Content-Type":"video/mp4",
    };
    res.writeHead(206,headers);
    const videoStream = fs.createReadStream(videoPath,{"start":start ,"end":end});
    videoStream.pipe(res);
};
const findFileName = async (_id)=>{
    const fName = await mongodb.findById(_id);
    return fName.fileName;
}
exports.videoPlayBackEndPointTest = (req,res,next)=>{
    res.sendFile("D:\\Programs\\VideoStreamingApplication\\Backend\\Nodejs\\VideoPlayBackEndPointTest\\"+"Test.html");
}