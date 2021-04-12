const mongoose = require('mongoose');

const database = async ()=>{
    await mongoose.connect("mongodb://127.0.0.1:27017/VideoStreamingApplication",{
        useNewUrlParser:true,
        useCreateIndex:true,
        useUnifiedTopology:true,
        useFindAndModify:true,
    });
    console.log(`Connected To Database`);
}

module.exports = database;