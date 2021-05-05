const bcrypt = require("bcryptjs");
const jwt = require("jsonwebtoken");
const userModel = require("../database/userModel");
const {userRegistrationValidation} = require("../configurations/userValidation");
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
exports.userRegistration = async (req,res,next)=>{

    const {error} = userRegistrationValidation(req.body);
    if(error) return res.status(400).json({message : error.details[0].message});

    const username = req.body.username;

    const salt = await bcrypt.genSalt(10);
    const hashedPassword = await bcrypt.hash(req.body.password , salt);
    const user = new userModel({
        username : username ,
        password : hashedPassword,
        email : req.body.email ,
        firstName : req.body.firstName,
        lastName : req.body.lastName ,
        countryOfUser : req.body.countryOfUser,
        date : Date.now()
    });
    await user.save();
/*
status = Passed
Message = User Registered
username
*/
    return res.status(201).json({
        status : "Passed",
        Message : "User Registered",
        username
    })
}
exports.userLogin = async (req,res,next)=>{
    console.log(req.body.username);
   const userFound  = await userModel.findOne({username : req.body.username});

   if(!userFound) return res.status(400).json({
       status : "Failed",
       message : "User Not Found",
       successfulLogin : false
   });
   const validatePassword = await bcrypt.compare(req.body.password , userFound.password);
   if(!validatePassword) return res.status(400).json({
       status : "Failed",
       message : "Incorrect Password",
       successfulLogin : false
   });
   const token = jwt.sign({
       userID : userFound._id ,
       username : userFound.username ,
       role : userFound.role
   },"SecretKey");
   res.header("Authorization",token).json({
       jwt : token ,
       successfulLogin : true
   });
}