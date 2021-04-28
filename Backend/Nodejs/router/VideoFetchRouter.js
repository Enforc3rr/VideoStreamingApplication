const Express = require("express");

const router = Express.Router();
const{findThumbnail} = require("../controller/thumbnailFetchController");
const { getVideos , playVideo ,videoPlayBackEndPointTest } = require("../controller/videoFetchController");


router.route("/fetch")
    .get(getVideos);

router.route("/playback/:id")
    .get(playVideo);

router.route("/thumbnail/:id")
    .get(findThumbnail);

//For PlaybackTesting Purpose
router.route("/video/test/:id")
    .get(videoPlayBackEndPointTest);


module.exports = router;