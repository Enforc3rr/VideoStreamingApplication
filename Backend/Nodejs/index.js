const express = require('express');
const app = express();
const router = require('./router/router');
const logger = require("morgan");
const dbConfig = require("./configurations/databaseConfig");

//Middleware To Convert The Json Received to Object.
app.use(express.json());

//Calling Database Configuration Method To Connect Database.
dbConfig();

//Middleware To Log The Requests Received
app.use(logger('combined'))

//Middleware To Route The Requests
app.use("/video",router);


//Defining PORT at which server will start.
const PORT = 8000 || process.env.PORT;

//Starting The server on PORT
app.listen(PORT, () => console.log(`Server Started On PORT ${PORT}`));

