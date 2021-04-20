const Express = require("express");

const router = Express.Router();

const {getVideos , playVideo , videoPlayBackEndPointTest } = require("../controller/videoFetchController");


router.route("/api/fetch")
    .get(getVideos);

router.route("/video/playback/:id")
    .get(playVideo);

//For PlaybackTesting Purpose
router.route("/video/test/:id")
    .get(videoPlayBackEndPointTest);


module.exports = router;