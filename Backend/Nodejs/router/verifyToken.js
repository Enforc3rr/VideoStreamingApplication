const jwt = require("jsonwebtoken");
const userModel = require("../database/userModel");
const redis = require("redis");
const redisClient = redis.createClient(); // since I am using Locally Hosted Redis Server . SO , I don't need to specify URL
const requestIp = require("request-ip");

const verifyToken = async (req,res,next)=>{
    const requestTokenHeader = req.header("Authorization");
    //Checking For Availability of Token
    if(!requestTokenHeader) return res.status(401).json({message : "Not Authorization Token Found"});
    //Checking For Formatting Of Token .
    if(!requestTokenHeader.startsWith("Bearer ")) return res.status(401).json({message : "Token does not start with proper format"});
    try{
        //Extracting Actual Token
        const token = requestTokenHeader.substring(7);
        //verifying Details

        //Getting IP Stored in Database For Corresponding username
        redisClient.get(jwtDetails.sub, async(error,ip)=>{
            //checking if stored IP is same as the one in database if that's the case then verify the token
            if(ip===requestIp.getClientIp(req)){
                const jwtDetails = await jwt.verify(token , Buffer.from("secretkey", 'base64'));
                req.user = await userModel.findOne({
                    username : jwtDetails.sub
                });
            }else{
                return res.status(400).json({
                   success : false ,
                   message : "Login Device/Location Has Been Changed , Please Login Again"
                });
            }
        });

        next();
    }catch (e) {
        console.log(e);
        return res.status(400).json({message : "Invalid Token"});
    }
}

module.exports = verifyToken;