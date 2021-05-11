const Express = require("express");

const router = Express.Router();
const{findThumbnail} = require("../controller/thumbnailController");
const { getVideos , playVideo ,videoPlayBackEndPointTest , deleteVideo } = require("../controller/videoController");
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

// //CRUD operations on Video And Video Details
// router.route("/delete/:id")
//     .delete(verifyToken,deleteVideo);



//For PlaybackTesting Purpose
router.route("/video/test/:id")
    .post(videoPlayBackEndPointTest);


module.exports = router;