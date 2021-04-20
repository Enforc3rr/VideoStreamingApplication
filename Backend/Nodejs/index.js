const express = require('express');
const router = require('./router/VideoFetchRouter');
const logger = require("morgan");
const dbConfig = require("./configurations/databaseConfig");


const app = express();

dbConfig();
app.use(logger("combined"));
app.use(router);
app.use(express.json());


const PORT = 8000 || process.env.PORT;

app.listen(PORT, () => console.log(`Server Started On PORT ${PORT}`));

