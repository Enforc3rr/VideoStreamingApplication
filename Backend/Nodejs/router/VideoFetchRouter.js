const Express = require("express");

const router = Express.Router();
const{findThumbnail} = require("../controller/thumbnailFetchController");
const { getVideos , playVideo ,videoPlayBackEndPointTest } = require("../controller/videoFetchController");
const {userRegistration , userLogin} = require("../controller/userController");


router.route("/fetch")
    .get(getVideos);

router.route("/playback/:id")
    .get(playVideo);

router.route("/thumbnail/:id")
    .get(findThumbnail);


//User Registration
router.route("/user/registration")
    .post(userRegistration);
router.route("/user/login")
    .post(userLogin);



//For PlaybackTesting Purpose
router.route("/video/test/:id")
    .post(videoPlayBackEndPointTest);


module.exports = router;