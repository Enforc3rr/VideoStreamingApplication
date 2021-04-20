const mongoose = require('mongoose');

const schema = new mongoose.Schema({
    fileName : String ,
    videoId : String ,
    captionOfVideo : String ,
    videoUploadedBy : String ,
    uploadTime : String
});

module.exports = mongoose.model("fileEntity",schema,"fileEntity");