const mongoose = require('mongoose');

const schema = new mongoose.Schema({
    captionOfVideo : String ,
    videoUploadedBy : String ,
    uploadTime : String
});

module.exports = mongoose.model("fileEntity",schema,"fileEntity");