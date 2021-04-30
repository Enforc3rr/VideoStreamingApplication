const mongoose = require("mongoose");
/*
username*
password
first name
last name
email*
date of joining
country where user belongs to
role
*/
const schema = new mongoose.Schema({
    username : {
        type : String ,
        required : true ,
        min : 5 ,
        unique : true
    },
    password : {
        type : String ,
        required : true ,
        min : 5
    },
    email : {
        type : String ,
        required : true ,
        unique : true
    },
    firstName : {
        type : String ,
        required : true
    },
    lastName : {
        type : String ,
        required : true
    },
    countryOfUser : {
        type : String ,
        required : true
    },
    dateOfUserJoining : {
        type : Date ,
        default : Date.now
    },
    role : {
        type : String ,
        default : "user"
    }
});

module.exports = mongoose.model("userEntity",schema,"userEntity");
