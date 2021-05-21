const bcrypt = require("bcryptjs");
const jwt = require("jsonwebtoken");
const userModel = require("../database/userModel");
const {userRegistrationValidation} = require("../configurations/userValidation");
/*
@desc For User Registration
@port 8000
@route POST /video/user/registration
@model
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
/*
@desc For User Login
@port 8000
@route POST /video/user/login
*/
exports.userLogin = async (req,res,next)=>{
    const userFound  = await userModel.findOne({username : req.body.username});
    /*
        @Response if Username is not found {
        status : Failed ,
        message : "User not Found",
        successfulLogin : false
        }
     */
   if(!userFound) return res.status(400).json({
       status : "Failed",
       message : "User Not Found",
       successfulLogin : false
   });
    /*
         @Response if password is not correct {
         status : Failed ,
         message : "Password Incorrect",
         successfulLogin : false
         }
      */
   const validatePassword = await bcrypt.compare(req.body.password , userFound.password);
   if(!validatePassword) return res.status(400).json({
       status : "Failed",
       message : "Incorrect Password",
       successfulLogin : false
   });
   const token = await jwt.sign({
       // userID : userFound._id ,
       sub : userFound.username ,
       role : userFound.role
   }, //We basically need to set the sign key in base64 encoding so that it can work with spring boot as spring expects the secret-key to be in that encoding instead of normal UTF-8
       Buffer.from("secretkey", 'base64'),
       {expiresIn: "24h"}
   );// need to replace "secret-key" with process.env.SECRET_KEY and it should of longer length and should be more complex.

   //Made Changes in the sign method and removed the line which put "typ" : "JWT" in the header of the final jwt to make it compatible with spring.
    /*
    @Response if User is found {
    jwt : token ,
    successfulLogin : true
    }
    */
   res.header("Authorization",token).json({
       jwt : token ,
       successfulLogin : true
   });
}