const express = require('express');
const app = express();
const router = require('./router/VideoFetchRouter');
const logger = require("morgan");
const dbConfig = require("./configurations/databaseConfig");

app.use(express.json());

dbConfig();

app.use(logger("combined"));
app.use("/video",router);



const PORT = 8000 || process.env.PORT;

app.listen(PORT, () => console.log(`Server Started On PORT ${PORT}`));

