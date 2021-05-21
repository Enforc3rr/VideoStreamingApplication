const mongoose = require('mongoose');

const schema = new mongoose.Schema({
    fileName : String ,
    _id : mongoose.ObjectId ,
    titleOfVideo : String ,
    captionOfVideo : String ,
    videoUploadedBy : String ,
    genreOfVideo : String ,
    uploadTime : String ,
    likesOnVideo : Number,
    dislikesOnVideo : Number
});

module.exports = mongoose.model("fileEntity",schema,"fileEntity");