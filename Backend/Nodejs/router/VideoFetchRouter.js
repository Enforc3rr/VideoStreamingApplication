const Express = require("express");

const router = Express.Router();

const {getVideos , playVideo , video} = require("../controller/videoFetchController");


router.route("/api/fetch")
    .get(getVideos);

router.route("/video/playback")
    .get(playVideo);


module.exports = router;