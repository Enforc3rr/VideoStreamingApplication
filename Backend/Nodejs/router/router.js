const Express = require("express");

const router = Express.Router();
const{findThumbnail} = require("../controller/thumbnailController");
const { getVideos , playVideo ,videoPlayBackEndPointTest , videoLikeIncrease, videoDisLikeIncrease,
    videoSearchByName } = require("../controller/videoController");
const {userRegistration , userLogin} = require("../controller/userController");
const verifyToken = require("../router/verifyToken");


router.route("/fetch")
    .get(getVideos);
router.route("/playback/:id")
    .get(verifyToken,playVideo);
router.route("/thumbnail/:id")
    .get(findThumbnail);


//User Registration
router.route("/user/registration")
    .post(userRegistration);
router.route("/user/login")
    .post(userLogin);

//Video Like-DisLike
router.route("/like/:id")
    .post(verifyToken,videoLikeIncrease);
router.route("/dislike/:id")
    .post(verifyToken,videoDisLikeIncrease);

//Searching Videos
router.route("/search/:search")
    .get(videoSearchByName);


//For PlaybackTesting Purpose
router.route("/test")
    .get(videoPlayBackEndPointTest);


module.exports = router;