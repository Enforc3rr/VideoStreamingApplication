/*
@Desc To Fetch Video Names
@port 8000
@route GET /video/fetch
Id Of Video
File Name
Title of the video
Caption of the video
Genre of the video
Uploaded By
Upload Time
*/
const mongodb = require("../database/videoModel");
const fs = require("fs");

exports.getVideos = async (req, res, next) =>{
    const videoData = await mongodb.find();
    const videoDetail = [];

    videoData.forEach(data =>{
        const {videoUploadedBy,captionOfVideo,uploadTime , _id,fileName,titleOfVideo,genreOfVideo} = data;
        const video = {
            _id : _id ,
            fName : fileName ,
            title : titleOfVideo,
            caption : captionOfVideo,
            genre : genreOfVideo ,
            uploadedBy : videoUploadedBy,
            uploadTime : uploadTime
        };
        videoDetail.push(video);
    });
    res.status(200).json(videoDetail);
    next();
};
/*
@desc  Streaming API
@port  8000
@route GET video/playback/:id

@Header Contains -
Range :bytes=0-
Playback Resolution which will decide and request for the resolution of file . (playbackresolution)
*/
exports.playVideo = async (req,res,next)=>{
    const playbackResolution = req.headers.playbackresolution;
    const range = req.headers.range;
    let flName = await findFileName(req.params.id);
    if(!range){
        return  res.status(400).json({message:"Range In Header Is Required"});
    }
    if(!playbackResolution){
       return  res.status(400).json({message:"Playback Resolution In Header Is Required"});
    }
    let chunkSize = 100001 ;
    /*
    Range = bytes=1234-
    Initial Value is bytes=0-
     */
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

/*
@desc  To Increase The Like On The Selected Video
@port  8000
@route POST video/:id
*/
exports.videoLikeIncrease = (req,res,next)=>{
    mongodb.updateOne({_id : req.params.id},{$inc : {likesOnVideo : 1}});
    return res.status(200).json({message : `${req.params.id} Video Liked`});
}

/*
@desc  To Increase The Like On The Selected Video
@port  8000
@route POST video/:id
*/
exports.videoDisLikeIncrease = async (req,res,next)=>{
    await mongodb.updateOne({_id : req.params.id},{$inc : {dislikesOnVideo : 1}});
    return res.status(200).json({message : `${req.params.id} Video Dis-Liked`});
}
/*
@desc To search Videos based on content present in their title
@port 8000
@route GET video/search/:search
*/
exports.videoSearchByName = async (req,res,next)=>{
    const searchResult = [];
    //the i option perform a case-insensitive match
    const videosBySearch = await mongodb.find({titleOfVideo : {$regex : req.params.search , $options:"i"}});
    videosBySearch.forEach(data => {
        data = data.toObject();
        const {_id , videoUploadedBy , titleOfVideo , genreOfVideo} = data;
        const modifiedData = {
            _id,
            uploadedBy:  videoUploadedBy,
            title : titleOfVideo ,
            genre : genreOfVideo
        }
        searchResult.push(modifiedData);
    });
    return res.status(200).json(searchResult);
}

//In Test.html file make sure to change the id in the src attribute.
exports.videoPlayBackEndPointTest = (req,res,next)=>{
    res.sendFile("D:\\Programs\\VideoStreamingApplication\\Backend\\Nodejs\\VideoPlayBackEndPointTest\\"+"Test.html");
}